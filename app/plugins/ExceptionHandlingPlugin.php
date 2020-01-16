<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 11:19:27
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Di\Injectable;
use Phalcon\Events\Event;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\Stream as LoggerAdapter;
use Phalcon\Dispatcher\Exception as DispatcherException;
use Phalcon\Dispatcher\AbstractDispatcher as MvcDispatcher;

/**
 * ExceptionPlugin handles all exceptions and display error pages for the application.
 *
 * @package TestZilla\plugin\ExceptionHandlingPlugin
 */
class ExceptionHandlingPlugin extends Injectable {
    /**
     * This action is executed before execute any action in the application
     * @param Event $event
     * @param Dispatcher $dispatcher
     */
    public function beforeException(Event $event, MvcDispatcher $dispatcher, Exception $exception) {
        if ( $exception instanceof DispatcherException ) {
            switch ( $exception->getCode() ) {
                case DispatcherException::EXCEPTION_HANDLER_NOT_FOUND:
                case DispatcherException::EXCEPTION_ACTION_NOT_FOUND:
                    $dispatcher->forward(array(
                        'controller'    => 'errors',
                        'action'        => 'resourceNotFound'
                    ));
                    return false;
            }
        }

        $logDir  = $this->config->application->logDir;
        $adapter = new LoggerAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
        $logger  = new Logger('messages', [
            'main' => $adapter,
        ]);
        $logger->error($exception->getMessage());
        $logger->error($exception->getTraceAsString());

        $dispatcher->forward(array(
            'controller'    => 'errors',
            'action'        => 'internalServerError'
        ));
        return false;
    }
}
