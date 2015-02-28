package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.ProductCategory;

/**
 * ProductCategory类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class ProductCategoryDao {
	/**
	 * 获取全部的产品分类对象.
	 * @return 全部产品分类对象的列表
	 */
	@Transactional
	public List<ProductCategory> getAllProductCategory() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ProductCategory> productCategories = (List<ProductCategory>)session
			.createQuery("FROM ProductCategory").list();
		return productCategories;
	}
	
	/**
	 * 通过产品分类的唯一标识符获取产品分类对象.
	 * @param productCategoryId - 产品分类的唯一标识符
	 * @return 对应的产品分类对象或空引用
	 */
	@Transactional
	public ProductCategory getProductCategoryUsingId(int productCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		ProductCategory productCategory = (ProductCategory)session.get(ProductCategory.class, productCategoryId);
		return productCategory;
	}
	
	/**
	 * 通过产品分类的唯一英文简写获取产品分类对象.
	 * @param productCategorySlug - 产品分类的唯一英文简写
	 * @return 对应的产品分类对象或空引用
	 */
	@Transactional
	public ProductCategory getProductCategoryUsingSlug(String productCategorySlug) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ProductCategory> productCategories = (List<ProductCategory>)session
			.createQuery("FROM ProductCategory WHERE productCategorySlug = ?0")
			.setString("0", productCategorySlug).list();
		
		for (ProductCategory productCategory : productCategories ) {
			if ( productCategory.getProductCategorySlug().equals(productCategorySlug) ) {
				return productCategory;
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
