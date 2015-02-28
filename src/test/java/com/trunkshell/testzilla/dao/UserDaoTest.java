package com.trunkshell.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.UserDao;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.model.UserGroup;
import com.trunkshell.testzilla.util.DigestUtils;

/**
 * UserGroupDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class UserDaoTest {
	/**
	 * 测试用例: 测试getUserUsingUid()方法
	 * 测试数据: 使用存在的用户的uid
	 * 预期结果: 返回预期的User对象
	 */
	@Test
	public void testGetUserUsingUidExists() {
		User user = userDao.getUserUsingUid(1001);
		Assert.assertNotNull(user);
		
		String username = user.getUsername();
		Assert.assertEquals("zjhzxhz", username);
	}
	
	/**
	 * 测试用例: 测试getUserUsingUid()方法
	 * 测试数据: 使用不存在的用户的uid
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetUserUsingUidNotExists() {
		User user = userDao.getUserUsingUid(0);
		Assert.assertNull(user);
	}
	
	/**
	 * 测试用例: 测试getUserUsingUsername()方法
	 * 测试数据: 使用存在的用户的用户名
	 * 预期结果: 返回预期的User对象
	 */
	@Test
	public void testGetUserUsingUsernameExists() {
		User user = userDao.getUserUsingUsername("zjhzxhz");
		Assert.assertNotNull(user);
		
		long uid = user.getUid();
		Assert.assertEquals(1001, uid);
	}
	
	/**
	 * 测试用例: 测试getUserUsingUsername()方法
	 * 测试数据: 使用不存在用户的用户名
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetUserUsingUsernameNotExists() {
		User user = userDao.getUserUsingUsername("NOT Exists");
		Assert.assertNull(user);
	}
	
	/**
	 * 测试用例: 测试getUserUsingEmail()方法
	 * 测试数据: 使用存在的用户的电子邮件地址
	 * 预期结果: 返回预期的User对象
	 */
	@Test
	public void testGetUserUsingEmailExists() {
		User user = userDao.getUserUsingEmail("zjhzxhz@gmail.com");
		Assert.assertNotNull(user);
		
		long uid = user.getUid();
		Assert.assertEquals(1001, uid);
	}
	
	/**
	 * 测试用例: 测试getUserUsingEmail()方法
	 * 测试数据: 使用不存在用户的电子邮件地址
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetUserUsingEmailNotExists() {
		User user = userDao.getUserUsingEmail("NOT Exists");
		Assert.assertNull(user);
	}
	
	/**
	 * 测试用例: 测试createUser()方法
	 * 测试数据: 使用合法的数据集, 且系统中不存在该用户
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testCreateUserNormal() {
		UserGroup userGroup = new UserGroup(1, "tester", "Tester");
		User user = new User("NewUser", DigestUtils.md5Hex("Password"), userGroup, 
				  "Tester", "user@testzilla.org", "China", "Zhejiang", "Hangzhou",
				  "+86-15695719136", null, false, false);
		Assert.assertTrue(userDao.createUser(user));
		
		user = userDao.getUserUsingUsername("NewUser");
		Assert.assertNotNull(user);
		
		String username = user.getUsername();
		Assert.assertEquals("NewUser", username);
	}
	
	/**
	 * 测试用例: 测试createUser()方法
	 * 测试数据: 使用合法的数据集, 但系统中已存在该用户名
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testCreateUserUsingExistingUsername() {
		UserGroup userGroup = new UserGroup(1, "tester", "Tester");
		User user = new User("zjhzxhz", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "user@testzilla.org", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
		userDao.createUser(user);
	}
    
    /**
	 * 测试用例: 测试createUser()方法
	 * 测试数据: 使用合法的数据集, 但系统中已存在该电子邮件地址
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testCreateUserUsingExistingEmail() {
    	UserGroup userGroup = new UserGroup(1, "tester", "Tester");
		User user = new User("NewUser", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "zjhzxhz@gmail.com", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
		userDao.createUser(user);
	}
    
    /**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据(缺少必填项)
     * 预期结果: 返回false, 表示操作未成功完成
     */
    @Test
    public void testCreateUserUsingEmptyUsername() {
    	UserGroup userGroup = new UserGroup(1, "tester", "Tester");
    	User user = new User("", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "zjhzxhz@gmail.com", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
        Assert.assertFalse(userDao.createUser(user));
    }
    
    /**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据(过长的用户名)
     * 预期结果: 抛出DataException异常
     */
    @Test(expected = org.hibernate.exception.DataException.class)
    public void testCreateUserUsingIllegalUsername() {
    	UserGroup userGroup = new UserGroup(1, "tester", "Tester");
    	User user = new User("VeryVeryLongUsername", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "zjhzxhz@gmail.com", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
        userDao.createUser(user);
    }

    /**
     * 测试用例: 测试createUser()方法
     * 测试数据: 使用不合法的数据(不存在的外键值)
     * 预期结果: 抛出ConstraintViolationException异常
     */
    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
    public void testCreateUserUsingIllegalUserGroup() {
        UserGroup userGroup = new UserGroup(65535, "not-exists", "Not Exists");
        User user = new User("NewUser", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "zjhzxhz@gmail.com", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
        userDao.createUser(user);
    }

    /**
     * 测试用例: 测试updateUser()方法
     * 测试数据: 使用合法的数据集, 且系统中已存在该用户
     * 预期结果: 返回true, 表示操作成功完成
     */
    @Test
    public void testUpdateUserNormal() {
        User user = userDao.getUserUsingUid(1001);
        Assert.assertNotNull(user);
        
        user.setPassword(DigestUtils.md5Hex("Password"));
        Assert.assertTrue(userDao.updateUser(user));
        
        user = userDao.getUserUsingUid(1001);
        Assert.assertEquals(DigestUtils.md5Hex("Password"), user.getPassword());
    }

    /**
     * 测试用例: 测试updateUser()方法
     * 测试数据: 使用合法的数据, 但系统中不存在该用户
     * 预期结果: 返回false, 表示操作未成功完成
     */
    @Test
    public void testUpdateUserNotExists() {
        User user = userDao.getUserUsingUid(0);
        Assert.assertNull(user);
        
        UserGroup userGroup = new UserGroup(1, "tester", "Tester");
        user = new User(0, "NewUser", DigestUtils.md5Hex("Password"), userGroup, 
				 "Tester", "zjhzxhz@gmail.com", "China", "Zhejiang", "Hangzhou",
				 "+86-15695719136", null, false, false);
        Assert.assertFalse(userDao.updateUser(user));
    }
    
    /**
     * 测试用例: 测试updateUser()方法
     * 测试数据: 使用不合法的数据(已存在的电子邮件地址)
     * 预期结果: 抛出ConstraintViolationException异常
     */
    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
    public void testUpdateUserUsingExistingEmail() {
    	User user = userDao.getUserUsingUid(1000);
        Assert.assertNotNull(user);
    	
        user.setEmail("zjhzxhz@gmail.com");
        userDao.updateUser(user);
    }
    
    /**
     * 测试用例: 测试updateUser()方法
     * 测试数据: 使用不合法的数据(过长的密码)
     * 预期结果: 抛出DataException异常
     */
    @Test(expected = org.hibernate.exception.DataException.class)
    public void testUpdateUserUsingIllegalPassword() {
    	User user = userDao.getUserUsingUid(1001);
        Assert.assertNotNull(user);
    	
        user.setPassword(DigestUtils.md5Hex("Password") + "X");
        userDao.updateUser(user);
    }
    
    /**
     * 测试用例: 测试updateUser()方法
     * 测试数据: 使用不合法的数据(不存在的外键值)
     * 预期结果: 抛出ConstraintViolationException异常
     */
    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
    public void testUpdateUserUsingIllegalUserGroup() {
    	User user = userDao.getUserUsingUid(1001);
        Assert.assertNotNull(user);
        
    	UserGroup userGroup = new UserGroup(65535, "not-exists", "Not Exists");
    	user.setUserGroup(userGroup);
        Assert.assertFalse(userDao.updateUser(user));
    }
    
	/**
     * 测试用例: 测试deleteUser()方法
     * 测试数据: 存在的用户唯一标识符
     * 预期结果: 返回true, 表示操作成功完成
     */
    @Test
    public void testDeleteUserExists() {
        User user = userDao.getUserUsingUid(1001);
        Assert.assertNotNull(user);
        
        Assert.assertTrue(userDao.deleteUser(1001));
        
        user = userDao.getUserUsingUid(1);
        Assert.assertNull(user);
    }
    
    /**
     * 测试用例: 测试deleteUser()方法
     * 测试数据: 不存在的用户唯一标识符
     * 预期结果: 返回false, 表示操作未成功完成
     */
    @Test
    public void testDeleteUserNotExists() {
        User user = userDao.getUserUsingUid(0);
        Assert.assertNull(user);
        
        Assert.assertFalse(userDao.deleteUser(0));
    }
	
	/**
	 * 待测试的UserDAO对象.
	 */
	@Autowired
	private UserDao userDao;
}
