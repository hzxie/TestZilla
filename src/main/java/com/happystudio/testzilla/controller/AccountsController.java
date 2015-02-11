package com.happystudio.testzilla.controller;

import java.util.HashMap;
import java.util.List;

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

import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.service.ProductService;
import com.happystudio.testzilla.service.UserService;
import com.happystudio.testzilla.util.DigestUtils;
import com.happystudio.testzilla.util.HttpRequestParser;

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
        String ipAddress = HttpRequestParser.getRemoteAddr(request);
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
    	String ipAddress = HttpRequestParser.getRemoteAddr(request);
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
        
        String ipAddress = HttpRequestParser.getRemoteAddr(request);
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
            @RequestParam(value="city", required=true) String city,
            @RequestParam(value="phone", required=true) String phone,
            @RequestParam(value="website", required=true) String website,
            @RequestParam(value="isIndividual", required=true) boolean isIndividual,
            HttpServletRequest request) {
    	String ipAddress = HttpRequestParser.getRemoteAddr(request);
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
     * @param email - 待验证的电子邮件地址
     * @param code - 随机生成的验证字符串 
     * @param request - HttpRequest对象
     * @return 包含验证电子邮件页面信息的ModelAndView对象
     */
    @RequestMapping(value = "/verifyEmail", method = RequestMethod.GET)
    public ModelAndView verifyEmailView(
    		@RequestParam(value="email", required=false) String email,
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
            view.addObject("email", currentUser.getEmail());
        }
        if ( email != null && code != null ) {
        	if ( currentUser.getEmail().equals(email) &&
        		 userService.isEmailCondidentialValid(email, code) ) {
        		currentUser.setEmailVerified(true);
        		view = new ModelAndView("redirect:/accounts/dashboard");
        	}
        }
        return view;
    }
    
    /**
     * 显示重置密码的页面。
     * @param email - 待重置用户的电子邮件地址
     * @param code - 随机生成的验证字符串
     * @param request - HttpRequest对象
     * @return 包含重置密码页面信息的ModelAndView对象
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPasswordView(
    		@RequestParam(value="email", required=false) String email,
            @RequestParam(value="code", required=false) String code,
    		HttpServletRequest request) {
    	ModelAndView view = new ModelAndView("accounts/resetPassword");
    	return view;
    }
    
    /**
     * 显示用户控制面板.
     * @param request - HttpRequest对象
     * @return 包含用户控制面板页面信息的ModelAndView对象
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardView(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	User currentUser = (User)session.getAttribute("user");
    	ModelAndView view = null;
    	
    	if ( !isLoggedIn(session) ) {
            view = new ModelAndView("redirect:/accounts/login");
        } else if ( currentUser.getUserGroup().getUserGroupSlug().equals("administrator") ) {
        	view = new ModelAndView("redirect:/administration/dashboard");
        } else {
        	view = new ModelAndView("accounts/dashboard");
        	view.addAllObjects(getDataForDevelopers());
        }
    	return view;
    }
    
    /**
     * 获取开发者所需的额外数据(如产品分类等).
     * @return 包含开发者所需数据的HashMap对象.
     */
    private HashMap<String, Object> getDataForDevelopers() {
    	HashMap<String, Object> extraData = new HashMap<String, Object>(); 
    	extraData.put("productCategories", productService.getProductCategories());
    	return extraData;
    }
    
    /**
     * 处理用户编辑个人资料的请求.
     * @param realName - 用户真实姓名或公司名称
     * @param email - 电子邮件地址
	 * @param country - 用户所在国家
	 * @param province - 用户所在省份
	 * @param city - 用户所在城市
	 * @param phone - 用户的联系电话
     * @param website - 用户的个人主页
     * @param oldPassword - 旧密码
     * @param newPassword - 新密码
     * @param confirmPassword - 确认新密码
     * @param request - HttpRequest对象
     * @return 一个包含若干标志位的JSON数据
     */
    @RequestMapping(value = "/editProfile.action", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Boolean> editProfileAction(
            @RequestParam(value="realName", required=true) String realName,
            @RequestParam(value="email", required=true) String email,
            @RequestParam(value="country", required=true) String country,
            @RequestParam(value="province", required=true) String province,
            @RequestParam(value="city", required=false) String city,
            @RequestParam(value="phone", required=true) String phone,
            @RequestParam(value="website", required=true) String website,
            @RequestParam(value="oldPassword", required=true) String oldPassword,
    		@RequestParam(value="newPassword", required=true) String newPassword,
            @RequestParam(value="confirmPassword", required=true) String confirmPassword,
            HttpServletRequest request) {
    	String ipAddress = HttpRequestParser.getRemoteAddr(request);
    	HttpSession session = request.getSession();
    	User currentUser = (User)session.getAttribute("user");
    	
    	HashMap<String, Boolean> result = userService.editProfile(currentUser, oldPassword, newPassword, 
    							 confirmPassword, realName, email, country, province, city, phone, website);
        
        if ( result.get("isSuccessful") ) {
            logger.info(String.format("User: {%s} updated profile at %s.", new Object[] {currentUser, ipAddress}));
        }
        return result;
    }
    
    /**
	 * 获取用户发布的产品列表.
	 * @param page - 分页的页码
	 * @param request - HttpRequest对象
	 * @return 一个包含用户产品列表的JSON对象
	 */
	@RequestMapping(value = "/getProducts.action", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getProductsAction(
			@RequestParam(value="page", required=false, defaultValue="1") int pageNumber,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
    	User currentUser = (User)session.getAttribute("user");
    	
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Product> products = getProducts(currentUser, pageNumber);
		long totalPages = getProductTotalPages(currentUser);
		
		result.put("isSuccessful", products.size() != 0);
		result.put("products", products);
		result.put("totalPages", totalPages);
		return result;
	}
	
	/**
	 * 根据筛选条件获取待测试的产品列表.
	 * @param category - 产品所属分类
	 * @param pageNumber - 分页的页码
	 * @param isSortedByTime - 是否按照发布时间排序
	 * @return 待测试的产品列表
	 */
	private List<Product> getProducts(User developer, int pageNumber) {
		int offset = (pageNumber - 1) * NUMBER_OF_PRODUCTS_PER_PAGE;
		List<Product> products = productService.getProductsUsingDeveloper(developer, offset, NUMBER_OF_PRODUCTS_PER_PAGE);;
		
		return products;
	}
	
	/**
	 * 获取某个产品分类中产品的总分页页数.
	 * @param category - 产品分类的对象
	 * @return 某个产品分类中产品的总分页页数
	 */
	private long getProductTotalPages(User developer) {
		return (long)Math.ceil((double)productService.getTotalProducts(developer) / NUMBER_OF_PRODUCTS_PER_PAGE);
	}
    
    /**
     * 处理用户创建产品的请求.
     * @param productName - 产品名称
	 * @param productLogo - 产品Logo的URL
	 * @param productCategorySlug - 产品分类目录的唯一英文缩写
	 * @param latestVersion - 最新版本
	 * @param prerequisites - 测试的先决条件
	 * @param productUrl - 产品主页
	 * @param description - 产品的描述信息
     * @param request - HttpRequest对象
     * @return 一个包含若干标志位的JSON对象
     */
    @RequestMapping(value = "/createProduct.action", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Boolean> createProductAction(
    		@RequestParam(value="productName", required=true) String productName,
    		@RequestParam(value="productLogo", required=true) String productLogo,
    		@RequestParam(value="productCategory", required=true) String productCategorySlug,
    		@RequestParam(value="latestVersion", required=true) String latestVersion,
    		@RequestParam(value="prerequisites", required=true) String prerequisites,
    		@RequestParam(value="productUrl", required=true) String productUrl,
    		@RequestParam(value="description", required=true) String description,
    		HttpServletRequest request) {
    	String ipAddress = HttpRequestParser.getRemoteAddr(request);
    	HttpSession session = request.getSession();
    	User currentUser = (User)session.getAttribute("user");
    	
    	HashMap<String, Boolean> result = productService.createProduct(productName, productLogo, 
    			productCategorySlug, latestVersion, currentUser, prerequisites, productUrl, description);
    	
    	if ( result.get("isSuccessful") ) {
            logger.info(String.format("User: {%s} created product [productName=%s] at %s.", 
            			new Object[] {currentUser, productName, ipAddress}));
        }
        return result;
    }
    
    /**
     * 处理用户编辑产品的请求.
     * @param productId - 产品的唯一标识符
     * @param productName - 产品名称
	 * @param productLogo - 产品Logo的URL
	 * @param productCategorySlug - 产品分类目录的唯一英文缩写
	 * @param latestVersion - 最新版本
	 * @param prerequisites - 测试的先决条件
	 * @param productUrl - 产品主页
	 * @param description - 产品的描述信息
     * @param request - HttpRequest对象
     * @return 一个包含若干标志位的JSON对象
     */
    @RequestMapping(value = "/editProduct.action", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Boolean> editProductAction(
    		@RequestParam(value="productId", required=true) long productId,
    		@RequestParam(value="productName", required=true) String productName,
    		@RequestParam(value="productLogo", required=true) String productLogo,
    		@RequestParam(value="productCategory", required=true) String productCategorySlug,
    		@RequestParam(value="latestVersion", required=true) String latestVersion,
    		@RequestParam(value="prerequisites", required=true) String prerequisites,
    		@RequestParam(value="productUrl", required=true) String productUrl,
    		@RequestParam(value="description", required=true) String description,
    		HttpServletRequest request) {
    	String ipAddress = HttpRequestParser.getRemoteAddr(request);
    	HttpSession session = request.getSession();
    	User currentUser = (User)session.getAttribute("user");
    	
    	HashMap<String, Boolean> result = productService.editProduct(productId, productName, productLogo, 
    			productCategorySlug, latestVersion, currentUser, prerequisites, productUrl, description);
    	
    	if ( result.get("isSuccessful") ) {
            logger.info(String.format("User: {%s} edited product [productName=%s] at %s.", 
            			new Object[] {currentUser, productName, ipAddress}));
        }
        return result;
    }

    /**
     * 自动注入的UserService对象.
     */
    @Autowired
    UserService userService;
    
    /**
     * 自动注入的ProductService对象.
     */
    @Autowired
    ProductService productService;
    
    /**
	 * 产品列表页面每页所显示的产品数量.
	 */
	private final int NUMBER_OF_PRODUCTS_PER_PAGE = 10;
    
    /**
     * 日志记录器.
     */
    private Logger logger = LogManager.getLogger(AccountsController.class);
}
