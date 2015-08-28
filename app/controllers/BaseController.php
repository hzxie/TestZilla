<?php

use Phalcon\Mvc\Controller;
use Phalcon\Mvc\View;

/**
 * The base controller that provides common functions for all
 * other controllers.
 *
 * @package TestZilla\controller\BaseController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class BaseController extends Controller {
    /**
     * Set the view template.
     */
    protected function __initialize() {
        if ( $this->request->isAjax() ) {
            $this->view->setRenderLevel(View::LEVEL_ACTION_VIEW);
        }

        $this->tag->setTitle(' | ' . $this->getWebsiteName());

        $this->view->setTemplateAfter('layout');
        $this->view->setVar('localization', $this->localization);
        $this->view->setVar('cdnUrl', $this->config->application->cdnUrl);
        $this->view->setVar('language', $this->getPreferNaturalLanguage());
        $this->view->setVar('appVerion', APP_VERSION);
        
        // Website Options
        $this->view->setVar('websiteName', $this->getWebsiteName());
        $this->view->setVar('description', $this->getDescription());
        $this->view->setVar('copyright', $this->getCopyright());
        $this->view->setVar('googleAnalyticsCode', $this->getGoogleAnalyticsCode());
        $this->view->setVar('contactAddress', $this->getContactAddress());
        $this->view->setVar('contactPhone', $this->getContactPhone());
        $this->view->setVar('contactEmail', $this->getContactEmail());

        // Load Profile for users
        $isLoggedIn = $this->isLoggedIn($this->session);
        $this->view->setVar('isLoggedIn', $isLoggedIn);
        if ( $isLoggedIn ) {
            $user   = $this->getCurrentUser($this->session);
            $this->view->setVar('user', $user);
        }
    }

    /**
     * Get all autoload options from database.
     * @param  String $optionName - the key of the option
     * @return the value of the option
     */
    private function getOptionValue($optionName) {
        if ( empty(self::$options) ) {
            $resultSet = Option::find("is_autoload = 1");
            foreach ( $resultSet as $rowSet ) {
                $optionKey                  = $rowSet->getOptionKey();
                $optionValue                = $rowSet->getOptionValue();
                self::$options[$optionKey]  = $optionValue;
            }
        }

        if ( !array_key_exists($optionName, self::$options) ) {
            return NULL;
        }
        return self::$options[$optionName];
    }

    /**
     * Get the name of the website from database options.
     * @return the name of the website
     */
    private function getWebsiteName() {
        return $this->getOptionValue('WebsiteName');
    }

    /**
     * Get the description of the website from database options.
     * @return the description of the website.
     */
    private function getDescription() {
        return $this->getOptionValue('Description');
    }

    /**
     * Get the copyright of the website from database options.
     * @return the copyright of the website
     */
    private function getCopyright() {
        return $this->getOptionValue('Copyright');
    }

    /**
     * Get the analytics code of the website from database options.
     * @return the analytics code of the website
     */
    private function getGoogleAnalyticsCode() {
        return $this->getOptionValue('GoogleAnalyticsCode');
    }

    /**
     * Get the contact address of the website from database options.
     * @return the contact address of the website
     */
    private function getContactAddress() {
        return $this->getOptionValue('ContactAddress');
    }

    /**
     * Get the contact phone of the website from database options.
     * @return the contact phone of the website
     */
    private function getContactPhone() {
        return $this->getOptionValue('ContactPhone');
    }

    /**
     * Get the contact email of the website from database options.
     * @return the contact email of the website
     */
    private function getContactEmail() {
        return $this->getOptionValue('ContactEmail');
    }

    /**
     * Get the Accept-Language from HTTP request.
     * @return the language code of user's preference
     */
    private function getPreferNaturalLanguage() {
        $languageDectorPlugin = new LanguageDectorPlugin();
        return $languageDectorPlugin->getCurrentLanguage($this->request, $this->session);
    }

    /**
     * Verify if the user has logged in according to Session.
     * @return if the user has logged in
     */
    protected function isLoggedIn($session) {
        if ( $session->has('isLoggedIn') ) {
            $isLoggedIn = $session->get('isLoggedIn');
            
            if ( $isLoggedIn ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get current User object from session.
     * @param  Session $session - the HTTP Session
     * @return an array contains information of user logged in
     */
    protected function getCurrentUser($session) {
        $userService = ServiceFactory::getService('UserService');
        $uid         = $session->get('uid');
        $user        = $userService->getUserUsingUid($uid);
        
        return $user;
    }

    /**
     * Get current User object from session.
     * @param  Session $session - the HTTP Session
     * @return the current User object or NULL
     */
    protected function getCurrentUserObject($session) {
        $userService = ServiceFactory::getService('UserService');
        $uid         = $session->get('uid');
        return $userService->getUserObjectUsingUid($uid);
    }

    /**
     * Redirect to another function to handle this request.
     * @param  String $uri - the URI to redirect
     */
    protected function forward($uri) {
        $uriParts = explode('/', $uri);
        $params   = array_slice($uriParts, 2);

        return $this->dispatcher->forward(
            array(
                'controller' => $uriParts[0],
                'action'     => $uriParts[1],
                'params'     => $params
            )
        );
    }

    /**
     * All autoload options of the application.
     * @var array
     */
    private static $options = array();
}
