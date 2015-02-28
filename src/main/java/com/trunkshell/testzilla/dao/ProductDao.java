package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.Product;
import com.trunkshell.testzilla.model.ProductCategory;
import com.trunkshell.testzilla.model.User;

/**
 * Product类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class ProductDao {
	/**
	 * 获取某个分类下产品的数量.
	 * @param category - 产品分类的对象
	 * @return 某个分类下产品的数量
	 */
	@Transactional
	public long getTotalProductsUsingFilters(ProductCategory category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		if ( category == null ) {
			query = session.createQuery("SELECT COUNT(*) FROM Product");
		} else {
			query = session.createQuery("SELECT COUNT(*) FROM Product WHERE productCategory = ?0")
							.setParameter("0", category);
		}
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取某个用户所发布的产品数量.
	 * @param developer - 开发者的用户对象
	 * @return 某个用户所发布的产品数量
	 */
	@Transactional
	public long getTotalProductsUsingFilters(User developer) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM Product WHERE developer = ?0")
								.setParameter("0", developer);
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取某个分类中最新的产品列表.
	 * @param category - 产品分类对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	@Transactional
	public List<Product> getLatestProductsUsingCategory(ProductCategory category, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		if ( category == null ) {
			query = session.createQuery("FROM Product ORDER BY productId DESC")
							.setFirstResult(offset).setMaxResults(limit);
		} else {
			query = session.createQuery("FROM Product WHERE productCategory = ?0 ORDER BY productId DESC")
							.setParameter("0", category).setFirstResult(offset).setMaxResults(limit);
		}
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>)query.list();
		
		return products;
	}
	
	/**
	 * 获取某个分类中最热门的产品列表.
	 * @param category - 产品分类对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	@Transactional
	public List<Product> getHotestProductsUsingCategory(ProductCategory category, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		if ( category == null ) {
			query = session.createQuery("FROM Product ORDER BY numberOfTesters DESC")
							.setFirstResult(offset).setMaxResults(limit);
		} else {
			query = session.createQuery("FROM Product WHERE productCategory = ?0 ORDER BY numberOfTesters DESC")
							.setParameter("0", category).setFirstResult(offset).setMaxResults(limit);
		}
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>)query.list();
		
		return products;
	}
	
	/**
	 * 通过开发者筛选产品列表.
	 * @param developer - 开发者(User对象)
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	@Transactional
	public List<Product> getProductsUsingDeveloper(User developer, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>)session.createQuery("FROM Product WHERE developer = ?0 ORDER BY productId DESC")
														.setParameter("0", developer)
														.setFirstResult(offset)
														.setMaxResults(limit).list();
		return products;
	}
	
	/**
	 * 通过关键词搜索产品.
	 * @param keyword - 关键词
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	@Transactional
	public List<Product> getProductsUsingKeyword(String keyword, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>)session.createQuery("FROM Product WHERE productName LIKE ?0")
														.setString("0", "%" + keyword + "%")
														.setFirstResult(offset)
														.setMaxResults(limit).list();
		return products;
	}
	
	/**
	 * 根据产品唯一标识符获取产品信息.
	 * @param productId - 产品唯一标识符
	 * @return 一个产品对象或空引用
	 */
	@Transactional
	public Product getProductsUsingProductId(long productId) {
		Session session = sessionFactory.getCurrentSession();
		Product product = (Product)session.get(Product.class, productId);
		return product;
	}
	
	/**
	 * 创建新的产品.
	 * @param product - 产品(Product对象)
	 * @return 操作是否成功完成 
	 */
	@Transactional
	public boolean createProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.save(product);
		session.flush();
		return true;
	}
	
	/**
	 * 添加产品测试者.
	 * @param tester - 测试者(User对象)
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean addProductTester(Product product, User tester) {
		Session session = sessionFactory.getCurrentSession();
		if ( product == null || tester == null ) {
			return false;
		}
		
		product.addTester(tester);
		session.save(product);
		session.flush();
		return true;
	}
	
	/**
	 * 更新产品信息.
	 * @param product - 待更新的产品(Product)对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean updateProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		if ( session.get(Product.class, product.getProductId()) == null ) {
			return false;
		}
		session.merge(product);
		session.flush();
		return true;
	}
	
	/**
	 * 通过产品的唯一标识符删除产品.
	 * @param productId - 产品的唯一标识符
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean deleteProduct(long productId) {
		Session session = sessionFactory.getCurrentSession();
		Product product = (Product)session.get(Product.class, productId);
		
		if ( product == null ) {
			return false;
		}
		session.delete(product);
		return true;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
