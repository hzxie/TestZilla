package com.trunkshell.testzilla.util;

import org.jsoup.Jsoup;

/**
 * HTML文本过滤组件.
 * @author Xie Haozhe
 */
public class HtmlTextFilter {
	/**
	 * 过滤包含HTML字符串.
	 * @param str - 待过滤的字符串
	 * @return 过滤后的字符串.
	 */
	public static String filter(String text) {
		return Jsoup.parse(text).text();
	}
}
