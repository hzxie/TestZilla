package com.trunkshell.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.PointsRuleDao;
import com.trunkshell.testzilla.model.PointsRule;

/**
 * PointsRule测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class PointsRuleDaoTest {
	/**
	 * 测试用例: 测试getPointsRule()方法
	 * 测试用例: 使用存在的积分规则
	 * 预期结果: 返回预期的积分规则对象
	 */
	@Test
	public void testGetPointsRuleExists() {
		PointsRule pointsRule = pointsRuleDao.getPointsRule("create-account");
		Assert.assertNotNull(pointsRule);
		
		int pointsRuleId = pointsRule.getPointsRuleId();
		Assert.assertEquals(1, pointsRuleId);
	}
	
	/**
	 * 测试用例: 测试getPointsRule()方法
	 * 测试用例: 使用不存在的积分规则
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetPointsRuleNotExists() {
		PointsRule pointsRule = pointsRuleDao.getPointsRule("Not Exists");
		Assert.assertNull(pointsRule);
	}
	
	/**
	 * 待测试的PointsRuleDao对象.
	 */
	@Autowired
	private PointsRuleDao pointsRuleDao;
}
