<?php

use Phalcon\Http\Response;
use Phalcon\Logger;
use Phalcon\Logger\Adapter\File as FileAdapter;
use Phalcon\Mvc\View;

/**
 * The controller used for handling profile and product management.
 * 
 * @package TestZilla\controller\DashboardController
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class DashboardController extends BaseController {
    /**
     * Initialize the Controller
     */
    public function initialize() {
        parent::__initialize();
        $logDir        = $this->config->application->logDir;
        $this->logger  = new FileAdapter(APP_PATH . "/{$logDir}/TestZilla.log");
    }

    /**
     * Render to dashboard page.
     */
    public function indexAction() {
        $this->tag->prependTitle($this->localization['dashboard.index.title']);
    }

    /**
     * Render to profile page.
     */
    public function profileAction() {
        $this->tag->prependTitle($this->localization['dashboard.profile.title']);
    }

    /**
     * Render to products page.
     */
    public function productsAction() {
        $this->tag->prependTitle($this->localization['dashboard.products.title']);
    }

    /**
     * Render to issues page.
     */
    public function issuesAction() {
        $this->tag->prependTitle($this->localization['dashboard.issues.title']);
    }

    /**
     * The logger of DashboardController.
     * @var Phalcon\Logger\Adapter\File
     */
    private $logger;
}