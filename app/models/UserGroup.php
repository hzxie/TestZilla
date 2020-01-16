<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:53:55
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Mvc\Model;

/**
 * The user groups in the application.
 * Different user groups have different privileges.
 *
 * @package TestZilla\model\UserGroup
 */
class UserGroup extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the 1-N relationship in this function.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
        $this->hasMany('user_group_id', 'User', 'user_group_id');
    }

    /**
     * The getter of the field user_group_id.
     * @return the unique ID of the user group
     */
    public function getUserGroupId() {
        return $this->user_group_id;
    }

    /**
     * The setter of the field user_group_id;
     * @param long $userGroupId - the unique ID of the user group
     */
    public function setUserGroupId($userGroupId) {
        $this->user_group_id = $userGroupId;
    }

    /**
     * The getter of the field user_group_slug.
     * @return the unique name of the user group
     */
    public function getUserGroupSlug() {
        return $this->user_group_slug;
    }

    /**
     * The setter of the field user_group_slug.
     * @param String $userGroupSlug - the unique name of the user group
     */
    public function setUserGroupSlug($userGroupSlug) {
        $this->user_group_slug = $userGroupSlug;
    }

    /**
     * The getter of the field user_group_name.
     * @return the display name of the user group
     */
    public function getUserGroupName() {
        return $this->user_group_name;
    }

    /**
     * The setter of the field user_group_name.
     * @param String $userGroupName - the display name of the user group
     */
    public function setUserGroupName($userGroupName) {
        $this->user_group_name = $userGroupName;
    }

    /**
     * Get the description of the UserGroup object.
     * @return the description of the UserGroup object
     */
    public function __toString() {
        return sprintf('UserGroup: [ID=%s, Slug=%s, Name=%s]', 
                $this->user_group_id, $this->user_group_slug, $this->user_group_name);
    }
    
    /**
     * The unique ID of the user group.
     * @var int
     */
    protected $user_group_id;

    /**
     * The unique name of the user group.
     * @var String
     */
    protected $user_group_slug;

    /**
     * The display name of the user group.
     * @var String
     */
    protected $user_group_name;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_user_groups';
}
