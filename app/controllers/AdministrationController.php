<?php

use Phalcon\Http\Response;
use Phalcon\Mvc\View;

/**
 * The controller used for handling system management.
 * 
 * @package TestZilla\controller\AdministrationController
 */
class AdministrationController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
        $this->view->setRenderLevel(View::LEVEL_LAYOUT);
    }

    public function indexAction() {
        $this->tag->prependTitle($this->localization['administration.index.title']);
    }

    /**
     * The logger of AdministrationController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}