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
        $this->view->setVar('websiteName', self::$websiteName);
        $this->view->setVar('description', $this->getDescription());
        $this->view->setVar('copyright', $this->getCopyright());
        $this->view->setVar('googleAnalyticsCode', $this->getGoogleAnalyticsCode());

        // Load Profile for users
        $isLoggedIn = $this->isLoggedIn($this->session);
        if ( $isLoggedIn ) {
            $user   = $this->getCurrentUser($this->session);
            $this->view->setVar('isLoggedIn', $isLoggedIn);
            $this->view->setVar('user', $user);
        }
    }

    /**
     * Get the name of the website from database options.
     * @return the name of the website
     */
    private function getWebsiteName() {
        if ( self::$websiteName != NULL ) {
            return self::$websiteName;
        }

        $websiteNameOption = Option::findFirst("option_key = 'WebsiteName'");
        self::$websiteName = $websiteNameOption->getOptionValue();

        return self::$websiteName;
    }

    /**
     * Get the description of the website from database options.
     * @return the description of the website.
     */
    private function getDescription() {
        if ( self::$description != NULL ) {
            return self::$description;
        }

        $descriptionOption = Option::findFirst("option_key = 'Description'");
        self::$description = $descriptionOption->getOptionValue();

        return self::$description;
    }

    /**
     * Get the copyright of the website from database options.
     * @return the copyright of the website
     */
    private function getCopyright() {
        if ( self::$copyright != NULL ) {
            return self::$copyright;
        }

        $copyrightOption = Option::findFirst("option_key = 'Copyright'");
        $copyright       = $copyrightOption->getOptionValue();

        // Update the '${year}' field in copyright
        self::$copyright = str_replace('%year%', date('Y'), $copyright);

        return self::$copyright;
    }

    /**
     * Get if the instance is for internal usage.
     * @return if it's for internal usage
     */
    private function isInternalUsage() {
        if ( self::$isInternalUsage != -1 ) {
            return self::$isInternalUsage;
        }

        $isInternalUsageOption = Option::findFirst("option_key = 'InternalUsage'");
        self::$isInternalUsage = $isInternalUsageOption->getOptionValue();

        return self::$isInternalUsage;
    }

    /**
     * Get the analytics code of the website from database options.
     * @return the analytics code of the website
     */
    private function getGoogleAnalyticsCode() {
        if ( self::$googleAnalyticsCode != NULL ) {
            return self::$googleAnalyticsCode;
        }

        $googleAnalyticsCodeOption = Option::findFirst("option_key = 'GoogleAnalyticsCode'");
        self::$googleAnalyticsCode = $googleAnalyticsCodeOption->getOptionValue();

        return self::$googleAnalyticsCode;
    }

    /**
     * Get the Accept-Language from HTTP request.
     * @return the language code of user's preference
     */
    private function getPreferNaturalLanguage() {
        $languageDectorPlugin = new LanguageDectorPlugin();
        return $languageDectorPlugin->getBestLanguage($this->request);
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
     * Get current User object from session.
     * @param  Session $session - the HTTP Session
     * @return the current User object or NULL
     */
    protected function getCurrentUser($session) {
        $userService = ServiceFactory::getService('UserService');
        $uid         = $session->get('uid');
        $user        = $userService->getUserUsingUid($uid);

        return $user;
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
     * The name of the website.
     * @var String
     */
    private static $websiteName = NULL;

    /**
     * The description of the website.
     * @var String
     */
    private static $description = NULL;

    /**
     * The copyright of the website.
     * @var String
     */
    private static $copyright = NULL;

    /**
     * If the value is true, the 'School & Partners' item won't
     * displayed in the website.
     * @var boolean
     */
    private static $isInternalUsage = -1;

    /**
     * The analytics code of website.
     * @var String
     */
    private static $googleAnalyticsCode = null;
}
