<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:52:29
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 12:52:34
 */

use Phalcon\Mvc\Model;

/**
 * The issues in the application.
 *
 * @package TestZilla\model\Issue
 * @author Haozhe Xie <cshzxie@gmail.com>
 */
class Issue extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
        $this->belongsTo('product_id', 'Product', 'product_id');
        $this->belongsTo('issue_category_id', 'IssueCategory', 'issue_category_id');
        $this->belongsTo('issue_status_id', 'IssueStatus', 'issue_status_id');
        $this->belongsTo('issue_hunter_id', 'User', 'uid');
        
        $this->hasMany('issue_id', 'IssueReply', 'issue_id');
    }

    /**
     * The getter of the field issue_id.
     * @return the unique ID of the issue
     */
    public function getIssueId() {
        return $this->issue_id;
    }

    /**
     * The setter of the field issue_id.
     * @param long $issueId - the unique ID of the issue
     */
    public function setIssueId($issueId) {
        $this->issue_id = $issueId;
    }

    /**
     * The getter of the field product_id.
     * @return the product related to the issue
     */
    public function getProduct() {
        return $this->product;
    }

    /**
     * The setter of the field product_id.
     * @param Product $product - the product related to the issue
     */
    public function setProduct(Product $product) {
        if ( $product == NULL ) {
            throw new InvalidArgumentException('[Model\Issue] Invalid Model\Product.');
        }
        $this->product_id = $product->getProductId();
    }

    /**
     * The getter of the field product_version.
     * @return the version of the product when the issue founded
     */
    public function getProductVersion() {
        return $this->product_version;
    }

    /**
     * The setter of the field product_version.
     * @param String $productVersion - the version of the product when the issue founded
     */
    public function setProductVersion($productVersion) {
        if ( mb_strlen($productVersion, 'utf-8') > 24 ) {
            throw new InvalidArgumentException('[Model\Issue] The length of productVersion CANNOT exceed 24 characters.');
        }
        $this->product_version = $productVersion;
    }

    /**
     * The getter of the field issue_category_id.
     * @return the category of the issue
     */
    public function getIssueCategory() {
        return $this->issueCategory;
    }

    /**
     * The setter of the field issue_category_id.
     * @param IssueCategory $issueCategory - the category of the issue
     */
    public function setIssueCategory(IssueCategory $issueCategory) {
        if ( $issueCategory == NULL ) {
            throw new InvalidArgumentException('[Model\Issue] Invalid Model\IssueCategory.');
        }
        $this->issue_category_id = $issueCategory->getIssueCategoryId();
    }

    /**
     * The getter of the field issue_status_id.
     * @return the status of the issue
     */
    public function getIssueStatus() {
        return $this->issueStatus;
    }

    /**
     * The setter of the field issue_status_id.
     * @param IssueStatus $issueStatus - the status of the issue
     */
    public function setIssueStatus(IssueStatus $issueStatus) {
        if ( $issueStatus == NULL ) {
            throw new InvalidArgumentException('[Model\Issue] Invalid Model\IssueStatus.');
        }
        $this->issue_status_id = $issueStatus->getIssueStatusId();
    }

    /**
     * The getter of the field issue_create_time.
     * @return the time when the issue was created
     */
    public function getCreateTime() {
        return $this->issue_create_time;
    }

    /**
     * The setter of the field issue_create_time.
     * @param String $createTime - the time when the issue was created
     */
    public function setCreateTime($createTime) {
        $this->issue_create_time = $createTime;
    }

    /**
     * The getter of the field issue_hunter_id.
     * @return the hunter of the issue
     */
    public function getHunter() {
        return $this->user;
    }

    /**
     * The setter of the field issue_hunter_id.
     * @param User $hunter - the hunter of the issue
     */
    public function setHunter(User $hunter) {
        if ( $hunter == NULL ) {
            throw new InvalidArgumentException('[Model\Issue] Invalid Model\User.');
        }
        $this->issue_hunter_id = $hunter->getUid();
    }

    /**
     * The getter of the field issue_title.
     * @return the title of the issue
     */
    public function getIssueTitle() {
        return $this->issue_title;
    }

    /**
     * The setter of the field issue_title.
     * @param String $issueTitle - the title of the issue
     */
    public function setIssueTitle($title) {
        if ( mb_strlen($title, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\Issue] The length of title CANNOT exceed 64 characters.');
        }
        $this->issue_title = $title;
    }

    /**
     * The getter of the field issue_description.
     * @return the description of the issue
     */
    public function getIssueDescription() {
        return $this->issue_description;
    }

    /**
     * The setter of the field issue_description.
     * @param String $description - the description of the issue
     */
    public function setIssueDescription($description) {
        $this->issue_description = $description;
    }

    /**
     * Get number of replies of the issue.
     * @return number of replies of the issue
     */
    public function getNumberOfIssueReplies() {
        return $this->issueReply->count();
    }

    /**
     * Get the description of the Issue object.
     * @return the description of the Issue object
     */
    public function __toString() {
        return sprintf('Issue: [ID=%d, ProductId=%d, LatestVersion=%s, IssueCategoryId=%d, StatusId=%d, HunterId=%d, Title=%s, Description=%s]', 
                $this->issue_id, $this->product_id, $this->product_version, $this->issue_category_id, 
                $this->issue_status_id, $this->issue_hunter_id, $this->issue_title, $this->issue_description);
    }

    /**
     * the unique ID of the issue.
     * @var long
     */
    protected $issue_id;

    /**
     * The ID of the product related to the issue.
     * @var long
     */
    protected $product_id;

    /**
     * the version of the product when the issue founded.
     * @var String
     */
    protected $product_version;
    
    /**
     * The unique ID of the category of the issue.
     * @var int
     */
    protected $issue_category_id;
    
    /**
     * The unique ID of the status of the issue.
     * @var int
     */
    protected $issue_status_id;

    /**
     * The time when the issue was created.
     * @var String
     */
    protected $issue_create_time;

    /**
     * The unique ID of the hunter of the issue.
     * @var long
     */
    protected $issue_hunter_id;

    /**
     * The title of the issue.
     * @var String
     */
    protected $issue_title;

    /**
     * The description of the issue.
     * @var String
     */
    protected $issue_description;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_issues';
}