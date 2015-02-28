package com.trunkshell.testzilla.util;

import org.junit.Assert;

import com.trunkshell.testzilla.util.SensitiveWordFilter;

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
	// @Test
	public void testFilterUsingSensitiveWord() {
		SensitiveWordFilter filter = SensitiveWordFilter.getInstance();
		Assert.assertNotNull(filter);
		Assert.assertEquals("****好 你好", filter.filter("法轮大法好 你好"));
	}
}
