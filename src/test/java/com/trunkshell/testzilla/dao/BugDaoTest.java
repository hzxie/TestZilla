package com.trunkshell.testzilla.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.BugDao;
import com.trunkshell.testzilla.dao.ProductDao;
import com.trunkshell.testzilla.dao.UserDao;
import com.trunkshell.testzilla.model.Bug;
import com.trunkshell.testzilla.model.BugCategory;
import com.trunkshell.testzilla.model.BugSeverity;
import com.trunkshell.testzilla.model.BugStatus;
import com.trunkshell.testzilla.model.Product;
import com.trunkshell.testzilla.model.User;

/**
 * BugDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class BugDaoTest {
	/**
	 * 测试用例: 测试getTotalBugsUsingProduct()方法
	 * 测试数据: 使用某个项目的产品对象
	 * 预期结果: 返回该项目的Bug数量(2)
	 */
	@Test
	public void testGetTotalBugsUsingProductExists() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		
		long numberOfBugs = bugDao.getTotalBugsUsingProduct(product);
		Assert.assertEquals(2, numberOfBugs);
	}
	
	/**
	 * 测试用例: 测试getTotalBugsUsingProduct()方法
	 * 测试数据: 使用不存在的产品对象
	 * 预期结果: 返回0
	 */
	@Test
	public void testGetTotalBugsUsingProductNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		Assert.assertNull(product);
		
		long numberOfBugs = bugDao.getTotalBugsUsingProduct(product);
		Assert.assertEquals(0, numberOfBugs);
	}
	
	/**
	 * 测试用例: 测试getBugsUsingProduct()方法
	 * 测试数据: 使用某个项目的产品对象
	 * 预期结果: 返回该项目的Bug列表
	 */
	@Test
	public void testGetBugsUsingProductExists() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		
		List<Bug> bugs = bugDao.getBugsUsingProduct(product, 0, 10);
		Assert.assertEquals(2, bugs.size());
		
		Bug bug = bugs.get(0);
		Assert.assertEquals(product, bug.getProduct());
	}
	
	/**
	 * 测试用例: 测试getBugsUsingProduct()方法
	 * 测试数据: 使用不存在的项目的产品对象
	 * 预期结果: 返回大小为0的Bug列表
	 */
	@Test
	public void testGetBugsUsingProductNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		Assert.assertNull(product);
		
		List<Bug> bugs = bugDao.getBugsUsingProduct(product, 0, 10);
		Assert.assertEquals(0, bugs.size());
	}
	
	/**
	 * 测试用例: 测试getTotalBugsUsingDeveloper()方法
	 * 测试数据: 使用某个用户的用户对象
	 * 预期结果: 返回该用户所开发产品的Bug数量(2)
	 */
	@Test
	public void testGetTotalBugsUsingDeveloperExists() {
		User developer = userDao.getUserUsingUid(1002);
		Assert.assertNotNull(developer);
		
		long totalBugs = bugDao.getTotalBugsUsingDeveloper(developer);
		Assert.assertEquals(2, totalBugs);
	}
	
	/**
	 * 测试用例: 测试getTotalBugsUsingDeveloper()方法
	 * 测试数据: 使用不存在用户的用户对象
	 * 预期结果: 返回0
	 */
	@Test
	public void testGetTotalBugsUsingDeveloperNotExists() {
		User developer = userDao.getUserUsingUid(0);
		Assert.assertNull(developer);
		
		long totalBugs = bugDao.getTotalBugsUsingDeveloper(developer);
		Assert.assertEquals(0, totalBugs);
	}
	
	/**
	 * 测试用例: 测试getBugsUsingDeveloper()方法
	 * 测试数据: 使用某个用户的用户对象
	 * 预期结果: 返回该用户所开发产品的Bug列表
	 */
	@Test
	public void testGetBugsUsingDeveloperExists() {
		User developer = userDao.getUserUsingUid(1002);
		Assert.assertNotNull(developer);
		
		List<Bug> bugs = bugDao.getBugsUsingDeveloper(developer, 0, 10);
		Assert.assertEquals(2, bugs.size());
	
		Bug bug = bugs.get(0);
		Assert.assertEquals("Administrator", bug.getHunter().getUsername());
	}
	
	/**
	 * 测试用例: 测试getBugsUsingDeveloper()方法
	 * 测试数据: 使用不存在用户的用户对象
	 * 预期结果: 返回该用户所开发产品的Bug列表(长度为0)
	 */
	@Test
	public void testGetBugsUsingDeveloperNotExists() {
		User developer = userDao.getUserUsingUid(0);
		Assert.assertNull(developer);
		
		List<Bug> bugs = bugDao.getBugsUsingDeveloper(developer, 0, 10);
		Assert.assertEquals(0, bugs.size());
	}
	
	/**
	 * 测试用例: 测试getTotalBugsUsingHunter()方法
	 * 测试数据: 使用某个用户的用户对象
	 * 预期结果: 返回该用户所发现的Bug数量(2)
	 */
	@Test
	public void testGetTotalBugsUsingHunterExists() {
		User hunter = userDao.getUserUsingUid(1001);
		Assert.assertNotNull(hunter);
		
		long totalBugs = bugDao.getTotalBugsUsingHunter(hunter);
		Assert.assertEquals(2, totalBugs);
	}
	
	/**
	 * 测试用例: 测试getTotalBugsUsingHunter()方法
	 * 测试数据: 使用某个用户的用户对象
	 * 预期结果: 返回0
	 */
	@Test
	public void testGetTotalBugsUsingHunterNotExists() {
		User hunter = userDao.getUserUsingUid(0);
		Assert.assertNull(hunter);
		
		long totalBugs = bugDao.getTotalBugsUsingHunter(hunter);
		Assert.assertEquals(0, totalBugs);
	}
	
	/**
	 * 测试用例: 测试getBugsUsingHunter()方法
	 * 测试数据: 使用某个用户的用户对象
	 * 预期结果: 返回该用户所发现的Bug列表
	 */
	@Test
	public void testGetBugsUsingHunterExists() {
		User hunter = userDao.getUserUsingUid(1001);
		Assert.assertNotNull(hunter);
		
		List<Bug> bugs = bugDao.getBugsUsingHunter(hunter, 0, 10);
		Assert.assertEquals(2, bugs.size());
		
		Bug bug = bugs.get(0);
		Assert.assertEquals("zjhzxhz", bug.getHunter().getUsername());
	}
	
	/**
	 * 测试用例: 测试getBugsUsingHunter()方法
	 * 测试数据: 使用不存在的用户对象 
	 * 预期结果: 返回该用户所发现的Bug列表(长度为0)
	 */
	@Test
	public void testGetBugsUsingHunterNotExists() {
		User hunter = userDao.getUserUsingUid(0);
		Assert.assertNull(hunter);
		
		List<Bug> bugs = bugDao.getBugsUsingHunter(hunter, 0, 10);
		Assert.assertEquals(0, bugs.size());
	}
	
	/**
	 * 测试用例: 测试createBug()方法
	 * 测试数据: 使用合法的数据集
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testCreateBugNormal() {
		Product product = productDao.getProductsUsingProductId(1000);
		BugCategory category = new BugCategory(1, "crashes", "Application Crashes");
		BugStatus status = new BugStatus(1, "unconfirmed", "unconfirmed");
		BugSeverity severity = new BugSeverity(1, "critical", "Critical");
		Date createTime = new Date();
		User hunter = userDao.getUserUsingUid(1000);
		
		Bug bug = new Bug(product, "1.0", category, status, severity, createTime, 
							hunter, "Bug Title", "Description", "Screenshots");
		Assert.assertTrue(bugDao.createBug(bug));
	}
	
	/**
	 * 测试用例: 测试createBug()方法
	 * 测试数据: 使用不合法的数据集(不存在的产品)
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testCreateBugUsingProductNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		BugCategory category = new BugCategory(1, "crashes", "Application Crashes");
		BugStatus status = new BugStatus(1, "unconfirmed", "unconfirmed");
		BugSeverity severity = new BugSeverity(1, "critical", "Critical");
		Date createTime = new Date();
		User hunter = userDao.getUserUsingUid(1000);
		
		Bug bug = new Bug(product, "1.0", category, status, severity, createTime, 
							hunter, "Bug Title", "Description", "Screenshots");
		bugDao.createBug(bug);
	}
	
	/**
	 * 测试用例: 测试updateBug()方法
	 * 测试数据: 使用合法的数据集且系统中存在该Bug
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testUpdateBugNormal() {
		Bug bug = bugDao.getBugUsingBugId(1000);
		Assert.assertNotNull(bug);
		
		bug.setProductVersion("1.0");
		Assert.assertTrue(bugDao.updateBug(bug));
		
		bug = bugDao.getBugUsingBugId(1000);
		Assert.assertEquals("1.0", bug.getProductVersion());
	}
	
	/**
	 * 测试用例: 测试updateBug()方法
	 * 测试数据: 使用不合法的数据集(不存在的产品)
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testUpdateBugUsingProductNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		
		Bug bug = bugDao.getBugUsingBugId(1000);
		Assert.assertNotNull(bug);
		
		bug.setProduct(product);
		bugDao.updateBug(bug);
	}
	
	/**
	 * 测试用例: 测试updateBug()方法
	 * 测试数据: 使用合法的数据集但系统中不存在该Bug
	 * 预期结果: 返回false, 表示操作未成功完成
	 */
	@Test
	public void testUpdateBugNotExists() {
		Bug bug = bugDao.getBugUsingBugId(1000);
		Assert.assertNotNull(bug);
		
		bug.setBugId(0);
		Assert.assertFalse(bugDao.updateBug(bug));
	}
	
	/**
	 * 测试用例: 测试deleteBug()方法
	 * 测试数据: 存在的Bug唯一标识符
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testDeleteBugExists() {
		Bug bug = bugDao.getBugUsingBugId(1000);
		Assert.assertNotNull(bug);
		
		Assert.assertTrue(bugDao.deleteBug(1000));
		bug = bugDao.getBugUsingBugId(1000);
		Assert.assertNull(bug);
	}
	
	/**
	 * 测试用例: 测试deleteBug()方法
	 * 测试数据: 不存在的Bug唯一标识符
	 * 预期结果: 返回false, 表示操作未成功完成
	 */
	@Test
	public void testDeleteBugNotExists() {
		Bug bug = bugDao.getBugUsingBugId(0);
		Assert.assertNull(bug);
		
		Assert.assertFalse(bugDao.deleteBug(0));
	}
	
	/**
	 * 待测试的BugDao对象.
	 */
	@Autowired
	private BugDao bugDao;
	
	/**
	 * 自动注入的UserDao对象.
	 * 协助完成单元测试, 构建测试用例.
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * 自动注入的ProductDao对象.
	 * 协助完成单元测试, 构建测试用例.
	 */
	@Autowired
	private ProductDao productDao;
}
