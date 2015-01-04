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
 * 产品分类的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_product_categories")
public class ProductCategory implements Serializable {
	/**
	 * ProductCategory的默认构造函数.
	 */
	public ProductCategory() {}
	
	/**
	 * ProductCategory的构造函数.
	 * @param productCategoryId - 产品分类的唯一标识符
	 * @param productCategorySlug - 产品分类的唯一英文简称
	 * @param productCategoryName - 产品分类的名称
	 */
	public ProductCategory(int productCategoryId, String productCategorySlug, String productCategoryName) {
		this.productCategoryId = productCategoryId;
		this.productCategorySlug = productCategorySlug;
		this.productCategoryName = productCategoryName;
	}
	
	/**
	 * 获取产品分类的唯一标识符.
	 * @return 产品分类的唯一标识符
	 */
	public int getProductCategoryId() {
		return productCategoryId;
	}

	/**
	 * 设置产品分类的唯一标识符.
	 * @param productCategoryId - 产品分类的唯一标识符
	 */
	public void setProductCategoryId(int productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	/**
	 * 获取产品分类的唯一英文简称.
	 * @return 产品分类的唯一英文简称
	 */
	public String getProductCategorySlug() {
		return productCategorySlug;
	}

	/**
	 * 设置产品分类的唯一英文简称.
	 * @param productCategorySlug - 产品分类的唯一英文简称
	 */
	public void setProductCategorySlug(String productCategorySlug) {
		this.productCategorySlug = productCategorySlug;
	}

	/**
	 * 获取产品分类的名称.
	 * @return 产品分类的名称
	 */
	public String getProductCategoryName() {
		return productCategoryName;
	}

	/**
	 * 设置产品分类的名称.
	 * @param productCategoryName - 产品分类的名称
	 */
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("ProductCategory [ID=%d, Slug=%s, Name=%s]",
					new Object[] { productCategoryId, productCategorySlug, productCategoryName });
	}

	/**
	 * 产品分类的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "product_category_id")
	private int productCategoryId;
	
	/**
	 * 产品分类的唯一英文简称.
	 */
	@Column(name = "product_category_slug")
	private String productCategorySlug;
	
	/**
	 * 产品分类的名称.
	 */
	@Column(name = "product_category_name")
	private String productCategoryName;
	
	/**
	 * 产品列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Product.class, 
			fetch = FetchType.LAZY, mappedBy = "productCategory")
	private List<Product> products = new ArrayList<Product>();
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -1737227489227837991L;
}
