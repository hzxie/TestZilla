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
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}