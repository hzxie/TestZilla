<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:53:19
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 12:53:51
 */
use Phalcon\Mvc\Model;

/**
 * The users in the application.
 *
 * @package TestZilla\model\Model\User
 * @author Haozhe Xie <cshzxie@gmail.com>
 */
class User extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
        $this->belongsTo('user_group_id', 'UserGroup', 'user_group_id');
    }
    
    /**
     * The getter of the field uid.
     * @return the unique ID of the user.
     */
    public function getUid() {
        return $this->uid;
    }

    /**
     * The setter of the field uid.
     * @param long $uid - the unique ID of the user
     */
    public function setUid($uid) {
        $this->uid = $uid;
    }

    /**
     * The getter of the field username.
     * @return the username of the user
     */
    public function getUsername() {
        return $this->username;
    }

    /**
     * The setter of the field username.
     * @param String $username - the username of the user
     */
    public function setUsername($username) {
        if ( mb_strlen($username, 'utf-8') > 32 ) {
            throw new InvalidArgumentException('[Model\User] The length of username CANNOT exceed 32 characters.');
        }
        $this->username = $username;
    }

    /**
     * The getter of the field password.
     * @return the password of the user(MD5 encrypted)
     */
    public function getPassword() {
        return $this->password;
    }

    /**
     * The setter of the field password.
     * @param String $password = the password of the user(MD5 encrypted)
     */
    public function setPassword($password) {
        if ( mb_strlen($password, 'utf-8') != 32 ) {
            throw new InvalidArgumentException('[Model\User] The length of password MUST equal to 32 characters.');
        }
        $this->password = $password;
    }

    /**
     * The getter of the field email.
     * @return the email of the user
     */
    public function getEmail() {
        return $this->email;
    }

    /**
     * The setter of the field email.
     * @param String $email - the email of the user
     */
    public function setEmail($email) {
        if ( mb_strlen($email, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\User] The length of email CANNOT exceed 64 characters.');
        }
        $this->email = $email;
    }

    /**
     * The getter of the field user_group_id.
     * @return the user group that the user belongs to
     */
    public function getUserGroup() {
        return $this->userGroup;
    }

    /**
     * The setter of the field user_group_id.
     * @param UserGroup $userGroup - the user group that the user belongs to
     */
    public function setUserGroup(UserGroup $userGroup)  {
        if ( $userGroup == NULL ) {
            throw new InvalidArgumentException('[Model\User] Invalid Model\UserGroup.');
        }
        $this->user_group_id = $userGroup->getUserGroupId();
    }

    /**
     * The getter of the field is_email_verified.
     * @return whether the email of the user is verified
     */
    public function isEmailVerified() {
        return $this->is_email_verified;
    }

    /**
     * The setter of the field is_email_verified.
     * @param boolean $isEmailVerified - whether the email of the user is verified
     */
    public function setEmailVerified($isEmailVerified) {
        $this->is_email_verified = $isEmailVerified;
    }

    /**
     * Get the description of the User object.
     * @return the description of the User object
     */
    public function __toString() {
        return sprintf('User: [Uid=%d, Username=%s, Email=%s, Password=%s, UserGroupID=%d]', 
                $this->uid, $this->username, $this->email, $this->password, $this->user_group_id);
    }

    /**
     * The unique ID of the user.
     * @var long
     */
    protected $uid;

    /**
     * The username of the user.
     * @var String
     */
    protected $username;
    
    /**
     * The password of the user(MD5 encrypted).
     * @var String
     */
    protected $password;
    
    /**
     * The email of the user.
     * @var String
     */
    protected $email;

    /**
     * The unique ID of the user group which the user belongs to.
     * @var int
     */
    protected $user_group_id;

    /**
     * Whether the email of the user is verified.
     * @var String
     */
    protected $is_email_verified;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_users';
}