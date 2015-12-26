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
     * Get content without offensive words.
     * @param  String $content - the content need to be filtered
     * @return the content without offensive words
     */
    protected function getFilteredContent($content) {
        $offensiveWordPlugin = new OffensiveWordPlugin();
        return $offensiveWordPlugin->getFilteredContent($content);
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
     * Get the best language for multi-language content for products.
     * @param  Array $products - an array of product list in multi-languages
     * @return an array of a list of product with the best language
     */
    protected function getProductsInBestLanguage($products) {
        if ( empty($products) ) {
            return $products;
        }
        foreach ( $products as &$product ) {
            $product = $this->getProductInBestLanguage($product);
        }
        return $products;
    }

    /**
     * Get the best language for multi-language content for product.
     * @param  Array $product - an array contains detail information of the product
     * @return an array contains detail information of the product with the best language
     */
    protected function getProductInBestLanguage($product) {
        if ( empty($product) ) {
            return $product;
        }
        if ( array_key_exists('productName', $product) ) {
            $product['productName']     = $this->getBestLanguageForContent($product['productName'], $this->request, $this->session);
        }
        if ( array_key_exists('productCategory', $product) ) {
            $product['productCategory']['productCategoryName'] = 
                $this->getBestLanguageForContent($product['productCategory']['productCategoryName'], $this->request, $this->session);
        }
        if ( array_key_exists('prerequisites', $product) ) {
            $product['prerequisites']   = $this->getBestLanguageForContent($product['prerequisites'], $this->request, $this->session);
        }
        if ( array_key_exists('description', $product) ) {
            $product['description']     = $this->getBestLanguageForContent($product['description'], $this->request, $this->session);
        }
        return $product;
    }

    /**
     * Get the best language for multi-language content for issues.
     * @param  Array $issues - an array of issue list in multi-languages
     * @return an array of a list of issue with the best language
     */
    protected function getIssuesInBestLanguage($issues) {
        if ( empty($issues) ) {
            return $issues;
        }

        foreach ( $issues as &$issue ) {
            $issue = $this->getIssueInBestLanguage($issue);
        }
        return $issues;
    }

    /**
     * Get the best language for multi-language content for issue.
     * @param  Array $issue - an array contains detail information of the issue
     * @return an array contains detail information of the issue with the best language
     */
    protected function getIssueInBestLanguage($issue) {
        if ( empty($issue) ) {
            return $issue;
        }
        if ( array_key_exists('product', $issue) ) {
            $issue['product']       = $this->getProductInBestLanguage($issue['product']);
        }
        if ( array_key_exists('issueCategory', $issue) ) {
            $issue['issueCategory'] = $this->getIssueCategoryInBestLanguage($issue['issueCategory']);
        }
        if ( array_key_exists('issueStatus', $issue) ) {
            $issue['issueStatus']   = $this->getIssueStatusInBestLanguage($issue['issueStatus']);
        }
        return $issue;
    }

    /**
     * Get the best language for multi-language content for categories of issues.
     * @param  Array $issueCategories - an array of product list in multi-languages
     * @return an array of a list of category of issues with the best language
     */
    protected function getIssueCategoriesInBestLanguage($issueCategories) {
        if ( empty($issueCategories) ) {
            return $issueCategories;
        }
        foreach ( $issueCategories as &$issueCategory ) {
            $issueCategory = $this->getIssueCategoryInBestLanguage($issueCategory);
        }
        return $issueCategories;
    }

    /**
     * Get the best language for multi-language content for category of issues.
     * @param  Array $issueCategory - an array contains detail information of the category of issues
     * @return an array contains detail information of the category of issues with the best language
     */
    protected function getIssueCategoryInBestLanguage($issueCategory) {
        if ( empty($issueCategory) ) {
            return $issueCategory;
        }
        if ( array_key_exists('issueCategoryName', $issueCategory) ) {
            $issueCategory['issueCategoryName'] = $this->getBestLanguageForContent($issueCategory['issueCategoryName'], $this->request, $this->session);
        }
        return $issueCategory;
    }

    /**
     * * Get the best language for multi-language content for status of issues list.
     * @param  Array $issueStatusList - an array of status of issues list in multi-languages
     * @return an array of a list of status of issues with the best language
     */
    protected function getIssueStatusListInBestLanguage($issueStatusList) {
        if ( empty($issueStatusList) ) {
            return $issueStatusList;
        }
        foreach ( $issueStatusList as &$issueStatus ) {
            $issueStatus = $this->getIssueStatusInBestLanguage($issueStatus);
        }
        return $issueStatusList;
    }

    /**
     * Get the best language for multi-language content for status of issues.
     * @param  Array $issueStatus - an array contains detail information of the status of issues
     * @return an array contains detail information of the status of issues with the best language
     */
    protected function getIssueStatusInBestLanguage($issueStatus) {
        if ( empty($issueStatus) ) {
            return $issueStatus;
        }
        if ( array_key_exists('issueStatusName', $issueStatus) ) {
            $issueStatus['issueStatusName'] = $this->getBestLanguageForContent($issueStatus['issueStatusName'], $this->request, $this->session);
        }
        return $issueStatus;
    }
    
    /**
     * Get the best language for multi-language content.
     * @param  Array       $content - multi-language content
     * @param  HttpRequest $request - the HTTP request
     * @param  HttpSession $session - the HTTP session
     * @return the best language for multi-language content
     */
    protected function getBestLanguageForContent($content, $request, $session) {
        if ( !is_array($content) ) {
            return $content;
        }
        $languageDectorPlugin   = new LanguageDectorPlugin();
        $currentLanguage        = $languageDectorPlugin->getCurrentLanguage($request, $session);
        $defaultLanguage        = 'en';
        $firstLanguage          = key($content);

        if ( array_key_exists($currentLanguage, $content) ) {
            return $content[$currentLanguage];
        } else if ( array_key_exists($defaultLanguage, $content) ) {
            return $content[$defaultLanguage];
        }
        return $content[$firstLanguage];
    }

    /**
     * All autoload options of the application.
     * @var array
     */
    private static $options = array();
}
