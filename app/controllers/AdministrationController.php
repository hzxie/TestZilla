<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 14:37:43
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 14:37:46
 */

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
}