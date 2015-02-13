package com.happystudio.testzilla.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * SensitiveWordFilter的测试类
 * @author Zhou YiHao
 */
public class SensitiveWordFilterTest {
	/**
	 * 测试用例：测试filter()方法
	 * 测试数据: 包含敏感词的数据(法轮大法)
	 * 预期结果: 过滤敏感词后的数据
	 */
	@Test
	public void testFilterUsingSensitiveWord() {
		Set<String> sensitiveWordSet = new HashSet<String>();
		sensitiveWordSet.add("法轮大法");
		sensitiveWordSet.add("法轮功");
		SensitiveWordFilter filter = SensitiveWordFilter.getInstance(sensitiveWordSet);
		
		Assert.assertEquals("****好 你好", filter.filter("法轮大法好 你好"));
	}
}
