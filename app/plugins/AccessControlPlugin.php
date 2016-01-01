<?php

use Phalcon\Acl;
use Phalcon\Acl\Adapter\Memory as AclList;
use Phalcon\Acl\Role;
use Phalcon\Acl\Resource;
use Phalcon\Events\Event;
use Phalcon\Mvc\User\Plugin;
use Phalcon\Mvc\Dispatcher;

/**
 * AccessControlPlugincontrols that users only have access to the modules they're assigned to.
 *
 * @package TestZilla\plugin\AccessControlPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class AccessControlPlugin extends Plugin {
    /**
     * This action is executed before execute any action in the application
     *
     * @param Event $event
     * @param Dispatcher $dispatcher
     */
    public function beforeDispatch(Event $event, Dispatcher $dispatcher) {
        $userGroup       = 'guest';
        $isLoggedIn      = $this->isLoggedIn($this->session);

        if ( $isLoggedIn ) {
            $uid         = $this->session->get('uid');
            $userService = ServiceFactory::getService('UserService');
            $user        = $userService->getUserUsingUid($uid);
            $userGroup   = $user['userGroup']['userGroupSlug'];
        }

        $controller      = $dispatcher->getControllerName();
        $action          = $dispatcher->getActionName();
        $acl             = $this->getAcl();
        $isAllowed       = $acl->isAllowed($userGroup, $controller, $action);

        if ( $isAllowed != Acl::ALLOW ) {
            $dispatcher->forward(array(
                'controller' => 'errors',
                'action'     => 'resourceNotFound',
            ));
            return false;
        }
    }

    /**
     * Verify if the user has logged in according to Session.
     * @return if the user has logged in
     */
    private function isLoggedIn($session) {
        if ( $session->has('isLoggedIn') ) {
            $isLoggedIn = $session->get('isLoggedIn');
            
            if ( $isLoggedIn ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get access control list.
     * @return access control list
     */
    private function getAcl() {
        if ( !isset($this->persistent->acl) ) {
            $acl = new AclList();
            $acl->setDefaultAction(Acl::DENY);

            //Register roles
            $roles = array(
                'guest'         => new Role('guest'),
                'user'          => new Role('user'),
                'administrator' => new Role('administrator'),
            );
            foreach ( $roles as $role ) {
                $acl->addRole($role);
            }

            // Resources for all users
            $publicResources    = array(
                'default'       => array('index', 'getCsrfToken', 'terms', 'privacy', 'changeLanguage'),
                'errors'        => array('notSupportedError', 'resourceNotFound', 'internalServerError'),
                'accounts'      => array('signIn', 'doSignIn', 'signUp', 'doSignUp', 'verifyEmail', 'signOut', 'user', 'getIssues', 'resetPassword', 'doForgotPassword', 'doResetPassword'),
                'products'      => array('index', 'getProducts', 'product', 'getIssues', 'newIssue', 'issue', 'getIssueReplies'),
            );
            foreach ( $publicResources as $resource => $actions ) {
                $acl->addResource(new Resource($resource), $actions);
            }

            // Resources for users logged in
            $loggedInResources  = array(
                'products'      => array('createIssue', 'createIssueReply'),
                'dashboard'     => array('index', 'profile', 'changePassword', 'updateProfile', 'products', 'getProducts', 'getProduct', 'createProduct', 'editProduct', 'receivedIssues', 'getReceivedIssues', 'submittedIssues', 'getSubmittedIssues'),
            );
            foreach ( $loggedInResources as $resource => $actions ) {
                $acl->addResource(new Resource($resource), $actions);
            }

            // Resources for administrators only
            $administrationResources    = array(
                'administration'        => array('index', 'users', 'user', 'products', 'product', 'issues', 'issue'),
            );
            foreach ( $administrationResources as $resource => $actions ) {
                $acl->addResource(new Resource($resource), $actions);
            }

            // Grant access to public areas to both guests, users and administrators
            foreach ( $roles as $role ) {
                foreach ( $publicResources as $resource => $actions ) {
                    foreach ( $actions as $action ){
                        $acl->allow($role->getName(), $resource, $action);
                    }
                }
            }
            // Grant acess to dashboard area to role users and administrators
            foreach ( $loggedInResources as $resource => $actions) {
                foreach ($actions as $action){
                    $acl->allow('user', $resource, $action);
                    $acl->allow('administrator', $resource, $action);
                }
            }
            // Grant acess to administration area to role administrators
            foreach ( $administrationResources as $resource => $actions) {
                foreach ($actions as $action){
                    $acl->allow('administrator', $resource, $action);
                }
            }

            // The acl is stored in session, APC would be useful here too
            $this->persistent->acl = $acl;
        }
        return $this->persistent->acl;
    }
}
