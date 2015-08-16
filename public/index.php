<?php

error_reporting(E_ALL);

use Phalcon\Mvc\Application;
use Phalcon\Config\Adapter\Ini as ConfigIni;

/**
 * Application BootStrap.
 *
 * @package TestZilla
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
try {
    /**
     * The physical path of the application.
     */
    define('APP_PATH', realpath('..') . '/');

    /**
     * The version of the application.
     * WARNING: Please DO NOT change it.
     */
    define('APP_VERSION', '2.0.0');

    /**
     * Read the configuration
     */
    $config = new ConfigIni(APP_PATH . 'app/config/config.ini');

    /**
     * Auto-loader configuration
     */
    require APP_PATH . 'app/config/loader.php';

    /**
     * Load application services
     */
    require APP_PATH . 'app/config/services.php';

    $application = new Application($di);

    echo $application->handle()->getContent();

} catch (Exception $e){
    echo $e->getMessage();
}
