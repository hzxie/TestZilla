package com.happystudio.testzilla.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.happystudio.testzilla.dao.UserDao;
import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.model.UserGroup;
import com.happystudio.testzilla.util.DigestUtils;

/**
 * UserService测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
     * 测试用例: 测试createUser方法
     * 测试数据: 已存在的用户名
     * 预期结果: 
     */
    @Test
    public void testCreateUserUsingExistingUsernameAndExistingEmail() {
    	String username = "zjhzxhz";
    	String password = DigestUtils.md5Hex("zjhzxhz");
    	UserGroup userGroup = new UserGroup(1, "tester", "Tester");
    	String realName = "谢浩哲";
    	String email = "test@testzilla.org";
    	String country = "China";
    	String province = "Zhejiang";
    	String city = "Hangzhou";
    	String phone = "+86-15695719136";
    	String website = "http://zjhzxhz.com";
    	boolean isIndividual = true;
    	boolean isEmailVerified = false;
    	
    	User user = new User(username, password, userGroup, realName, email, country, 
    			province, city, phone, website, isIndividual, isEmailVerified);
    	Mockito.when(userDao.createUser(user)).thenReturn(false);
    }
    
    /**
     * 待测试的UserService对象.
     */
    @InjectMocks
    private UserService userService;
    
    /**
     * Mock的UserService对象.
     */
    @Mock
    private UserDao userDao;
}
