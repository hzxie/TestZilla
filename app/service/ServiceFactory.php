<?php

/**
 * The bussiness layer for AccountsController.
 *
 * @package TestZilla\Service\ServiceFactory
 */
class ServiceFactory {
	/**
	 * Get the instance of a service.
	 * @param  String $serviceName - the name of service
	 * @return the instance of the expected service
	 */
	public static function getService($serviceName) {
		if ( !class_exists($serviceName) ) {
			throw new Exception("[Serivce\ServiceFactory] ClassName: $serviceName NOT FOUND!");
		}

		return new $serviceName;
	}
}