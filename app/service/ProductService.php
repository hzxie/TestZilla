<?php

use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;

/**
 * The bussiness layer for products.
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
    }

    /**
     * Get all categories of product.
     * @return an array with contains information of product categories
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
        $rowSet = ProductCategory::findFirst(array(
            'conditions'    => 'product_category_slug = ?1',
            'bind'          => array(
                1           => $productCategorySlug,
            ),
        ));
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getProductCategoryId();
    }

    /**
     * Get detail information of a product
     * @param  long $productId - the unique ID of the product
     * @return an object of Product which contains information of the product
     */
    public function getProductObjectUsingId($productId) {
        $rowSet = Product::findFirst(array(
            'conditions'    => 'product_id = ?1',
            'bind'          => array(
                1           => $productId,
            ),
        ));
        return $rowSet;
    }

    /**
     * Get detail information of a product
     * @param  long $productId - the unique ID of the product
     * @return an array which contains information of the product
     */
    public function getProductUsingId($productId) {
        $rowSet = $this->getProductObjectUsingId($productId);

        if ( $rowSet == NULL ) {
            return NULL;
        }
        return array(
            'productId'         => $rowSet->getProductId(),
            'productName'       => (array)json_decode($rowSet->getProductName()),
            'productCategory'   => array(
                'productCategoryId'     => $rowSet->getProductCategory()->getProductCategoryId(),
                'productCategoryName'   => (array)json_decode($rowSet->getProductCategory()->getProductCategoryName()),
            ),
            'productLogo'       => $rowSet->getProductLogo(),
            'latestVersion'     => $rowSet->getLatestVersion(),
            'productUrl'        => $rowSet->getProductUrl(),
            'developer'         => array(
                'uid'                   => $rowSet->getDeveloper()->getUid(),
                'username'              => $rowSet->getDeveloper()->getUsername(),
                'email'                 => $rowSet->getDeveloper()->getEmail(),
            ),
            'prerequisites'     => (array)json_decode($rowSet->getPrerequisites()),
            'description'       => (array)json_decode($rowSet->getDescription()),
        );
    }

    /**
     * Get products in a certain category.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @param  long   $offset            - the index of first record of result set
     * @param  int    $limit             - the number of records to get for each request
     * @return an array which contains products of a certain category
     */
    public function getProductsUsingCategory($productCategoryId, $offset, $limit) {
        $products       = array();
        $resultSet      = Product::find(array(
            'conditions'    => 'product_category_id = ?1',
            'bind'          => array(
                1           => $productCategoryId,
            ),
            'limit'     => $limit,
            'offset'    => $offset,
            'order'     => 'product_id DESC',
        ));
        foreach ( $resultSet as $rowSet ) {
            array_push($products, array(
                'productId'         => $rowSet->getProductId(),
                'productName'       => (array)json_decode($rowSet->getProductName()),
                'productLogo'       => $rowSet->getProductLogo(),
                'latestVersion'     => $rowSet->getLatestVersion(),
            ));
        }
        return $products;
    }

    /**
     * Get products of a certain category.
     * @param  int    $productCategoryId - the unique ID of the category of product
     * @param  String $keyword           - the keyword of the product name
     * @param  long   $offset            - the index of first record of result set
     * @param  int    $limit             - the number of records to get for each request
     * @return an array which contains products of a certain category
     */
    public function getProductsUsingCategoryAndKeyword($productCategoryId, $keyword, $offset, $limit) {
        $products       = array();
        $conditions     = $this->getQueryOfProductsUsingCategoryAndKeyword($productCategoryId, $keyword);

        $resultSet      = Product::find(array_merge($conditions, array(
            'limit'     => $limit,
            'offset'    => $offset,
            'order'     => 'product_id DESC',
        )));
        foreach ( $resultSet as $rowSet ) {
            array_push($products, array(
                'productId'         => $rowSet->getProductId(),
                'productName'       => (array)json_decode($rowSet->getProductName()),
                'productCategory'   => array(
                    'productCategoryName'   => (array)json_decode($rowSet->getProductCategory()->getProductCategoryName()),
                ),
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
    public function getProductsCountUsingCategoryAndKeyword($productCategoryId, $keyword) {
        $conditions     = $this->getQueryOfProductsUsingCategoryAndKeyword($productCategoryId, $keyword);
        $resultSet      = Product::find($conditions);

        return $resultSet->count();
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