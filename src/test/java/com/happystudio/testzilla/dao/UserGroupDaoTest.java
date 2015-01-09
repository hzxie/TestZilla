package com.happystudio.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.happystudio.testzilla.model.UserGroup;

/**
 * UserGroupDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class UserGroupDaoTest {
	/**
	 * 测试用例: 测试getUserGroupUsingId()方法
	 * 测试数据: 使用测试用户的userGroupId
	 * 预期结果: 返回测试用户的userGroup对象
	 */
	@Test
	public void testGetUserGroupUsingIdExists() {
		UserGroup userGroup = userGroupDao.getUserGroupUsingId(1);
		Assert.assertNotNull(userGroup);
		
		String userGroupSlug = userGroup.getUserGroupSlug();
		Assert.assertEquals("tester", userGroupSlug);
	}
	
	/**
	 * 测试用例: 测试getUserGroupUsingId()方法
	 * 测试数据: 使用不存在的userGroupId
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetUserGroupUsingIdNotExists() {
		UserGroup userGroup = userGroupDao.getUserGroupUsingId(0);
		Assert.assertNull(userGroup);
	}
	
	/**
	 * 测试用例: 测试getUserGroupUsingSlug()方法
	 * 测试数据: 使用开发者用户的userGroupSlug
	 * 预期结果: 返回开发者用户的userGroup对象
	 */
	@Test
	public void testGetUserGroupUsingSlugExists() {
		UserGroup userGroup = userGroupDao.getUserGroupUsingSlug("developer");
		Assert.assertNotNull(userGroup);
		
		int userGroupId = userGroup.getUserGroupId();
		Assert.assertEquals(2, userGroupId);
	}
	
	/**
	 * 测试用例: 测试getUserGroupUsingSlug()方法
	 * 测试数据: 使用不存在的userGroupSlug
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetUserGroupUsingSlugNotExists() {
		UserGroup userGroup = userGroupDao.getUserGroupUsingSlug("NOT Exists");
		Assert.assertNull(userGroup);
	}
	
	/**
     * 待测试的UserGroupDao对象.
     */
    @Autowired
    private UserGroupDao userGroupDao;
}
