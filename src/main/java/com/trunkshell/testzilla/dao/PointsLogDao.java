package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trunkshell.testzilla.model.PointsLog;
import com.trunkshell.testzilla.model.PointsRule;
import com.trunkshell.testzilla.model.User;

/**
 * PointsLog类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class PointsLogDao {
	/**
	 * 获取用户的积分日志.
	 * @param user - 待查询积分日志的用户
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 用户的积分日志
	 */
	public List<PointsLog> getPointsLogUsingUser(User user, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<PointsLog> pointsLogs = (List<PointsLog>)session.createQuery("FROM PointsLog WHERE user = ?0 ORDER BY getTime DESC")
																.setParameter("0", user)
																.setFirstResult(offset)
																.setMaxResults(limit).list();
		return pointsLogs;
	}
	
	/**
	 * 获取用户的威望值.
	 * @param user - 待查询威望值的用户
	 * @return 用户的威望值
	 */
	public long getReputationUsingUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT SUM(p.pointsRule.reputation) FROM PointsLog p WHERE user = ?0")
								.setParameter("0", user);
		
		Object result = query.uniqueResult();
		if ( result == null ) {
			return 0;
		}
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取用户的积分值.
	 * @param user - 待查询积分值的用户
	 * @return 用户的积分值
	 */
	public long getCreditsUsingUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT SUM(p.pointsRule.credits) FROM PointsLog p WHERE user = ?0")
								.setParameter("0", user);
		
		Object result = query.uniqueResult();
		if ( result == null ) {
			return 0;
		}
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 检查积分日志是否已经存在.
	 * (例如验证电子邮件的积分只允许奖励1次)
	 * @param user - 待检查的用户
	 * @param rule - 待检查的规则
	 * @return 积分日志是否已经存在
	 */
	public boolean isPointsLogExists(User user, PointsRule rule) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<PointsLog> pointsLogs = (List<PointsLog>)session.createQuery("FROM PointsLog p WHERE user = ?0 AND pointsRule = ?1")
																.setParameter("0", user)
																.setParameter("1", rule).list();
		for (PointsLog pointsLog : pointsLogs ) {
            if ( pointsLog.getUser().equals(user) && pointsLog.getPointsRule().equals(rule) ) {
                return true;
            }
        }
		return false;
	}
	
	/**
	 * 创建积分日志.
	 * @param pointsLog - 待创建的积分日志
	 * @return 操作是否成功完成
	 */
	public boolean createPointsLog(PointsLog pointsLog) {
		Session session = sessionFactory.getCurrentSession();
        session.save(pointsLog);
        session.flush();
        return true;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
