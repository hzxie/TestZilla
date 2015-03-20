package com.trunkshell.testzilla.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.model.PointsLog;
import com.trunkshell.testzilla.service.LeaderBoardService;

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
    public ModelAndView leaderBoardView(
    		HttpServletRequest request) {
		ModelAndView view = new ModelAndView("leaderboard/leaderboard");
		List<PointsLog> rankingUsers = leaderBoardService.getReputationRankingAllTime(0, NUMBER_OF_USERS_PER_PAGE);
		long totalPages = (long)Math.ceil(leaderBoardService.getRankingUsersAllTime() / NUMBER_OF_USERS_PER_PAGE);
		view.addObject("rankingUsers", rankingUsers);
		view.addObject("totalPages", totalPages);
		return view;
	}
	
	/**
	 * 每页显示的用户数量
	 */
	public static final int NUMBER_OF_USERS_PER_PAGE = 10;
	
	/**
	 * 自动注入的LeaderBoardService对象
	 */
	@Autowired
	private LeaderBoardService leaderBoardService;
}
