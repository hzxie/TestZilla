package com.happystudio.testzilla.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.dao.ProductCategoryDao;
import com.happystudio.testzilla.dao.ProductDao;
import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.ProductCategory;
import com.happystudio.testzilla.model.User;
import com.happystudio.testzilla.util.TextFilter;

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
	 * 验证数据并创建产品.
	 * @param productName - 产品名称
	 * @param productLogo - 产品Logo的URL
	 * @param productCategory - 产品分类目录
	 * @param latestVersion - 最新版本
	 * @param developer - 产品开发者
	 * @param prerequisites - 测试的先决条件
	 * @param productUrl - 产品主页
	 * @param description - 产品的描述信息
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	public HashMap<String, Boolean> createProduct(String productName, 
			String productLogo, String productCategorySlug, String latestVersion, 
			User developer, String prerequisites, String productUrl, String description) {
		ProductCategory productCategory = getProductCategoryUsingSlug(productCategorySlug);
		Product product = new Product(TextFilter.filterHtml(productName),
							TextFilter.filterHtml(productLogo), productCategory, 
							TextFilter.filterHtml(latestVersion), developer, 
							TextFilter.filterHtml(prerequisites), 
							TextFilter.filterHtml(productUrl), 
							TextFilter.filterHtml(description));
		HashMap<String, Boolean> result = getCreateProductResult(product);
		
		if ( result.get("isSuccessful") ) {
			boolean isSuccessful = productDao.createProduct(product);
			result.put("isSuccessful", isSuccessful);
		}
		return result;
	}
	
	/**
	 * 验证数据并创建产品.
	 * @param product - 待创建的产品对象
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	private HashMap<String, Boolean> getCreateProductResult(Product product) {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isProductNameEmpty", product.getProductName().isEmpty());
		result.put("isProductNameLegal", isProductNameLegal(product.getProductName()));
		result.put("isProductLogoEmpty", product.getProductLogo().isEmpty());
		result.put("isProductLogoLegal", isProductLogoLegal(product.getProductLogo()));
		result.put("isProductCategoryEmpty", product.getProductCategory() == null);
		result.put("isLatestVersionEmpty", product.getLatestVersion().isEmpty());
		result.put("isLatestVersionLegal", isLatestVersionLegal(product.getLatestVersion()));
		result.put("isDeveloperEmpty", isDeveloperEmpty(product.getDeveloper()));
		result.put("isPrerequisitesEmpty", product.getPrerequisites().isEmpty());
		result.put("isPrerequisitesLegal", isPrerequisitesLegal(product.getPrerequisites()));
		result.put("isProductUrlEmpty", product.getUrl().isEmpty());
		result.put("isProductUrlLegal", isProductUrlLegal(product.getUrl()));
		result.put("isDescriptionEmpty", product.getDescription().isEmpty());
		
		boolean isSuccessful = !result.get("isProductNameEmpty")     &&  result.get("isProductNameLegal")   &&
							   !result.get("isProductLogoEmpty")     &&  result.get("isProductLogoLegal")   &&
							   !result.get("isProductCategoryEmpty") && !result.get("isLatestVersionEmpty") &&
							    result.get("isLatestVersionLegal")   && !result.get("isDeveloperEmpty")     &&
							   !result.get("isPrerequisitesEmpty")   &&  result.get("isPrerequisitesLegal") &&
							   !result.get("isProductUrlEmpty")      &&  result.get("isProductUrlLegal")    &&
							   !result.get("isDescriptionEmpty");
		result.put("isSuccessful", isSuccessful);
		return result;
	}
	
	/**
	 * 检查产品名称是否合法.
	 * 规则: 产品名称的长度不得超过32个字符.
	 * @param productName - 产品名称
	 * @return 产品名称是否合法
	 */
	private boolean isProductNameLegal(String productName) {
		int productNameLength = productName.length();
		return productNameLength <= 32;
	}
	
	/**
	 * 检查产品Logo的URL是否合法.
	 * 规则: 产品Logo的URL不得超过128个字符.
	 * @param productLogo - 产品Logo的URL
	 * @return 产品Logo的URL是否合法
	 */
	private boolean isProductLogoLegal(String productLogo) {
		int productLogoLength = productLogo.length();
		return productLogoLength <= 128;
	}
	
	/**
	 * 检查软件版本号是否合法.
	 * 规则: 软件版本号不得超过24个字符.
	 * @param productVersion - 软件版本号
	 * @return 软件版本号是否合法
	 */
	private boolean isLatestVersionLegal(String productVersion) {
		int productVersionLength = productVersion.length();
		return productVersionLength <= 24;
	}
	
	/**
	 * 检查用户是否具有创建课程的权限.
	 * @param user - 用户(User)对象
	 * @return 用户是否具有创建课程的权限
	 */
	private boolean isDeveloperEmpty(User user) {
		if ( user == null || 
		    !user.getUserGroup().getUserGroupSlug().equals("developer") ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查测试先决条件是否合法.
	 * 规则: 先决条件不得超过128个字符.
	 * @param prerequisites - 先决条件 
	 * @return 测试先决条件是否合法
	 */
	private boolean isPrerequisitesLegal(String prerequisites) {
		int prerequisitesLength = prerequisites.length();
		return prerequisitesLength <= 128;
	}
	
	/**
	 * 检查项目主页地址是否合法.
	 * 规则: 项目主页地址不得超过256个字符.
	 * @param productUrl - 项目主页地址
	 * @return 项目主页地址是否合法
	 */
	private boolean isProductUrlLegal(String productUrl) {
		int productUrlLength = productUrl.length();
		return productUrlLength <= 256;
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
