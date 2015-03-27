package com.trunkshell.testzilla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.LeaderBoardDao;
import com.trunkshell.testzilla.model.PointsLog;

/**
 * 用户Reputation排名的Service，为Controller服务
 * @author Zhou Yihao
 *
 */
@Service
@Transactional
public class LeaderBoardService {
	/**
	 * 获取一段时间内，上榜用户数量，时间参数不合法时，返回0
	 * @param days - 时间段
	 * @return - 上榜用户数
	 */
	public long getTotalReputationRanking(int days) {
		return leaderBoardDao.getTotalReputationRanking(days);
	}
	
	/**
	 * 获取一段时间内，上榜用户列表
	 * @param days - 时间段
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return - 用户声望排名列表
	 */
	public List<PointsLog> getReputationRanking(int days, int offset, int limit) {
		return leaderBoardDao.getReputationRanking(days, offset, limit);
	}
	
	/**
	 * 自动注入的LeaderBoardDao对象
	 */
	@Autowired
	private LeaderBoardDao leaderBoardDao;
}
