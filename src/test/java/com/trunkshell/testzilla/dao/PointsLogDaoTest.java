package com.trunkshell.testzilla.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.PointsLog;
import com.trunkshell.testzilla.model.User;

/**
 * PointsLogDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class PointsLogDaoTest {
	/**
	 * 测试用例: 测试getPointsLogUsingUser()方法
	 * 测试数据: 使用系统中存在积分日志的用户
	 * 预期结果: 返回该用户积分日志
	 */
	@Test
	public void testGetPointsLogUsingExistingUserWithLogs() {
		User user = userDao.getUserUsingUsername("zjhzxhz");
		Assert.assertNotNull(user);
		
		List<PointsLog> pointsLogs = pointsLogDao.getPointsLogUsingUser(user, 0, 10);
		Assert.assertEquals(2, pointsLogs.size());
		
		PointsLog pointsLog = pointsLogs.get(1);
		Assert.assertEquals(100, pointsLog.getPointsRule().getCredit());
	}
	
	/**
	 * 测试用例: 测试getPointsLogUsingUser()方法
	 * 测试数据: 使用系统中不存在积分日志的用户
	 * 预期结果: 返回空的积分日志列表
	 */
	@Test
	public void testGetPointsLogUsingExistingUserWithNoLogs() {
		User user = userDao.getUserUsingUsername("Administrator");
		Assert.assertNotNull(user);
		
		List<PointsLog> pointsLogs = pointsLogDao.getPointsLogUsingUser(user, 0, 10);
		Assert.assertEquals(0, pointsLogs.size());
	}
	
	/**
	 * 测试用例: 测试getPointsLogUsingUser()方法
	 * 测试数据: 使用系统中不存在的用户
	 * 预期结果: 返回空的积分日志列表
	 */
	@Test
	public void testGetPointsLogUsingNotUserNotExists() {
		User user = userDao.getUserUsingUsername("Not Exists");
		Assert.assertNull(user);
		
		List<PointsLog> pointsLogs = pointsLogDao.getPointsLogUsingUser(user, 0, 10);
		Assert.assertEquals(0, pointsLogs.size());
	}
	
	/**
	 * 测试用例: 测试getReputationUsingUser()方法
	 * 测试数据: 使用系统中存在的用户
	 * 预期结果: 返回用户的威望值
	 */
	@Test
	public void testGetReputationUsingExistingUserWithLogs() {
		User user = userDao.getUserUsingUsername("zjhzxhz");
		Assert.assertNotNull(user);
		
		long totalReputation = pointsLogDao.getReputationUsingUser(user);
		Assert.assertEquals(5, totalReputation);
	}

	/**
	 * 测试getReputationUsingUser()方法
	 * 测试数据: 使用系统中不存在积分日志的用户
	 * 预期结果: 返回0
	 */
	public void testGetReputationUsingExistingUserWithNoLogs() {
		User user = userDao.getUserUsingUsername("Administrator");
		Assert.assertNotNull(user);
		
		long totalReputation = pointsLogDao.getReputationUsingUser(user);
		Assert.assertEquals(0, totalReputation);
	}
	
	/**
	 * 测试用例: 测试getReputationUsingUser()方法
	 * 测试数据: 使用系统中不存在的用户
	 * 预期结果: 返回0
	 */
	@Test
	public void testGetReputationUsingUserNotExists() {
		User user = userDao.getUserUsingUsername("Not Exists");
		Assert.assertNull(user);
		
		long totalReputation = pointsLogDao.getReputationUsingUser(user);
		Assert.assertEquals(0, totalReputation);
	}
	
	/**
	 * 测试getCreditsUsingUser()方法
	 * 测试数据: 使用系统中存在积分日志的用户
	 * 预期结果: 返回用户的积分值
	 */
	@Test
	public void testGetCreditsUsingExistingUserWithLogs() {
		User user = userDao.getUserUsingUsername("zjhzxhz");
		Assert.assertNotNull(user);
		
		long totalCredits = pointsLogDao.getCreditsUsingUser(user);
		Assert.assertEquals(25, totalCredits);
	}
	
	/**
	 * 测试getCreditsUsingUser()方法
	 * 测试数据: 使用系统中不存在积分日志的用户
	 * 预期结果: 返回0
	 */
	public void testGetCreditsUsingExistingUserWithNoLogs() {
		User user = userDao.getUserUsingUsername("Administrator");
		Assert.assertNotNull(user);
		
		long totalCredits = pointsLogDao.getCreditsUsingUser(user);
		Assert.assertEquals(0, totalCredits);
	}
	
	/**
	 * 测试用例: 测试getCreditsUsingUser()方法
	 * 测试数据: 使用系统中不存在的用户
	 * 预期结果: 返回0
	 */
	@Test
	public void testGetCreditsUsingUserNotExists() {
		User user = userDao.getUserUsingUsername("Not Exists");
		Assert.assertNull(user);
		
		long totalCredits = pointsLogDao.getCreditsUsingUser(user);
		Assert.assertEquals(0, totalCredits);
	}
	
	/**
	 * 待测试的PointsLogDao对象.
	 */
	@Autowired
	private PointsLogDao pointsLogDao;
	
	/**
	 * 自动注入的UserDao对象.
	 * 协助完成单元测试, 构建测试用例.
	 */
	@Autowired
	private UserDao userDao;
}
