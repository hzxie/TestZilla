<?php

use Phalcon\Dispatcher;
use Phalcon\Events\Event;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\Dispatcher\Exception as DispatcherException;
use Phalcon\Mvc\Dispatcher as MvcDispatcher;
use Phalcon\Mvc\User\Plugin;

/**
 * ExceptionPlugin handles all exceptions and display error pages for the application.
 *
 * @package TestZilla\plugin\ExceptionHandlingPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class ExceptionHandlingPlugin extends Plugin {
    /**
     * This action is executed before execute any action in the application
     * @param Event $event
     * @param Dispatcher $dispatcher
     */
    public function beforeException(Event $event, MvcDispatcher $dispatcher, Exception $exception) {
        if ( $exception instanceof DispatcherException ) {
            switch ( $exception->getCode() ) {
                case Dispatcher::EXCEPTION_HANDLER_NOT_FOUND:
                case Dispatcher::EXCEPTION_ACTION_NOT_FOUND:
                    $dispatcher->forward(array(
                        'controller'    => 'errors',
                        'action'        => 'resourceNotFound'
                    ));
                    return false;
            }
        }

        $logDir = $this->config->application->logDir;
        $logger = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
        $logger->log($exception->getMessage(), Logger::ERROR);
        $logger->log($exception->getTraceAsString(), Logger::ERROR);

        $dispatcher->forward(array(
            'controller'    => 'errors',
            'action'        => 'internalServerError'
        ));
        return false;
    }
}
