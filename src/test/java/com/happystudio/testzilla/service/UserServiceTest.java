package com.happystudio.testzilla.service;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.dao.MailVerificationDao;
import com.happystudio.testzilla.dao.UserDao;
import com.happystudio.testzilla.dao.UserGroupDao;
import com.happystudio.testzilla.model.MailVerification;
import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.model.UserGroup;
import com.happystudio.testzilla.util.DigestUtils;

/**
 * UserService测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class UserServiceTest {
	/**
     * 初始化Mock对象.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试用例: 测试getUserUsingUsernameOrEmail()方法
     * 测试数据: 使用存在的用户名
     * 预期结果: 返回预期的User对象
     */
	@Test
    public void testGetUserUsingUsernameExists() {
    	String username = "zjhzxhz";
    	User expectedUser = userDao.getUserUsingUsername(username);
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(expectedUser);
    	
    	User user = userService.getUserUsingUsernameOrEmail(username);
    	Assert.assertEquals(expectedUser, user);
    }
	
	/**
     * 测试用例: 测试getUserUsingUsernameOrEmail()方法
     * 测试数据: 使用不存在的用户名
     * 预期结果: 返回空引用
     */
	@Test
    public void testGetUserUsingUsernameNotExists() {
    	String username = "Not Exists";
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(null);
    	
    	User user = userService.getUserUsingUsernameOrEmail(username);
    	Assert.assertNull(user);
    }
	
	/**
     * 测试用例: 测试getUserUsingUsernameOrEmail()方法
     * 测试数据: 使用存在的电子邮件地址
     * 预期结果: 返回预期的User对象
     */
	@Test
    public void testGetUserUsingEmailExists() {
		String email = "zjhzxhz@gmail.com";
    	User expectedUser = userDao.getUserUsingEmail(email);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(expectedUser);
    	
    	User user = userService.getUserUsingUsernameOrEmail(email);
    	Assert.assertEquals(expectedUser, user);
	}
	
	/**
     * 测试用例: 测试getUserUsingUsernameOrEmail()方法
     * 测试数据: 使用存在的电子邮件地址
     * 预期结果: 返回预期的User对象
     */
	@Test
    public void testGetUserUsingEmailNotExists() {
		String email = "not-exists@testzilla.org";
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	
    	User user = userService.getUserUsingUsernameOrEmail(email);
    	Assert.assertNull(user);
	}

	/**
	 * 测试用例: 测试isAccountValid()方法
	 * 测试数据: 使用合法的登录凭据
	 * 预期结果: 返回包含{isSuccessful: true}的HashMap数据
	 */
	@Test
	public void testAccountValidUsingCorrectConfidential() {
		String username = "zjhzxhz@gmail.com";
		String password = DigestUtils.md5Hex("zjhzxhz");
    	User expectedUser = userDao.getUserUsingEmail(username);
    	Mockito.when(mockedUserDao.getUserUsingEmail(username)).thenReturn(expectedUser);
    	
    	HashMap<String, Boolean> result = userService.isAccountValid(username, password);
    	Assert.assertTrue(result.get("isSuccessful"));
	}
	
	/**
	 * 测试用例: 测试isAccountValid()方法
	 * 测试数据: 使用无效的登录凭据
	 * 预期结果: 返回包含{isAccountValid: false}的HashMap数据
	 */
	@Test
	public void testAccountValidUsingInvalidConfidential() {
		String username = "zjhzxhz";
		String password = DigestUtils.md5Hex("WrongPassword");
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(null);
    	
    	HashMap<String, Boolean> result = userService.isAccountValid(username, password);
    	Assert.assertFalse(result.get("isAccountValid"));
    	Assert.assertFalse(result.get("isSuccessful"));
	}
	
	/**
	 * 测试用例: 测试isAccountValid()方法
	 * 测试数据: 使用空用户名
	 * 预期结果: 返回包含{isUsernameEmpty: true}的HashMap数据
	 */
	@Test
	public void testAccountValidUsingEmptyUsername() {
		String username = "";
		String password = DigestUtils.md5Hex("zjhzxhz");
    	
    	HashMap<String, Boolean> result = userService.isAccountValid(username, password);
    	Assert.assertTrue(result.get("isUsernameEmpty"));
    	Assert.assertFalse(result.get("isSuccessful"));
	}
	
	/**
	 * 测试用例: 测试isAccountValid()方法
	 * 测试数据: 使用空用户名
	 * 预期结果: 返回包含{isPasswordEmpty: true}的HashMap数据
	 */
	@Test
	public void testAccountValidUsingEmptyPassword() {
		String username = "zjhzxhz";
		String password = DigestUtils.md5Hex("");
    	
    	HashMap<String, Boolean> result = userService.isAccountValid(username, password);
    	Assert.assertTrue(result.get("isPasswordEmpty"));
    	Assert.assertFalse(result.get("isSuccessful"));
	}
	
	/**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用合法的数据集且系统中不存在该用户
     * 预期结果: 返回包含{isSuccessful: true}的HashMap数据
     */
	@Test
	public void testCreateUserNormal() {
		String username = "zjhzxhz";
    	String email = "zjhzxhz@gmail.com";
    	String password = "Password";

    	User user = userDao.getUserUsingUsername(username);
    	user.setUid(0);
    	
    	UserGroup userGroup = user.getUserGroup();
    	String userGroupSlug = userGroup.getUserGroupSlug();
    	
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(null);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	Mockito.when(mockedUserDao.createUser(user)).thenReturn(true);
    	Mockito.when(mockedUserGroupDao.getUserGroupUsingSlug(userGroupSlug)).thenReturn(userGroup);
    	
    	HashMap<String, Boolean> result = userService.createUser(user.getUsername(), 
    			password, password, user.getUserGroup().getUserGroupSlug(), 
    			user.getRealName(), user.getEmail(), user.getCountry(), user.getProvince(), 
    			user.getCity(), user.getPhone(), user.getWebsite(), user.isIndividual());
    	
		Assert.assertTrue(result.get("isSuccessful"));
	}
	
    /**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用合法的数据集但系统中已存在该用户名
     * 预期结果: 返回包含{isUsernameExists: true}的HashMap数据
     */
	@Test
    public void testCreateUserUsingExistingUsername() {
    	String username = "zjhzxhz";
    	String email = "zjhzxhz@gmail.com";
    	String password = "Password";
    	User user = userDao.getUserUsingUsername(username);
    	
    	UserGroup userGroup = user.getUserGroup();
    	String userGroupSlug = userGroup.getUserGroupSlug();
    	
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(user);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	Mockito.when(mockedUserGroupDao.getUserGroupUsingSlug(userGroupSlug)).thenReturn(userGroup);
    	
    	HashMap<String, Boolean> result = userService.createUser(user.getUsername(), 
    			password, password, user.getUserGroup().getUserGroupSlug(), 
    			user.getRealName(), user.getEmail(), user.getCountry(), user.getProvince(), 
    			user.getCity(), user.getPhone(), user.getWebsite(), user.isIndividual());
    	
    	Assert.assertTrue(result.get("isUsernameExists"));
    	Assert.assertFalse(result.get("isEmailExists"));
    	Assert.assertFalse(result.get("isSuccessful"));
    }
	
	/**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据集(不匹配的密码和无效的电子邮件地址)
     * 预期结果: 返回包含{isPasswordMatched: false}的HashMap数据
     */
	@Test
    public void testCreateUserUsingMismatchedPasswordAndIllegalEmail() {
    	String username = "zjhzxhz";
    	String email = "zjhzxhz";
    	
    	User user = userDao.getUserUsingUsername(username);
    	user.setEmail(email);
    	user.setCity("");
    	user.setWebsite("");
    	
    	UserGroup userGroup = user.getUserGroup();
    	String userGroupSlug = userGroup.getUserGroupSlug();
    	
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(user);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	Mockito.when(mockedUserGroupDao.getUserGroupUsingSlug(userGroupSlug)).thenReturn(userGroup);
    	
    	HashMap<String, Boolean> result = userService.createUser(user.getUsername(), 
    			"Password", "password", user.getUserGroup().getUserGroupSlug(), 
    			user.getRealName(), user.getEmail(), user.getCountry(), user.getProvince(), 
    			user.getCity(), user.getPhone(), user.getWebsite(), user.isIndividual());
    	
    	Assert.assertFalse(result.get("isPasswordMatched"));
    	Assert.assertFalse(result.get("isEmailLegal"));
    	Assert.assertTrue(result.get("isCityLegal"));
    	Assert.assertFalse(result.get("isSuccessful"));
    }
	
	/**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据集(空用户名和过长的密码)
     * 预期结果: 返回包含{isUsernameEmpty: true, isPasswordLegal: false}的HashMap数据
     */
	@Test
    public void testCreateUserUsingEmptyUsernameAndIllegalPassword() {
    	String username = "zjhzxhz";
    	String email = "zjhzxhz@gmail.com";
    	
    	User user = userDao.getUserUsingUsername(username);
    	user.setUsername("");
    	
    	UserGroup userGroup = user.getUserGroup();
    	String userGroupSlug = userGroup.getUserGroupSlug();
    	
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(user);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	Mockito.when(mockedUserGroupDao.getUserGroupUsingSlug(userGroupSlug)).thenReturn(userGroup);
    	
    	HashMap<String, Boolean> result = userService.createUser(user.getUsername(), 
    			user.getPassword(), user.getPassword(), user.getUserGroup().getUserGroupSlug(), 
    			user.getRealName(), user.getEmail(), user.getCountry(), user.getProvince(), 
    			user.getCity(), user.getPhone(), user.getWebsite(), user.isIndividual());
    	
    	Assert.assertTrue(result.get("isUsernameEmpty"));
    	Assert.assertFalse(result.get("isPasswordLegal"));
    	Assert.assertFalse(result.get("isSuccessful"));
    }
	
	/**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据集(过长的国家名称和无效的电话号码)
     * 预期结果: 返回包含{isCountryLegal: false, isPhoneLegal: false}的HashMap数据
     */
	@Test
    public void testCreateUserUsingIllegalCountryAndPhoneNumber() {
    	String username = "zjhzxhz";
    	String email = "zjhzxhz@gmail.com";
    	
    	User user = userDao.getUserUsingUsername(username);
    	user.setCountry("VeryVeryVeryVeryLongCountryName");
    	user.setPhone("Invalid Phone");
    	
    	UserGroup userGroup = user.getUserGroup();
    	String userGroupSlug = userGroup.getUserGroupSlug();
    	
    	Mockito.when(mockedUserDao.getUserUsingUsername(username)).thenReturn(user);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(null);
    	Mockito.when(mockedUserGroupDao.getUserGroupUsingSlug(userGroupSlug)).thenReturn(userGroup);
    	
    	HashMap<String, Boolean> result = userService.createUser(user.getUsername(), 
    			user.getPassword(), user.getPassword(), user.getUserGroup().getUserGroupSlug(), 
    			user.getRealName(), user.getEmail(), user.getCountry(), user.getProvince(), 
    			user.getCity(), user.getPhone(), user.getWebsite(), user.isIndividual());
    	
    	Assert.assertFalse(result.get("isPhoneLegal"));
    	Assert.assertFalse(result.get("isCountryLegal"));
    	Assert.assertFalse(result.get("isSuccessful"));
    }
    
    /**
     * 测试用例: 测试isEmailCondidentialValid()方法
     * 测试数据: 使用有效的验证凭据
     * 预期结果: 返回true, 表示凭据有效
     */
    @Test
    public void testEmailCondidentialValidExists() {
    	String email = "zjhzxhz@gmail.com";
    	String code = DigestUtils.generateGuid();
    	
    	MailVerification verification = new MailVerification(email, code);
    	Mockito.when(mockedMailVerificationDao.getMailVerification(email)).thenReturn(verification);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(new User());
    	
    	Assert.assertTrue(userService.isEmailCondidentialValid(email, code));
    }
    
    /**
     * 测试用例: 测试isEmailCondidentialValid()方法
     * 测试数据: 使用无效的验证凭据
     * 预期结果: 返回false, 表示凭据无效
     */
    @Test
    public void testEmailCondidentialValidNotExists() {
    	String email = "zjhzxhz@gmail.com";
    	String code = DigestUtils.generateGuid();
    	
    	MailVerification verification = new MailVerification(email, code);
    	Mockito.when(mockedMailVerificationDao.getMailVerification(email)).thenReturn(verification);
    	Mockito.when(mockedUserDao.getUserUsingEmail(email)).thenReturn(new User());
    	
    	Assert.assertFalse(userService.isEmailCondidentialValid(email, "Invalid Code"));
    }
    
    /**
     * 待测试的UserService对象.
     */
    @InjectMocks
    private UserService userService;
    
    /**
     * 自动注入的UserDao对象.
     * 协助完成单元测试, 构建测试用例.
     */
    @Autowired
    private UserDao userDao;
    
    /**
     * Mock的UserDao对象.
     */
    @Mock
    private UserDao mockedUserDao;
    
    /**
     * Mock的UserGroupDao对象.
     */
    @Mock
    private UserGroupDao mockedUserGroupDao;
    
    /**
     * Mock的MailVerificationDao对象.
     */
    @Mock
    private MailVerificationDao mockedMailVerificationDao;
}
