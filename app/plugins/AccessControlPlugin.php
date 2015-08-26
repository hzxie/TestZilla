<?php

use Phalcon\Acl;
use Phalcon\Acl\Adapter\Memory as AclList;
use Phalcon\Acl\Role;
use Phalcon\Acl\Resource;
use Phalcon\Events\Event;
use Phalcon\Mvc\User\Plugin;
use Phalcon\Mvc\Dispatcher;

/**
 * AccessControlPlugincontrols that users only have access to the modules they're assigned to.
 *
 * @package TestZilla\plugin\AccessControlPlugin
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class AccessControlPlugin extends Plugin {
    /**
     * This action is executed before execute any action in the application
     *
     * @param Event $event
     * @param Dispatcher $dispatcher
     */
    public function beforeDispatch(Event $event, Dispatcher $dispatcher) {
        // Do nothing
    }
}
