<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:54:54
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 12:55:01
 */

use Phalcon\Mvc\Model;

/**
 * The product in the application.
 *
 * @package TestZilla\model\Model\Product
 * @author Haozhe Xie <cshzxie@gmail.com>
 */
class Product extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the N-1 relationship in this function.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
        $this->belongsTo('product_category_id', 'ProductCategory', 'product_category_id');
        $this->belongsTo('product_developer_id', 'User', 'uid');
        
        $this->hasMany('product_id', 'Issue', 'product_id');
    }

    /**
     * The getter of the field product_id.
     * @return the unique ID of the product
     */
    public function getProductId() {
        return $this->product_id;
    }

    /**
     * The setter of the field product_id.
     * @param long $productId - the unique ID of the product
     */
    public function setProductId($productId) {
        $this->product_id = $productId;
    }

    /**
     * The getter of the field product_name.
     * @return the name of the product in JSON format
     */
    public function getProductName() {
        return $this->product_name;
    }
    
    /**
     * The setter of the field product_name.
     * @param String $productName - the name of the product in JSON format
     */
    public function setProductName($productName) {
        $json = json_encode($productName);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\Product] The productName seems not a valid JSON.');
        }
        $this->product_name = $productName;
    }

    /**
     * The getter of the field product_logo.
     * @return the URL of the product logo
     */
    public function getProductLogo() {
        return $this->product_logo;
    }

    /**
     * The setter of the field product_logo.
     * @param String $productLogo - the URL of the product logo
     */
    public function setProductLogo($productLogo) {
        if ( mb_strlen($productLogo, 'utf-8') > 128 ) {
            throw new InvalidArgumentException('[Model\Product] The length of productLogo CANNOT exceed 128 characters.');
        }
        $this->product_logo = $productLogo;
    }

    /**
     * The getter of the field product_category_id.
     * @return the category of the product
     */
    public function getProductCategory() {
        return $this->productCategory;
    }

    /**
     * The setter of the field product_category_id.
     * @param ProductCategory $productCategry - the category of the product
     */
    public function setProductCategory(ProductCategory $productCategry) {
        if ( $productCategry == NULL ) {
            throw new InvalidArgumentException('[Model\Product] Invalid Model\ProductCategory.');
        }
        $this->product_category_id = $productCategry->getProductCategoryId();
    }

    /**
     * The getter of the field product_latest_version.
     * @return the latest version of the product
     */
    public function getLatestVersion() {
        return $this->product_latest_version;
    }

    /**
     * The setter of the field product_latest_version.
     * @param $latestVersion - the latest version of the product
     */
    public function setLatestVersion($latestVersion) {
        if ( mb_strlen($latestVersion, 'utf-8') > 24 ) {
            throw new InvalidArgumentException('[Model\Product] The length of latestVersion CANNOT exceed 24 characters.');
        }
        $this->product_latest_version = $latestVersion;
    }

    /**
     * The getter of the field product_developer_id.
     * @return the developer of the product
     */
    public function getDeveloper() {
        return $this->user;
    }

    /**
     * The setter of the field product_developer_id.
     * @param User $developer - the developer of the product
     */
    public function setDeveloper(User $developer) {
        if ( $developer == NULL ) {
            throw new InvalidArgumentException('[Model\Product] Invalid developer (Model\User).');
        }
        $this->product_developer_id = $developer->getUid();
    }

    /**
     * The getter of the field product_prerequisites.
     * @return the prerequisites of testing the product in JSON format
     */
    public function getPrerequisites() {
        return $this->product_prerequisites;
    }

    /**
     * The getter of the field product_prerequisites.
     * @param String $prerequisites - the prerequisites of testing the product in JSON format
     */
    public function setPrerequisites($prerequisites) {
        $json = json_encode($prerequisites);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\Product] The prerequisites seems not a valid JSON.');
        }
        $this->product_prerequisites = $prerequisites;
    }

    /**
     * The getter of the field product_url.
     * @return the URL to get the product
     */
    public function getProductUrl() {
        return $this->product_url;
    }

    /**
     * The setter of the field product_url.
     * @param String $productUrl - the URL to get the product
     */
    public function setProductUrl($productUrl) {
        if ( mb_strlen($productUrl, 'utf-8') > 256 ) {
            throw new InvalidArgumentException('[Model\Product] The length of productUrl CANNOT exceed 256 characters.');
        }
        $this->product_url = $productUrl;
    }

    /**
     * The getter of the field product_description.
     * @return the description of the product in JSON format
     */
    public function getDescription() {
        return $this->product_description;
    }

    /**
     * The setter of the field product_description.
     * @param String $description - the description of the product in JSON format
     */
    public function setDescription($description) {
        $json = json_encode($description);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\Product] The description seems not a valid JSON.');
        }
        $this->product_description = $description;
    }

    /**
     * Get number of issues of the product.
     * @return number of issues of the product
     */
    public function getNumberOfIssues() {
        return $this->issue->count();
    }

    /**
     * Get the description of the User object.
     * @return the description of the User object
     */
    public function __toString() {
        return sprintf('Product: [ID=%d, Name=%s, LogoUrl=%s, CategoryId=%d, LatestVersion=%s, DeveloperId=%d, URL=%s, Description=%s]', 
                $this->product_id, $this->product_name, $this->product_logo, $this->product_category_id, 
                $this->product_latest_version, $this->product_developer_id, $this->product_url, $this->product_description);
    }

    /**
     * The unique ID of the product.
     * @var long
     */
    protected $product_id;

    /**
     * The name of the product (JSON format).
     * @var String
     */
    protected $product_name;
    
    /**
     * The URL of the product logo.
     * @var String
     */
    protected $product_logo;
    
    /**
     * The unique ID of the product category.
     * @var int
     */
    protected $product_category_id;
    
    /**
     * The latest version of the product.
     * @var String
     */
    protected $product_latest_version;
    
    /**
     * The developer(User) of the product.
     * @var long
     */
    protected $product_developer_id;
    
    /**
     * The prerequisites of testing the product (JSON format).
     * @var String
     */
    protected $product_prerequisites;
    
    /**
     * The URL to get the product.
     * @var String
     */
    protected $product_url;
    
    /**
     * The description of the product (JSON format).
     * @var String
     */
    protected $product_description;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_products';
}