<?php

use Phalcon\Http\Response;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\View;

/**
 * The controller used for handling account management.
 * 
 * @package TestZilla\controller\AccountsController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class AccountsController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
        $logDir            = $this->config->application->logDir;
        $this->logger      = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Handle the sign in requests.
     * @return a HTTP response object with JSON
     */
    public function signInAction() {
        $ipAddress   = $this->request->getClientAddress();
        $username    = $this->request->getPost('username');
        $password    = $this->request->getPost('password');

        $userService = ServiceFactory::getService('UserService');
        $result      = $userService->isAccountValid($username, $password);
        $this->logger->log(sprintf('User: [Username=%s] tried to sign in at %s.', $username, $ipAddress), Logger::INFO);

        if ( $result['isSuccessful'] ) {
            $user    = $userService->getUserUsingUsernameOrEmail($username);
            $this->getSession($this->session, $user);
            $this->logger->log(sprintf('User: [Username=%s] signed in at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response    = new Response();
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Create session for users who have signed in.
     * @param  Session $session - the HTTP Session
     * @param  User $user - the user object of current user
     */
    private function getSession($session, $user) {
        $session->set('isLoggedIn', true);
        $session->set('uid', $user->getUid());
    }

    /**
     * Handle the sign out requests.
     * @return a HTTP response object with JSON
     */
    public function signOutAction() {
        $ipAddress   = $this->request->getClientAddress();
        $user        = $this->getCurrentUser($this->session);

        $this->destroySession($this->session);
        $this->logger->log(sprintf('User: [Username=%s] signed out at %s.', $user, $ipAddress), Logger::INFO);
        
        $result      = array(
            'isSuccessful' => true,
        );
        $response    = new Response();
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Destroy session when the user signed out.
     * @param  Session $session - the HTTP Session
     */
    private function destroySession($session) {
        $session->destroy();
    }

    /**
     * Handle the sign up requests.
     * @return a HTTP response object with JSON
     */
    public function signUpAction() {
        $ipAddress              = $this->request->getClientAddress();
        $fullName               = $this->request->getPost('fullName');
        $username               = $this->request->getPost('username');
        $password               = $this->request->getPost('password');
        $email                  = $this->request->getPost('email');
        $isTokenValid           = $this->security->checkToken();

        $userService            = ServiceFactory::getService('UserService');
        $result                 = $userService->createAccount($fullName, $username, $password, $email, $isTokenValid);
        $result['csrfTokenKey'] = $this->security->getTokenKey();
        $result['csrfToken']    = $this->security->getToken();

        if ( $result['isSuccessful'] ) {
            $user    = $userService->getUserUsingUsernameOrEmail($username);
            $this->getSession($this->session, $user);
            $this->logger->log(sprintf('User: [Username=%s] created at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}
