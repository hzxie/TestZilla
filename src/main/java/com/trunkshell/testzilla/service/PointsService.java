package com.trunkshell.testzilla.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.PointsLogDao;
import com.trunkshell.testzilla.dao.PointsRuleDao;
import com.trunkshell.testzilla.model.PointsLog;
import com.trunkshell.testzilla.model.PointsRule;
import com.trunkshell.testzilla.model.User;

/**
 * 用户积分Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class PointsService {
	/**
	 * 通过积分规则的唯一英文缩写获取积分规则对象.
	 * @param pointsRuleSlug - 积分规则的唯一英文缩写
	 * @return 预期的积分规则对象
	 */
	public PointsRule getPointsRuleUsingSlug(String pointsRuleSlug) {
		PointsRule pointsRule = pointsRuleDao.getPointsRule(pointsRuleSlug);
		return pointsRule;
	}
	
	/**
	 * 追加积分日志.
	 * @param user - 待追加积分日志的用户
	 * @param rule - 匹配的积分日志规则
	 * @return 操作是否成功完成
	 */
	public boolean appendPointsLogs(User user, PointsRule rule, String meta) {
		Date createTime = new Date();
		PointsLog pointsLog = new PointsLog(user, createTime, rule, meta);
		return pointsLogDao.createPointsLog(pointsLog);
	}
	
	/**
	 * 获取用户的积分日志.
	 * @param user - 待查询积分日志的用户
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 用户的积分日志
	 */
	public List<PointsLog> getPointsLogUsingUser(User user, int offset, int limit) {
		List<PointsLog> pointsLogs = pointsLogDao.getPointsLogUsingUser(user, offset, limit);
		return pointsLogs;
	}
	
	/**
	 * 获取用户的威望值.
	 * @param user - 待查询威望值的用户
	 * @return 用户的威望值
	 */
	public long getReputationUsingUser(User user) {
		long totalReputation = pointsLogDao.getReputationUsingUser(user);
		return totalReputation;
	}
	
	/**
	 * 获取用户的积分值.
	 * @param user - 待查询积分值的用户
	 * @return 用户的积分值
	 */
	public long getCreditsUsingUser(User user) {
		long totalCredits = pointsLogDao.getCreditsUsingUser(user);
		return totalCredits;
	}
	
	/**
	 * 自动注入的PointsLogDao对象.
	 */
	@Autowired
	private PointsLogDao pointsLogDao;
	
	/**
	 * 自动注入的PointsRuleDao对象.
	 */
	@Autowired
	private PointsRuleDao pointsRuleDao;
}
