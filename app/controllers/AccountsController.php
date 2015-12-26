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
        $logDir        = $this->config->application->logDir;
        $this->logger  = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Render to sign in page.
     */
    public function signInAction() {
        $isLoggedOut = $this->request->get('logout') == 'true';
        $forwardUrl  = $this->request->get('forward');

        if ( $this->isLoggedIn($this->session) ) {
            $response    = new Response();
            $response->redirect('/');
            return $response;
        }
        $this->tag->prependTitle($this->localization['accounts.signin.title']);
        $this->view->setVar('isLoggedOut', $isLoggedOut);
        $this->view->setVar('forwardUrl', $forwardUrl);
    }

    /**
     * Handle the sign in requests.
     * @return a HTTP response object with JSON
     */
    public function doSignInAction() {
        $ipAddress   = $this->request->getClientAddress();
        $username    = $this->request->getPost('username');
        $password    = $this->request->getPost('password');

        $userService = ServiceFactory::getService('UserService');
        $result      = $userService->isAccountValid($username, $password);
        $this->logger->log(sprintf('User: [Username=%s] tried to sign in at %s.', $username, $ipAddress), Logger::INFO);

        if ( $result['isSuccessful'] ) {
            $user    = $userService->getUserUsingUsernameOrEmail($username);
            $this->getSession($this->session, $user);
            $this->logger->log(sprintf('User: [%s] signed in at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response    = new Response();
        $response->setHeader('Content-Type', 'application/json');
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
        $this->logger->log(sprintf('User: [%s] signed out at %s.', $user, $ipAddress), Logger::INFO);
        
        $response    = new Response();
        $response->redirect('/accounts/signin?logout=true');
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
     * Render to sign up page.
     */
    public function signUpAction() {
        $forwardUrl  = $this->request->get('forward');
        $username    = $this->request->getPost('username');
        $email       = $this->request->getPost('email');
        $password    = $this->request->getPost('password');

        if ( $this->isLoggedIn($this->session) ) {
            $response    = new Response();
            $response->redirect('/');
            return $response;
        }
        $this->tag->prependTitle($this->localization['accounts.signup.title']);
        $this->view->setVar('isPost', $this->request->isPost());
        $this->view->setVar('username', $username);
        $this->view->setVar('email', $email);
        $this->view->setVar('password', $password);
        $this->view->setVar('forwardUrl', $forwardUrl);
    }

    /**
     * Handle the sign up requests.
     * @return a HTTP response object with JSON
     */
    public function doSignUpAction() {
        $username       = $this->request->getPost('username');
        $password       = $this->request->getPost('password');
        $email          = $this->request->getPost('email');
        $isTokenValid   = $this->security->checkToken();
        $userService    = ServiceFactory::getService('UserService');
        $result         = $userService->createAccount($username, $password, $email, $isTokenValid);
        
        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $result['isSuccessful'] ) {
            $user       = $userService->getUserUsingUsernameOrEmail($username);
            $ipAddress  = $this->request->getClientAddress();
            $this->getSession($this->session, $user);
            $this->logger->log(sprintf('User: [%s] created at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    public function verifyEmailAction() {
        $email              = $this->request->get('email');
        $token              = $this->request->get('token');
        $newPassword        = $this->request->get('newPassword');
        $confirmPassword    = $this->request->get('confirmPassword');
        $isTokenValid       = $this->security->checkToken();
        $userService        = ServiceFactory::getService('UserService');
        $result             = $userService->resetPassword($email, $token, $newPassword, $confirmPassword, $isTokenValid);

        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $result['isSuccessful'] ) {
            $user       = $userService->getUserUsingUsernameOrEmail($email);
            $ipAddress  = $this->request->getClientAddress();
            $this->logger->log(sprintf('User: [%s] reset password at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to user's profile page.
     * @param  long $uid - the unique ID of the user
     */
    public function userAction($uid) {
        $userService    = ServiceFactory::getService('UserService');
        $issueService   = ServiceFactory::getService('IssueService');
        $productService = ServiceFactory::getService('ProductService');
        $user           = $userService->getUserUsingUid($uid);

        if ( $user == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }

        $userMeta       = $userService->getUserMetaUsingUid($uid);
        $issueCount     = $issueService->getNumberOfIssueUsingHunter($uid);
        $productCount   = $productService->getNumberOfProductsUsingDeveloper($uid);

        $this->tag->prependTitle($user['username']);
        $this->view->setVar('user', $user);
        $this->view->setVar('issueCount', $issueCount);
        $this->view->setVar('productCount', $productCount);
        $this->view->setVars($userMeta);
    }

    /**
     * Get the issues submitted by a user.
     * @param  long $uid - the unique ID of the user
     * @return an HttpResponse with JSON data infer issues submitted by a user
     */
    public function getIssuesAction($uid) {
        $limit          = self::NUMBER_OF_ISSUES_PER_REQUEST;
        $offset         = $this->request->get('currentIssues');

        $issueService   = ServiceFactory::getService('IssueService');
        $issues         = $this->getIssuesInBestLanguage($issueService->getIssuesUsingHunter($uid, $offset, $limit));

        $result         = array(
            'isSuccessful'  => !empty($issues),
            'hasMore'       => count($issues) == $limit,
            'issues'        => $issues,
        );
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to reset password page.
     */
    public function resetPasswordAction() {
        $email          = $this->request->get('email');
        $token          = $this->request->get('token');
        $isTokenSet     = !empty($token);
        $isTokenValid   = false;

        if ( $this->isLoggedIn($this->session) ) {
            $response    = new Response();
            $response->redirect('/');
            return $response;
        }
        if ( $isTokenSet ) {
            $userService    = ServiceFactory::getService('UserService');
            $isTokenValid   = $userService->isEmailTokenValid($email, $token);
        }

        $this->tag->prependTitle($this->localization['accounts.reset-password.title']);
        $this->view->setVar('email', $email);
        $this->view->setVar('token', $token);
        $this->view->setVar('isTokenSet', $isTokenSet);
        $this->view->setVar('isTokenValid', $isTokenValid);
    }

    /**
     * Verify the username and email and send verification email.
     * @return an HttpResponse which contains JSON data infers whether the username and email exists
     */
    public function doForgotPasswordAction() {
        $username       = $this->request->get('username');
        $email          = $this->request->get('email');
        $isTokenValid   = $this->security->checkToken();
        $userService    = ServiceFactory::getService('UserService');
        $result         = $userService->forgotPassword($username, $email, $isTokenValid);

        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Reset the password if the email and token is correct.
     * @return an HttpResponse which contains JSON data infers whether the password is reset
     */
    public function doResetPasswordAction() {
        $email              = $this->request->get('email');
        $token              = $this->request->get('token');
        $newPassword        = $this->request->get('newPassword');
        $confirmPassword    = $this->request->get('confirmPassword');
        $isTokenValid       = $this->security->checkToken();
        $userService        = ServiceFactory::getService('UserService');
        $result             = $userService->resetPassword($email, $token, $newPassword, $confirmPassword, $isTokenValid);

        if ( $isTokenValid ) {
            $result['csrfTokenKey'] = $this->security->getTokenKey();
            $result['csrfToken']    = $this->security->getToken();
        }
        if ( $result['isSuccessful'] ) {
            $user       = $userService->getUserUsingUsernameOrEmail($email);
            $ipAddress  = $this->request->getClientAddress();
            $this->logger->log(sprintf('User: [%s] reset password at %s.', $user, $ipAddress), Logger::INFO);
        }
        $response = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Number of issues to get in a request.
     */
    const NUMBER_OF_ISSUES_PER_REQUEST = 25;

    /**
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}
