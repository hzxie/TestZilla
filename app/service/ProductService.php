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
        $rowSet = ProductCategory::findFirst("product_category_slug = '${productCategorySlug}'");
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getProductCategoryId();
    }

    /**
     * Get detail information of a product
     * @param  long $productId - the unique ID of the product
     * @return an array which contains information of the product
     */
    public function getProductUsingId($productId) {
        $rowSet = Product::findFirst("product_id = '${productId}'");

        if ( $rowSet == NULL ) {
            return NULL;
        }
        return array(
            'productId'         => $rowSet->getProductId(),
            'productName'       => (array)json_decode($rowSet->getProductName()),
            'productCategory'   => (array)json_decode($rowSet->getProductCategory()->getProductCategoryName()),
            'productLogo'       => $rowSet->getProductLogo(),
            'latestVersion'     => $rowSet->getLatestVersion(),
            'productUrl'        => $rowSet->getProductUrl(),
            'developer'         => array(
                'uid'           => $rowSet->getDeveloper()->getUid(),
                'username'      => $rowSet->getDeveloper()->getUsername(),
                'email'         => $rowSet->getDeveloper()->getEmail(),
            ),
            'prerequisites'     => (array)json_decode($rowSet->getPrerequisites()),
            'description'       => (array)json_decode($rowSet->getDescription()),
        );
    }

    /**
     * Get products of a certain category.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @param  String $keyword           - the keyword of the product name
     * @param  long   $offset            - the index of first record of result set
     * @param  int    $limit             - the number of records to get for each request
     * @return an array which contains products of a certain category
     */
    public function getProductsUsingCategory($productCategoryId, $keyword, $offset, $limit) {
        $products       = array();
        $conditions     = $this->getQueryOfProductsUsingCategoryAndKeyword($productCategoryId, $keyword);

        $resultSet      = Product::find(array_merge($conditions, array(
            'limit'     => $limit,
            'offset'    => $offset,
            'order'     => 'product_id DESC'
        )));
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
     * @param  String $keyword           - the keyword of the product name
     * @return number of products of a certain category
     */
    public function getProductsCountUsingCategory($productCategoryId, $keyword) {
        $conditions     = $this->getQueryOfProductsUsingCategoryAndKeyword($productCategoryId, $keyword);
        $resultSet      = Product::find($conditions);

        return $resultSet->count();
        return 0;
    }

    /**
     * Get the conditions of query statement.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @param  String $keyword           - the keyword of the product name
     * @return the conditions of query statement
     */
    private function getQueryOfProductsUsingCategoryAndKeyword($productCategoryId, $keyword) {
        $conditions             = '';
        $parameters             = array();
        $isFirstCondition       = true;

        if ( $productCategoryId != 0 ) {
            $isFirstCondition   = false;
            $conditions        .= ' product_category_id = ?1';
            $parameters         = array_replace($parameters, array(
                1               => $productCategoryId,
            ));
        }
        if ( !empty(trim($keyword)) ) {
            if ( !$isFirstCondition ) {
                $conditions    .= ' AND ';
            }
            $conditions        .= 'product_name LIKE ?2';
            $parameters         = array_replace($parameters, array(
                2               => "%{$keyword}%",
            ));
        }
        return array(
            'conditions'        => $conditions,
            'bind'              => $parameters,
        );
    }
}