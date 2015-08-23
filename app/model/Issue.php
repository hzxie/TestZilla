<?php

use Phalcon\Mvc\Model;

/**
 * The issues in the application.
 *
 * @package TestZilla\model\Issue
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class Issue extends Model {
	/**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->belongsTo('issue_category_id', 'IssueCategory', 'issue_category_id');
        $this->belongsTo('issue_status_id', 'IssueStatus', 'issue_status_id');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
    }

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_issues';
}