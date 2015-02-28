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
 * Bug分类的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_bug_categories")
public class BugCategory implements Serializable {
	/**
	 * BugCategory的默认构造函数.
	 */
	public BugCategory() { }
	
	/**
	 * BugCategory的构造函数.
	 * @param bugCategoryId - Bug分类的唯一标识符
	 * @param bugCategorySlug - Bug分类的唯一英文简称
	 * @param bugCategoryName - Bug分类的名称
	 */
	public BugCategory(int bugCategoryId, String bugCategorySlug, String bugCategoryName) {
		this.bugCategoryId = bugCategoryId;
		this.bugCategorySlug = bugCategorySlug;
		this.bugCategoryName = bugCategoryName;
	}
	
	/**
	 * 获取Bug分类的唯一标识符.
	 * @return Bug分类的唯一标识符
	 */
	public int getBugCategoryId() {
		return bugCategoryId;
	}

	/**
	 * 设置Bug分类的唯一标识符.
	 * @param bugCategoryId - Bug分类的唯一标识符
	 */
	public void setBugCategoryId(int bugCategoryId) {
		this.bugCategoryId = bugCategoryId;
	}

	/**
	 * 获取Bug分类的唯一英文简称.
	 * @return Bug分类的唯一英文简称
	 */
	public String getBugCategorySlug() {
		return bugCategorySlug;
	}

	/**
	 * 设置Bug分类的唯一英文简称.
	 * @param bugCategorySlug - Bug分类的唯一英文简称
	 */
	public void setBugCategorySlug(String bugCategorySlug) {
		this.bugCategorySlug = bugCategorySlug;
	}

	/**
	 * 获取Bug分类的名称.
	 * @return Bug分类的名称
	 */
	public String getBugCategoryName() {
		return bugCategoryName;
	}

	/**
	 * 设置Bug分类的名称.
	 * @param bugCategoryName - Bug分类的名称
	 */
	public void setBugCategoryName(String bugCategoryName) {
		this.bugCategoryName = bugCategoryName;
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
		if ( !(o instanceof BugCategory) ) {
			return false;
		}
		return this.bugCategoryId == ((BugCategory)o).bugCategoryId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("BugCategory [Id=%d, Slug=%s, Name=%s]",
				new Object[] { bugCategoryId, bugCategorySlug, bugCategoryName });
	}

	/**
	 * Bug分类的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "bug_category_id")
	private int bugCategoryId;

	/**
	 * Bug分类的唯一英文简称.
	 */
	@Column(name = "bug_category_slug")
	private String bugCategorySlug;

	/**
	 * Bug分类的名称.
	 */
	@Column(name = "bug_category_name")
	private String bugCategoryName;
	
	/**
	 * Bug列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Bug.class, 
			fetch = FetchType.LAZY, mappedBy = "bugCategory")
	private List<Bug> bugs = new ArrayList<Bug>();

	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -6145913406397010218L;
}
