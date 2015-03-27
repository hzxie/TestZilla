package com.trunkshell.testzilla.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		return view;
	}
	
	/**
	 * 加载排行榜信息.
	 * @param timeRange - 时间间隔
	 * @param request - HttpRequest对谐
	 * @return 一个包含排行榜信息的JSON对象
	 */
	@RequestMapping(value = "/getReputationRanking.action", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object>  getReputationRanking(
    		@RequestParam(value="timeRange", required=true) int timeRange,
    		@RequestParam(value="page", required=false, defaultValue="1") int pageNumber,
            HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<PointsLog> reputationRanking = getReputationRanking(timeRange, pageNumber);
		long totalPages = getReputationRankingTotalPages(timeRange);

		result.put("isSuccessful", reputationRanking.size() != 0);
		result.put("reputationRanking", reputationRanking);
		result.put("totalPages", totalPages);
		return result;
	}
	
	private List<PointsLog> getReputationRanking(int timeRange, int pageNumber) {
		int offset = (pageNumber - 1) * NUMBER_OF_USERS_PER_PAGE;
		return leaderBoardService.getReputationRanking(timeRange, offset, NUMBER_OF_USERS_PER_PAGE);
	}
	
	private long getReputationRankingTotalPages(int days) {
		return (long)Math.ceil((double)leaderBoardService.getTotalReputationRanking(days) / NUMBER_OF_USERS_PER_PAGE);
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
