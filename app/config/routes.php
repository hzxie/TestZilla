<?php

use Phalcon\Mvc\Router;

/**
 * The global router of the application.
 *
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
$router = new Router();

/* Routers for DefaultController */
$router->add('/', array(
    'controller' => 'default',
    'action'     => 'index',
));
$router->add('/getCsrfToken.action', array(
    'controller' => 'default',
    'action'     => 'getCsrfToken',
));
$router->add('/terms', array(
    'controller' => 'default',
    'action'     => 'terms',
));
$router->add('/privacy', array(
    'controller' => 'default',
    'action'     => 'privacy',
));
$router->add('/changeLanguage.action', array(
    'controller' => 'default',
    'action'     => 'changeLanguage',
));

/* Routers for ErrorsController */
$router->add('/not-supported', array(
    'controller' => 'errors',
    'action'     => 'notSupportedError',
));

/* Routers for AccountsController */
$router->add('/accounts/signin', array(
    'controller' => 'accounts',
    'action'     => 'signInView',
));
$router->add('/accounts/signin.action', array(
    'controller' => 'accounts',
    'action'     => 'signIn',
));
$router->add('/accounts/signup.action', array(
    'controller' => 'accounts',
    'action'     => 'signUp',
));
$router->add('/accounts/signout.action', array(
    'controller' => 'accounts',
    'action'     => 'signOut',
));

$router->handle();
