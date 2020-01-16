<?php

use Phalcon\Http\Response;
use Phalcon\Mvc\Url;
use Phalcon\Mvc\View;

/**
 * The default controller of the application.
 * 
 * @package TestZilla\controller\DefaultController
 * @author Haozhe Xie <cshzxie@gmail.com>
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
        if ( $this->isLoggedIn($this->session) ) {
            $this->forward('dashboard/index');
        }
        $this->tag->prependTitle($this->localization['default.index.title']);
    }

    /**
     * Render to terms page.
     */
    public function termsAction() {
        $this->tag->prependTitle($this->localization['default.terms.title']);
    }

    /**
     * Render to privacy page.
     */
    public function privacyAction() {
        $this->tag->prependTitle($this->localization['default.privacy.title']);
    }

    /**
     * Change the language of view.
     * @return whether the operation is successful
     */
    public function changeLanguageAction() {
        $language    = $this->request->get('language');
        $this->session->set('language', $language);

        $result      = array(
            'isSuccessful'  => true,
        );
        $response    = new Response();
        $response->setContent(json_encode($result));
        return $response;
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
