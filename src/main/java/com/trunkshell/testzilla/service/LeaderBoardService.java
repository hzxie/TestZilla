package com.trunkshell.testzilla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.LeaderBoardDao;
import com.trunkshell.testzilla.exception.TimeRangeException;
import com.trunkshell.testzilla.model.PointsLog;

/**
 * 用户Reputation排名的Service，为controller服务
 * @author Zhou Yihao
 *
 */
@Service
@Transactional
public class LeaderBoardService {

	/**
	 * 获取到目前为止，上榜用户的数量
	 * @return - 上榜用户数
	 */
	public long getRankingUsersAllTime() {
		return leaderBoardDao.getRankingUsersAllTime();
	}
	
	/**
	 * 获取一段时间内，上榜用户数量，时间参数不合法时，返回0
	 * @param days - 时间段
	 * @return - 上榜用户数
	 */
	public long getRankingUsers(int days) {
		try {
			return leaderBoardDao.getRankingUsers(days);
		} catch (TimeRangeException e) {
			return 0;
		}
	}
	
	/**
	 * 获取到目前为止，上榜用户列表
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return - 用户声望排名列表
	 */
	public List<PointsLog> getReputationRankingAllTime(int offset, int limit) {
		if (offset < 0) {
			offset = 0;
		}
		return leaderBoardDao.getReputationRankingAllTime(offset, limit);
	}
	
	/**
	 * 获取一段时间内，上榜用户列表
	 * @param days - 时间段
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return - 用户声望排名列表
	 */
	public List<PointsLog> getReputationRanking(int days, int offset, int limit) {
		try {
			return leaderBoardDao.getReputationRanking(days, offset, limit);
		} catch (TimeRangeException e) {
			return null;
		}
	}
	
	/**
	 * 自动注入的LeaderBoardDao对象
	 */
	@Autowired
	private LeaderBoardDao leaderBoardDao;
}
