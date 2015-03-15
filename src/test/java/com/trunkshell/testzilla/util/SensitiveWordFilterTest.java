package com.trunkshell.testzilla.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * SensitiveWordFilter的测试类
 * @author Zhou YiHao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class SensitiveWordFilterTest {
	/**
	 * 测试用例：测试filter()方法
	 * 测试数据: 包含敏感词的数据(法轮大法)
	 * 预期结果: 过滤敏感词后的数据
	 */
	@Test
	public void testFilterUsingSensitiveWord() {
		Assert.assertNotNull(filter);
		Assert.assertEquals("**大法好 你好", filter.filter("法轮大法好 你好"));
	}
	
	@Test
	public void testJson() {
		String jsonString = "[\"a\", \"b\", \"c\"]";
		JSONArray jsonarray = JSON.parseArray(jsonString);
		System.out.println("jsonarray.size = " + jsonarray.size());
		ArrayList<String> arraylist = new ArrayList<String> ((int) (jsonarray.size() * 1.5));
		for (Object o : jsonarray) {
			arraylist.add( (String) o );
		}
		Iterator<String> it = arraylist.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
	}
	
	/**
	 * 自动注入的SensitiveWordFilter对象.
	 */
	@Autowired
	private SensitiveWordFilter filter;
}
