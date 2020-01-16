<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 11:19:40
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Acl\Adapter\Memory as AclAdapter;
use Phalcon\Acl\Enum as AclEnum;
use Phalcon\Acl\Role;
use Phalcon\Di\Injectable;
use Phalcon\Events\Event;
use Phalcon\Mvc\Dispatcher;

/**
 * AccessControlPlugincontrols that users only have access to the modules they're assigned to.
 *
 * @package TestZilla\plugin\AccessControlPlugin
 */
class AccessControlPlugin extends Injectable {
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

        if ( $isAllowed != AclEnum::ALLOW ) {
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
            $acl = new AclAdapter();
            $acl->setDefaultAction(AclEnum::DENY);

            //Register roles
            $roles = array(
                'guest'         => new Role('guest'),
                'user'          => new Role('user'),
                'administrator' => new Role('administrator'),
            );
            foreach ( $roles as $role ) {
                $acl->addRole($role);
            }

            // Components for all users
            $publicComponents   = array(
                'default'       => array('index', 'getCsrfToken', 'terms', 'privacy', 'changeLanguage'),
                'errors'        => array('notSupportedError', 'resourceNotFound', 'internalServerError'),
                'accounts'      => array('signIn', 'doSignIn', 'signUp', 'doSignUp', 'verifyEmail', 'signOut', 'user', 'getIssues', 'resetPassword', 'doForgotPassword', 'doResetPassword'),
                'products'      => array('index', 'getProducts', 'product', 'getIssues', 'newIssue', 'issue', 'getIssueReplies'),
            );
            foreach ( $publicComponents as $comp => $actions ) {
                $acl->addComponent($comp, $actions);
            }

            // Components for users logged in
            $loggedInComponents = array(
                'products'      => array('createIssue', 'createIssueReply'),
                'dashboard'     => array('index', 'profile', 'changePassword', 'updateProfile', 'products', 'getProducts', 'getProduct', 'createProduct', 'editProduct', 'receivedIssues', 'getReceivedIssues', 'submittedIssues', 'getSubmittedIssues'),
            );
            foreach ( $loggedInComponents as $comp => $actions ) {
                $acl->addComponent($comp, $actions);
            }

            // Components for administrators only
            $administrationComponents   = array(
                'administration'        => array('index', 'users', 'user', 'products', 'product', 'issues', 'issue'),
            );
            foreach ( $administrationComponents as $comp => $actions ) {
                $acl->addComponent($comp, $actions);
            }

            // Grant access to public areas to both guests, users and administrators
            foreach ( $roles as $role ) {
                foreach ( $publicComponents as $comp => $actions ) {
                    $acl->allow($role->getName(), $comp, $actions);
                }
            }
            // Grant acess to dashboard area to role users and administrators
            foreach ( $loggedInComponents as $comp => $actions) {
                $acl->allow('user', $comp, $actions);
                $acl->allow('administrator', $comp, $actions);
            }
            // Grant acess to administration area to role administrators
            foreach ( $administrationComponents as $comp => $actions) {
                $acl->allow('administrator', $comp, $actions);
            }

            // The acl is stored in session, APC would be useful here too
            $this->persistent->acl = $acl;
        }
        return $this->persistent->acl;
    }
}
