<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 11:19:51
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Di\Injectable;
use Phalcon\Events\Event;
use Phalcon\Mvc\Dispatcher;

/**
 * Recommend best language for user according to the Accept-Language in 
 * HTTP header.
 * 
 * @package TestZilla\plugin\LanguageDectorPlugin
 */
class LanguageDectorPlugin extends Injectable {
    /**
     * Recommend best language for user according to the Accept-Language 
     * in HTTP header.
     * @param  HttpRequest $request - the HTTP request
     * @return the language code of recommend language
     */
    public function getBestLanguage($request) {
        $languages    = $this->request->getLanguages();
        $languageDir  = APP_PATH . $this->config->application->languageDir;

        foreach ( $languages as $language ) {
            $languageCode = $this->getLanguageCodeWithoutLocale($language['language']);
            $languageFile = "${languageDir}/{$languageCode}.php";

            if ( file_exists( $languageFile ) ) {
                return $languageCode;
            }
        }
        return self::DEFAULT_LANGUAGE;
    }

    /**
     * Get current language of the view.
     * @param  HttpRequest $request - the HTTP request
     * @param  HttpSession $request - the HTTP session
     * @return the current language of the view
     */
    public function getCurrentLanguage($request, $session) {
        $language     = self::DEFAULT_LANGUAGE;
        if ( $session->has('language') ) {
            $language = $session->get('language');
        } else {
            $language = $this->getBestLanguage($request);
        }

        $languageDir  = APP_PATH . $this->config->application->languageDir;
        $languageFile = "${languageDir}/${language}.php";
        if ( file_exists( $languageFile ) ) {
            return $language;
        }
        return self::DEFAULT_LANGUAGE;
    }

    /**
     * Get the language code without locale.
     * @param  String $languageCode - the language code(eg: en-US, en)
     * @return the language code without locale(eg: em)
     */
    private function getLanguageCodeWithoutLocale($languageCode) {
        if ( strlen($languageCode) > 2 ) {
            $languageCode = substr($languageCode, 0, 2);
        }
        return strtolower($languageCode);
    }

    /**
     * The default language to fallback.
     */
    const DEFAULT_LANGUAGE = 'en';
}
