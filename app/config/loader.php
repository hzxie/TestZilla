<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 13:07:07
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:11
 */

$loader = new \Phalcon\Loader();

/**
 * We're a registering a set of directories taken from the configuration file
 */
$loader->registerDirs(
	array(
		APP_PATH . $config->application->controllerDir,
		APP_PATH . $config->application->modelDir,
		APP_PATH . $config->application->viewDir,
		APP_PATH . $config->application->languageDir,
		APP_PATH . $config->application->libraryDir,
		APP_PATH . $config->application->logDir,
		APP_PATH . $config->application->pluginDir,
		APP_PATH . $config->application->serviceDir,
	)
)->register();
