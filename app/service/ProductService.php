<?php

use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;

/**
 * The bussiness layer for AccountsController.
 *
 * @package TestZilla\Service\ProductService
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class ProductService extends Service {
    /**
     * Initialize the Service
     */
    public function initialize() {
        parent::initialize();
        $logDir            = $this->config->application->logDir;
        $this->logger      = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Get all categories of product.
     * @return an array with contains information of product categories.
     */
    public function getProductCategories() {
        $categories = array();
        $resultSet  = ProductCategory::find();

        foreach ( $resultSet as $rowSet ) {
            array_push($categories, array(
                'productCategoryId'     => $rowSet->getProductCategoryId(),
                'productCategorySlug'   => $rowSet->getProductCategorySlug(),
                'productCategoryName'   => (array)json_decode($rowSet->getProductCategoryName()),
            ));
        }
        return $categories;
    }

    /**
     * The logger of UserService.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}