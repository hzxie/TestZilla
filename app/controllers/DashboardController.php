<?php

use Phalcon\Http\Response;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\View;

/**
 * The controller used for handling profile and product management.
 * 
 * @package TestZilla\controller\DashboardController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
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
        $this->tag->prependTitle($this->localization['dashboard.index.title']);
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
        $this->tag->prependTitle($this->localization['dashboard.products.title']);
    }

    /**
     * Render to received issues page.
     */
    public function receivedIssuesAction() {
        $issueService       = ServiceFactory::getService('IssueService');
        $issueCategories    = $this->getIssueCategoriesInBestLanguage($issueService->getIssueCategories());
        $issueStatusList    = $this->getIssueStatusListInBestLanguage($issueService->getIssueStatusList());
     
        $this->tag->prependTitle($this->localization['dashboard.received-issues.title']);
        $this->view->setVar('issueCategories', $issueCategories);
        $this->view->setVar('issueStatusList', $issueStatusList);
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
     * The logger of DashboardController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}