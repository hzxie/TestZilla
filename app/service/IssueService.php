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
        if ( empty($issueCategorySlug) ) {
            return 0;            
        }
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
        if ( empty($issueStatusSlug) ) {
            return 0;            
        }
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
     * @return an object of Issue which contains information of the issue
     */
    public function getIssueObjectUsingId($issueId) {
        $rowSet     = Issue::findFirst(array(
            'conditions'    => 'issue_id = ?1',
            'bind'          => array(
                1           => $issueId,
            ),
        ));
        return $rowSet;
    }

    /**
     * Get detail information of a issue.
     * @param  long $issueId - the unique ID of the issue
     * @return an array which contains information of the issue
     */
    public function getIssueUsingId($issueId) {
        $rowSet     = $this->getIssueObjectUsingId($issueId);

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
     * Get number of issues submitted by a hunter.
     * @param  long $uid - the unique user ID of the hunter
     * @return number of issues submitted by a hunter
     */
    public function getNumberOfIssueUsingHunter($uid) {
        $resultSet          = Issue::find(array(
            'conditions'    => 'issue_hunter_id = ?1',
            'bind'          => array(
                1           => $uid,
            ),
        ));
        return $resultSet->count();
    }

    /**
     * Get issues submitted by a hunter.
     * @param  long $uid - the unique user ID of the hunter
     * @return an array contains issues submitted by a hunter
     */
    public function getIssuesUsingHunter($uid, $offset, $limit) {
        $issues             = array();
        $resultSet          = Issue::find(array(
            'conditions'    => 'issue_hunter_id = ?1',
            'bind'          => array(
                1           => $uid,
            ),
            'limit'         => $limit,
            'offset'        => $offset,
            'order'         => 'issue_id DESC',
        ));

        foreach ( $resultSet as $rowSet ) {
            array_push($issues, array(
                'issueId'           => $rowSet->getIssueId(),
                'issueCategory'     => array(
                    'issueCategoryId'   => $rowSet->getIssueCategory()->getIssueCategoryId(),
                    'issueCategoryName' => (array)json_decode($rowSet->getIssueCategory()->getIssueCategoryName()),
                ),
                'issueStatus'       => array(
                    'issueStatusId'     => $rowSet->getIssueStatus()->getIssueStatusId(),
                    'issueStatusName'   => (array)json_decode($rowSet->getIssueStatus()->getIssueStatusName()),
                ),
                'issueTitle'        => $rowSet->getIssueTitle(),
            ));
        }
        return $issues;
    }

    /**
     * Get issues in a certain product, category, status and founded by a certain user.
     * @param  String $hunterUsername  - the username who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @param  long   $offset          - the index of first record of result set
     * @param  int    $limit           - the number of records to get for each request
     * @return an array which contains issues of a certain product, category, status and founded by a certain user
     */
    public function getIssuesUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId, $offset, $limit) {
        $issues         = array();
        $conditions     = $this->getQueryOfIssuesUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId);

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
                ),
                'issueTitle'        => $rowSet->getIssueTitle(),
                'issueRepliesCount' => $rowSet->getNumberOfIssueReplies(),
            ));
        }
        return $issues;
    }

    /**
     * Get number of issues in a certain product, category, status and founded by a certain user.
     * @param  String $hunterUsername  - the username who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @return number of issues in a certain product, category, status and founded by a certain user
     */
    public function getIssuesCountUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId) {
        $conditions     = $this->getQueryOfIssuesUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId);
        $resultSet      = Issue::find($conditions);

        return $resultSet->count();
    }

    /**
     * Get the conditions of query statement.
     * @param  String $hunterUsername  - the username who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @return the conditions of query statement
     */
    private function getQueryOfIssuesUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId) {
        $hunterUid              = 0;

        if ( !empty($hunterUsername) ) {
            $userService        = ServiceFactory::getService('UserService');
            $hunter             = $userService->getUserUsingUsername($hunterUsername);
            $hunterUid          = $hunter == NULL ? -1 : $hunter->getUid();
        }
        return $this->getQueryOfIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId);
    }

    /**
     * Get issues in a certain product, category, status and founded by a certain user.
     * @param  long   $hunterUid       - the unique ID of the user who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @param  long   $offset          - the index of first record of result set
     * @param  int    $limit           - the number of records to get for each request
     * @return an array which contains issues of a certain product, category, status and founded by a certain user
     */
    public function getIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId, $offset, $limit) {
        $issues         = array();
        $conditions     = $this->getQueryOfIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId);

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
                'issueTitle'        => $rowSet->getIssueTitle(),
                'issueRepliesCount' => $rowSet->getNumberOfIssueReplies(),
            ));
        }
        return $issues;
    }

    /**
     * Get number of issues in a certain product, category, status and founded by a certain user.
     * @param  long   $hunterUid       - the unique ID of the user who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @return number of issues in a certain product, category, status and founded by a certain user
     */
    public function getIssuesCountUsingHunterUidAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId) {
        $conditions     = $this->getQueryOfIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId);
        $resultSet      = Issue::find($conditions);

        return $resultSet->count();
    }

    /**
     * Get the conditions of query statement.
     * @param  long   $hunterUid       - the unique ID of the user who founded the issue
     * @param  long   $productId       - the unique ID of the product
     * @param  int    $issueCategoryId - the unique ID of a category of issue
     * @param  int    $issueStatusId   - the unique ID of a status of issue
     * @return the conditions of query statement
     */
    private function getQueryOfIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId) {
        $conditions             = ' 1 = 1';
        $parameters             = array();

        if ( $hunterUid != 0 ) {
            $conditions        .= ' AND issue_hunter_id = ?1';
            $parameters         = array_replace($parameters, array(
                1               => $hunterUid,
            ));
        }
        if ( $productId != 0 ) {
            $conditions        .= ' AND product_id = ?2';
            $parameters         = array_replace($parameters, array(
                2               => $productId,
            ));
        }
        if ( $issueCategoryId != 0 ) {
            $conditions        .= ' AND issue_category_id = ?3';
            $parameters         = array_replace($parameters, array(
                3               => $issueCategoryId,
            ));
        }
        if ( $issueStatusId != 0 ) {
            $conditions        .= ' AND issue_status_id = ?4';
            $parameters         = array_replace($parameters, array(
                4               => $issueStatusId,
            ));
        }
        return array(
            'conditions'        => $conditions,
            'bind'              => $parameters,
        );
    }

    /**
     * Create an issue of a product.
     * @param  Product $product           - the product which the issue points to
     * @param  String  $productVersion    - the version of the product where the issue found
     * @param  String  $issueCategorySlug - the unique slug of the category of the issue
     * @param  User    $hunter            - the user who found the issue
     * @param  String  $issueTitle        - the title of the issue
     * @param  String  $issueDescription  - the description of the issue
     * @param  boolean $isTokenValid      - whether the CSRF token is correct
     * @return an array with data validation result
     */
    public function createIssue($product, $productVersion, $issueCategorySlug, $hunter, $issueTitle, $issueDescription, $isTokenValid) {
        $issueCategory      = $this->getIssueCategoryObjectUsingSlug($issueCategorySlug);
        $result             = array(
            'isSuccessful'          => false,
            'isProductExists'       => $product != NULL,
            'isProductVersionEmpty' => empty($productVersion),
            'isProductVersionLegal' => $this->isProductVersionLegal($productVersion),
            'isIssueCategoryEmpty'  => $issueCategory == NULL,
            'isUserLoggedIn'        => $hunter != NULL,
            'isIssueTitleEmpty'     => empty($issueTitle),
            'isIssueTitleLegal'     => $this->isIssueTitleLegal($issueTitle),
            'isDescriptionEmpty'    => empty($issueDescription),
            'isTokenValid'          => $isTokenValid,
        );
        $result['isSuccessful']     =  $result['isProductExists']       && !$result['isProductVersionEmpty'] &&
                                       $result['isProductVersionLegal'] && !$result['isIssueCategoryEmpty']  &&  
                                       $result['isUserLoggedIn']        && !$result['isIssueTitleEmpty']     &&
                                       $result['isIssueTitleLegal']     && !$result['isDescriptionEmpty']    &&
                                       $result['isTokenValid'];

        if ( $result['isSuccessful'] ) {
            $issueStatus    = IssueStatus::findFirst("issue_status_slug = 'unconfirmed'");
            $issue          = new Issue();
            $issue->setProduct($product);
            $issue->setProductVersion($productVersion);
            $issue->setIssueCategory($issueCategory);
            $issue->setIssueStatus($issueStatus);
            $issue->setHunter($hunter);
            $issue->setIssueTitle($issueTitle);
            $issue->setIssueDescription($issueDescription);

            if ( !$issue->create() ) {
                $result['isSuccessful'] = false;
            } else {
                $result['issueId']      = $issue->getIssueId();
            }
        }
        return $result;
    }

    /**
     * Get the object of a category of issues using a slug.
     * @param  String $issueCategorySlug - the unique slug of the category of the issue
     * @return an expetcted object of a category of issues
     */
    private function getIssueCategoryObjectUsingSlug($issueCategorySlug) {
        $issueCategory      = IssueCategory::findFirst(array(
            'conditions'    => 'issue_category_slug = ?1',
            'bind'          => array(
                1           => $issueCategorySlug,
            ),
        ));
        return $issueCategory;
    }

    /**
     * Verify if the product version is legal.
     * NOTE: the length of a valid product version should no more than 24 characters.
     * @param  String  $productVersion - the version of the product where the issue found
     * @return whether the product version is legal
     */
    private function isProductVersionLegal($productVersion) {
        if ( mb_strlen($productVersion, 'utf-8') > 24 ) {
            return false;
        }
        return true;
    }

    /**
     * Verify if the title of the issue is legal.
     * NOTE: the length of a valid title of the issue should no more than 64 characters.
     * @param  String  $issueTitle        - the title of the issue
     * @return whether the title of the issue is legal
     */
    private function isIssueTitleLegal($issueTitle) {
        if ( mb_strlen($issueTitle, 'utf-8') > 64 ) {
            return false;
        }
        return true;
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
     * Create a reply of an issue.
     * @param  Issue   $issue        - the issue which reply to
     * @param  User    $submiter     - the user who submit the reply
     * @param  String  $description  - the content of the the reply
     * @param  boolean $isTokenValid - whether the CSRF token is correct
     * @return an array with data validation result
     */
    public function createIssueReply($issue, $submiter, $description, $isTokenValid) {
        $result                 = array(
            'isSuccessful'      => false,
            'isIssueExists'     => $issue != NULL,
            'isUserLoggedIn'    => $submiter != NULL,
            'isContentEmpty'    => empty($description),
            'isTokenValid'      => $isTokenValid,
        );
        $result['isSuccessful'] = $result['isIssueExists'] &&  $result['isUserLoggedIn'] &&
                                  $result['isTokenValid']  && !$result['isContentEmpty'];

        if ( $result['isSuccessful'] ) {
            $issueReply = new IssueReply();
            $issueReply->setIssue($issue);
            $issueReply->setSubmiter($submiter);
            $issueReply->setDescription($description);

            if ( !$issueReply->create() ) {
                $result['isSuccessful'] = false;
            } else {
                $result['issueReplyId'] = $issueReply->getIssueReplyId();
            }
        }
        return $result;
    }

    /**
     * Get product list of submitted issues of a user.
     * @param  long $uid - the unique ID of the user
     * @return an array contains essential information of products
     */
    public function getProductsRelatedToSubmittedIssues($uid) {
        $products       = array();
        $resultSet      = $this->modelsManager->executeQuery(
            'SELECT DISTINCT(Product.product_id) AS productId, product_name AS productName
             FROM Issue 
             NATURAL JOIN Product
             WHERE issue_hunter_id = ?1 
             ORDER BY issue_id DESC',
            array(
                1       => $uid,
            )
        );

        foreach ( $resultSet as $rowSet ) {
            array_push($products, array(
                'productId'     => $rowSet->productId,
                'productName'   => (array)json_decode($rowSet->productName),
            ));
        }
        return $products;
    }
}