<?php

use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;

/**
 * The bussiness layer for accounts.
 *
 * @package TestZilla\Service\UserService
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class UserService extends Service {
    /**
     * Initialize the Service
     */
    public function initialize() {
        parent::initialize();
    }

    /**
     * Get user object using the unique ID of the user.
     * @param  long $uid - the unique ID of the user
     * @return an array contains infomration of the user
     */
    public function getUserUsingUid($uid) {
        $rowSet = $this->getUserObjectUsingUid($uid);

        if ( $rowSet == NULL ) {
            return $NULL;
        }
        return array(
            'uid'       => $rowSet->getUid(),
            'username'  => $rowSet->getUsername(),
            'email'     => $rowSet->getEmail(),
            'userGroup' => array(
                'userGroupId'   => $rowSet->getUserGroup()->getUserGroupId(),
                'userGroupSlug' => $rowSet->getUserGroup()->getUserGroupSlug(),
                'userGroupName' => $rowSet->getUserGroup()->getUserGroupName(),
            ),
        );
    }

    /**
     * Get user object using the unique ID of the user.
     * @param  long $uid - the unique ID of the user
     * @return an expected user object or NULL
     */
    public function getUserObjectUsingUid($uid) {
        return User::findFirst(array(
            'conditions'    => 'uid = ?1',
            'bind'          => array(
                1           => $uid,
            ),
        ));
    }

    /**
     * Get User object using username or email.
     * @param  String $username - the username or email of the user
     * @return an expected user object or NULL
     */
    public function getUserUsingUsernameOrEmail($username) {
        $isUsingEmail = strpos($username, '@');
        $user         = NULL;

        if ( $isUsingEmail === false ) {
            $user     = $this->getUserUsingUsername($username);
        } else {
            $user     = $this->getUserUsingEmail($username);
        }
        return $user;
    }

    /**
     * Get User object using username.
     * @param String $username - the username of the user
     * @return an expected user object or NULL
     */
    public function getUserUsingUsername($username) {
        $user = User::findFirst(array(
            'conditions'    => 'username = ?1',
            'bind'          => array(
                1           => $username,
            ),
        ));
        return $user;
    }

    /**
     * Get User object using email.
     * @param String $email - the email of the user
     * @return an expected user object or NULL
     */
    public function getUserUsingEmail($email) {
        $user = User::findFirst(array(
            'conditions'    => 'email = ?1',
            'bind'          => array(
                1           => $email,
            ),
        ));
        return $user;
    }

    /**
     * Get the meta data of a user.
     * @param  long  $uid - the unique ID of the user
     * @return an array contains all meta data of the user
     */
    public function getUserMetaUsingUid($uid) {
        $userMeta       = array();
        $resultSet      = UserMeta::find(array(
            'conditions'    => 'uid = ?1',
            'bind'          => array(
                1           => $uid,
            ),
        ));

        foreach ( $resultSet as $rowSet ) {
            $key        = $rowSet->getMetaKey();
            $value      = $rowSet->getMetaValue();

            if ( $key == 'socialLinks' ) {
                $value  = (array)json_decode($value);
            }
            $userMeta   = array_merge($userMeta, array(
                $key  => $value
            ));
        }
        return $userMeta;
    }

    /**
     * Verify if the username and password is correct.
     * @param  String  $username - the username of the user
     * @param  String  $password - the password of the user
     * @return an array with data validation result
     */
    public function isAccountValid($username, $password) {
        $result                 = array(
            'isSuccessful'      => false,
            'isUsernameEmpty'   => empty($username),
            'isPasswordEmpty'   => empty($password),
            'isAccountValid'    => false,
        );

        if ( !$result['isUsernameEmpty'] && !$result['isPasswordEmpty'] ) {
            $user = $this->getUserUsingUsernameOrEmail($username);

            if ( $user != null && $user->getPassword() == $password ) {
                $result['isSuccessful']   = true;
                $result['isAccountValid'] = true;
            }
        }
        return $result;
    }

    /**
     * Verify the data and create the account.
     * @param  String $username - the username of the user
     * @param  String $password - the password of the user
     * @param  String $email - the email of the user
     * @param  boolean $isTokenValid - the CSRF token is valid
     * @return an array with data validation result
     */
    public function createAccount($username, $password, $email, $isTokenValid) {
        $result                 = array(
            'isSuccessful'      => false,
            'isUsernameEmpty'   => empty($username),
            'isUsernameLegal'   => $this->isUsernameLegal($username),
            'isUsernameExists'  => $this->isUsernameExists($username),
            'isPasswordEmpty'   => empty($password),
            'isPasswordLegal'   => $this->isPasswordLegal($password),
            'isEmailEmpty'      => empty($email),
            'isEmailLegal'      => $this->isEmailLegal($email),
            'isEmailExists'     => $this->isEmailExists($email),
            'isTokenValid'      => $isTokenValid,
        );
        $result['isSuccessful'] = !$result['isUsernameEmpty']  &&  $result['isUsernameLegal'] &&
                                  !$result['isUsernameExists'] && !$result['isPasswordEmpty'] &&
                                   $result['isPasswordLegal']  && !$result['isEmailEmpty']    &&
                                   $result['isEmailLegal']     && !$result['isEmailExists']   &&
                                   $result['isTokenValid'];
        
        if ( $result['isSuccessful'] ) {
            $defaultUserGroup   = UserGroup::findFirst("user_group_slug = 'user'");
            $user               = new User();
            $user->setUsername($username);
            $user->setPassword(md5($password));
            $user->setEmail($email);
            $user->setUserGroup($defaultUserGroup);
            
            if ( !$user->create() ) {
                $result['isSuccessful']      = false;
            } else {
                $this->updateUserMeta($user, 'registerTime', date('Y-m-d H:i:s'));
                $this->sendVerificationEmail('Welcome to TestZilla', 'HelloMessage', $username, $email);
            }
        }
        return $result;
    }

    /**
     * Change password for user logged in.
     * @param  User   $user            - the user who wants to change password
     * @param  String $oldPassword     - the password the user used now
     * @param  String $newPassword     - the new password
     * @param  String $confirmPassword - the confirmation of the new password
     * @return an array contains data infer whether the password is changed
     */
    public function changePassword($user, $oldPassword, $newPassword, $confirmPassword) {
        $result     = array(
            'isSuccessful'              => false,
            'isUserLoggedIn'            => $user != NULL,
            'isOldPasswordCorrect'      => $user->getPassword() == md5($oldPassword),
            'isNewPasswordEmpty'        => empty($newPassword),
            'isNewPasswordLegal'        => $this->isPasswordLegal($newPassword),
            'isConfirmPasswordMatched'  => $newPassword == $confirmPassword,
        );
        $result['isSuccessful'] =  $result['isUserLoggedIn']     && $result['isOldPasswordCorrect'] &&
                                  !$result['isNewPasswordEmpty'] && $result['isNewPasswordLegal']   &&
                                   $result['isConfirmPasswordMatched'];

        if ( $result['isSuccessful'] ) {
            $user->setPassword(md5($newPassword));
            if ( !$user->update() ) {
                $result['isSuccessful'] = false;
            }
        }
        return $result;
    }

    /**
     * Update profile of user.
     * @param  User   $user        - the user who wants to update profile
     * @param  String $email       - the email of the user
     * @param  String $location    - the location of the user
     * @param  String $website     - the website of the user
     * @param  String $socialLinks - the social links of the user (in JSON format)
     * @param  String $aboutMe     - the introcution of the user
     * @return an array contains information of updateing profile
     */
    public function updateProfile($user, $email, $location, $website, $socialLinks, $aboutMe) {
        $result = array(
            'isSuccessful'      => false,
            'isEmailEmpty'      => empty($email),
            'isEmailLegal'      => $this->isEmailLegal($email),
            'isEmailExists'     => $this->isEmailExistsExceptCurrentUser($user, $email),
            'isLocationLegal'   => mb_strlen($location, 'utf-8') <= 128,
            'isWebsiteLegal'    => $this->isWebsiteLegal($website),
            'isAboutMeLegal'    => mb_strlen($aboutMe, 'utf-8') <= 256,
        );
        $result['isSuccessful'] = !$result['isEmailEmpty']   && $result['isEmailLegal']    &&
                                  !$result['isEmailExists']  && $result['isLocationLegal'] &&
                                   $result['isWebsiteLegal'] && $result['isAboutMeLegal'];

        if ( $result['isSuccessful'] ) {
            $user->setEmail($email);
            $user->update();

            $this->updateUserMeta($user, 'location', $location);
            $this->updateUserMeta($user, 'website', $website);
            $this->updateUserMeta($user, 'socialLinks', $socialLinks);
            $this->updateUserMeta($user, 'aboutMe', $aboutMe);
        }
        return $result;
    }

    /**
     * Update the meta data of the user.
     * @param  User  $user       - the user who wants to update meta data
     * @param  String $metaKey   - the key of the meta
     * @param  String $metaValue - the value of the meta
     */
    private function updateUserMeta($user, $metaKey, $metaValue) {
        $userMeta = UserMeta::findFirst(array(
            'conditions'    => 'uid = ?1 AND meta_key = ?2',
            'bind'          => array(
                1           => $user->getUid(),
                2           => $metaKey,
            ),
        ));

        if ( $userMeta == NULL ) {
            if ( empty($metaValue) ) {
                return;
            }
            $userMeta = new UserMeta();
            $userMeta->setUser($user);
            $userMeta->setMetaKey($metaKey);
            $userMeta->setMetaValue($metaValue);
            $userMeta->create();
        } else {
            $userMeta->setMetaValue($metaValue);
            $userMeta->update();
        }
    }

    /**
     * Verify if the username field is legal.
     * @param  String $username - the username of the user
     * @return if the username field is legal
     */
    private function isUsernameLegal($username) {
        return preg_match('/^[A-Za-z][A-Za-z0-9_]{5,15}$/', $username);
    }

    /**
     * Check if the username has been taken.
     * @param  String $username - the username of the user
     * @return if the username has been taken
     */
    private function isUsernameExists($username) {
        $user = User::findFirst(array(
            'conditions'    => 'username = ?1',
            'bind'          => array(
                1           => $username,
            ),
        ));
        return $user != NULL;
    }

    /**
     * Verify if the password field is legal.
     * @param  String $password - the password of the user
     * @return if the password field is legal
     */
    private function isPasswordLegal($password) {
        $passwordLength = mb_strlen($password, 'utf-8');
        return $passwordLength >= 6 && $passwordLength <= 16;
    }

    /**
     * Verify if the email field is legal.
     * @param  String $email - the email of the user
     * @return if the email field is legal
     */
    private function isEmailLegal($email) {
        return strlen($email) <= 64 && 
               preg_match('/^[A-Za-z0-9\._-]+@[A-Za-z0-9_-]+\.[A-Za-z0-9\._-]+$/', $email);
    }

    /**
     * Check if the email has been taken.
     * @param  String $email - the email of the user
     * @return if the email has been taken
     */
    private function isEmailExists($email) {
        $user = User::findFirst(array(
            'conditions'    => 'email = ?1',
            'bind'          => array(
                1           => $email,
            ),
        ));
        return $user != NULL;
    }

    /**
     * Check if the email has been taken by other users (except current user).
     * @param  User    $user  - current user
     * @param  String  $email - the email of the user wants to use
     * @return whether the email has been taken by other users
     */
    private function isEmailExistsExceptCurrentUser($user, $email) {
        if ( $user->getEmail() == $email ) {
            return false;
        }
        return $this->isEmailExists($email);
    }

    /**
     * Check if the URL of website is legal.
     * @param  String  $website - the URL of the website
     * @return whether the URL of the website is legal
     */
    private function isWebsiteLegal($website) {
        return strlen($website) <= 64 &&
               preg_match('/^(http|https):\/\/[A-Za-z0-9-]+\.[A-Za-z0-9_.]+$/', $website);
    }

    /**
     * Verify if the token and the email address is valid.
     * @param  String  $email - the email address to verify
     * @param  String  $token - the token used for verify the email
     * @return whether the token and the email address is valid
     */
    public function isEmailTokenValid($email, $token) {
        $emailVerification = EmailVerification::findFirst(array(
            'conditions'    => 'email = ?1',
            'bind'          => array(
                1           => $email,
            ),
        ));

        if ( $emailVerification != NULL && 
             $emailVerification->getToken() == $token &&
             strtotime($emailVerification->getExpireTime()) >= strtotime('now') ) {
            return true;
        }
        return false;
    }

    /**
     * Verify the username and email and send verification email.
     * @param  String  $username     - the username of the user
     * @param  String  $email        - the email of the user
     * @param  boolean $isTokenValid - the CSRF token is valid
     * @return an array which contains data infer whether the username and email exists
     */
    public function forgotPassword($username, $email, $isTokenValid) {
        $isUserExists   = false;
        if ( $isTokenValid && !empty($username) && !empty($email) ) {
            $user               = $this->getUserUsingEmail($email);
            if ( $user != NULL && 
                 $user->getUsername() == $username) {
                $isUserExists   = true;
                $this->sendVerificationEmail('Reset Your Password', 'ResetPassword', $username, $email);
            }
        }

        $result         = array(
            'isSuccessful'      => false,
            'isCsrfTokenValid'  => $isTokenValid,
            'isUserExists'      => $isUserExists,
        );
        $result['isSuccessful'] = $result['isCsrfTokenValid'] && $result['isUserExists'];

        return $result;
    }

    /**
     * Send the verification email to the user.
     * @param  String $subject      - the subject of the email
     * @param  String $templateName - the template name of the email
     * @param  String $username     - the username of the user
     * @param  String $email        - the email of the user
     * @return whether the mail is successfully sent
     */
    private function sendVerificationEmail($subject, $templateName, $username, $email) {
        $token              = uniqid();
        $tomorrowDateTime   = date('Y-m-d H:i:s', strtotime('+1 day'));

        $emailVerification  = EmailVerification::findFirst(array(
            'conditions'    => 'email = ?1',
            'bind'          => array(
                1           => $email,
            ),
        ));
        if ( $emailVerification != NULL ) {
            $emailVerification->delete();
        }

        $emailVerification  = new EmailVerification();
        $emailVerification->setEmail($email);
        $emailVerification->setToken($token);
        $emailVerification->setExpireTime($tomorrowDateTime);
        $emailVerification->create();

        $parameters         = array(
            'username'      => $username,
            'email'         => $email,
            'token'         => $token,
        );
        return $this->mailSender->sendMail($email, $subject, $templateName, $parameters);
    }

    /**
     * Reset the password if the email and token is correct.
     * @param  String  $email           - the email address to verify
     * @param  String  $token           - the token used for verify the email
     * @param  String  $newPassword     - the new password
     * @param  String  $confirmPassword - the confirmation of the new password
     * @param  boolean $isTokenValid    - the CSRF token is valid
     * @return an array which contains data infer whether the password is reset
     */
    public function resetPassword($email, $token, $newPassword, $confirmPassword, $isTokenValid) {
        $isEmailTokenValid      = false;
        if ( $isTokenValid ) {
            $emailVerification  = EmailVerification::findFirst(array(
                'conditions'    => 'email = ?1 AND token = ?2 AND expire_time >= ?3',
                'bind'          => array(
                    1           => $email,
                    2           => $token,
                    3           => date('Y-m-d H:i:s', strtotime('now')),
                ),
            ));

            if ( $emailVerification != NULL ) {
                $isEmailTokenValid = true;
            }
        }

        $result                 = array(
            'isSuccessful'              => false,
            'isCsrfTokenValid'          => $isTokenValid,
            'isEmailTokenValid'         => $isEmailTokenValid,
            'isNewPasswordEmpty'        => empty($newPassword),
            'isNewPasswordLegal'        => $this->isPasswordLegal($newPassword),
            'isConfirmPasswordMatched'  => $newPassword == $confirmPassword,
        );
        $result['isSuccessful'] =  $result['isCsrfTokenValid']   && $result['isEmailTokenValid']  && 
                                  !$result['isNewPasswordEmpty'] && $result['isNewPasswordLegal'] && 
                                   $result['isConfirmPasswordMatched'];

        if ( $result['isSuccessful'] ) {
            $user = $this->getUserUsingEmail($email);
            $user->setPassword(md5($newPassword));

            if ( !$user->update() ) {
                $result['isSuccessful'] = false;
            }
        }
        return $result;
    }

    /**
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}