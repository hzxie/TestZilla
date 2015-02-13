package com.happystudio.testzilla.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * SensitiveWordUtil 的测试类
 * @author xsx
 */
public class SensitiveWordUtilTest {

	/**
	 * 测试用例：测试敏感词过滤
	 */
	@Test
	public void testSensitiveWordFilter() {
		//初始化
		Set<String> sensitiveWordSet = new HashSet<String>();
		sensitiveWordSet.add("法轮大法");
		sensitiveWordSet.add("法轮功");
		SensitiveWordFilter filter = SensitiveWordFilter.getInstance(sensitiveWordSet);
		
		String txt = "法轮大法好";
		String expected = "****好";
		
		String result = filter.Filter(txt);
		Assert.assertEquals(expected, result);
	}

}
