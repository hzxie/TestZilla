<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 11:07:07
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:30:41
 */
error_reporting(E_ALL);

use Phalcon\Config\Adapter\Ini as ConfigIni;
use Phalcon\Http\Request;
use Phalcon\Mvc\Application;

/**
 * Application BootStrap.
 *
 * @package TestZilla
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

    $request = new Request();
    $application = new Application($di);
    $uri = substr($request->getUri(), strlen($config->application->baseUri));
    $response = $application->handle($uri);
    $response->send();
} catch (Exception $e){
    echo $e->getTraceAsString();
}
