<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:30:54
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Mvc\Model;

/**
 * The email validation records of the application.
 *
 * @package TestZilla\model\EmailVerification
 */
class EmailVerification extends Model {
    /**
     * Initialize this model.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
    }

    /**
     * The getter of the field email.
     * @return the email address of the account
     */
    public function getEmail() {
        return $this->email;
    }

    /**
     * The setter of the field email.
     * @param String $email - the email address of the account
     */
    public function setEmail($email) {
        if ( mb_strlen($email, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\EmailVerification] The length of email CANNOT exceed 64 characters.');
        }
        $this->email = $email;
    }

    /**
     * The getter of the field token.
     * @return the token used to verify the email
     */
    public function getToken() {
        return $this->token;
    }

    /**
     * The setter of the field token.
     * @param String $token - the token used to verify the email
     */
    public function setToken($token) {
        if ( mb_strlen($token, 'utf-8') > 36 ) {
            throw new InvalidArgumentException('[Model\EmailVerification] The length of token CANNOT exceed 36 characters.');
        }
        $this->token = $token;
    }

    /**
     * The getter of the field expire_time.
     * @return the time when the token becomes invalid
     */
    public function getExpireTime() {
        return $this->expire_time;
    }

    /**
     * The setter of the field expire_time.
     * @param DateTime $expireTime - the time when the token becomes invalid
     */
    public function setExpireTime($expireTime) {
        $this->expire_time = $expireTime;
    }

    /**
     * Get the description of the EmailVerification object.
     * @return the description of the EmailVerification object
     */
    public function __toString() {
        return sprintf('EmailVerification: [Email=%s, Token=%s, ExpireTime=%s]', 
                $this->email, $this->token, $this->expire_time);
    }

    /**
     * The email address of the account.
     * @var String
     */
    protected $email;

    /**
     * The token used to verify the email.
     * @var String
     */
    protected $token;

    /**
     * The time when the token becomes invalid.
     * @var DateTime
     */
    protected $expire_time;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_mail_verification';
}