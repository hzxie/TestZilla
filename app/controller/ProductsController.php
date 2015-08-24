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
     * @return a HttpResponse which contains information of products
     */
    public function getProductsAction() {
        $productCategorySlug    = $this->request->get('productCategory');
        $keyword                = $this->request->get('keyword');
        $pageNumber             = $this->request->get('page');
        $limit                  = self::NUMBER_OF_PRODUCTS_PER_PAGE;
        $offset                 = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $productService         = ServiceFactory::getService('ProductService');
        $productCategoryId      = $productService->getProductCategoryId($productCategorySlug);
        $products               = $this->getProductsInBestLanguage(
                                    $productService->getProductsUsingCategoryAndKeyword($productCategoryId, $keyword, $offset, $limit)
                                  );
        $numberOfProducts       = $productService->getProductsCountUsingCategoryAndKeyword($productCategoryId, $keyword);

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
     * Get the best language for multi-language content for products.
     * @param  Array $products - an array of product list in multi-languages
     * @return an array of a list of product with the best language
     */
    private function getProductsInBestLanguage($products) {
        if ( empty($products) ) {
            return $products;
        }
        foreach ( $products as &$product ) {
            $product = $this->getProductInBestLanguage($product);
        }
        return $products;
    }

    /**
     * Render to product detail information page.
     * @param long $productId - the unique ID of the product
     */
    public function productAction($productId) {
        $productService = ServiceFactory::getService('ProductService');
        $product        = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        
        $product            = $this->getProductInBestLanguage($product);
        $productCategoryId  = $product['productCategory']['productCategoryId'];
        $relationalProducts = $this->getProductsInBestLanguage($productService->getProductsUsingCategory($productCategoryId, 0, 5));

        $this->tag->prependTitle($product['productName']);
        $this->view->setVar('product', $product);
        $this->view->setVar('relationalProducts', $relationalProducts);
    }

    /**
     * Get issues of a certain product.
     * @param  long $productId - the unique ID of the product
     * @return a HttpResponse which contains information of issues of a product
     */
    public function getIssuesAction($productId) {
        $issueCategorySlug  = $this->request->get('issueCategory');
        $issueStatusSlug    = $this->request->get('issueStatus');
        $hunterUsername     = $this->request->get('hunter');
        $pageNumber         = $this->request->get('page');
        $limit              = self::NUMBER_OF_ISSUES_PER_REQUEST;
        $offset             = $pageNumber <= 1 ? 0 :  ($pageNumber - 1) * $limit;

        $productService     = ServiceFactory::getService('ProductService');
        $issueService       = ServiceFactory::getService('IssueService');
        $product            = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $issueCategoryId    = $issueService->getIssueCategoryId($issueCategorySlug);
        $issueStatusId      = $issueService->getIssueStatusId($issueStatusSlug);
        $issues             = $issueService->getIssuesUsingCategoryAndStatusAndHunter($issueCategoryId, $issueStatusId, $hunterUsername, $offset, $limit);
        $numberOfIssues     = $issueService->getIssuesCountUsingCategoryAndStatusAndHunter($issueCategoryId, $issueStatusId, $hunterUsername);

        $result             = array(
            'isSuccessful'  => !empty($issues),
            'products'      => $issues,
            'totalPages'    => ceil($numberOfIssues / $limit),
        );
        $response   = new Response();
        $response->setHeader('Content-Type', 'application/json');
        $response->setContent(json_encode($result));
        return $response;
    }

    /**
     * Render to new issue page to create an issue.
     * @param  long $productId - the unique ID of the product
     */
    public function newIssueAction($productId) {
        if ( !$this->isLoggedIn($this->session) ) {
            $response    = new Response();
            $response->redirect("/accounts/signin?forward=/product/{$productId}/new-issue");
            return $response;
        }
        $productService     = ServiceFactory::getService('ProductService');
        $issueService       = ServiceFactory::getService('IssueService');
        $product            = $productService->getProductUsingId($productId);

        if ( $product == NULL ) {
            $this->forward('errors/resourceNotFound');
            return;
        }
        $product            = $this->getProductInBestLanguage($product);
        $issueCategories    = $issueService->getIssueCategories();
        $issueStatusList    = $issueService->getIssueStatusList();

        $this->tag->prependTitle($this->localization['products.new-issue.title'] . ' · ' . $product['productName']);
        $this->view->setVar('product', $product);
    }

    /**
     * Get the best language for multi-language content for product.
     * @param  Array $product - an array contains detail information of the product
     * @return an array contains detail information of the product with the best language
     */
    private function getProductInBestLanguage($product) {
        if ( empty($product) ) {
            return $product;
        }
        if ( array_key_exists('productName', $product) ) {
            $product['productName']     = $this->getBestLanguageForContent($product['productName'], $this->request, $this->session);
        }
        if ( array_key_exists('productCategory', $product) ) {
            $product['productCategory']['productCategoryName'] = 
                $this->getBestLanguageForContent($product['productCategory']['productCategoryName'], $this->request, $this->session);
        }
        if ( array_key_exists('prerequisites', $product) ) {
            $product['prerequisites']   = $this->getBestLanguageForContent($product['prerequisites'], $this->request, $this->session);
        }
        if ( array_key_exists('description', $product) ) {
            $product['description']     = $this->getBestLanguageForContent($product['description'], $this->request, $this->session);
        }
        return $product;
    }
    
    /**
     * Get the best language for multi-language content.
     * @param  Array       $content - multi-language content
     * @param  HttpRequest $request - the HTTP request
     * @param  HttpSession $session - the HTTP session
     * @return the best language for multi-language content
     */
    private function getBestLanguageForContent($content, $request, $session) {
        $languageDectorPlugin   = new LanguageDectorPlugin();
        $currentLanguage        = $languageDectorPlugin->getCurrentLanguage($request, $session);
        $defaultLanguage        = 'en';
        $firstLanguage          = key($content);

        if ( array_key_exists($currentLanguage, $content) ) {
            return $content[$currentLanguage];
        } else if ( array_key_exists($defaultLanguage, $content) ) {
            return $content[$defaultLanguage];
        }
        return $content[$firstLanguage];
    }

    /**
     * Number of products to display in a page in the view.
     */
    const NUMBER_OF_PRODUCTS_PER_PAGE = 15;

    /**
     * Number of issues to get in a request.
     */
    const NUMBER_OF_ISSUES_PER_REQUEST = 15;

    /**
     * The logger of AccountsController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}