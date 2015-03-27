package com.trunkshell.testzilla.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.PointsLog;
import com.trunkshell.testzilla.model.User;


/**
 * 用户Reputation排名有关的Data Access Object.
 * @author Zhou Yihao
 */
@Repository
public class LeaderBoardDao {
	/**
	 * 获取给定的时候内，用户声望的排名
	 * @param days - 给定的时间间隔
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @throws TimeRangeException - 时间段参数设置错误时抛出此异常
	 * @return - 用户声望的排名
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PointsLog> getReputationRanking(int days, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> queryList = null;
		
		if ( days <= 0 ) {
			queryList = session.createQuery("SELECT p.user, SUM(p.pointsRule.reputation) as totalReputation FROM PointsLog p GROUP BY p.user ORDER BY totalReputation DESC")
								.setFirstResult(offset)
								.setMaxResults(limit).list();
		} else {
			Date nowDate = new Date();
			Date beginDate = this.getBeginDate(days);
			queryList = session.createQuery("SELECT p.user, SUM(p.pointsRule.reputation) as totalReputation FROM PointsLog p WHERE p.getTime BETWEEN ?0 AND ?1 GROUP BY p.user ORDER BY totalReputation DESC")
								.setParameter("0", beginDate)
								.setParameter("1", nowDate)
								.setFirstResult(offset)
								.setMaxResults(limit).list();
		}
		List<PointsLog> reputationRankingList = new ArrayList<PointsLog>(queryList.size());
		
		for (Object[] queryRow : queryList) {
			//从query结果集中取出user和totalReputation
			User user = (User) queryRow[0];
			long totalReputation = Long.valueOf( String.valueOf(queryRow[1]) );
			
			PointsLog userReputation = new PointsLog(user, totalReputation);
			reputationRankingList.add(userReputation);
		}
		return reputationRankingList; 
	}
	
	/**
	 * 获取到目前为止，上榜用户的数量
	 * @return - 上榜用户数量
	 */
	@Transactional
	public long getTotalReputationRanking(int days) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		if ( days <= 0 ) {
			query = session.createQuery("SELECT COUNT(DISTINCT p.user) FROM PointsLog p");
		} else {
			Date nowDate = new Date();
			Date beginDate = this.getBeginDate(days);
			query = session.createQuery("SELECT COUNT(DISTINCT p.user) FROM PointsLog p WHERE p.getTime BETWEEN ?0 AND ?1")
							.setParameter("0", beginDate)
							.setParameter("1", nowDate);
		}
		return (Long) query.uniqueResult();
	}
	
	private Date getBeginDate (int days) {
		java.util.Date nowDate = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DATE, - days);
		
		Date beginDate = calendar.getTime();
		return beginDate;
	}
	
	/**
     * 自动注入的SessionFactory.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * 设置一周的时间为七天
     */
    public static final int DAYS_OF_WEEK = 7;
    
    /**
     * 设置一月的时间为三十天
     */
    public static final int DAYS_OF_MONTH = 30;
}
