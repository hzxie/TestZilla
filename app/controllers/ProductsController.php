<?php

use Phalcon\Http\Response;
use Phalcon\Mvc\View;

/**
 * The controller used for handling products management.
 * 
 * @package TestZilla\controller\ProductController
 */
class ProductsController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
    }

    /**
     * Render to products page.
     */
    public function indexAction() {
        $productService = ServiceFactory::getService('ProductService');
        $categories     = $productService->getProductCategories();

        $this->tag->prependTitle($this->localization['products.index.title']);
        $this->view->setVar('productCategories', $categories);
    }

    /**
     * Get products of a certain category.
     * @return a HttpResponse which contains information of products
     */
    public function getProductsAction() {
        $productCategorySlug    = $this->request->get('productCategory');
        $keyword                = $this->request->get('keyword');
        $pageNumber             = $this->request->get('page');
        $limit                  = self::NUMBER_OF_PRODUCTS_PER_PAGE;
        $offset                 = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $productService         = ServiceFactory::getService('ProductService');
        $productCategoryId      = $productService->getProductCategoryId($productCategorySlug);
        $products               = $this->getProductsInBestLanguage(
                                    $productService->getProductsUsingCategoryAndKeyword($productCategoryId, $keyword, $offset, $limit)
                                  );
        $numberOfProducts       = $productService->getProductsCountUsingCategoryAndKeyword($productCategoryId, $keyword);

        $result                 = array(
            'isSuccessful'      => !empty($products),
            'products'          => $products,
            'totalPages'        => ceil($numberOfProducts / $limit),
        );
        $response               = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to product detail information page.
     * @param long $productId - the unique ID of the product
     */
    public function productAction($productId) {
        $productService = ServiceFactory::getService('ProductService');
        $issueService   = ServiceFactory::getService('IssueService');
        $product        = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        
        $product            = $this->getProductInBestLanguage($product);
        $productCategoryId  = $product['productCategory']['productCategoryId'];
        $relationalProducts = $this->getProductsInBestLanguage($productService->getProductsUsingCategory($productCategoryId, 0, 5));
        $issueCategories    = $this->getIssueCategoriesInBestLanguage($issueService->getIssueCategories());
        $issueStatusList    = $this->getIssueStatusListInBestLanguage($issueService->getIssueStatusList());

        $this->tag->prependTitle($product['productName']);
        $this->view->setVar('product', $product);
        $this->view->setVar('relationalProducts', $relationalProducts);
        $this->view->setVar('issueCategories', $issueCategories);
        $this->view->setVar('issueStatusList', $issueStatusList);
    }

    /**
     * Get issues of a certain product.
     * @param  long $productId - the unique ID of the product
     * @return a HttpResponse which contains information of issues of a product
     */
    public function getIssuesAction($productId) {
        $issueCategorySlug  = $this->request->get('issueCategory');
        $issueStatusSlug    = $this->request->get('issueStatus');
        $hunterUsername     = $this->request->get('hunter');
        $pageNumber         = $this->request->get('page');
        $limit              = self::NUMBER_OF_ISSUES_PER_REQUEST;
        $offset             = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $productService     = ServiceFactory::getService('ProductService');
        $issueService       = ServiceFactory::getService('IssueService');
        $product            = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $issueCategoryId    = $issueService->getIssueCategoryId($issueCategorySlug);
        $issueStatusId      = $issueService->getIssueStatusId($issueStatusSlug);
        $issues             = $this->getIssuesInBestLanguage(
                                $issueService->getIssuesUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId, $offset, $limit)
                              );
        $numberOfIssues     = $issueService->getIssuesCountUsingHunterUsernameAndProductAndCategoryAndStatus($hunterUsername, $productId, $issueCategoryId, $issueStatusId);

        $result             = array(
            'isSuccessful'  => !empty($issues),
            'issues'        => $issues,
            'totalPages'    => ceil($numberOfIssues / $limit),
        );
        $response   = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to detail information of issue page.
     * @param  long $issueId - the unique ID of the issue
     */
    public function issueAction($issueId) {
        $issueService   = ServiceFactory::getService('IssueService');
        $issue          = $issueService->getIssueUsingId($issueId);

        if ( $issue == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $issue          = $this->getIssueInBestLanguage($issue);
        $issueTitle     = $issue['issueTitle'];
        $this->tag->prependTitle($issueTitle . ' · ' . $issue['product']['productName']);
        $this->view->setVar('issue', $issue);
    }

    /**
     * Get the list of replies of an issue.
     * @param  long $issueId - the unique ID of the issue
     * @return a HttpResponse which contains information of replies of an issue
     */
    public function getIssueRepliesAction($issueId) {
        $limit          = self::NUMBER_OF_ISSUE_REPLIES_PER_REQUEST;
        $offset         = $this->request->get('currentReplies');

        $issueService   = ServiceFactory::getService('IssueService');
        $issueReplies   = $issueService->getIssueReplies($issueId, $offset, $limit);

        $result         = array(
            'isSuccessful'  => !empty($issueReplies),
            'hasMore'       => count($issueReplies) == $limit,
            'issueReplies'  => $issueReplies,
        );
        $response   = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Create a reply of an issue.
     * @param  long $issueId - the unique ID of the issue
     * @return an HttpResponse contains data validation result
     */
    public function createIssueReplyAction($issueId) {
        $replyContent   = $this->getFilteredContent(strip_tags($this->request->getPost('content')));
        $submiter       = $this->getCurrentUserObject($this->session);
        $isTokenValid   = $this->security->checkToken();
        $issueService   = ServiceFactory::getService('IssueService');
        $issue          = $issueService->getIssueObjectUsingId($issueId);

        if ( $issue == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $result         = $issueService->createIssueReply($issue, $submiter, $replyContent, $isTokenValid);
        
        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $isSuccessful ) {
            $issueReplyId   = $result['issueReplyId'];
            $ipAddress      = $this->request->getClientAddress();
            $this->logger->log(sprintf('IssueReply #%d created by User[%s] at %s.', $issueReplyId, $submiter, $ipAddress));
        }
        $response       = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to new issue page to create an issue.
     * @param  long $productId - the unique ID of the product
     */
    public function newIssueAction($productId) {
        if ( !$this->isLoggedIn($this->session) ) {
            $response    = new Response();
            $response->redirect("/accounts/signin?forward=/product/{$productId}/new-issue");
            return $response;
        }
        $productService     = ServiceFactory::getService('ProductService');
        $issueService       = ServiceFactory::getService('IssueService');
        $product            = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $product            = $this->getProductInBestLanguage($product);
        $issueCategories    = $this->getIssueCategoriesInBestLanguage($issueService->getIssueCategories());

        $this->tag->prependTitle($this->localization['products.new-issue.title'] . ' · ' . $product['productName']);
        $this->view->setVar('product', $product);
        $this->view->setVar('issueCategories', $issueCategories);
    }

    /**
     * Create an issue of a product.
     * @param  long $productId - the unique ID of the product
     * @return an HttpResponse contains data validation result
     */
    public function createIssueAction($productId) {
        $issueTitle         = $this->getFilteredContent(strip_tags($this->request->getPost('issueTitle')));
        $issueCategorySlug  = $this->getFilteredContent(strip_tags($this->request->getPost('issueCategory')));
        $productVersion     = $this->getFilteredContent(strip_tags($this->request->getPost('productVersion')));
        $issueDescription   = $this->getFilteredContent(strip_tags($this->request->getPost('issueDescription')));
        $isTokenValid       = $this->security->checkToken();
        $productService     = ServiceFactory::getService('ProductService');
        $issueService       = ServiceFactory::getService('IssueService');
        $product            = $productService->getProductObjectUsingId($productId);
        $hunter             = $this->getCurrentUserObject($this->session);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $result             = $issueService->createIssue($product, $productVersion, $issueCategorySlug, 
                                                $hunter, $issueTitle, $issueDescription, $isTokenValid);
        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $isSuccessful ) {
            $issueId        = $result['issueId'];
            $ipAddress      = $this->request->getClientAddress();
            $this->logger->log(sprintf('Issue #%d created by User[%s] at %s.', $issueId, $hunter, $ipAddress));
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Number of products to display in a page in the view.
     */
    const NUMBER_OF_PRODUCTS_PER_PAGE = 15;

    /**
     * Number of issues to get in a request.
     */
    const NUMBER_OF_ISSUES_PER_REQUEST = 15;

    /**
     * Number of replies of issue to get in a request.
     */
    const NUMBER_OF_ISSUE_REPLIES_PER_REQUEST = 50;
}