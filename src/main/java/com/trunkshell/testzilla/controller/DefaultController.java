package com.trunkshell.testzilla.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.service.OptionService;
import com.trunkshell.testzilla.service.UserService;

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
    	long totalCountries = userService.getTotalCountries() + 10;
    	long totalTesters = userService.getTotalUsers() + 1024;
    	
        ModelAndView view = new ModelAndView("index");
        view.addObject("totalCountries", totalCountries);
        view.addObject("totalTesters", totalTesters);
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
        String termsOfUse = optionService.getOption("termsOfUse");
        
        ModelAndView view = new ModelAndView("terms");
    	view.addObject("termsOfUse", termsOfUse);
        return view;
    }
    
    /**
     * 自动注入的UserService对象.
     */
    @Autowired
    private UserService userService;
    
    /**
     * 自动注入的OptionService对象.
     */
    @Autowired
    private OptionService optionService;
    
    /**
     * 日志记录器.
     */
    @SuppressWarnings("unused")
    private Logger logger = LogManager.getLogger(DefaultController.class);
}

