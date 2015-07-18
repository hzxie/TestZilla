<?php

use Phalcon\Events\Event;
use Phalcon\Mvc\User\Plugin;
use Phalcon\Mvc\Dispatcher;

/**
 * Recommend best language for user according to the Accept-Language in 
 * HTTP header.
 * 
 * @package TestZilla\plugin\LanguageDectorPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class LanguageDectorPlugin extends Plugin {
    /**
     * Recommend best language for user according to the Accept-Language 
     * in HTTP header.
     * @param  String $request - the HTTP Request
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
