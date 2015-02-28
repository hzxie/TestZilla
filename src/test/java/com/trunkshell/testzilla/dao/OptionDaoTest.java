package com.trunkshell.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.OptionDao;
import com.trunkshell.testzilla.model.Option;

/**
 * OptionDao测试类.
 *@author Zhou Yihao 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class OptionDaoTest {
	
	/**
	 * 测试用例：测试getOption()方法
	 * 测试数据：使用存在的系统设置Key来查找
	 * 预期结果：返回对应的Option对象
	 */
	@Test
	public void testGetOptionExists() {
		Option option = optionDao.getOption("SensitiveWords");
		Assert.assertNotNull(option);
		Assert.assertEquals(8, option.getOptionId());
	}
	
	/**
	 * 测试用例：测试getOption()方法
	 * 测试数据：使用不存在的系统设置Key来查找
	 * 预期结果：返回空引用
	 */
	@Test
	public void testGetOptionNotExists() {
		Option option = optionDao.getOption("NotExists");
		Assert.assertNull(option);
	}
	
	/**
	 * 测试用例：测试updateOption(Option option)方法
	 * 测试数据：使用使用合法数据集, 且系统中存在该Option
	 * 预期结果：返回true, 表示操作成功完成
	 */
	@Test 
	public void testUpdateOptionNormal() {
		Option option = new Option(8, "SensitiveWords", "OptionValue");
		Assert.assertTrue(optionDao.updateOption(option));
		
		option = optionDao.getOption("SensitiveWords");
		Assert.assertEquals("OptionValue", option.getOptionValue());
	}
	
	/**
	 * 测试用例：测试updateOption(Option option)方法
	 * 测试数据：使用不存在的Option更新
	 * 预期结果：返回false, 表示操作未成功完成
	 */
	@Test 
	public void testUpdateOptionNotExists() {
		Option option = new Option(0, "OptionKey", "OptionValue");
		Assert.assertFalse(optionDao.updateOption(option));
	}
	
	/**
	 * 测试用例：测试updateOption()方法
	 * 测试数据：使用不合法的数据集(过长的OptionKey)
	 * 预期结果: 抛出DataException异常
     */
    @Test(expected = org.hibernate.exception.DataException.class)
	public void testUpdateOptionUsingTooLongOptionKey() {
		Option option = new Option(8, "VeryVeryVeryVeryVeryLongOptionKey", "OptionValue");
		optionDao.updateOption(option);
	}
	
	/**
	 * 待测试的OptionDAO对象
	 */
	@Autowired
	private OptionDao optionDao;
}
