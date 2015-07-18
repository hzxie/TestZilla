<?php

use Phalcon\Mvc\Model;

/**
 * The users in the application.
 *
 * @package TestZilla\model\Model\User
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class User extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
       $this->belongsTo('user_group_id', 'UserGroup', 'user_group_id');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
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
     * The getter of the field full_name.
     * @return the full name of the user
     */
    public function getFullName() {
        return $this->full_name ;
    }

    /**
     * The setter of the field full_name.
     * @param String $fullName - the full name of the user
     */
    public function setFullName($fullName) {
        if ( mb_strlen($fullName, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\User] The length of fullName CANNOT exceed 64 characters.');
        }
        $this->full_name = $fullName;
    }

    /**
     * Get the description of the User object.
     * @return the description of the User object
     */
    public function __toString() {
        return sprintf('User: [Uid=%d, Username=%s, Email=%s, UserGroupID=%d, FullName=%s]', 
                $this->uid, $this->username, $this->email, $this->user_group_id, $this->full_name);
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
     * The full name of the user.
     * @var String
     */
    protected $full_name;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_users';
}