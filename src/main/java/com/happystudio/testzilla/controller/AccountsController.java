package com.happystudio.testzilla.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.service.UserService;
import com.happystudio.testzilla.util.DigestUtils;

/**
 * 处理用户账户相关的请求.
 * @author Xie Haozhe
 */
@Controller
@RequestMapping(value = "/accounts")
public class AccountsController {
	/**
	 * 加载用户登录页面.
	 * @param request - HttpRequest对象
     * @return 一个包含登录页内容的ModelAndView对象
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginView(
    		@RequestParam(value="logout", required=false, defaultValue="false") boolean isLogout,
    		HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ( isLogout ) {
            destroySession(request, session);
        }
		
		ModelAndView view = null;
        if ( isLoggedIn(session) ) {
            view = new ModelAndView("redirect:/");
        } else {
            view = new ModelAndView("accounts/login");
            view.addObject("isLogout", isLogout);
        }
        return view;
    }
	
	/**
     * 为注销的用户销毁Session.
     * @param request - HttpServletRequest对象
     * @param session - HttpSession 对象
     */
    private void destroySession(HttpServletRequest request, HttpSession session) {
        session.removeAttribute("isLoggedIn");
        
        User currentUser = (User)session.getAttribute("user");
        String ipAddress = request.getRemoteAddr();
        logger.info(String.format("%s logged out at %s", new Object[] {currentUser, ipAddress}));
    }
    
    /**
     * 检查用户是否已经登录.
     * @param session - HttpSession 对象
     * @return 用户是否已经登录
     */
    private boolean isLoggedIn(HttpSession session) {
        Boolean isLoggedIn = (Boolean)session.getAttribute("isLoggedIn");
        if ( isLoggedIn == null || !isLoggedIn.booleanValue() ) {
            return false;
        }
        return true;
    }
    
    /**
     * 处理用户的异步登录请求.
     * @param username - 用户名
     * @param password - 密码(已使用MD5加密)
     * @param request - Http Servlet Request对象
     * @return 一个包含若干标志位的JSON数据
     */
    @RequestMapping(value = "/login.action", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Boolean> loginAction(
            @RequestParam(value="username", required=true) String username,
            @RequestParam(value="password", required=true) String password,
            HttpServletRequest request) {
    	String ipAddress = request.getRemoteAddr();
        HashMap<String, Boolean> result = userService.isAccountValid(username, password);
        logger.info(String.format("User: [Username=%s] tried to log in at %s", new Object[] {username, ipAddress}));
        if ( result.get("isSuccessful") ) {
            User user = userService.getUserUsingUsernameOrEmail(username);
        	getSession(request, user);
        }
        return result;
    }

    /**
     * 为登录的用户创建Session.
     * @param request - HttpServletRequest对象
     * @param user - 一个User对象, 包含用户的基本信息
     */
    private void getSession(HttpServletRequest request, User user) {
    	HttpSession session = request.getSession();
    	session.setAttribute("isLoggedIn", true);
        session.setAttribute("user", user);
        
        String ipAddress = request.getRemoteAddr();
        logger.info(String.format("%s logged in at %s", new Object[] {user, ipAddress}));
    }
 
    /**
     * 显示用户的注册页面
     * @param request - Http Servlet Request对象
     * @return 包含注册页面信息的ModelAndView对象
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView registerView(HttpServletRequest request) {
    	ModelAndView view = null;
        if ( isLoggedIn(request.getSession()) ) {
            view = new ModelAndView("redirect:/");
        } else {
    	    view = new ModelAndView("accounts/join");
        }
        return view;
    }
    
    /**
     * 处理用户异步注册请求.
     * @param username - 用户名
     * @param password - 密码
     * @param confirmPassword - 确认密码
     * @param userGroupSlug - 用户组的唯一英文简称
     * @param realName - 用户真实姓名或公司名称
     * @param email - 电子邮件地址
	 * @param country - 用户所在国家
	 * @param province - 用户所在省份
	 * @param city - 用户所在城市
	 * @param phone - 用户的联系电话
     * @param website - 用户的个人主页
	 * @param isIndividual - 是否为个人用户
     * @param request - HttpRequest对象
     * @return 一个包含若干标志位的JSON数据
     */
    @RequestMapping(value = "/join.action", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Boolean> joinAction(
            @RequestParam(value="username", required=true) String username,
            @RequestParam(value="password", required=true) String password,
            @RequestParam(value="confirmPassword", required=true) String confirmPassword,
            @RequestParam(value="userGroup", required=true) String userGroupSlug,
            @RequestParam(value="realName", required=true) String realName,
            @RequestParam(value="email", required=true) String email,
            @RequestParam(value="country", required=true) String country,
            @RequestParam(value="province", required=true) String province,
            @RequestParam(value="city", required=false) String city,
            @RequestParam(value="phone", required=true) String phone,
            @RequestParam(value="website", required=false) String website,
            @RequestParam(value="isIndividual", required=true) boolean isIndividual,
            HttpServletRequest request) {
    	String ipAddress = request.getRemoteAddr();
        HashMap<String, Boolean> result = userService.createUser(username, password, 
        		confirmPassword, userGroupSlug, realName, email, country, province, 
        		city, phone, website, isIndividual);
        
        if ( result.get("isSuccessful") ) {
            User user = userService.getUserUsingUsernameOrEmail(username);
        	getSession(request, user);
        	sendActivationMail(user);
            logger.info(String.format("User: [Username=%s] created at %s.", new Object[] {username, ipAddress}));
        }
        return result;
    }
    
    /**
     * 向注册的用户发送电子邮件确认信.
     * @param user - 当前注册用户的User对象
     */
    private void sendActivationMail(User user) {
    	String username = user.getUsername();
    	String email = user.getEmail();
    	String code = DigestUtils.generateGuid();
    	
    	userService.dumpEmailConfidential(email, code);
    	userService.sendActivationMail(username, email, code);
    }
    
    /**
     * 显示用户验证电子邮件页面.
     * @param request - HttpRequest对象
     * @return 包含验证电子邮件页面信息的ModelAndView对象
     */
    @RequestMapping(value = "/verifyEmail", method = RequestMethod.GET)
    public ModelAndView verifyEmailView(
            @RequestParam(value="code", required=false) String code,
    		HttpServletRequest request) {
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute("user");
    	ModelAndView view = null;
    	
        if ( !isLoggedIn(session) ) {
            view = new ModelAndView("redirect:/accounts/login");
        } else if ( currentUser.isEmailVerified() ) {
            view = new ModelAndView("redirect:/");
        } else {
            view = new ModelAndView("accounts/verifyEmail");
            view.addObject("email", currentUser.getEmail())
            	.addObject("code", code);
        }
        return view;
    }

    /**
     * 自动注入的UserService对象.
     */
    @Autowired
    UserService userService;
    
    /**
     * 日志记录器.
     */
    private Logger logger = LogManager.getLogger(AccountsController.class);
}
