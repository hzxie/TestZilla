<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:54:11
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Mvc\Model;

/**
 * The categories of product in the application.
 *
 * @package TestZilla\model\Model\ProductCategory
 */
class ProductCategory extends Model {
    /**
     * Initialize the model.
     * According to the document of Phalcon framework, we have to announce 
     * the 1-N relationship in this function.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
        $this->belongsTo('product_category_id', 'Product', 'product_category_id');
    }

    /**
     * The getter of the field product_category_id.
     * @return the unique ID of the product category
     */
    public function getProductCategoryId() {
        return $this->product_category_id;
    }

    /**
     * The setter of the field product_category_id.
     * @param  int $productCategoryId - the unique ID of the product category
     */
    public function setProductCategoryId($productCategoryId) {
        $this->product_category_id = $productCategoryId;
    }

    /**
     * The getter of the field product_category_slug.
     * @return the unique slug of the product category.
     */
    public function getProductCategorySlug() {
        return $this->product_category_slug;
    }

    /**
     * The setter of the field product_category_slug.
     * @param String $productCategorySlug - the unique slug of the product category
     */
    public function setProductCategorySlug($productCategorySlug) {
        if ( mb_strlen($productCategorySlug, 'utf-8') > 24 ) {
            throw new InvalidArgumentException('[Model\ProductCategory] The length of productCategorySlug CANNOT exceed 24 characters.');
        }
        $this->product_category_slug = $productCategorySlug;
    }

    /**
     * The getter of the field product_category_name.
     * @return The name of the product category in JSON format
     */
    public function getProductCategoryName() {
        return $this->product_category_name;
    }

    /**
     * The setter of the field product_category_name.
     * @param String $productCategoryName - the name of the product category in JSON format
     */
    public function setProductCategoryName($productCategoryName) {
        $json = json_encode($productCategoryName);
        if ( json_last_error() != JSON_ERROR_NONE ) {
            throw new InvalidArgumentException('[Model\ProductCategory] The productCategoryName seems not a valid JSON.');
        }
        $this->product_category_name = $productCategoryName;
    }

    /**
     * Get the description of the ProductCategory object.
     * @return the description of the ProductCategory object
     */
    public function __toString() {
        return sprintf('ProductCategory: [ID=%d, Slug=%s, Name=%s]', 
                $this->product_category_id, $this->product_category_slug, $this->product_category_name);
    }

    /**
     * The unique ID of the product category.
     * @var int
     */
    protected $product_category_id;
    
    /**
     * The unique slug of the product category.
     * @var String
     */
    protected $product_category_slug;

    /**
     * The name of the product category (JSON format).
     * @var String
     */
    protected $product_category_name;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_product_categories';
}