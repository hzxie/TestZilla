package com.trunkshell.testzilla.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 积分规则的Model.
 * 说明: 积分 = 威望(Reputation) + 信用积分(Credit)
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_points_rules")
public class PointsRule implements Serializable {
	/**
	 * PointsRule的默认构造函数.
	 */
	public PointsRule() {}
	
	/**
	 * PointsRule的构造函数.
	 * @param pointsRuleId - 积分规则的唯一标识符
	 * @param reputation - 积分规则对应的威望
	 * @param credits - 积分规则对应的信用积分
	 * @param title - 积分规则的标题(概述)
	 * @param description - 积分规则的详细描述
	 */
	public PointsRule(int pointsRuleId, int reputation, int credits, 
			String title, String description) {
		this.pointsRuleId = pointsRuleId;
		this.reputation = reputation;
		this.credits = credits;
		this.title = title;
		this.description = description;
	}
	
	/**
	 * 获取积分规则的唯一标识符.
	 * @return 积分规则的唯一标识符
	 */
	public int getPointsRuleId() {
		return pointsRuleId;
	}

	/**
	 * 设置积分规则的唯一标识符.
	 * @param pointsRuleId - 积分规则的唯一标识符
	 */
	public void setPointsRuleId(int pointsRuleId) {
		this.pointsRuleId = pointsRuleId;
	}
	
	/**
	 * 获取积分规则的唯一英文缩写.
	 * @return 积分规则的唯一英文缩写
	 */
	public String getPointsRuleSlug() {
		return pointsRuleSlug;
	}

	/**
	 * 设置积分规则的唯一英文缩写.
	 * @param pointsRuleSlug - 积分规则的唯一英文缩写
	 */
	public void setPointsRuleSlug(String pointsRuleSlug) {
		this.pointsRuleSlug = pointsRuleSlug;
	}

	/**
	 * 获取积分规则对应的威望.
	 * @return 积分规则对应的威望
	 */
	public int getReputation() {
		return reputation;
	}

	/**
	 * 设置积分规则对应的威望.
	 * @param reputation - 积分规则对应的威望
	 */
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	/**
	 * 获取积分规则对应的信用积分.
	 * @return 积分规则对应的信用积分
	 */
	public int getCredit() {
		return credits;
	}

	/**
	 * 设置积分规则对应的信用积分.
	 * @param credits - 积分规则对应的信用积分
	 */
	public void setCredit(int credits) {
		this.credits = credits;
	}

	/**
	 * 获取积分规则的标题(概述).
	 * @return 积分规则的标题(概述)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置积分规则的标题(概述).
	 * @param title - 积分规则的标题(概述)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取积分规则的详细说明.
	 * @return 积分规则的详细说明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置积分规则的详细说明.
	 * @param description - 积分规则的详细说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取积分记录列表(用于1-N关联).
	 * @return 积分记录列表
	 */
	@JsonIgnore
	public List<PointsLog> getPointsLogs() {
		return pointsLogs;
	}

	/**
	 * 设置积分记录列表.
	 * @param pointsLogs - 积分记录列表
	 */
	public void setPointsLogs(List<PointsLog> pointsLogs) {
		this.pointsLogs = pointsLogs;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof PointsRule) ) {
			return false;
		}
		return this.pointsRuleId == ((PointsRule)o).pointsRuleId; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("PointsRule: [ID=%s, Reputation=%s, Credit={%s}, Title=%s]", 
				new Object[] { pointsRuleId, reputation, credits, title });
	}

	/**
	 * 积分规则的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "points_rule_id")
	private int pointsRuleId;
	
	/**
	 * 积分规则的唯一英文缩写.
	 */
	@Column(name = "points_rule_slug")
	private String pointsRuleSlug;
	
	/**
	 * 积分规则满足时, 增加(或减少)威望的数值.
	 */
	@Column(name = "points_rule_reputation")
	private int reputation;
	
	/**
	 * 积分规则满足时, 增加(或减少)信用积分的数值.
	 */
	@Column(name = "points_rule_credits")
	private int credits;
	
	/**
	 * 积分规则的标题(概述).
	 */
	@Column(name = "points_rule_title")
	private String title;
	
	/**
	 * 积分规则的详细说明.
	 */
	@Column(name = "points_rule_description")
	private String description;
	
	/**
	 * 积分记录列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = PointsLog.class, 
			fetch = FetchType.LAZY, mappedBy = "pointsRule")
	private List<PointsLog> pointsLogs = new ArrayList<PointsLog>();
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = 2077797304425367261L;
}
