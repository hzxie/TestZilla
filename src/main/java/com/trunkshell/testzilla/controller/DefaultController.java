package com.trunkshell.testzilla.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.service.OptionService;
import com.trunkshell.testzilla.service.UserService;
import com.trunkshell.testzilla.util.LocaleUtils;

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
     * @param response - HttpResponse对象
     * @return 一个包含首页内容的ModelAndView对象
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
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
     * @param response - HttpResponse对象
     * @return 一个包含搜索页内容的ModelAndView对象
     */
    @RequestMapping(value = "/localization", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Boolean> localizationAction(
    		@RequestParam(value="language", required=true) String language,
    		HttpServletRequest request, HttpServletResponse response) {
    	LocaleUtils.setLocale(request, response, language);
    	
    	HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    	result.put("isSuccessful", true);
    	return result;
    }
    
    /**
     * 显示搜索页面.
     * @param request - HttpRequest对象
     * @param response - HttpResponse对象
     * @return 一个包含搜索页内容的ModelAndView对象
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("search");
        return view;
    }
    
    /**
     * 显示关于页面.
     * @param request - HttpRequest对象
     * @param response - HttpResponse对象
     * @return 一个包含关于页内容的ModelAndView对象
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView aboutView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("about");
        return view;
    }
    
    /**
     * 显示帮助页.
     * @param request - HttpRequest对象
     * @param response - HttpResponse对象
     * @return 一个包含帮助页内容的ModelAndView对象
     */
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public ModelAndView helpView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("help");
        return view;
    }
    
    /**
     * 显示服务条款页面.
     * @param request - HttpRequest对象
     * @param response - HttpResponse对象
     * @return 一个包含服务条款页内容的ModelAndView对象
     */
    @RequestMapping(value = "/terms", method = RequestMethod.GET)
    public ModelAndView termsView(HttpServletRequest request, HttpServletResponse response) {
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

