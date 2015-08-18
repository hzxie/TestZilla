<?php

use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;

/**
 * The bussiness layer for AccountsController.
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
        $logDir            = $this->config->application->logDir;
        $this->logger      = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Get user object using the unique ID of the user.
     * @param  long $uid - the unique ID of the user
     * @return an expected user object or NULL
     */
    public function getUserUsingUid($uid) {
        return User::findFirst("uid = '$uid'");
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
            $user = User::findFirst("username = '$username'");
        } else {
            $user = User::findFirst("email = '$username'");
        }
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
            'isQuerySuccessful' => false,
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
            $defaultUserGroup   = UserGroup::findFirst("user_group_slug = 'student'");
            $user               = new User();
            $user->setUsername($username);
            $user->setPassword(md5($password));
            $user->setEmail($email);
            $user->setUserGroup($defaultUserGroup);
            
            if ( !$user->create() ) {
                $result['isSuccessful'] = false;
                foreach ( $user->getMessages() as $message ) {
                    $this->logger->log('[Service\UserService] Error occurred while creating user.', Logger::ERROR);
                    $this->logger->log('[Service\UserService] ' . $message, Logger::ERROR);
                }
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
        $user = User::findFirst("username = '$username'");
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
        $user = User::findFirst("email = '$email'");
        return $user != NULL;
    }

    /**
     * The logger of UserService.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}