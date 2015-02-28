package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trunkshell.testzilla.model.PointsRule;

/**
 * Points类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class PointsRuleDao {
	/**
	 * 通过积分规则的唯一英文缩写获取积分规则对象
	 * @param pointsRuleSlug - 积分规则的唯一英文缩写
	 * @return 积分规则对象或空引用
	 */
	public PointsRule getPointsRule(String pointsRuleSlug) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<PointsRule> pointsRules = (List<PointsRule>)session.createQuery("FROM PointsRule WHERE pointsRuleSlug = ?0")
															.setString("0", pointsRuleSlug)
															.list();
		for (PointsRule pointsRule : pointsRules) {
			if (pointsRule.getPointsRuleSlug().equals(pointsRuleSlug)) {
				return pointsRule;
			}
		}
		return null;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
