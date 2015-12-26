<?php

use Phalcon\Events\Event;
use Phalcon\Mvc\User\Plugin;
use Phalcon\Mvc\Dispatcher;

/**
 * Filter the offensive words.
 * 
 * @package TestZilla\plugin\OffensiveWordPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class OffensiveWordPlugin extends Plugin {
    /**
     * Get content without offensive words.
     * @param  String $content - the content need to be filtered
     * @return the content without offensive words
     */
    public function getFilteredContent($content) {
        $offensiveWords = $this->getOffensiveWords();
        return $this->getContentAfterRidOfOffensiveWords($offensiveWords, $content);
    }

    /**
     * Get content without offensive words.
     * @param  String $content        - the content need to be filtered
     * @param  Array  $offensiveWords - an array which contains sensitve words
     * @return the content without offensive words
     */
    private function getContentAfterRidOfOffensiveWords(&$offensiveWords, $content) {
        // TODO: Implement this function
        return $content;
    }

    /**
     * Get offensive words from database.
     * @return an array which contains sensitve words
     */
    private function getOffensiveWords() {
        if ( empty(self::$offensiveWords) ) {
            $rowSet = Option::findFirst("option_key = 'OffensiveWords'");

            if ( $rowSet != NULL ) {
                self::$offensiveWords = json_decode($rowSet->getOptionValue()); 
            }
        }
        return self::$offensiveWords;
    }

    /**
     * An array which contains sensitve words
     * @var array
     */
    private static $offensiveWords = array();
}