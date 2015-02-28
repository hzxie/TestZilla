package com.trunkshell.testzilla.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bug的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_bugs")
public class Bug implements Serializable {
	/**
	 * Bug的默认构造函数. 
	 */
	public Bug() {}
	
	/**
	 * Bug的构造函数.
	 * @param product - Bug所对应产品的对象
	 * @param productVersion - Bug所对应产品版本
	 * @param bugCategory - Bug的分类
	 * @param bugStatus - Bug的状态
	 * @param bugSeverity - Bug的严重程度
	 * @param createTime - Bug的创建时间
	 * @param hunter - Bug发现者的用户对象
	 * @param title - Bug的标题(概述)
	 * @param description - Bug的详细描述
	 * @param screenshots - Bug的屏幕截图相对路径的列表
	 */
	public Bug(Product product, String productVersion, BugCategory bugCategory,
			BugStatus bugStatus, BugSeverity bugSeverity, Date createTime, User hunter,
			String title, String description, String screenshots) {
		this.product = product;
		this.productVersion = productVersion;
		this.bugCategory = bugCategory;
		this.bugStatus = bugStatus;
		this.bugSeverity = bugSeverity;
		this.createTime = createTime;
		this.hunter = hunter;
		this.title = title;
		this.description = description;
		this.screenshots = screenshots;
	}
	
	/**
	 * Bug的构造函数(用于单元测试).
	 * @param bugId - Bug的唯一标识符
	 * @param product - Bug所对应产品的对象
	 * @param productVersion - Bug所对应产品版本
	 * @param bugCategory - Bug的分类
	 * @param bugStatus - Bug的状态
	 * @param bugSeverity - Bug的严重程度
	 * @param createTime - Bug的创建时间
	 * @param hunter - Bug发现者的用户对象
	 * @param title - Bug的标题(概述)
	 * @param description - Bug的详细描述
	 * @param screenshots - Bug的屏幕截图相对路径的列表
	 */
	public Bug(long bugId, Product product, String productVersion, BugCategory bugCategory,
			BugStatus bugStatus, BugSeverity bugSeverity, Date createTime, User hunter,
			String title, String description, String screenshots) {
		this(product, productVersion, bugCategory, bugStatus, bugSeverity, 
				createTime, hunter, title, description, screenshots);
		this.bugId = bugId;
	}
	
	/**
	 * 获取Bug的唯一标识符.
	 * @return the bugId - Bug的唯一标识符
	 */
	public long getBugId() {
		return bugId;
	}

	/**
	 * 设置Bug的唯一标识符.
	 * @param bugId the bugId to set
	 */
	public void setBugId(long bugId) {
		this.bugId = bugId;
	}
	
	/**
	 * 获取Bug所对应产品的对象.
	 * @return Bug所对应产品的对象
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置Bug所对应产品的对象.
	 * @param product - Bug所对应产品的对象
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取Bug所对应产品版本.
	 * @return Bug所对应产品版本
	 */
	public String getProductVersion() {
		return productVersion;
	}

	/**
	 * 设置Bug所对应产品版本.
	 * @param productVersion - Bug所对应产品版本
	 */
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	/**
	 * 获取Bug的分类.
	 * @return Bug的分类
	 */
	public BugCategory getBugCategory() {
		return bugCategory;
	}

	/**
	 * 设置Bug的分类.
	 * @param bugCategory - Bug的分类
	 */
	public void setBugCategory(BugCategory bugCategory) {
		this.bugCategory = bugCategory;
	}

	/**
	 * 获取Bug的状态.
	 * @return Bug的状态
	 */
	public BugStatus getBugStatus() {
		return bugStatus;
	}

	/**
	 * 设置Bug的状态.
	 * @param bugStatus - Bug的状态
	 */
	public void setBugStatus(BugStatus bugStatus) {
		this.bugStatus = bugStatus;
	}

	/**
	 * 获取Bug的严重程度.
	 * @return Bug的严重程度
	 */
	public BugSeverity getBugSeverity() {
		return bugSeverity;
	}

	/**
	 * 设置Bug的严重程度.
	 * @param bugSeverity - Bug的严重程度
	 */
	public void setBugSeverity(BugSeverity bugSeverity) {
		this.bugSeverity = bugSeverity;
	}

	/**
	 * 获取Bug的创建时间.
	 * @return Bug的创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置Bug的创建时间.
	 * @param createTime - Bug的创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取Bug发现者的用户对象.
	 * @return Bug发现者的用户对象
	 */
	public User getHunter() {
		return hunter;
	}

	/**
	 * 设置Bug发现者的用户对象.
	 * @param hunter - Bug发现者的用户对象
	 */
	public void setHunter(User hunter) {
		this.hunter = hunter;
	}

	/**
	 * 获取Bug的标题(概述).
	 * @return Bug的标题(概述)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置Bug的标题(概述).
	 * @param title - Bug的标题(概述)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取Bug的详细描述.
	 * @return Bug的详细描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置Bug的详细描述.
	 * @param description - Bug的详细描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取Bug的屏幕截图相对路径的列表.
	 * @return Bug的屏幕截图相对路径的列表
	 */
	public String getScreenshots() {
		return screenshots;
	}

	/**
	 * 设置Bug的屏幕截图相对路径的列表.
	 * @param screenshots - Bug的屏幕截图相对路径的列表
	 */
	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof Bug) ) {
			return false;
		}
		return this.bugId == ((Bug)o).bugId; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Bug [Id=%d, Product={%s}, ProductVersion=%s, CreateTime=%s, Hunter={%s}, BugTitle=%s]",
				new Object[] { bugId, product, productVersion, createTime, hunter, title });
	}

	/**
	 * Bug的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "bug_id")
	private long bugId;
	
	/**
	 * Bug所对应产品的对象.
	 */
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "product_id")
	private Product product;
	
	/**
	 * Bug所对应产品版本.
	 */
	@Column(name = "product_version")
	private String productVersion;
	
	/**
	 * Bug的分类.
	 */
	@ManyToOne(targetEntity = BugCategory.class)
	@JoinColumn(name = "bug_category_id")
	private BugCategory bugCategory;
	
	/**
	 * Bug的状态.
	 */
	@ManyToOne(targetEntity = BugStatus.class)
	@JoinColumn(name = "bug_status_id")
	private BugStatus bugStatus;
	
	/**
	 * Bug的严重程度.
	 */
	@ManyToOne(targetEntity = BugSeverity.class)
	@JoinColumn(name = "bug_severity_id")
	private BugSeverity bugSeverity;
	
	/**
	 * Bug的创建时间.
	 */
	@Column(name = "bug_create_time")
	private Date createTime;
	
	/**
	 * Bug发现者的用户对象.
	 */
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "bug_hunter_id")
	private User hunter;
	
	/**
	 * Bug的标题(概述).
	 */
	@Column(name = "bug_title")
	private String title;
	
	/**
	 * Bug的详细描述.
	 */
	@Column(name = "bug_description")
	private String description;
	
	/**
	 * Bug的屏幕截图相对路径的列表.
	 * 当出现多个屏幕截图时, 路径之间使用;分隔.
	 */
	@Column(name = "bug_screenshots")
	private String screenshots;
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -7826234495677979891L;
}
