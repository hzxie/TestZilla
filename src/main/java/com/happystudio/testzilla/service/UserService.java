package com.happystudio.testzilla.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.dao.MailVerificationDao;
import com.happystudio.testzilla.dao.UserDao;
import com.happystudio.testzilla.dao.UserGroupDao;
import com.happystudio.testzilla.model.MailVerification;
import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.model.UserGroup;
import com.happystudio.testzilla.util.DigestUtils;
import com.happystudio.testzilla.util.MailSender;

/**
 * 用户Service对象. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class UserService {
	/**
	 * 通过用户名或电子邮件地址获取用户对象.
	 * @param username - 用户名或电子邮件地址
	 * @return 一个User对象或空引用
	 */
	public User getUserUsingUsernameOrEmail(String username) {
		boolean isUsingEmail = username.indexOf('@') != -1;
	    User user = null;
	        
	    if ( !isUsingEmail ) {
	    	user = userDao.getUserUsingUsername(username);
	    } else {
	    	user = userDao.getUserUsingEmail(username);
	    }
	    return user;
	}
	
    /**
     * 验证用户身份是否有效.
     * @param username - 用户名或电子邮件地址
     * @param password - 密码(已使用MD5加密)
     * @return 一个User的对象或空引用
     */
    public HashMap<String, Boolean> isAccountValid(String username, String password) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("isUsernameEmpty", username.isEmpty());
        result.put("isPasswordEmpty", password.isEmpty());
        result.put("isAccountValid", false);
        result.put("isSuccessful", false);

        if ( !result.get("isUsernameEmpty") && !result.get("isPasswordEmpty") ) {
        	User user = getUserUsingUsernameOrEmail(username);
        	if ( user != null && user.getPassword().equals(password) ) {
        		result.put("isAccountValid", true);
                result.put("isSuccessful", true);
        	}
        }
        return result;
    }
    
    /**
     * 验证数据并创建新用户.
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
    public HashMap<String, Boolean> createUser(String username, String password, 
    		String confirmPassword, String userGroupSlug, String realName, String email, 
    		String country, String province, String city, String phone, String website, 
    		boolean isIndividual) {
    	UserGroup userGroup = getUserGroup(userGroupSlug);
    	boolean isEmailVerified = false;
    	User user = new User(username, DigestUtils.md5Hex(password), userGroup, realName, email, 
    							country, province, city, phone, website, isIndividual, isEmailVerified);
    	HashMap<String, Boolean> result = getJoinResult(user, password, confirmPassword);
    	
    	if ( result.get("isSuccessful") ) {
    		boolean isSuccessful = userDao.createUser(user);
    		result.put("isSuccessful", isSuccessful);
    	}
    	return result;
    }
    
    /**
     * 检查用户所提交的注册信息是否合法.
     * @param user - 待注册的用户对象
     * @param password - 密码(未使用MD5加密)
     * @param confirmPassword - 确认密码
     * @return 一个包含若干标志位的HashMap<String, Boolean>对象
     */
    private HashMap<String, Boolean> getJoinResult(User user, String password, String confirmPassword) {
    	HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    	result.put("isUsernameEmpty", user.getUsername().isEmpty());
    	result.put("isUsernameLegal", isUsernameLegal(user.getUsername()));
    	result.put("isUsernameExists", isUsernameExists(user.getUsername()));
    	result.put("isPasswordEmpty", password.isEmpty());
    	result.put("isPasswordLegal", isPasswordLegal(password));
    	result.put("isPasswordMatched", password.equals(confirmPassword));
    	result.put("isUserGroupLegal", isUserGroupLegal(user.getUserGroup()));
    	result.put("isEmailEmpty", user.getEmail().isEmpty());
    	result.put("isEmailLegal", isEmailLegal(user.getEmail()));
    	result.put("isEmailExists", isEmailExists(user.getEmail()));
    	result.put("isCountryEmpty", user.getCountry().isEmpty());
    	result.put("isCountryLegal", user.getCountry().length() <= 24);
    	result.put("isProvinceEmpty", user.getProvince().isEmpty());
    	result.put("isProvinceLegal", user.getProvince().length() <= 24);
    	result.put("isCityLegal", user.getCity().length() <= 24);
    	result.put("isPhoneEmpty", user.getPhone().isEmpty());
    	result.put("isPhoneLegal", isPhoneLegal(user.getPhone()));
    	result.put("isWebsiteLegal", isWebsiteLegal(user.getWebsite()));
    	
    	boolean isSuccessful = !result.get("isUsernameEmpty")  &&  result.get("isUsernameLegal")   &&
    			               !result.get("isUsernameExists") && !result.get("isPasswordEmpty")   &&
    			                result.get("isPasswordLegal")  &&  result.get("isPasswordMatched") &&
    			                result.get("isUserGroupLegal") && !result.get("isEmailEmpty")      &&
    			                result.get("isEmailLegal")     && !result.get("isEmailExists")     &&
    			               !result.get("isCountryEmpty")   &&  result.get("isCountryLegal")    &&
    			               !result.get("isProvinceEmpty")  &&  result.get("isProvinceLegal")   &&
    			                result.get("isCityLegal")      && !result.get("isPhoneEmpty")      &&
    			                result.get("isPhoneLegal")     &&  result.get("isWebsiteLegal");
    	result.put("isSuccessful", isSuccessful);
    	return result;
    }
    
    /**
     * 检查用户名是否合法.
     * 规则: 用户名应由[A-Za-z0-9_]组成, 以字母起始且长度在6-16个字符.
     * @param username - 用户名
     * @return 用户名是否合法
     */
    private boolean isUsernameLegal(String username) {
    	return username.matches("^[A-Za-z][A-Za-z0-9_]{5,15}$");
    }
    
    /**
     * 检查用户名是否存在.
     * @param username - 用户名
     * @return 用户名是否存在
     */
    private boolean isUsernameExists(String username) {
    	User user = userDao.getUserUsingUsername(username);
    	return user != null;
    }
    
    /**
     * 检查密码是否合法.
     * 规则: 密码的长度在6-16个字符.
     * @param password - 密码(未经MD5加密)
     * @return 密码是否合法
     */
    private boolean isPasswordLegal(String password) {
    	int passwordLength = password.length();
    	return passwordLength >= 6 && passwordLength <= 16;
    }
    
    /**
     * 通过用户组的唯一英文缩写获取用户组对象.
     * @param userGroupSlug - 用户组的唯一英文缩写
     * @return 用户组对象或空引用
     */
    private UserGroup getUserGroup(String userGroupSlug) {
    	UserGroup userGroup = userGroupDao.getUserGroupUsingSlug(userGroupSlug);
    	return userGroup;
    }
    
    /**
     * 检查用户组是否合法.
     * 规则: 用户组对象不是空引用且不是管理员的用户组对象
     * @param userGroup - 用户组的对象
     * @return 用户组是否合法
     */
    private boolean isUserGroupLegal(UserGroup userGroup) {
    	return userGroup != null && !userGroup.getUserGroupSlug().equals("administrator");
    }
    
    /**
     * 检查电子邮件地址是否合法.
     * 规则: 合法的电子邮件地址且长度不超过64个字符.
     * @param email - 电子邮件地址
     * @return 电子邮件地址是否合法
     */
    private boolean isEmailLegal(String email) {
    	int emailLength = email.length();
    	return emailLength <= 64 && email.matches("^[A-Za-z0-9\\._-]+@[A-Za-z0-9_-]+\\.[A-Za-z0-9\\._-]+$");
    }
    
    /**
     * 检查电子邮件地址是否存在.
     * @param email - 电子邮件地址
     * @return 电子邮件地址是否存在
     */
    private boolean isEmailExists(String email) {
    	User user = userDao.getUserUsingEmail(email);
    	return user != null;
    }

    /**
     * 检查联系电话是否合法.
     * 规则: 由数字, +和-组成的字符串且长度不超过24个字符.
     * @param phone - 联系电话
     * @return 联系电话是否合法
     */
    private boolean isPhoneLegal(String phone) {
    	return phone.matches("^(\\+[0-9]+\\-)*[0-9]+$");
    }
    
    /**
     * 检查个人主页的地址是否合法.
     * 规则: 合法的HTTP(S)协议URL且长度不超过64个字符.
     * @param website - 个人主页的地址
     * @return 个人主页的地址是否合法
     */
    private boolean isWebsiteLegal(String website) {
    	int websiteLength = website.length();
    	return website.isEmpty() || 
    		  (websiteLength <= 64 && website.matches("^(http|https):\\/\\/[A-Za-z0-9-]+\\.[A-Za-z0-9_.]+$"));
    }
    
    /**
     * 保存电子邮件随机确认代码.
     * @param email - 用户的电子邮件
     * @param code - 随机生成的确认代码
     */
    public void dumpEmailConfidential(String email, String code) {
    	MailVerification verification = new MailVerification(email, code);
    	mailVerificationDao.createMailVerification(verification);
    }
    
    /**
     * 向新注册的用户(更改电子邮件地址的用户)发送验证邮件.
     * @param username - 用户的用户名
     * @param email - 用户的电子邮件
     * @param code - 随机生成的确认代码
     */
    public void sendActivationMail(String username, String email, String code) {
    	String templatePath = "/verifyEmail.vm";
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("username", username);
    	model.put("email", email);
    	model.put("code", code);
    	
    	String subject = "Activate Your Account";
    	String body = mailSender.getMailContent(templatePath, model);
    	mailSender.sendMail(email, subject, body);
    }
    
    /**
     * 自动注入的UserDAO对象.
     */
    @Autowired
    private UserDao userDao;
    
    /**
     * 自动注入的UserGroupDAO对象.
     */
    @Autowired
    private UserGroupDao userGroupDao;
    
    /**
     * 自动注入的MailVerificationDao对象.
     */
    @Autowired
    private MailVerificationDao mailVerificationDao; 
    
    /**
     * 自动注入的MailSender对象.
     * 用于发送电子邮件至用户邮箱.
     */
    @Autowired
    @Qualifier("testzillaMailSender")
    private MailSender mailSender;
}

