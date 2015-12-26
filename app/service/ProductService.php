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
     * Get the ID of a ProductCategory using slug.
     * @param  String $productCategorySlug - the unique slug of the ProductCategory
     * @return the unique ID of the ProductCategory
     */
    public function getProductCategoryId($productCategorySlug) {
        $rowSet = $this->getProductCategoryUsingSlug($productCategorySlug);
        
        if ( $rowSet == NULL ) {
            return 0;
        }
        return $rowSet->getProductCategoryId();
    }

    /**
     * Get the product category using slug.
     * @param  String $productCategorySlug - the unique slug of the ProductCategory
     * @return an object of product category
     */
    private function getProductCategoryUsingSlug($productCategorySlug) {
        $rowSet = ProductCategory::findFirst(array(
            'conditions'    => 'product_category_slug = ?1',
            'bind'          => array(
                1           => $productCategorySlug,
            ),
        ));
        return $rowSet;
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
                'productCategorySlug'   => $rowSet->getProductCategory()->getProductCategorySlug(),
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
     * Get the products developered by a user.
     * @param  long   $developerUid - the unique ID of the developer
     * @param  long   $offset       - the index of first record of result set
     * @param  int    $limit        - the number of records to get for each request
     * @return an array which contains products developered by a user
     */
    public function getProductUsingDeveloper($developerUid, $offset, $limit) {
        $products       = array();
        $resultSet      = Product::find(array(
            'conditions'    => 'product_developer_id = ?1',
            'bind'          => array(
                1           => $developerUid,
            ),
            'limit'     => $limit,
            'offset'    => $offset,
            'order'     => 'product_id DESC',
        ));
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
                'issuesCount'       => $rowSet->getNumberOfIssues(),
            ));
        }
        return $products;
    }

    /**
     * Get the number of products developered by a user.
     * @param  long   $developerUid - the unique ID of the developer
     * @return number of products developered by a user
     */
    public function getProducCounttUsingDeveloper($developerUid) {
        $resultSet      = Product::find(array(
            'conditions'    => 'product_developer_id = ?1',
            'bind'          => array(
                1           => $developerUid,
            ),
        ));
        return $resultSet->count();
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
     * Get number of products developed by the user.
     * @param  long $uid - the unique user ID of the developer
     * @return number of products developed by the user
     */
    public function getNumberOfProductsUsingDeveloper($uid) {
        $resultSet  = Product::find(array(
            'conditions'    => 'product_developer_id = ?1',
            'bind'          => array(
                1           => $uid,
            ),
        ));
        return $resultSet->count();
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
                'issuesCount'       => $rowSet->getNumberOfIssues(),
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

    /**
     * The handler of creating product.
     * @param  String  $productName         - the name of product in JSON format
     * @param  String  $productCategorySlug - the unique slug of the product category
     * @param  User    $developer           - the user object of the developer of the product
     * @param  String  $productLogoUrl      - the URL of the logo of the product
     * @param  String  $latestVersion       - the latest version of the product
     * @param  String  $productUrl          - the homepage of the product
     * @param  String  $prerequisites       - the prerequisites of testing in JSON format
     * @param  String  $description         - the description of the product in JSON format
     * @param  boolean $isTokenValid        - whether the CSRF token is valid
     * @return an array infers whether the creation is successful
     */
    public function createProduct($productName, $productCategorySlug, $developer, $productLogoUrl, 
            $latestVersion, $productUrl, $prerequisites, $description, $isTokenValid) {
        $productCategory = $this->getProductCategoryUsingSlug($productCategorySlug);

        $result = array(
            'isSuccessful'      => false,
            'isProductNameEmpty'        => $this->isProductNameEmpty($productName),
            'isProductNameLegal'        => $this->isProductNameLegal($productName),
            'isProductCategoryLegal'    => $productCategory != NULL,
            'isDeveloperLegal'          => $developer != NULL,
            'isProductLogoEmpty'        => empty($productLogoUrl),
            'isProductLogoLegal'        => $this->isProductLogoLegal($productLogoUrl),
            'isLatestVersionEmpty'      => empty($latestVersion),
            'isLatestVersionLegal'      => $this->isLatestVersionLegal($latestVersion),
            'isProductUrlEmpty'         => empty($productUrl),
            'isProductUrlLegal'         => $this->isProductUrlLegal($productUrl),
            'isPrerequisitesEmpty'      => $this->isPrerequisitesEmpty($prerequisites),
            'isPrerequisitesLegal'      => $this->isPrerequisitesLegal($prerequisites),
            'isDescriptionEmpty'        => $this->isDescriptionEmpty($description),
            'isDescriptionLegal'        => $this->isDescriptionLegal($description),
            'isTokenValid'              => $isTokenValid,
        );
        $result['isSuccessful'] = !$result['isProductNameEmpty']        &&  $result['isProductNameLegal']   &&
                                   $result['isProductCategoryLegal']    &&  $result['isDeveloperLegal']     &&
                                  !$result['isProductLogoEmpty']        &&  $result['isProductLogoLegal']   && 
                                  !$result['isLatestVersionEmpty']      &&  $result['isLatestVersionLegal'] && 
                                  !$result['isProductUrlEmpty']         &&  $result['isProductUrlLegal']    && 
                                  !$result['isPrerequisitesEmpty']      &&  $result['isPrerequisitesLegal'] && 
                                  !$result['isDescriptionEmpty']        &&  $result['isDescriptionLegal']   &&  
                                   $result['isTokenValid'];
        if ( $result['isSuccessful'] ) {
            $product = new Product();
            $product->setProductName($productName);
            $product->setProductCategory($productCategory);
            $product->setDeveloper($developer);
            $product->setProductLogo($productLogoUrl);
            $product->setLatestVersion($latestVersion);
            $product->setProductUrl($productUrl);
            $product->setPrerequisites($prerequisites);
            $product->setDescription($description);

            if ( !$product->create() ) {
                $result['isSuccessful']      = false;
            }
        }
        return $result;
    }

    /**
     * The handler of editing product.
     * @param  int     $productId           - the unique ID of the product
     * @param  String  $productName         - the name of product in JSON format
     * @param  String  $productCategorySlug - the unique slug of the product category
     * @param  User    $developer           - the user who wants to edit the product
     * @param  String  $productLogoUrl      - the URL of the logo of the product
     * @param  String  $latestVersion       - the latest version of the product
     * @param  String  $productUrl          - the homepage of the product
     * @param  String  $prerequisites       - the prerequisites of testing in JSON format
     * @param  String  $description         - the description of the product in JSON format
     * @param  boolean $isTokenValid        - whether the CSRF token is valid
     * @return an array infers whether the edit is successful
     */
    public function editProduct($productId, $productName, $productCategorySlug, $developer, $productLogoUrl, 
            $latestVersion, $productUrl, $prerequisites, $description, $isTokenValid) {
        $productCategory = $this->getProductCategoryUsingSlug($productCategorySlug);

        $result  = array(
            'isSuccessful'              => false,
            'isProductExists'           => $this->isProductExists($productId, $developer),
            'isProductNameEmpty'        => $this->isProductNameEmpty($productName),
            'isProductNameLegal'        => $this->isProductNameLegal($productName),
            'isProductCategoryLegal'    => $productCategory != NULL,
            'isProductLogoEmpty'        => empty($productLogoUrl),
            'isProductLogoLegal'        => $this->isProductLogoLegal($productLogoUrl),
            'isLatestVersionEmpty'      => empty($latestVersion),
            'isLatestVersionLegal'      => $this->isLatestVersionLegal($latestVersion),
            'isProductUrlEmpty'         => empty($productUrl),
            'isProductUrlLegal'         => $this->isProductUrlLegal($productUrl),
            'isPrerequisitesEmpty'      => $this->isPrerequisitesEmpty($prerequisites),
            'isPrerequisitesLegal'      => $this->isPrerequisitesLegal($prerequisites),
            'isDescriptionEmpty'        => $this->isDescriptionEmpty($description),
            'isDescriptionLegal'        => $this->isDescriptionLegal($description),
            'isTokenValid'              => $isTokenValid,
        );
        $result['isSuccessful'] =  $result['isProductExists']           && !$result['isProductNameEmpty']       &&
                                   $result['isProductNameLegal']        &&  $result['isProductCategoryLegal']   && 
                                  !$result['isProductLogoEmpty']        &&  $result['isProductLogoLegal']       && 
                                  !$result['isLatestVersionEmpty']      &&  $result['isLatestVersionLegal']     && 
                                  !$result['isProductUrlEmpty']         &&  $result['isProductUrlLegal']        && 
                                  !$result['isPrerequisitesEmpty']      &&  $result['isPrerequisitesLegal']     && 
                                  !$result['isDescriptionEmpty']        &&  $result['isDescriptionLegal']       &&  
                                   $result['isTokenValid'];
        if ( $result['isSuccessful'] ) {
            $product = $this->getProductObjectUsingId($productId);
            $product->setProductName($productName);
            $product->setProductCategory($productCategory);
            $product->setProductLogo($productLogoUrl);
            $product->setLatestVersion($latestVersion);
            $product->setProductUrl($productUrl);
            $product->setPrerequisites($prerequisites);
            $product->setDescription($description);

            if ( !$product->update() ) {
                $result['isSuccessful'] = false;
            }
        }
        return $result;
    }

    /**
     * Check if the product exists.
     * @param  int  $productId   - the unique ID of the product
     * @param  User $developer   - the user who wants to edit the product
     * @return whether the product is existing
     */
    private function isProductExists($productId, $developer) {
        $product = $this->getProductUsingId($productId);
        return $product != NULL && $product['developer']['uid'] == $developer['uid'];
    }

    /**
     * Check if the name of the product is empty.
     * @param  String  $productName - the name of the product in JSON format
     * @return whether the name of the product is empty
     */
    private function isProductNameEmpty($productName) {
        $productName = json_decode($productName);

        if ( empty($productName) ) {
            return true;
        }
        foreach ( $productName as $language => $name ) {
            if ( !empty($name) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the name of the product is legal.
     * @param  String  $productName - the name of the product in JSON format
     * @return whether the name of the product is legal
     */
    private function isProductNameLegal($productName) {
        $availableLanguages = $this->languages;
        $productName = json_decode($productName);

        if ( empty($productName) ) {
            return true;
        }
        foreach ( $productName as $language => $name ) {
            if ( !array_key_exists($language, $availableLanguages) || 
                    mb_strlen($name, 'utf-8') > 64 ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the logo of the product is legal.
     * @param  String  $productLogo - the logo of the product
     * @return whether the logo of the product is legal
     */
    private function isProductLogoLegal($productLogo) {
        return strlen($productLogo) <= 128 && 
               preg_match('/^(http|https):\/\/[A-Za-z0-9-]+\.[A-Za-z0-9\.-\/]+\/[A-Za-z0-9-_]+\.(jpg|png|gif|svg)$/', $productLogo);
    }

    /**
     * Check if the latest version of the product is legal.
     * @param  String  $latestVersion - the latest version of product
     * @return whether the latest version of the product is legal
     */
    private function isLatestVersionLegal($latestVersion) {
        return mb_strlen($latestVersion, 'utf-8') <= 24; 
    }

    /**
     * Check if the URL of the product is legal.
     * @param  String  $productUrl - the URL of the product
     * @return whether the URL of the product is legal
     */
    private function isProductUrlLegal($productUrl) {
        return strlen($productUrl) <= 256 && 
               preg_match('/^(http|https):\/\/[A-Za-z0-9-]+\.[A-Za-z0-9\.\/-]+$/', $productUrl);
    }

    /**
     * Check if the prerequisites of testing the product is empty.
     * @param  String  $prerequisites - the prerequisites of testing the product in JSON format
     * @return whether the prerequisites of testing the product is empty
     */
    private function isPrerequisitesEmpty($prerequisites) {
        $prerequisites = json_decode($prerequisites);

        if ( empty($prerequisites) ) {
            return true;
        }
        foreach ( $prerequisites as $language => $prerequisite ) {
            if ( !empty($prerequisite) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the prerequisites of testing the product is legal.
     * @param  String  $prerequisites - the prerequisites of testing the product in JSON format
     * @return whether the prerequisites of testing the product is legal
     */
    private function isPrerequisitesLegal($prerequisites) {
        $availableLanguages = $this->languages;
        $prerequisites      = json_decode($prerequisites);

        if ( empty($prerequisites) ) {
            return true;
        }
        foreach ( $prerequisites as $language => $prerequisite ) {
            if ( !array_key_exists($language, $availableLanguages) || 
                    mb_strlen($prerequisite, 'utf-8') > 256 ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if the description of the product is empty.
     * @param  String  $description - the description of the product
     * @return whether the description of the product is empty
     */
    private function isDescriptionEmpty($description) {
        $description        = json_decode($description);

        if ( empty($description) ) {
            return true;
        }
        foreach ( $description as $language => $desc ) {
            if ( !empty($desc) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the description of the product is legal.
     * @param  String  $description - the description of the product
     * @return whether the description of the product is legal
     */
    private function isDescriptionLegal($description) {
        $availableLanguages = $this->languages;
        $description        = json_decode($description);

        if ( empty($description) ) {
            return true;
        }
        foreach ( $description as $language => $desc ) {
            if ( !array_key_exists($language, $availableLanguages) || 
                    mb_strlen($desc, 'utf-8') > 1024 ) {
                return false;
            }
        }
        return true;
    }
}