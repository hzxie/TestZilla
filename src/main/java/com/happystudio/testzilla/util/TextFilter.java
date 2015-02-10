package com.happystudio.testzilla.util;

import org.jsoup.Jsoup;

/**
 * 文本过滤组件.
 * 用于防止XSS攻击, 敏感词过滤等.
 * @author Xie Haozhe
 */
public class TextFilter {
	/**
	 * 过滤包含HTML字符串.
	 * @param str - 待过滤的字符串
	 * @return 过滤后的字符串.
	 */
	public static String filterHtml(String str) {
		return Jsoup.parse(str).text();
	}
}
