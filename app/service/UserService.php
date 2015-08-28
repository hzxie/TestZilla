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
     * @return an expected user object or NULL
     */
    public function getUserUsingUid($uid) {
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
            }
        }
        return $result;
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
                $this->sendVerificationEmail($username, $email);
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
     * @param  String $username - the username of the user
     * @param  String $email    - the email of the user
     * @return whether the mail is successfully sent
     */
    private function sendVerificationEmail($username, $email) {
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

        $subject            = 'Reset Your Password';
        $templateName       = 'resetPassword';
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
}