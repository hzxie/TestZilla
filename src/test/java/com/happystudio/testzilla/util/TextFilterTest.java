package com.happystudio.testzilla.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * TextFilter的测试类.
 * @author Xie Haozhe
 */
public class TextFilterTest {
	/**
	 * 测试用例: 测试filterHtml()方法.
	 * 测试数据: 匹配的HTML的字符串.
	 * 与其结果: 返回过滤HTML的字符串
	 */
	@Test
	public void testFilterHtmlUsingMatchedHtml() {
		String str = "XSS <script type=\"text/javascript\">alert('XSS')</script>.";
		Assert.assertEquals("XSS .", TextFilter.filterHtml(str));
	}
	
	/**
	 * 测试用例: 测试filterHtml()方法.
	 * 测试数据: 不匹配的HTML的字符串.
	 * 与其结果: 返回过滤HTML的字符串
	 */
	@Test
	public void testFilterHtmlUsingUnmatchedHtml() {
		String str = "XSS <a href=\"http://zjhzxhz.com\">alert('XSS')</script>.";
		Assert.assertEquals("XSS alert('XSS').", TextFilter.filterHtml(str));
	}
}
