<?php

use Phalcon\Mvc\Model;

/**
 * The reply of issues in the application.
 *
 * @package TestZilla\model\IssueReply
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class IssueReply extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->belongsTo('issue_id', 'Issue', 'issue_id');
        $this->belongsTo('issue_reply_submiter_uid', 'User', 'uid');
    }

    /**
     * Override the name of table in database.
     * @return the name of table in database
     */
    public function getSource() {
        return self::TABLE_NAME;
    }

    /**
     * The getter of the field issue_reply_id.
     * @return the unique ID of the reply of the issue
     */
    public function getIssueReplyId() {
        return $this->issue_reply_id;
    }

    /**
     * The setter of the field issue_reply_id.
     * @param long $issueReplyId - the unique ID of the reply of the issue
     */
    public function setIssueReplyId($issueReplyId) {
        $this->issue_reply_id = $issueReplyId;
    }

    /**
     * The getter of the field issue_id.
     * @return the issue related to the reply
     */
    public function getIssue() {
        return $this->issue;
    }

    /**
     * The setter of the field issue_id.
     * @param Issue $issue - the issue related to the reply
     */
    public function setIssue(Issue $issue) {
        if ( $issue == NULL ) {
            throw new InvalidArgumentException('[Model\IssueReply] Invalid Model\Issue.');
        }
        $this->issue_id = $issue->getIssueId();
    }

    /**
     * The getter of the field issue_reply_create_time.
     * @return the time when the reply is created
     */
    public function getCreateTime() {
        return $this->issue_reply_create_time;
    }

    /**
     * The setter of the field issue_reply_create_time.
     * @param DateTime $createTime - the time when the reply is created
     */
    public function setCreateTime($createTime) {
        $this->issue_reply_create_time = $createTime;
    }

    /**
     * The getter of the field issue_reply_submiter_uid.
     * @return the user who submit the the reply
     */
    public function getSubmiter() {
        return $this->user;
    }

    /**
     * The setter of the field issue_reply_submiter_uid
     * @param User $submiter - the user who submit the the reply
     */
    public function setSubmiter(User $submiter) {
        if ( $submiter == NULL ) {
            throw new InvalidArgumentException('[Model\IssueReply] Invalid Model\User.');
        }
        $this->issue_reply_submiter_uid = $submiter->getUid();
    }

    /**
     * The getter of the field issue_reply_description.
     * @return the content of the reply
     */
    public function getDescription() {
        return $this->issue_reply_description;
    }

    /**
     * The setter of the field issue_reply_description.
     * @param String $description - the content of the reply
     */
    public function setDescription($description) {
        $this->issue_reply_description = $description;
    }

    /**
     * Get the description of the IssueReply object.
     * @return the description of the IssueReply object
     */
    public function __toString() {
        return sprintf('IssueReply: [ID=%d, IssueId=%d, CreateTime=%s, SubmiterUid=%d, Description=%s]', 
                $this->issue_reply_id, $this->issue_id, $this->issue_reply_create_time,
                $this->issue_reply_submiter_uid, $this->issue_reply_description);
    }

    /**
     * The unique ID of the reply of the issue
     * @var long
     */
    protected $issue_reply_id;
    
    /**
     * The unique ID of the issue.
     * @var long
     */
    protected $issue_id;
    
    /**
     * The time when the reply is created
     * @var DateTime
     */
    protected $issue_reply_create_time;
    
    /**
     * The unique ID of the user who submit the reply.
     * @var long
     */
    protected $issue_reply_submiter_uid;

    /**
     * The content of the reply.
     * @var String
     */
    protected $issue_reply_description;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_issue_replies';
}