package com.trunkshell.testzilla.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.MailVerificationDao;
import com.trunkshell.testzilla.dao.UserDao;
import com.trunkshell.testzilla.dao.UserGroupDao;
import com.trunkshell.testzilla.model.MailVerification;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.model.UserGroup;
import com.trunkshell.testzilla.util.DigestUtils;
import com.trunkshell.testzilla.util.MailSender;

/**
 * 用户Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class UserService {
	/**
	 * 通过用户唯一标识符获取用户对象.
	 * @param uid - 用户唯一标识符
	 * @return 预期的用户对象
	 */
	public User getUserUsingUid(long uid) {
		User user = userDao.getUserUsingUid(uid);
		return user;
	}
	
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
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
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
		result.put("isRealNameEmpty", user.getRealName().isEmpty());
		result.put("isRealNameLegal", isRealNameLegal(user.getRealName()));
		result.put("isEmailEmpty", user.getEmail().isEmpty());
		result.put("isEmailLegal", isEmailLegal(user.getEmail()));
		result.put("isEmailExists", isEmailExists(user.getEmail()));
		result.put("isCountryEmpty", user.getCountry().isEmpty());
		result.put("isCountryLegal", isCountryLegal(user.getCountry()));
		result.put("isProvinceEmpty", user.getProvince().isEmpty());
		result.put("isProvinceLegal", isProvinceLegal(user.getProvince()));
		result.put("isCityLegal", isCityLegal(user.getCity()));
		result.put("isPhoneEmpty", user.getPhone().isEmpty());
		result.put("isPhoneLegal", isPhoneLegal(user.getPhone()));
		result.put("isWebsiteLegal", isWebsiteLegal(user.getWebsite()));
		
		boolean isSuccessful = !result.get("isUsernameEmpty")  &&  result.get("isUsernameLegal")   &&
							   !result.get("isUsernameExists") && !result.get("isPasswordEmpty")   &&
							    result.get("isPasswordLegal")  &&  result.get("isPasswordMatched") &&
							    result.get("isUserGroupLegal") && !result.get("isRealNameEmpty")   && 
							    result.get("isRealNameLegal")  && !result.get("isEmailEmpty")      &&
							    result.get("isEmailLegal")     && !result.get("isEmailExists")     &&
							   !result.get("isCountryEmpty")   &&  result.get("isCountryLegal")    &&
							   !result.get("isProvinceEmpty")  &&  result.get("isProvinceLegal")   &&
							    result.get("isCityLegal")      && !result.get("isPhoneEmpty")      &&
							    result.get("isPhoneLegal")     &&  result.get("isWebsiteLegal");
		result.put("isSuccessful", isSuccessful);
		return result;
	}

	/**
	 * 验证数据并编辑用户个人资料.
	 * @param user - 当前用户的用户对象
	 * @param oldPassword - 旧密码
	 * @param newPassword - 新密码
	 * @param confirmPassword - 确认新密码
	 * @param realName - 用户真实姓名或公司名称
	 * @param email - 电子邮件地址
	 * @param country - 用户所在国家
	 * @param province - 用户所在省份
	 * @param city - 用户所在城市
	 * @param phone - 用户的联系电话
	 * @param website - 用户的个人主页
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	public HashMap<String, Boolean> editProfile(User user, String oldPassword, String newPassword, 
			String confirmPassword, String realName, String email, String country, String province, 
			String city, String phone, String website) {
		String currentEmail = user.getEmail();
		user.setRealName(realName); user.setEmail(email); user.setCountry(country);
		user.setProvince(province); user.setCity(city);   user.setPhone(phone);
		user.setWebsite(website);
		
		HashMap<String, Boolean> result = getEditProfileResult(user, currentEmail, 
											oldPassword, newPassword, confirmPassword);
		
		if ( result.get("isSuccessful") ) {
			if ( !oldPassword.isEmpty() && !newPassword.isEmpty() ) {
				user.setPassword(DigestUtils.md5Hex(newPassword));
			}
			if ( !user.getEmail().equals(currentEmail) ) {
				user.setEmailVerified(false);
			}
			boolean isSuccessful = userDao.updateUser(user);
			result.put("isSuccessful", isSuccessful);
		}
		return result;
	}
	
	/**
	 * 检查用户所提交的个人资料是否合法.
	 * @param user - 待编辑的用户对象
	 * @param currentEmail - 用户当前使用的Email地址 
	 * @param oldPassword - 密码(未使用MD5加密)
	 * @param newPassword - 密码(未使用MD5加密)
	 * @param confirmPassword - 确认密码
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	private HashMap<String, Boolean> getEditProfileResult(User user, String currentEmail,
			String oldPassword, String newPassword, String confirmPassword) {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isRealNameEmpty", user.getRealName().isEmpty());
		result.put("isRealNameLegal", isRealNameLegal(user.getRealName()));
		result.put("isEmailEmpty", user.getEmail().isEmpty());
		result.put("isEmailLegal", isEmailLegal(user.getEmail()));
		result.put("isEmailExists", isEmailExists(currentEmail, user.getEmail()));
		result.put("isCountryEmpty", user.getCountry().isEmpty());
		result.put("isCountryLegal", isCountryLegal(user.getCountry()));
		result.put("isProvinceEmpty", user.getProvince().isEmpty());
		result.put("isProvinceLegal", isProvinceLegal(user.getProvince()));
		result.put("isCityLegal", isCityLegal(user.getCity()));
		result.put("isPhoneEmpty", user.getPhone().isEmpty());
		result.put("isPhoneLegal", isPhoneLegal(user.getPhone()));
		result.put("isWebsiteLegal", isWebsiteLegal(user.getWebsite()));
		result.put("isOldPasswordCorrect", isOldPasswordCorrect(user.getPassword(), oldPassword));
		result.put("isNewPasswordLegal", newPassword.isEmpty() || isPasswordLegal(newPassword));
		result.put("isPasswordMatched", newPassword.equals(confirmPassword));
		
		boolean isSuccessful = !result.get("isRealNameEmpty")     &&  result.get("isRealNameLegal")      &&
							   !result.get("isEmailEmpty")        &&  result.get("isEmailLegal")         && 
							   !result.get("isEmailExists")       && !result.get("isCountryEmpty")       &&  
							    result.get("isCountryLegal")      && !result.get("isProvinceEmpty")      &&  
							    result.get("isProvinceLegal")     &&  result.get("isCityLegal")          && 
							   !result.get("isPhoneEmpty")        &&  result.get("isPhoneLegal")         &&  
							    result.get("isWebsiteLegal")      &&  result.get("isOldPasswordCorrect") &&
							    result.get("isNewPasswordLegal")  &&  result.get("isPasswordMatched") ;
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
	 * 更改密码时, 验证用户的旧密码是否正确.
	 * @param oldPassword - 用户的旧密码(已使用MD5加密)
	 * @param submitedPassword - 所提交进行验证的旧密码(未使用MD5加密)
	 * @return 用户旧密码是否正确
	 */
	private boolean isOldPasswordCorrect(String oldPassword, String submitedPassword) {
		if ( submitedPassword.isEmpty() ) {
			return true;
		}
		return oldPassword.equals(DigestUtils.md5Hex(submitedPassword));
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
	 * 检查用户真实姓名(公司名称)是否合法.
	 * 规则: 长度不超过32个字符.
	 * @param realName - 用户真实姓名或公司名称
	 * @return 用户真实姓名(公司名称)是否合法  
	 */
	private boolean isRealNameLegal(String realName) {
		int realNameLength = realName.length();
		return realNameLength <= 32;
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
	 * 说明: 仅用于用户创建新账户
	 * @param email - 电子邮件地址
	 * @return 电子邮件地址是否存在
	 */
	private boolean isEmailExists(String email) {
		User user = userDao.getUserUsingEmail(email);
		return user != null;
	}
	
	/**
	 * 检查电子邮件地址是否存在.
	 * 说明: 仅用于用户编辑个人资料
	 * @param currentEmail - 之前所使用的Email地址
	 * @param email - 待更新的Email地址
	 * @return 电子邮件地址是否存在
	 */
	private boolean isEmailExists(String currentEmail, String email) {
		if ( currentEmail.equals(email) ) {
			return false;
		}
		User user = userDao.getUserUsingEmail(email);
		return user != null;
	}
	
	/**
	 * 检查国家名称是否合法.
	 * 规则: 长度不超过24个字符.
	 * @param country - 国家名称 
	 * @return 国家名称是否合法
	 */
	private boolean isCountryLegal(String country) {
		int countryLength = country.length();
		return countryLength <= 24;
	}
	
	/**
	 * 检查省份(州)名称是否合法.
	 * 规则: 长度不超过24个字符.
	 * @param province - 省份(州)名称 
	 * @return 省份(州)名称是否合法
	 */
	private boolean isProvinceLegal(String province) {
		int provinceLength = province.length();
		return provinceLength <= 24;
	}
	
	/**
	 * 检查城市名称是否合法.
	 * 规则: 长度不超过24个字符.
	 * @param city - 城市名称 
	 * @return 城市名称是否合法
	 */
	private boolean isCityLegal(String city) {
		int cityLength = city.length();
		return cityLength <= 24;
	}

	/**
	 * 检查联系电话是否合法.
	 * 规则: 由数字, +和-组成的字符串且长度不超过24个字符.
	 * @param phone - 联系电话
	 * @return 联系电话是否合法
	 */
	private boolean isPhoneLegal(String phone) {
		return phone.matches("^(\\+[0-9]+\\-)*[0-9\\-]+$");
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
	 * 保存电子邮件随机验证代码.
	 * @param email - 用户的电子邮件
	 * @param code - 随机生成的验证代码
	 */
	public void dumpEmailConfidential(String email, String code) { 
		mailVerificationDao.deleteMailVerification(email);
			
		MailVerification verification = new MailVerification(email, code);
		mailVerificationDao.createMailVerification(verification);
	}
	
	/**
	 * 验证电子邮件验证凭据有效性.
	 * @param email - 待验证的电子邮件地址
	 * @param code - 随机生成的验证代码
	 * @return 电子邮件验证凭据有效性
	 */
	public boolean isEmailCondidentialValid(String email, String code) {
		MailVerification verification = mailVerificationDao.getMailVerification(email);
		
		if ( verification != null && verification.getCode().equals(code) ) {
			mailVerificationDao.deleteMailVerification(email);
			
			User user = userDao.getUserUsingEmail(email);
			user.setEmailVerified(true);
			userDao.updateUser(user);
			return true;
		}
		return false;
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

