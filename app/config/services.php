<?php

use Phalcon\Config\Adapter\Ini as ConfigIni;
use Phalcon\DI\FactoryDefault;
use Phalcon\Events\Manager as EventsManager;
use Phalcon\Flash\Session as FlashSession;
use Phalcon\Mvc\Dispatcher;
use Phalcon\Mvc\Model\Manager as ModelsManager;
use Phalcon\Mvc\Url as UrlProvider;
use Phalcon\Mvc\View;
use Phalcon\Mvc\Model\Metadata\Memory as MetaData;
use Phalcon\Security;
use Phalcon\Session\Adapter\Files as SessionAdapter;
use Phalcon\Translate\Adapter\NativeArray as TranslateArray;

/**
 * The FactoryDefault Dependency Injector automatically register the right services 
 * providing a full stack framework.
 *
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
$di = new FactoryDefault();

/**
 * We register the events manager
 */
$di->set('dispatcher', function() use ($di) {
    $eventsManager = new EventsManager;
    /**
     * Check if the user is allowed to access certain action using the AccessControlPlugin
     */
    $eventsManager->attach('dispatch:beforeDispatch', new AccessControlPlugin);
    /**
     * Handle exceptions and not-found exceptions using ExceptionHandlingPlugin
     */
    $eventsManager->attach('dispatch:beforeException', new ExceptionHandlingPlugin);

    $dispatcher = new Dispatcher;
    $dispatcher->setEventsManager($eventsManager);

    return $dispatcher;
});

/**
 * Add routing capabilities.
 */
$di->set('router', function(){
    require APP_PATH . '/app/config/routes.php';
    return $router;
});

/**
 * Set config file to DI.
 */
$di->set('config', function() {
    $configFile = APP_PATH . '/app/config/config.ini';
    $config     = new ConfigIni($configFile);
    return $config;
});

/**
 * The URL component is used to generate all kind of urls in the application
 */
$di->set('url', function() use ($config){
    $url = new UrlProvider();
    $url->setBaseUri($config->application->baseUri);
    return $url;
});

/**
 * Setup the view component
 */
$di->set('view', function() use ($config) {
    $view = new View();
    $view->setViewsDir(APP_PATH . $config->application->viewDir);
    return $view;
});

/**
 * Database connection is created based in the parameters defined in the configuration file
 */
$di->set('db', function() use ($config) {
    $dbclass = 'Phalcon\Db\Adapter\Pdo\\' . $config->database->adapter;
    return new $dbclass(array(
        'host'     => $config->database->host,
        'username' => $config->database->username,
        'password' => $config->database->password,
        'dbname'   => $config->database->name,
        'charset'  => 'utf8',
    ));
});

/**
 * If the configuration specify the use of metadata adapter use it or use memory otherwise
 */
$di->set('modelsMetadata', function() {
    return new MetaData();
});

/**
 * Start the session the first time some component request the session service
 */
$di->set('session', function() {
    $session = new SessionAdapter();
    $session->start();
    return $session;
});

/**
 * Security component used for CSRF protection. 
 */
$di->set('security', function(){
    $security = new Security();
    $security->setWorkFactor(12);
    return $security;
}, true);

/**
 * Register the flash service with custom CSS classes
 */
$di->set('flash', function() {
    return new FlashSession(array(
        'error'   => 'alert alert-danger',
        'success' => 'alert alert-success',
        'notice'  => 'alert alert-info',
    ));
});

/**
 * Localization Service.
 */
$di->set('localization', function() use ($di, $config) { 
    $languageDectorPlugin   = new LanguageDectorPlugin();
    $session                = $di->getShared('session');
    $request                = $di->getShared('request');
    $language               = $languageDectorPlugin->getCurrentLanguage($request, $session);
    $languageDir            = APP_PATH . $config->application->languageDir;
    $languageFile           = "${languageDir}/${language}.php";

    if ( file_exists( $languageFile ) ) {
        require $languageFile;
    } else {
        require "${languageDir}/en.php";
    }
    return new TranslateArray(array(
        "content" => $messages
    ));
});
