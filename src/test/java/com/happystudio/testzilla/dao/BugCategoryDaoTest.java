package com.happystudio.testzilla.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.happystudio.testzilla.model.BugCategory;

/**
 * BugCategoryDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class BugCategoryDaoTest {
	/**
	 * 测试用例: 测试getAllBugCategory()方法
	 * 测试数据: N/a
	 * 预期结果: 返回所有BugCategory对象的列表
	 */
	@Test
	public void testGetAllBugCategory() {
		List<BugCategory> bugCategories = bugCategoryDao.getAllBugCategory();
		Assert.assertNotNull(bugCategories);
		Assert.assertEquals(12, bugCategories.size());
		
		BugCategory bugCategory = bugCategories.get(5);
		Assert.assertEquals("database", bugCategory.getBugCategorySlug());
	}
	
	/**
	 * 测试用例: 测试getBugCategoryUsingId()方法
	 * 测试数据: 使用数据库异常的bugCategoryId
	 * 预期结果: 返回数据库异常的BugCategory对象
	 */
	@Test
	public void testGetBugCategoryUsingIdExists() {
		BugCategory bugCategory = bugCategoryDao.getBugCategoryUsingId(6);
		Assert.assertNotNull(bugCategory);
		
		String bugCategorySlug = bugCategory.getBugCategorySlug();
		Assert.assertEquals("database", bugCategorySlug);
	}
	
	/**
	 * 测试用例: 测试getBugCategoryUsingId()方法
	 * 测试数据: 使用不存在的bugCategoryId
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugCategoryUsingIdNotExists() {
		BugCategory bugCategory = bugCategoryDao.getBugCategoryUsingId(0);
		Assert.assertNull(bugCategory);
	}
	
	/**
	 * 测试用例: 测试getBugCategoryUsingSlug()方法
	 * 测试数据: 使用数据库异常的bugCategorySlug
	 * 预期结果: 返回数据库异常的BugCategory对象
	 */
	@Test
	public void testGetBugCategoryUsingSlugExists() {
		BugCategory bugCategory = bugCategoryDao.getBugCategoryUsingSlug("database");
		Assert.assertNotNull(bugCategory);
		
		int bugCategoryId = bugCategory.getBugCategoryId();
		Assert.assertEquals(6, bugCategoryId);
	}
	
	/**
	 * 测试用例: 测试getBugCategoryUsingSlug()方法
	 * 测试数据: 使用不存在的bugCategorySlug
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetBugCategoryUsingSlugNotExists() {
		BugCategory bugCategory = bugCategoryDao.getBugCategoryUsingSlug("Not Exists");
		Assert.assertNull(bugCategory);
	}
	
	/**
	 * 待测试的BugCategoryDao对象.
	 */
	@Autowired
	private BugCategoryDao bugCategoryDao;
}
