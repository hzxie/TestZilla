<?php

use Phalcon\Mvc\Model;

/**
 * The status of issues in the application.
 *
 * @package TestZilla\model\IssueStatus
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class IssueStatus extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the 1-N relationship in this function.
     */
    public function initialize() {
        $this->hasMany('issue_status_id', 'Issue', 'issue_status_id');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
    }

    /**
     * The getter of the field issue_status_id.
     * @return the unique ID of the status of issues
     */
    public function getIssueStatusId() {
        return $this->issue_status_id;
    }

    /**
     * The setter of the field issue_status_id;
     * @param long $issueStatusId - the unique ID of the status of issues
     */
    public function setIssueStatusId($issueStatusId) {
        $this->issue_status_id = $issueStatusId;
    }

    /**
     * The getter of the field issue_status_slug.
     * @return the unique name of the status of issues
     */
    public function getIssueStatusSlug() {
        return $this->issue_status_slug;
    }

    /**
     * The setter of the field issue_status_slug.
     * @param String $issueStatusSlug - the unique name of the status of issues
     */
    public function setIssueStatusSlug($issueStatusSlug) {
        if ( mb_strlen($issueStatusSlug, 'utf-8') > 24 ) {
            throw new InvalidArgumentException('[Model\IssueStatus] The length of issueStatusSlug CANNOT exceed 24 characters.');
        }
        $this->issue_status_slug = $issueStatusSlug;
    }

    /**
     * The getter of the field issue_status_name.
     * @return the display name of the status of issues
     */
    public function getIssueStatusName() {
        return $this->issue_status_name;
    }

    /**
     * The setter of the field issue_status_name.
     * @param String $issueStatusName - the display name of the status of issues
     */
    public function setIssueStatusName($issueStatusName) {
        $json = json_encode($issueStatusName);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\IssueStatus] The issueStatusName seems not a valid JSON.');
        }
        $this->issue_status_name = $issueStatusName;
    }

    /**
     * Get the description of the IssueStatus object.
     * @return the description of the IssueStatus object
     */
    public function __toString() {
        return sprintf('IssueStatus: [ID=%s, Slug=%s, Name=%s]', 
                $this->issue_status_id, $this->issue_status_slug, $this->issue_status_name);
    }
    
    /**
     * The unique ID of the status of issues.
     * @var int
     */
    protected $issue_status_id;

    /**
     * The unique slug of the status of issues.
     * @var String
     */
    protected $issue_status_slug;

    /**
     * The name of the status of issues (JSON format).
     * @var String
     */
    protected $issue_status_name;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_issue_status';
}