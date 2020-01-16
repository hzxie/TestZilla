<?php

use Phalcon\Http\Response;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\View;

/**
 * The controller used for handling profile and product management.
 * 
 * @package TestZilla\controller\DashboardController
 * @author Haozhe Xie <cshzxie@gmail.com>
 */
class DashboardController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
        $logDir        = $this->config->application->logDir;
        $this->logger  = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Render to dashboard page.
     */
    public function indexAction() {
        $uid            = $this->session->get('uid');
        $currentUser    = $this->getCurrentUserObject($this->session);
        $redirectUrl    = '/dashboard/issues';

        if ( $currentUser->getUserGroup()->getUserGroupSlug() == 'administrator' ) {
            $redirectUrl = '/administration';
        }
        $response = new Response();
        $response->redirect($redirectUrl);
        return $response;
    }

    /**
     * Render to profile page.
     */
    public function profileAction() {
        $uid            = $this->session->get('uid');
        $userService    = ServiceFactory::getService('UserService');
        $userMeta       = $userService->getUserMetaUsingUid($uid);

        $this->tag->prependTitle($this->localization['dashboard.profile.title']);
        $this->view->setVars($userMeta);
    }

    /**
     * Change password for user logged in.
     * @return a HttpResponse contains JSON data infer whether the operation is successful
     */
    public function changePasswordAction() {
        $oldPassword        = $this->request->getPost('oldPassword');
        $newPassword        = $this->request->getPost('newPassword');
        $confirmPassword    = $this->request->getPost('confirmPassword');
        $currentUser        = $this->getCurrentUserObject($this->session);

        $userService        = ServiceFactory::getService('UserService');
        $result             = $userService->changePassword($currentUser, $oldPassword, $newPassword, $confirmPassword);

        if ( $result['isSuccessful'] ) {
            $ipAddress      = $this->request->getClientAddress();
            $this->logger->log(sprintf('User: [%s] changed password at %s.', $currentUser, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Update profile for user logged in.
     * @return a HttpResponse contains JSON data infer whether the operation is successful
     */
    public function updateProfileAction() {
        $email          = $this->request->getPost('email');
        $location       = $this->getFilteredContent(strip_tags($this->request->getPost('location')));
        $website        = $this->request->getPost('website');
        $socialLinks    = $this->request->getPost('socialLinks');
        $aboutMe        = $this->getFilteredContent(strip_tags($this->request->getPost('aboutMe')));
        $currentUser    = $this->getCurrentUserObject($this->session);
        
        $userService    = ServiceFactory::getService('UserService');
        $result         = $userService->updateProfile($currentUser, $email, $location, $website, $socialLinks, $aboutMe);

        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to products page.
     */
    public function productsAction() {
        $productService = ServiceFactory::getService('ProductService');
        $categories     = $productService->getProductCategories();

        $this->tag->prependTitle($this->localization['dashboard.products.title']);
        $this->view->setVar('productCategories', $categories);
    }

    /**
     * Get the products developered by a user.
     * @return a HttpResponse contains JSON data contains information of products developered by a user
     */
    public function getProductsAction() {
        $uid                = $this->session->get('uid');
        $pageNumber         = $this->request->get('page');
        $limit              = self::NUMBER_OF_PRODUCTS_PER_REQUEST;
        $offset             = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;
        
        $productService     = ServiceFactory::getService('ProductService');
        $products           = $this->getProductsInBestLanguage(
                                $productService->getProductUsingDeveloper($uid, $offset, $limit)
                              );
        $numberOfProducts   = $productService->getProducCounttUsingDeveloper($uid);

        $result             = array(
            'isSuccessful'  => !empty($products),
            'products'      => $products,
            'totalPages'    => ceil($numberOfProducts / $limit),
        );
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Get detail information of a product using product ID.
     * @return a HttpResponse contains JSON data contains information of the product
     */
    public function getProductAction() {
        $uid                = $this->session->get('uid');
        $productId          = $this->request->get('productId');

        $productService     = ServiceFactory::getService('ProductService');
        $product            = $productService->getProductUsingId($productId);

        if ( $product != NULL && $product['developer']['uid'] != $uid ) {
            $product        = NULL;
        }
        $result             = array(
            'isSuccessful'  => $product != NULL,
            'product'       => $product,
        );
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Create a new product.
     * @return a HttpResponse contains JSON data infers whether the product is created
     */
    public function createProductAction() {
        $productName            = $this->getFilteredContent(strip_tags($this->request->getPost('productName')));
        $productCategorySlug    = $this->request->getPost('productCategory');
        $productLogoUrl         = $this->request->getPost('productLogoUrl');
        $latestVersion          = $this->getFilteredContent(strip_tags($this->request->getPost('latestVersion')));
        $productUrl             = $this->request->getPost('productUrl');
        $prerequisites          = $this->getFilteredContent(strip_tags($this->request->getPost('prerequisites')));
        $description            = $this->getFilteredContent(strip_tags($this->request->getPost('description')));
        $isTokenValid           = $this->security->checkToken();
        $user                   = $this->getCurrentUserObject($this->session);

        $productService         = ServiceFactory::getService('ProductService');
        $result                 = $productService->createProduct($productName, $productCategorySlug, 
                                    $user, $productLogoUrl, $latestVersion, $productUrl, 
                                    $prerequisites, $description, $isTokenValid);
        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $result['isSuccessful'] ) {
            $ipAddress   = $this->request->getClientAddress();
            $this->logger->log(sprintf('User: [%s] created a product[%s] at %s.', $user, $productName, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Edit an existing product.
     * @return a HttpResponse contains JSON data infers whether the product is edited
     */
    public function editProductAction() {
        $productId              = $this->request->getPost('productId');
        $productName            = $this->getFilteredContent(strip_tags($this->request->getPost('productName')));
        $productCategorySlug    = $this->request->getPost('productCategory');
        $productLogoUrl         = $this->request->getPost('productLogoUrl');
        $latestVersion          = $this->getFilteredContent(strip_tags($this->request->getPost('latestVersion')));
        $productUrl             = $this->request->getPost('productUrl');
        $prerequisites          = $this->getFilteredContent(strip_tags($this->request->getPost('prerequisites')));
        $description            = $this->getFilteredContent(strip_tags($this->request->getPost('description')));
        $isTokenValid           = $this->security->checkToken();
        $user                   = $this->getCurrentUser($this->session);

        $productService         = ServiceFactory::getService('ProductService');
        $result                 = $productService->editProduct($productId, $productName, 
                                    $productCategorySlug, $user, $productLogoUrl, $latestVersion, 
                                    $productUrl, $prerequisites, $description, $isTokenValid);
        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $result['isSuccessful'] ) {
            $ipAddress   = $this->request->getClientAddress();
            $this->logger->log(sprintf('User: [%s] edited a product[%d] at %s.', $user, $productId, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to received issues page.
     */
    public function receivedIssuesAction() {
        $uid                = $this->session->get('uid');
        $issueService       = ServiceFactory::getService('IssueService');
        $products           = $this->getProductsInBestLanguage($issueService->getProductsRelatedToDevelopers($uid));
        $issueCategories    = $this->getIssueCategoriesInBestLanguage($issueService->getIssueCategories());
        $issueStatusList    = $this->getIssueStatusListInBestLanguage($issueService->getIssueStatusList());
     
        $this->tag->prependTitle($this->localization['dashboard.received-issues.title']);
        $this->view->setVar('products', $products);
        $this->view->setVar('issueCategories', $issueCategories);
        $this->view->setVar('issueStatusList', $issueStatusList);
    }

    /**
     * Get issues list of the product the user developed.
     * @return a HttpResponse contains JSON data contains information of issues related to the products the user developed
     */
    public function getReceivedIssuesAction() {
        $uid                = $this->session->get('uid');
        $productId          = $this->request->get('product');
        $issueCategorySlug  = $this->request->get('issueCategory');
        $issueStatusSlug    = $this->request->get('issueStatus');
        $pageNumber         = $this->request->get('page');
        $limit              = self::NUMBER_OF_ISSUES_PER_REQUEST;
        $offset             = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $issueService       = ServiceFactory::getService('IssueService');
        $issueCategoryId    = $issueService->getIssueCategoryId($issueCategorySlug);
        $issueStatusId      = $issueService->getIssueStatusId($issueStatusSlug);
        $issues             = $this->getIssuesInBestLanguage(
                                $issueService->getIssuesUsingDeveloperUidAndProductAndCategoryAndStatus($uid, $productId, $issueCategoryId, $issueStatusId, $offset, $limit)
                              );
        $numberOfIssues     = $issueService->getIssuesCountUsingDeveloperUidAndProductAndCategoryAndStatus($uid, $productId, $issueCategoryId, $issueStatusId);

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
     * Render to submitted issues page.
     */
    public function submittedIssuesAction() {
        $uid                = $this->session->get('uid');
        $issueService       = ServiceFactory::getService('IssueService');
        $products           = $this->getProductsInBestLanguage($issueService->getProductsRelatedToSubmittedIssues($uid));
        $issueCategories    = $this->getIssueCategoriesInBestLanguage($issueService->getIssueCategories());
        $issueStatusList    = $this->getIssueStatusListInBestLanguage($issueService->getIssueStatusList());
     
        $this->tag->prependTitle($this->localization['dashboard.submitted-issues.title']);
        $this->view->setVar('products', $products);
        $this->view->setVar('issueCategories', $issueCategories);
        $this->view->setVar('issueStatusList', $issueStatusList);
    }

    /**
     * Get issues list submitted by the user logged in.
     * @return a HttpResponse contains JSON data contains information of issues submitted by the user
     */
    public function getSubmittedIssuesAction() {
        $hunterUid          = $this->session->get('uid');
        $productId          = $this->request->get('product');
        $issueCategorySlug  = $this->request->get('issueCategory');
        $issueStatusSlug    = $this->request->get('issueStatus');
        $pageNumber         = $this->request->get('page');
        $limit              = self::NUMBER_OF_ISSUES_PER_REQUEST;
        $offset             = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $issueService       = ServiceFactory::getService('IssueService');
        $issueCategoryId    = $issueService->getIssueCategoryId($issueCategorySlug);
        $issueStatusId      = $issueService->getIssueStatusId($issueStatusSlug);
        $issues             = $this->getIssuesInBestLanguage(
                                $issueService->getIssuesUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId, $offset, $limit)
                              );
        $numberOfIssues     = $issueService->getIssuesCountUsingHunterUidAndProductAndCategoryAndStatus($hunterUid, $productId, $issueCategoryId, $issueStatusId);

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
     * Number of products to get in a request.
     */
    const NUMBER_OF_PRODUCTS_PER_REQUEST = 15;

    /**
     * Number of issues to get in a request.
     */
    const NUMBER_OF_ISSUES_PER_REQUEST = 15;

    /**
     * The logger of DashboardController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}