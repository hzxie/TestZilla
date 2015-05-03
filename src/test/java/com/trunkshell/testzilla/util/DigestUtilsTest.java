package com.trunkshell.testzilla.util;

import org.junit.Assert;
import org.junit.Test;

import com.trunkshell.testzilla.util.DigestUtils;

/**
 * DigestUtils的测试类.
 * @author Xie Haozhe
 */
public class DigestUtilsTest {
	/**
	 * 测试用例: 测试md5Hex方法
	 * 测试数据: 有效的密码
	 * 测试用例: 返回MD5加密后的密码
	 */
	@Test
	public void testMd5HexCase1() {
		String password = "zjhzxhz";
		String runtimeValue = DigestUtils.md5Hex(password);
		String expectedValue = "785ee107c11dfe36de668b1ae7baacbb";
		
		Assert.assertEquals(expectedValue, runtimeValue);
	}
	
	/**
	 * 测试用例: 测试md5Hex方法
	 * 测试数据: 有效的密码
	 * 测试用例: 返回MD5加密后的密码
	 */
	@Test
	public void testMd5HexCase2() {
		String password = "meigaofei0926";
		String runtimeValue = DigestUtils.md5Hex(password);
		String expectedValue = "0adb82835139278edb78d1e8c4548600";
		
		Assert.assertEquals(expectedValue, runtimeValue);
	}
	
	/**
	 * 测试用例: 测试md5Hex方法
	 * 测试数据: 空密码
	 * 测试用例: 返回空字符串
	 */
	@Test
	public void testMd5HexEmpty() {
		String password = "";
		String runtimeValue = DigestUtils.md5Hex(password);
		String expectedValue = "";
		
		Assert.assertEquals(expectedValue, runtimeValue);
	}
}
