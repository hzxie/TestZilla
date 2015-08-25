<?php

use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;

/**
 * The bussiness layer for issues.
 *
 * @package TestZilla\Service\IssueService
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class IssueService extends Service {
    /**
     * Initialize the Service
     */
    public function initialize() {
        parent::initialize();
    }

    /**
     * Get all categories of issues.
     * @return an array with contains information of issues categories.
     */
    public function getIssueCategories() {
        $issueCategories    = array();
        $resultSet          = IssueCategory::find();

        foreach ( $resultSet as $rowSet ) {
            array_push($issueCategories, array(
                'issueCategoryId'   => $rowSet->getIssueCategoryId(),
                'issueCategorySlug' => $rowSet->getIssueCategorySlug(),
                'issueCategoryName' => (array)json_decode($rowSet->getIssueCategoryName()),
            ));
        }
        return $issueCategories;
    }

    /**
     * Get the unique ID of the category of issue.
     * @param  String $issueCategorySlug - the unique slug of the category of issue
     * @return the unique ID of the category of issue
     */
    public function getIssueCategoryId($issueCategorySlug) {
        $rowSet = IssueCategory::findFirst(array(
            'conditions'    => 'issue_category_slug = ?1',
            'bind'          => array(
                1           => $issueCategorySlug,
            ),
        ));
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getIssueCategoryId();
    }

    /**
     * Get all status list of issues.
     * @return an array with contains information of issues categories.
     */
    public function getIssueStatusList() {
        $issueStatusList    = array();
        $resultSet          = IssueStatus::find();

        foreach ( $resultSet as $rowSet ) {
            array_push($issueStatusList, array(
                'issueStatusId'   => $rowSet->getIssueStatusId(),
                'issueStatusSlug' => $rowSet->getIssueStatusSlug(),
                'issueStatusName' => (array)json_decode($rowSet->getIssueStatusName()),
            ));
        }
        return $issueStatusList;
    }

    /**
     * Get the unique ID of the status of issue.
     * @param  String $issueStatusSlug - the unique slug of the status of issue
     * @return the unique ID of the status of issue
     */
    public function getIssueStatusId($issueStatusSlug) {
        $rowSet = IssueStatus::findFirst(array(
            'conditions'    => 'issue_status_slug = ?1',
            'bind'          => array(
                1           => $issueStatusSlug,
            ),
        ));
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getIssueStatusId();
    }

    /**
     * Get detail information of a issue.
     * @param  long $issueId - the unique ID of the issue
     * @return an array which contains information of the issue
     */
    public function getIssueUsingId($issueId) {
        $rowSet     = Issue::findFirst(array(
            'conditions'    => 'issue_id = ?1',
            'bind'          => array(
                1           => $issueId,
            ),
        ));

        if ( $rowSet == NULL ) {
            return NULL;
        }
        return array(
            'issueId'       => $rowSet->getIssueId(),
            'product'       => array(
                'productId'         => $rowSet->getProduct()->getProductId(),
                'productName'       => (array)json_decode($rowSet->getProduct()->getProductName()),
                'latestVersion'     => $rowSet->getProduct()->getLatestVersion(),
            ),
            'productVersion'    => $rowSet->getProductVersion(),
            'issueCategory'     => array(
                'issueCategoryId'   => $rowSet->getIssueCategory()->getIssueCategoryId(),
                'issueCategoryName' => (array)json_decode($rowSet->getIssueCategory()->getIssueCategoryName()),
            ),
            'issueStatus'       => array(
                'issueStatusId'     => $rowSet->getIssueStatus()->getIssueStatusId(),
                'issueStatusName'   => (array)json_decode($rowSet->getIssueStatus()->getIssueStatusName()),
            ),
            'createTime'        => $rowSet->getCreateTime(),
            'hunter'            => array(
                'uid'               => $rowSet->getHunter()->getUid(),
                'username'          => $rowSet->getHunter()->getUsername(),
                'email'             => $rowSet->getHunter()->getEmail(),
            ),
            'issueTitle'        => $rowSet->getIssueTitle(),
            'issueDescription'  => $rowSet->getIssueDescription(),
            'issueRepliesCount' => $rowSet->getNumberOfIssueReplies(),
        );
    }

    /**
     * Get the list of replies of an issue.
     * @param  long $issueId - the unique ID of the issue
     * @param  long $offset  - the index of first record of result set
     * @param  int  $limit   - the number of records to get for each request
     * @return the list of replies of an issue
     */
    public function getIssueReplies($issueId, $offset, $limit) {
        $issueReplies   = array();
        $resultSet      = IssueReply::find(array(
            'conditions'    => 'issue_id = ?1',
            'bind'          => array(
                1           => $issueId,
            ),
            'limit'         => $limit,
            'offset'        => $offset,
            'order'         => 'issue_reply_id DESC',
        ));

        foreach ( $resultSet as $rowSet ) {
            array_push($issueReplies, array(
                'issueReplyId'  => $rowSet->getIssueReplyId(),
                'createTime'    => $rowSet->getCreateTime(),
                'submiter'      => array(
                    'uid'       => $rowSet->getSubmiter()->getUid(),
                    'username'  => $rowSet->getSubmiter()->getUsername(),
                    'email'     => $rowSet->getSubmiter()->getEmail(),
                ),
                'description'   => $rowSet->getDescription(),
            ));            
        }
        return $issueReplies;
    }

    /**
     * Get issues in a certain category, status and founded by a certain user.
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @param  String $hunterUsername  - the username who founded the issue
     * @param  long   $offset          - the index of first record of result set
     * @param  int    $limit           - the number of records to get for each request
     * @return an array which contains issues of a certain category, status and founded by a certain user
     */
    public function getIssuesUsingCategoryAndStatusAndHunter($productId, $issueCategoryId, $issueStatusId, $hunterUsername, $offset, $limit) {
        $issues         = array();
        $conditions     = $this->getQueryOfIssuesUsingCategoryAndStatusAndHunter($productId, $issueCategoryId, $issueStatusId, $hunterUsername);

        $resultSet      = Issue::find(array_merge($conditions, array(
            'limit'     => $limit,
            'offset'    => $offset,
            'order'     => 'issue_id DESC',
        )));

        foreach ( $resultSet as $rowSet ) {
            array_push($issues, array(
                'issueId'           => $rowSet->getIssueId(),
                'product'           => array(
                    'productId'         => $rowSet->getProduct()->getProductId(),
                    'productName'       => $rowSet->getProduct()->getProductName(),
                    'latestVersion'     => $rowSet->getProduct()->getLatestVersion(),
                ),
                'productVersion'    => $rowSet->getProductVersion(),
                'issueCategory'     => array(
                    'issueCategoryId'   => $rowSet->getIssueCategory()->getIssueCategoryId(),
                    'issueCategoryName' => (array)json_decode($rowSet->getIssueCategory()->getIssueCategoryName()),
                ),
                'issueStatus'       => array(
                    'issueStatusId'     => $rowSet->getIssueStatus()->getIssueStatusId(),
                    'issueStatusName'   => (array)json_decode($rowSet->getIssueStatus()->getIssueStatusName()),
                ),
                'createTime'        => $rowSet->getCreateTime(),
                'hunter'            => array(
                    'uid'               => $rowSet->getHunter()->getUid(),
                    'username'          => $rowSet->getHunter()->getUsername(),
                ),
                'issueTitle'        => $rowSet->getIssueTitle(),
                'issueRepliesCount' => $rowSet->getNumberOfIssueReplies(),
            ));
        }
        return $issues;
    }

    /**
     * Get number of issues in a certain category, status and founded by a certain user.
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @param  String $hunterUsername  - the username who founded the issue
     * @return number of issues in a certain category, status and founded by a certain user
     */
    public function getIssuesCountUsingCategoryAndStatusAndHunter($productId, $issueCategoryId, $issueStatusId, $hunterUsername) {
        $conditions     = $this->getQueryOfIssuesUsingCategoryAndStatusAndHunter($productId, $issueCategoryId, $issueStatusId, $hunterUsername);
        $resultSet      = Issue::find($conditions);

        return $resultSet->count();
    }

    /**
     * Get the conditions of query statement.
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @param  String $hunterUsername  - the username who founded the issue
     * @return the conditions of query statement
     */
    private function getQueryOfIssuesUsingCategoryAndStatusAndHunter($productId, $issueCategoryId, $issueStatusId, $hunterUsername) {
        $conditions             = 'product_id = ?1';
        $parameters             = array(
            1               => $productId,
        );

        if ( $issueCategoryId != 0 ) {
            $conditions        .= ' AND issue_category_id = ?2';
            $parameters         = array_replace($parameters, array(
                2               => $issueCategoryId,
            ));
        }
        if ( $issueStatusId != 0 ) {
            $isFirstCondition   = false;
            $conditions        .= ' AND issue_status_id = ?3';
            $parameters         = array_replace($parameters, array(
                3               => $issueStatusId,
            ));
        }
        if ( !empty($hunterUsername) ) {
            $userService        = ServiceFactory::getService('UserService');
            $hunter             = $userService->getUserUsingUsername($hunterUsername);
            $hunterUid          = $hunter == NULL ? 0 : $hunter->getUid();

            $conditions        .= ' AND issue_hunter_id = ?4';
            $parameters         = array_replace($parameters, array(
                4               => $hunterUid,
            ));
        }
        return array(
            'conditions'        => $conditions,
            'bind'              => $parameters,
        );
    }
}