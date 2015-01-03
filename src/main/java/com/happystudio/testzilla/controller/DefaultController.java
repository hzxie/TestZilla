package com.happystudio.testzilla.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理应用程序公共的请求.
 * @author Xie Haozhe
 */
@Controller
@RequestMapping(value = "/")
public class DefaultController {
    /**
     * 显示应用程序的首页.
     * @param request - HttpRequest对象
     * @return 一个包含首页内容的ModelAndView对象
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("index");
        return view;
    }
    
    /**
     * 显示搜索页面.
     * @param request - HttpRequest对象
     * @return 一个包含搜索页内容的ModelAndView对象
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("search");
        return view;
    }
    
    /**
     * 显示关于页面.
     * @param request - HttpRequest对象
     * @return 一个包含关于页内容的ModelAndView对象
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView aboutView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("about");
        return view;
    }
    
    /**
     * 显示帮助页.
     * @param request - HttpRequest对象
     * @return 一个包含帮助页内容的ModelAndView对象
     */
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public ModelAndView helpView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("help");
        return view;
    }
    
    /**
     * 显示服务条款页面.
     * @param request - HttpRequest对象
     * @return 一个包含服务条款页内容的ModelAndView对象
     */
    @RequestMapping(value = "/terms", method = RequestMethod.GET)
    public ModelAndView termsView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("terms");
        return view;
    }
    
    /**
     * 日志记录器.
     */
    @SuppressWarnings("unused")
    private Logger logger = LogManager.getLogger(DefaultController.class);
}

