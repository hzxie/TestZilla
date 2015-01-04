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

/**
 * Bug状态的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_bug_status")
public class BugStatus implements Serializable {
	/**
	 * BugStatus的默认构造函数.
	 */
	public BugStatus() { }
	
	/**
	 * BugStatus的构造函数.
	 * @param bugStatusId - Bug状态的唯一标识符
	 * @param bugStatusSlug - Bug状态的唯一英文简称
	 * @param bugStatusName - Bug状态的名称
	 */
	public BugStatus(int bugStatusId, String bugStatusSlug, String bugStatusName) {
		this.bugStatusId = bugStatusId;
		this.bugStatusSlug = bugStatusSlug;
		this.bugStatusName = bugStatusName;
	}
	
	/**
	 * 获取Bug状态的唯一标识符.
	 * @return Bug状态的唯一标识符
	 */
	public int getBugStatusId() {
		return bugStatusId;
	}

	/**
	 * 设置Bug状态的唯一标识符.
	 * @param bugStatusId - Bug状态的唯一标识符
	 */
	public void setBugStatusId(int bugStatusId) {
		this.bugStatusId = bugStatusId;
	}

	/**
	 * 获取Bug状态的唯一英文简称.
	 * @return Bug状态的唯一英文简称
	 */
	public String getBugStatusSlug() {
		return bugStatusSlug;
	}

	/**
	 * 设置Bug状态的唯一英文简称.
	 * @param bugStatusSlug - Bug状态的唯一英文简称
	 */
	public void setBugStatusSlug(String bugStatusSlug) {
		this.bugStatusSlug = bugStatusSlug;
	}

	/**
	 * 获取Bug状态的名称.
	 * @return Bug状态的名称
	 */
	public String getBugStatusName() {
		return bugStatusName;
	}

	/**
	 * 设置Bug状态的名称.
	 * @param bugStatusName - Bug状态的名称
	 */
	public void setBugStatusName(String bugStatusName) {
		this.bugStatusName = bugStatusName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("BugStatus [Id=%d, Slug=%s, Name=%s]",
				new Object[] { bugStatusId, bugStatusSlug, bugStatusName });
	}

	/**
	 * Bug状态的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "bug_status_id")
	private int bugStatusId;

	/**
	 * Bug状态的唯一英文简称.
	 */
	@Column(name = "bug_status_slug")
	private String bugStatusSlug;

	/**
	 * Bug状态的名称.
	 */
	@Column(name = "bug_status_name")
	private String bugStatusName;
	
	/**
	 * Bug列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Bug.class, 
				fetch = FetchType.LAZY, mappedBy = "bugStatus")
	private List<Bug> bugs = new ArrayList<Bug>();

	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -574610247251337567L;
}

