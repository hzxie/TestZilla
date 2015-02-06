package com.happystudio.testzilla.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 产品测试比赛的Controller.
 * @author Xie Haozhe
 */
@Controller
@RequestMapping(value = "/leaderboard")
public class LeaderboardController {
	/**
	 * 加载排行榜页面.
	 * @param request - HttpRequest对象
     * @return 一个包含登录页内容的ModelAndView对象
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView loginView(
    		HttpServletRequest request) {
		ModelAndView view = new ModelAndView("leaderboard/leaderboard");
		return view;
	}
}
