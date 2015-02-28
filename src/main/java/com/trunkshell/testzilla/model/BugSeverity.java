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
 * Bug严重性Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_bug_severities")
public class BugSeverity implements Serializable {
	/**
	 * BugSeverity的默认构造函数.
	 */
	public BugSeverity() { }
	
	/**
	 * BugSeverity的构造函数.
	 * @param bugSeverityId - Bug严重性唯一标识符
	 * @param bugSeveritySlug - Bug严重性唯一英文简称
	 * @param bugSeverityName - Bug严重性的名称
	 */
	public BugSeverity(int bugSeverityId, String bugSeveritySlug, String bugSeverityName) {
		this.bugSeverityId = bugSeverityId;
		this.bugSeveritySlug = bugSeveritySlug;
		this.bugSeverityName = bugSeverityName;
	}
	
	/**
	 * 获取Bug严重性的唯一标识符.
	 * @return Bug严重性的唯一标识符
	 */
	public int getBugSeverityId() {
		return bugSeverityId;
	}

	/**
	 * 设置Bug严重性的唯一标识符.
	 * @param bugSeverityId - Bug严重性的唯一标识符
	 */
	public void setBugSeverityId(int bugSeverityId) {
		this.bugSeverityId = bugSeverityId;
	}

	/**
	 * 获取Bug严重性的唯一英文简称.
	 * @return Bug严重性的唯一英文简称
	 */
	public String getBugSeveritySlug() {
		return bugSeveritySlug;
	}

	/**
	 * 设置Bug严重性的唯一英文简称.
	 * @param bugSeveritySlug - Bug严重性的唯一英文简称
	 */
	public void setBugSeveritySlug(String bugSeveritySlug) {
		this.bugSeveritySlug = bugSeveritySlug;
	}

	/**
	 * 获取Bug严重性的名称.
	 * @return Bug严重性的名称
	 */
	public String getBugSeverityName() {
		return bugSeverityName;
	}

	/**
	 * 设置Bug严重性的名称.
	 * @param bugSeverityName - Bug严重性的名称
	 */
	public void setBugSeverityName(String bugSeverityName) {
		this.bugSeverityName = bugSeverityName;
	}

	/**
	 * 获取Bug严重性的描述.
	 * @return Bug严重性的描述
	 */
	public String getBugSeverityDescription() {
		return bugSeverityDescription;
	}

	/**
	 * 设置Bug严重性的描述.
	 * @param bugSeverityDescription - Bug严重性的描述
	 */
	public void setBugSeverityDescription(String bugSeverityDescription) {
		this.bugSeverityDescription = bugSeverityDescription;
	}

	/**
	 * 获取Bug列表(用于1-N关联).
	 * @return Bug列表
	 */
	@JsonIgnore
	public List<Bug> getBugs() {
		return bugs;
	}

	/**
	 * 设置Bug列表.
	 * @param bugs - Bug列表
	 */
	public void setBugs(List<Bug> bugs) {
		this.bugs = bugs;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof BugSeverity) ) {
			return false;
		}
		return this.bugSeverityId == ((BugSeverity)o).bugSeverityId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("BugSeverity [Id=%d, Slug=%s, Name=%s, Description=%s]",
				new Object[] { bugSeverityId, bugSeveritySlug, bugSeverityName, bugSeverityDescription });
	}

	/**
	 * Bug严重性的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "bug_severity_id")
	private int bugSeverityId;

	/**
	 * Bug严重性的唯一英文简称.
	 */
	@Column(name = "bug_severity_slug")
	private String bugSeveritySlug;

	/**
	 * Bug严重性的名称.
	 */
	@Column(name = "bug_severity_name")
	private String bugSeverityName;

	/**
	 * Bug严重性的描述.
	 */
	@Column(name = "bug_severity_description")
	private String bugSeverityDescription;
	
	/**
	 * Bug列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Bug.class, 
				fetch = FetchType.LAZY, mappedBy = "bugSeverity")
	private List<Bug> bugs = new ArrayList<Bug>();

	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -191292237682771236L;
}
