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
        $this->tag->prependTitle($this->localization['dashboard.profile.title']);
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
        $result   = array();

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
     * Render to issues page.
     */
    public function issuesAction() {
        $this->tag->prependTitle($this->localization['dashboard.issues.title']);
    }

    /**
     * The logger of DashboardController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}