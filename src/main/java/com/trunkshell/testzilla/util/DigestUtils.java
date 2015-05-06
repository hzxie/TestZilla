package com.trunkshell.testzilla.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * MD5加密函数类.
 * @author Xie Haozhe
 */
public class DigestUtils {
	/**
	 * 获取MD5加密后的密码.
	 * @param password - 未经MD5加密的密码
	 * @return MD5加密后的密码
	 */
	public static String md5Hex(String password) {
		String md5 = "";
		if ( password == null || password.isEmpty() ) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
	 		byte byteData[] = md.digest();
	 
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 生成GUID.
	 * @return GUID
	 */
	public static String generateGuid() {
		return UUID.randomUUID().toString();
	}
}