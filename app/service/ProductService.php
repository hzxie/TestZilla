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
     * Get the ID of a ProductCategory.
     * @param  String $productCategorySlug - the unique slug of the ProductCategory
     * @return the unique ID of the ProductCategory
     */
    public function getProductCategoryId($productCategorySlug) {
        $rowSet     = ProductCategory::findFirst("product_category_slug = '${productCategorySlug}'");
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getProductCategoryId();
    }

    public function getProductUsingId($productId) {

    }

    public function getProductsUsingKeyword($keyword, $offset, $limit) {
    }

    /**
     * Get products of a certain category.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @param  long   $offset            - the index of first record of result set
     * @param  int    $limit             - the number of records to get for each request
     * @return an array which contains products of a certain category
     */
    public function getProductsUsingCategory($productCategoryId, $offset, $limit) {
        $products       = array();
        $whereCondition = $productCategoryId == 0 ? '' : "product_category_id = ${productCategoryId}";

        $resultSet      = Product::find(array(
            $whereCondition,
            'limit'     => $limit,
            'offset'    => $offset,
        ));
        foreach ( $resultSet as $rowSet ) {
            array_push($products, array(
                'productId'         => $rowSet->getProductId(),
                'productName'       => (array)json_decode($rowSet->getProductName()),
                'productCategory'   => (array)json_decode($rowSet->getProductCategory()->getProductCategoryName()),
                'productLogo'       => $rowSet->getProductLogo(),
                'latestVersion'     => $rowSet->getLatestVersion(),
                'description'       => (array)json_decode($rowSet->getDescription()),
            ));
        }
        return $products;
    }

    /**
     * Get number of products of a certain category.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @return number of products of a certain category
     */
    public function getProductsCountUsingCategory($productCategoryId) {
        $whereCondition = $productCategoryId == 0 ? '' : "product_category_id = ${productCategoryId}";
        $resultSet      = Product::find($whereCondition);

        return $resultSet->count();
    }

    /**
     * The logger of UserService.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}