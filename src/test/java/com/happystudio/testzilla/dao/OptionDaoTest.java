package com.happystudio.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.Option;

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
	 * 测试用例：测试getOption(String Key)方法
	 * 测试数据：使用存在的系统设置Key来查找
	 * 预期结果：返回对应的Option对象
	 */
	@Test
	public void testGetOptionExists() {
		Option option = optionDao.getOption("test");
		Assert.assertEquals("test", option.getOptionKey());
	}
	
	/**
	 * 测试用例：测试getOption(String Key)方法
	 * 测试数据：使用不存在的系统设置Key来查找
	 * 预期结果：返回空引用
	 */
	@Test
	public void testGetOptionNotExists() {
		Option option = optionDao.getOption("test");
		Assert.assertNull(option);
	}
	
	/**
	 * 测试用例：测试updateOption(Option option)方法
	 * 测试数据：使用已存在的系统设置Option来更新
	 * 预期结果：返回TRUE
	 */
	@Test 
	public void testUpdateOptionExists() {
		Option option = new Option(1000, "test", "test");
		boolean result = optionDao.updateOption(option);
		Assert.assertTrue(result);
	}
	
	/**
	 * 测试用例：测试updateOption(Option option)方法
	 * 测试数据：使用不存在的系统设置Option来更新
	 * 预期结果：返回FALSE
	 */
	@Test 
	public void testUpdateOptionNotExists() {
		Option option = new Option(0, "test", "[]");
		boolean result = optionDao.updateOption(option);
		Assert.assertFalse(result);
	}
	
	/**
	 * 测试用例：测试createOption(Option option)方法
	 * 测试数据：使用已存在的系统设置Option来创建
	 * 预期结果：返回FLASE
	 */
	@Test
	public void testCreateOptionExists() {
		Option option = new Option(1000, "test", "test");
		boolean result = optionDao.createOption(option);
		Assert.assertFalse(result);
	}
	
	/**
	 * 测试用例：测试createOption(Option option)方法
	 * 测试数据：使用不存在的系统设置Option来创建
	 * 预期结果：返回TRUE
	 */
	@Test
	public void testCreateOptionNotExists() {
		Option option = new Option(0, "test", "[]");
		boolean result = optionDao.createOption(option);
		Assert.assertTrue(result);
	}

	/**
	 * 待测试的OptionDAO对象
	 */
	@Autowired
	private OptionDao optionDao;
}
