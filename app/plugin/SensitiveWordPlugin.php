<?php

use Phalcon\Events\Event;
use Phalcon\Mvc\User\Plugin;
use Phalcon\Mvc\Dispatcher;

/**
 * Filter the sensitive words.
 * 
 * @package TestZilla\plugin\SensitiveWordPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class SensitiveWordPlugin extends Plugin {
    /**
     * Get content without sensitive words.
     * @param  String $content - the content need to be filtered
     * @return the content without sensitive words
     */
    public function getFilteredContent($content) {
        $sensitiveWords = $this->getSensitiveWords();
        return $this->getContentAfterRidOfSensitiveWords($sensitiveWords, $content);
    }

    /**
     * Get content without sensitive words.
     * @param  String $content        - the content need to be filtered
     * @param  Array  $sensitiveWords - an array which contains sensitve words
     * @return the content without sensitive words
     */
    private function getContentAfterRidOfSensitiveWords(&$sensitiveWords, $content) {
        // TODO: Implement this function
        return $content;
    }

    /**
     * Get sensitive words from database.
     * @return an array which contains sensitve words
     */
    private function getSensitiveWords() {
        if ( empty(self::$sensitiveWords) ) {
            $rowSet                 = Option::findFirst("option_key = 'SensitiveWords'");
            self::$sensitiveWords   = json_decode($rowSet->getOptionValue()); 
        }
        return self::$sensitiveWords;
    }

    /**
     * An array which contains sensitve words
     * @var array
     */
    private static $sensitiveWords = array();
}