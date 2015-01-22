package com.happystudio.testzilla.model;

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
 * 说明: 积分 = 威望(Reputation) + 金钱(Money)
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
	 * @param money - 积分规则对应的金钱
	 * @param title - 积分规则的标题(概述)
	 * @param description - 积分规则的详细描述
	 */
	public PointsRule(int pointsRuleId, int reputation, int money, 
			String title, String description) {
		this.pointsRuleId = pointsRuleId;
		this.reputation = reputation;
		this.money = money;
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
	 * 获取积分规则对应的金钱.
	 * @return 积分规则对应的金钱
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * 设置积分规则对应的金钱.
	 * @param money - 积分规则对应的金钱
	 */
	public void setMoney(int money) {
		this.money = money;
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
		return String.format("PointsRule: [ID=%s, Reputation=%s, Money={%s}, Title=%s]", 
				new Object[] { pointsRuleId, reputation, money, title });
	}

	/**
	 * 积分规则的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "points_rule_id")
	private int pointsRuleId;
	
	/**
	 * 积分规则满足时, 增加(或减少)威望的数值.
	 */
	@Column(name = "points_rule_reputation")
	private int reputation;
	
	/**
	 * 积分规则满足时, 增加(或减少)金钱的数值.
	 */
	@Column(name = "points_rule_money")
	private int money;
	
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
	private static final long serialVersionUID = 8610551581115372254L;
}
