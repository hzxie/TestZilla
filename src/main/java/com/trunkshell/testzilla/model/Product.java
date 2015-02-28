package com.trunkshell.testzilla.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 产品的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_products")
public class Product implements Serializable {
	/**
	 * Product的默认构造函数.
	 */
	public Product() {}
	
	/**
	 * Product的构造函数.
	 * @param productName - 产品的名称
	 * @param productLogo - 产品Logo的相对路径
	 * @param productCategory - 产品的分类
	 * @param latestVersion - 产品最新版本号
	 * @param developer - 产品的开发者
	 * @param prerequisites - 产品使用的先决条件
	 * @param url - 产品安装包的相对路径或网站URL
	 * @param description - 产品的相关描述
	 */
	public Product(String productName, String productLogo, ProductCategory productCategory, 
				   String latestVersion, User developer, String prerequisites, String url, 
				   String description) {
		this.productName = productName;
		this.productLogo = productLogo;
		this.productCategory = productCategory;
		this.latestVersion = latestVersion;
		this.developer = developer;
		this.prerequisites = prerequisites;
		this.url = url;
		this.description = description;
	}
	
	/**
	 * Product的构造函数(用于单元测试).
	 * @param productId - 产品的唯一标识符
	 * @param productName - 产品的名称
	 * @param productLogo - 产品Logo的相对路径
	 * @param productCategory - 产品的分类
	 * @param latestVersion - 产品最新版本号
	 * @param developer - 产品的开发者
	 * @param prerequisites - 产品使用的先决条件
	 * @param url - 产品安装包的相对路径或网站URL
	 * @param description - 产品的相关描述
	 */
	public Product(long productId, String productName, String productLogo, ProductCategory productCategory, 
			   String latestVersion, User developer, String prerequisites, String url, 
			   String description) {
		this(productName, productLogo, productCategory, latestVersion, developer, prerequisites, url, description);
		this.productId = productId;
	}

	/**
	 * 获取产品的唯一标识符.
	 * @return 产品的唯一标识符
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * 设置产品的唯一标识符.
	 * @param productId - 产品的唯一标识符
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * 获取产品的名称.
	 * @return 产品的名称
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置产品的名称.
	 * @param productName - 产品的名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 获取产品Logo的相对路径.
	 * @return 产品Logo的相对路径
	 */
	public String getProductLogo() {
		return productLogo;
	}

	/**
	 * 设置产品Logo的相对路径.
	 * @param productLogo - 产品Logo的相对路径
	 */
	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}

	/**
	 * 获取产品的分类.
	 * @return 产品的分类
	 */
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置产品的分类.
	 * @param productCategory - 产品的分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	/**
	 * 获取产品最新版本号.
	 * @return 产品最新版本号
	 */
	public String getLatestVersion() {
		return latestVersion;
	}

	/**
	 * 设置产品最新版本号.
	 * @param productLatestVersion - 产品最新版本号
	 */
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	/**
	 * 获取产品开发者.
	 * @return 产品开发者
	 */
	@JsonIgnore
	public User getDeveloper() {
		return developer;
	}

	/**
	 * 设置产品开发者.
	 * @param developer - 产品开发者
	 */
	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	/**
	 * 获取产品使用的先决条件.
	 * @return 产品使用的先决条件
	 */
	public String getPrerequisites() {
		return prerequisites;
	}

	/**
	 * 设置产品使用的先决条件.
	 * @param prerequisites - 产品使用的先决条件
	 */
	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	/**
	 * 获取产品安装包的相对路径或网站URL.
	 * @return 产品安装包的相对路径或网站URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置产品安装包的相对路径或网站URL.
	 * @param url - 产品安装包的相对路径或网站URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取产品的相关描述.
	 * @return 产品的相关描述
	 */
	public String getDescription() {
		return description;
	}

	/**@JsonInclude(JsonInclude.Include.NON_NULL)
	 * 设置产品的相关描述.
	 * @param description - 产品的相关描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取Bug列表(以便1-N关联).
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

	/**
	 * 获取已发现的Bug数量.
	 * @return 已发现的Bug数量
	 */
	public int getNumberOfIssues() {
		return numberOfIssues;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof Product) ) {
			return false;
		}
		return this.productId == ((Product)o).productId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Product: [Id=%s, Name=%s, productCategory={%s}, latestVersion=%s, developer={%s}, prerequisites=%s, url=%s]", 
				new Object[] { productId, productName, productCategory, latestVersion, developer, prerequisites, url });
	}

	/**
	 * 产品的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private long productId;
	
	/**
	 * 产品的名称.
	 */
	@Column(name = "product_name")
	private String productName;
	
	/**
	 * 产品Logo的相对路径.
	 */
	@Column(name = "product_logo")
	private String productLogo;
	
	/**
	 * 产品的分类.
	 */
	@ManyToOne(targetEntity = ProductCategory.class)
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;
	
	/**
	 * 产品最新版本号.
	 */
	@Column(name = "product_latest_version")
	private String latestVersion;
	
	/**
	 * 产品的开发者.
	 */
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "product_developer_id")
	private User developer;
	
	/**
	 * 产品使用的先决条件.
	 */
	@Column(name = "product_prerequisites")
	private String prerequisites;
	
	/**
	 * 产品安装包的相对路径或网站URL.
	 */
	@Column(name = "product_url")
	private String url;
	
	/**
	 * 产品的相关描述.
	 */
	@Column(name = "product_description")
	private String description;
	
	/**
	 * Bug列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Bug.class, 
				fetch = FetchType.LAZY, mappedBy = "product")
	private List<Bug> bugs = new ArrayList<Bug>();
	
	/**
	 * 已发现的Bug数量.
	 */
	@Formula(value = "(SELECT COUNT(*) FROM tz_bugs b WHERE b.product_id = product_id)")
	private int numberOfIssues;
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = 2604441212604210469L;
}
