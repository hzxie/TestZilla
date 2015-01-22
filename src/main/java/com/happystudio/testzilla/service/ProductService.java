package com.happystudio.testzilla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.dao.ProductCategoryDao;
import com.happystudio.testzilla.dao.ProductDao;
import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.ProductCategory;

/**
 * 产品Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class ProductService {
	/**
	 * 获取某个分类中最新的产品列表.
	 * @param category - 产品分类对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	public List<Product> getLatestProductsUsingCategory(ProductCategory category, int offset, int limit) {
		List<Product> products = productDao.getLatestProductsUsingCategory(category, offset, limit);
		return products;
	}
	
	/**
	 * 获取某个分类中最热门的产品列表.
	 * @param category - 产品分类对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的产品列表
	 */
	public List<Product> getHotestProductsUsingCategory(ProductCategory category, int offset, int limit) {
		List<Product> products = productDao.getHotestProductsUsingCategory(category, offset, limit);
		return products;
	}
	
	/**
	 * 获取某个分类下产品的数量.
	 * @param category - 产品分类的对象
	 * @return 某个分类下产品的数量
	 */
	public long getTotalProducts(ProductCategory category) {
		long totalProducts = productDao.getTotalProductsUsingFilters(category);
		return totalProducts;
	}
	
	/**
	 * 获取全部产品类别的列表.
	 * @return 全部产品类别的列表
	 */
	public List<ProductCategory> getProductCategories() {
		return productCategoryDao.getAllProductCategory();
	}
	
	/**
	 * 通过产品分类的唯一英文简写获取产品分类对象.
	 * @param productCategorySlug - 产品分类的唯一英文简写
	 * @return 对应的产品分类对象或空引用
	 */
	public ProductCategory getProductCategoryUsingSlug(String productCategorySlug) {
		return productCategoryDao.getProductCategoryUsingSlug(productCategorySlug);
	}
	
	/**
	 * 根据产品唯一标识符获取产品信息.
	 * @param productId - 产品唯一标识符
	 * @return 一个产品对象或空引用
	 */
	public Product getProductsUsingProductId(long productId) {
		return productDao.getProductsUsingProductId(productId);
	}
	
	/**
	 * 自动注入的ProductDao对象.
	 */
	@Autowired
	private ProductDao productDao;
	
	/**
	 * 自动注入的ProductCategoryDao对象.
	 */
	@Autowired
	private ProductCategoryDao productCategoryDao;
}
