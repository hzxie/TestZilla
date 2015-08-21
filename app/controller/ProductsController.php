<?php

use Phalcon\Http\Response;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\View;

/**
 * The controller used for handling products management.
 * 
 * @package TestZilla\controller\ProductController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class ProductsController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
        $logDir         = $this->config->application->logDir;
        $this->logger   = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Render to products page.
     */
    public function indexAction() {
        $productService = ServiceFactory::getService('ProductService');
        $categories     = $productService->getProductCategories();

        $this->tag->prependTitle($this->localization['products.index.title']);
        $this->view->setVar('productCategories', $categories);
    }

    /**
     * Get products of a certain category.
     * @return an HttpResponse which contains information of products
     */
    public function getProductsAction() {
        $productCategorySlug    = $this->request->get('productCategory');
        $pageNumber             = $this->request->get('page');
        $limit                  = self::NUMBER_OF_PRODUCTS_PER_PAGE;
        $offset                 = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $productService         = ServiceFactory::getService('ProductService');
        $productCategoryId      = $productService->getProductCategoryId($productCategorySlug);
        $products               = $productService->getProductsUsingCategory($productCategoryId, $offset, $limit);
        $numberOfProducts       = $productService->getProductsCountUsingCategory($productCategoryId);
        $result                 = array(
            'isSuccessful'      => !empty($products),
            'products'          => $products,
            'totalPages'        => ceil($numberOfProducts / $limit),
        );

        $response               = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Number of products to display in one page in the view.
     */
    const NUMBER_OF_PRODUCTS_PER_PAGE = 15;

    /**
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}