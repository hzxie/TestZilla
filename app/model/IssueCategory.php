<?php

use Phalcon\Mvc\Model;

/**
 * The category of issues in the application.
 *
 * @package TestZilla\model\IssueCategory
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class IssueCategory extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the 1-N relationship in this function.
     */
    public function initialize() {
        $this->hasMany('issue_category_id', 'Issue', 'issue_category_id');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
    }

    /**
     * The getter of the field issue_category_id.
     * @return the unique ID of the category of issues
     */
    public function getIssueCategoryId() {
        return $this->issue_category_id;
    }

    /**
     * The setter of the field issue_category_id;
     * @param long $issueCategoryId - the unique ID of the category of issues
     */
    public function setIssueCategoryId($issueCategoryId) {
        $this->issue_category_id = $issueCategoryId;
    }

    /**
     * The getter of the field issue_category_slug.
     * @return the unique name of the category of issues
     */
    public function getIssueCategorySlug() {
        return $this->issue_category_slug;
    }

    /**
     * The setter of the field issue_category_slug.
     * @param String $issueCategorySlug - the unique name of the category of issues
     */
    public function setIssueCategorySlug($issueCategorySlug) {
        if ( mb_strlen($issueCategorySlug, 'utf-8') > 24 ) {
            throw new InvalidArgumentException('[Model\IssueCategory] The length of issueCategorySlug CANNOT exceed 24 characters.');
        }
        $this->issue_category_slug = $issueCategorySlug;
    }

    /**
     * The getter of the field issue_category_name.
     * @return the display name of the category of issues
     */
    public function getIssueCategoryName() {
        return $this->issue_category_name;
    }

    /**
     * The setter of the field issue_category_name.
     * @param String $issueCategoryName - the display name of the category of issues
     */
    public function setIssueCategoryName($issueCategoryName) {
        $json = json_encode($issueCategoryName);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\IssueCategory] The issueCategoryName seems not a valid JSON.');
        }
        $this->issue_category_name = $issueCategoryName;
    }

    /**
     * Get the description of the IssueCategory object.
     * @return the description of the IssueCategory object
     */
    public function __toString() {
        return sprintf('IssueCategory: [ID=%s, Slug=%s, Name=%s]', 
                $this->issue_category_id, $this->issue_category_slug, $this->issue_category_name);
    }
    
    /**
     * The unique ID of the category of issues.
     * @var int
     */
    protected $issue_category_id;

    /**
     * The unique slug of the category of issues.
     * @var String
     */
    protected $issue_category_slug;

    /**
     * The name of the category of issues (JSON format).
     * @var String
     */
    protected $issue_category_name;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_issue_categories';
}