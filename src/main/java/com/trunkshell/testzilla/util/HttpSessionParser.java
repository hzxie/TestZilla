package com.trunkshell.testzilla.util;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.service.UserService;

/**
 * HttpSession解析器.
 * @author Xie Haozhe
 */
@Component
public class HttpSessionParser {
	/**
	 * HttpSessionParser的构造函数.
	 * @param userService - 自动注入的UserService对象
	 */
	@Autowired
	public HttpSessionParser(UserService userService) {
		HttpSessionParser.userService = userService;
	}
	
	/**
	 * 获取Session中的用户对象.
	 * @param session - HttpSession对象
	 * @return Session中的用户对象
	 */
	public static User getCurrentUser(HttpSession session) {
		Object obj = session.getAttribute("uid");
		
		if ( obj == null ) {
			return null;
		}
		long uid = (Long)obj;
    	User user = userService.getUserUsingUid(uid);
    	
    	return user;
	}
	
	/**
     * 自动注入的UserService对象.
     */
    private static UserService userService;
}
