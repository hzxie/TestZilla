<?php

use Phalcon\Mvc\Model;

/**
 * The meta of users in the application.
 *
 * @package TestZilla\model\Model\UserMeta
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class UserMeta extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->belongsTo('uid', 'User', 'uid');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
    }

    /**
     * The getter of the field meta_id.
     * @return the unique ID of the meta
     */
    public function getMetaId() {
        return $this->meta_id;
    }

    /**
     * The setter of the field meta_id.
     * @param long $metaId - the unique ID of the meta
     */
    public function setMetaId($metaId) {
        $this->meta_id = $metaId;
    }

    /**
     * The getter of the field uid.
     * @return the user which the meta belongs to
     */
    public function getUser() {
        return $this->user;
    }

    /**
     * The setter of the field uid.
     * @param User $user - the user which the meta belongs to
     */
    public function setUser(User $user) {
        if ( $user == NULL ) {
            throw new InvalidArgumentException('[Model\UserMeta] Invalid Model\User.');
        }
        $this->uid = $user->getUid();
    }

    /**
     * The getter of the field meta_key.
     * @return the name of the meta
     */
    public function getMetaKey() {
        return $this->meta_key;
    }

    /**
     * The setter of the field meta_key.
     * @param String $metaKey - the name of the meta
     */
    public function setMetaKey($metaKey) {
        if ( mb_strlen($metaKey, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\UserMeta] The length of metaKey CANNOT exceed 64 characters.');
        }
        $this->meta_key = $metaKey;
    }

    /**
     * The getter of the field meta_value.
     * @return the value of the meta
     */
    public function getMetaValue() {
        return $this->meta_value;
    }

    /**
     * The setter of the field meta_value.
     * @param String $metaValue - the value of the meta
     */
    public function setMetaValue($metaValue) {
        $this->meta_value = $metaValue;
    }

    /**
     * Get the description of the UserMeta object.
     * @return the description of the UserMeta object
     */
    public function __toString() {
        return sprintf('UserMeta: [ID=%d, Uid=%d, Key=%s, Value=%s]', 
                $this->meta_id, $this->uid, $this->meta_key, $this->meta_value);
    }

    /**
     * The unique ID of the meta.
     * @var long
     */
    protected $meta_id;

    /**
     * The unique ID of the user.
     * @var long
     */
    protected $uid;

    /**
     * The name of the meta.
     * @var String
     */
    protected $meta_key;
    
    /**
     * The value of the meta.
     * @var String
     */
    protected $meta_value;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_usermeta';
}