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

import com.trunkshell.testzilla.dao.BugStatusDao;
import com.trunkshell.testzilla.model.BugStatus;

/**
 * BugStatusDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class BugStatusDaoTest {
	/**
	 * 测试用例: 测试getAllBugStatus()方法
	 * 测试数据: N/a
	 * 预期结果: 返回所有BugStatus对象的列表
	 */
	@Test
	public void testGetAllBugStatus() {
		List<BugStatus> bugStatusList = bugStatusDao.getAllBugStatus();
		Assert.assertNotNull(bugStatusList);
		Assert.assertEquals(9, bugStatusList.size());
		
		BugStatus bugStatus = bugStatusList.get(0);
		String bugStatusSlug = bugStatus.getBugStatusSlug();
		Assert.assertEquals("unconfirmed", bugStatusSlug);
	}
	
	/**
	 * 测试用例: 测试getBugStatusUsingId()方法
	 * 测试数据: 使用未确认状态的bugStatusId
	 * 预期结果: 返回未确认状态的BugStatus对象
	 */
	@Test
	public void testGetBugStatusUsingIdExists() {
		BugStatus bugStatus = bugStatusDao.getBugStatusUsingId(1);
		Assert.assertNotNull(bugStatus);
		
		String bugStatusSlug = bugStatus.getBugStatusSlug();
		Assert.assertEquals("unconfirmed", bugStatusSlug);
	}
	
	/**
	 * 测试用例: 测试getBugStatusUsingId()方法
	 * 测试数据: 使用不存在的bugStatusId
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugStatusUsingIdNotExists() {
		BugStatus bugStatus = bugStatusDao.getBugStatusUsingId(0);
		Assert.assertNull(bugStatus);
	}
	
	/**
	 * 测试用例: 测试getBugStatusUsingSlug()方法
	 * 测试数据: 使用未确认状态的bugStatusSlug
	 * 预期结果: 返回未确认状态的BugStatus对象
	 */
	@Test
	public void testGetBugStatusUsingSlugExists() {
		BugStatus bugStatus = bugStatusDao.getBugStatusUsingSlug("unconfirmed");
		Assert.assertNotNull(bugStatus);
		
		int bugStatusId = bugStatus.getBugStatusId();
		Assert.assertEquals(1, bugStatusId);
	}
	
	/**
	 * 测试用例: 测试getBugStatusUsingSlug()方法
	 * 测试数据: 使用不存在的bugStatusSlug
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugStatusUsingSlugNotExists() {
		BugStatus bugStatus = bugStatusDao.getBugStatusUsingSlug("Not Exists");
		Assert.assertNull(bugStatus);
	}
	
	/**
	 * 待测试的BugStatusDao对象.
	 */
	@Autowired
	private BugStatusDao bugStatusDao;
}
