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

import com.trunkshell.testzilla.dao.BugSeverityDao;
import com.trunkshell.testzilla.model.BugSeverity;

/**
 * BugSeverityDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class BugSeverityDaoTest {
	/**
	 * 测试用例: 测试getAllBugSeverity()方法
	 * 测试数据: N/a
	 * 预期结果: 返回所有BugSeverity对象的列表
	 */
	@Test
	public void testGetAllBugSeverity() {
		List<BugSeverity> bugSeverityList = bugSeverityDao.getAllBugSeverity();
		Assert.assertNotNull(bugSeverityList);
		Assert.assertEquals(4, bugSeverityList.size());
		
		BugSeverity bugSeverity = bugSeverityList.get(0);
		String bugSeveritySlug = bugSeverity.getBugSeveritySlug();
		Assert.assertEquals("critical", bugSeveritySlug);
	}
	
	/**
	 * 测试用例: 测试getBugSeverityUsingId()方法
	 * 测试数据: 使用致命(Critical)级别的bugSeverityId
	 * 预期结果: 返回致命(Critical)级别的BugSeverity对象
	 */
	@Test
	public void testGetBugSeverityUsingIdExists() {
		BugSeverity bugSeverity = bugSeverityDao.getBugSeverityUsingId(1);
		Assert.assertNotNull(bugSeverity);
		
		String bugSeveritySlug = bugSeverity.getBugSeveritySlug();
		Assert.assertEquals("critical", bugSeveritySlug);
	}
	
	/**
	 * 测试用例: 测试getBugSeverityUsingId()方法
	 * 测试数据: 使用不存在的bugSeverityId
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugSeverityUsingIdNotExists() {
		BugSeverity bugSeverity = bugSeverityDao.getBugSeverityUsingId(0);
		Assert.assertNull(bugSeverity);
	}
	
	/**
	 * 测试用例: 测试getBugSeverityUsingSlug()方法
	 * 测试数据: 使用致命(Critical)级别的bugSeveritySlug
	 * 预期结果: 返回致命(Critical)级别的BugSeverity对象
	 */
	@Test
	public void testGetBugSeverityUsingSlugExists() {
		BugSeverity bugSeverity = bugSeverityDao.getBugSeverityUsingSlug("critical");
		Assert.assertNotNull(bugSeverity);
		
		int bugSeverityId = bugSeverity.getBugSeverityId();
		Assert.assertEquals(1, bugSeverityId);
	}
	
	/**
	 * 测试用例: 测试getBugSeverityUsingSlug()方法
	 * 测试数据: 使用不存在的bugSeveritySlug
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugSeverityUsingSlugNotExists() {
		BugSeverity bugSeverity = bugSeverityDao.getBugSeverityUsingSlug("Not Exists");
		Assert.assertNull(bugSeverity);
	}
	
	/**
	 * 待测试的BugSeverityDao对象.
	 */
	@Autowired
	private BugSeverityDao bugSeverityDao;
}
