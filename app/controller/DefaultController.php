<?php

use Phalcon\Http\Response;
use Phalcon\Mvc\Url;
use Phalcon\Mvc\View;

/**
 * The default controller of the application.
 * 
 * @package TestZilla\controller\DefaultController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class DefaultController extends BaseController {
    /**
     * Initialize the Controller.
     */
    public function initialize() {
        parent::__initialize();
    }

    /**
     * Render to the homepage.
     */
    public function indexAction() {
        $this->tag->prependTitle($this->localization['default.index.title']);
    }

    /**
     * Get CSRF token for Ajax request.
     * @return a HTTP response object with JSON
     */
    public function getCsrfTokenAction() {
        $result = array(
            'csrfTokenKey'  => $this->security->getTokenKey(),
            'csrfToken'     => $this->security->getToken(),
        );

        $response    = new Response();
        $response->setContent(json_encode($result));
        return $response;
    }
}
