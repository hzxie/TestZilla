package com.happystudio.testzilla.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.ProductCategory;
import com.happystudio.testzilla.model.User;

/**
 * ProductDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class ProductDaoTest {
	/**
	 * 测试用例: 测试getLatestProducts()方法
	 * 测试数据: 使用合法的offset和limit值
	 * 预期结果: 预期的产品列表
	 */
	@Test
	public void testGetLatestProducts() {
		List<Product> products = productDao.getLatestProducts(0, 10);
		Assert.assertEquals(2, products.size());
		
		Product product = products.get(0);
		Assert.assertEquals("IT Training Platform", product.getProductName());
	}
	
	/**
	 * 测试用例: 测试getProductsUsingCategory()方法
	 * 测试数据: 使用存在的产品分类
	 * 预期结果: 预期的产品列表
	 */
	@Test
	public void testGetProductsUsingCategory() {
		ProductCategory category = new ProductCategory(3, "web", "Web Application");
		List<Product> products = productDao.getProductsUsingCategory(category, 1, 1);
		Assert.assertEquals(1, products.size());
		
		Product product = products.get(0);
		Assert.assertEquals("IT Training Platform", product.getProductName());
	}
	
	/**
	 * 测试用例: 测试getProductsUsingDeveloper()方法
	 * 测试数据: 使用存在的产品分类
	 * 预期结果: 预期的产品列表
	 */
	@Test
	public void testGetProductsUsingDeveloper() {
		User developer = userDao.getUserUsingUid(1001);
		List<Product> products = productDao.getProductsUsingDeveloper(developer, 0, 1);
		Assert.assertEquals(1, products.size());
		
		Product product = products.get(0);
		Assert.assertEquals("IT Training Platform", product.getProductName());
	}
	
	/**
	 * 测试用例: 测试getProductsUsingKeyword()方法
	 * 测试数据: 使用合适的关键词
	 * 预期结果: 预期的产品列表
	 */
	@Test
	public void testGetProductsUsingKeyword() {
		List<Product> products = productDao.getProductsUsingKeyword("Platform", 0, 10);
		Assert.assertEquals(1, products.size());
		
		Product product = products.get(0);
		Assert.assertEquals("IT Training Platform", product.getProductName());
	}
	
	/**
	 * 测试用例: 测试getProductsUsingProductId()方法
	 * 测试数据: 使用存在的产品唯一标识符
	 * 预期结果: 返回预期的Product对象
	 */
	@Test
	public void testGetProductsUsingProductIdExists() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		
		String productName = product.getProductName();
		Assert.assertEquals("TestZilla", productName);
	}
	
	/**
	 * 测试用例: 测试getProductsUsingProductId()方法
	 * 测试数据: 使用不存在的产品唯一标识符
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetProductsUsingProductIdNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		Assert.assertNull(product);
	}
	
	/**
	 * 测试用例: 测试createProduct()方法
	 * 测试数据: 使用合法的数据集
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testCreateProductNormal() {
		ProductCategory category = new ProductCategory(3, "web", "Web Application");
		User developer = userDao.getUserUsingUid(1001);
		Product product = new Product("New Product", "Product Logo", category, "Product Version", 
										developer, "Prerequisites", "URL", "Description");
		Assert.assertTrue(productDao.createProduct(product));
	}
	
	/**
	 * 测试用例: 测试createProduct()方法
	 * 测试数据: 使用不合法的数据集(过长的产品名称)
	 * 预期结果: 抛出DataException异常
	 */
	@Test(expected = org.hibernate.exception.DataException.class)
	public void testCreateProductUsingIllegalProductName() {
		ProductCategory category = new ProductCategory(3, "web", "Web Application");
		User developer = userDao.getUserUsingUid(1001);
		Product product = new Product("Toooooooooooooo Long Product Name", "Product Logo", category, 
										"Product Version", developer, "Prerequisites", "URL", "Description");
		productDao.createProduct(product);
	}
	
	/**
	 * 测试用例: 测试createProduct()方法
	 * 测试数据: 使用不合法的数据集(不存在的产品分类)
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testCreateProductUsingNotExistingCategory() {
		ProductCategory category = new ProductCategory(0, "not-exists", "Not Exists");
		User developer = userDao.getUserUsingUid(1001);
		Product product = new Product("New Product", "Product Logo", category, "Product Version", 
				developer, "Prerequisites", "URL", "Description");
		productDao.createProduct(product);
	}
	
	/**
	 * 测试用例: 测试addProductTester()方法
	 * 测试数据: 使用存在的产品唯一标识符和用户唯一标识符
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testAddProductTesterNormal() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		User user = userDao.getUserUsingUid(1000);
		Assert.assertNotNull(user);
		
		Assert.assertTrue(productDao.addProductTester(product, user));
	}
	
	/**
	 * 测试用例: 测试addProductTester()方法
	 * 测试数据: 使用不存在的产品唯一标识符和存在的用户唯一标识符
	 * 预期结果: 返回false, 表示操作未成功完成
	 */
	@Test
	public void testAddProductTesterUsingNotExistingProduct() {
		Product product = productDao.getProductsUsingProductId(0);
		Assert.assertNull(product);
		User user = userDao.getUserUsingUid(1000);
		Assert.assertNotNull(user);
		
		Assert.assertFalse(productDao.addProductTester(product, user));
	}
	
	/**
	 * 测试用例: 测试addProductTester()方法
	 * 测试数据: 使用存在的产品唯一标识符和不存在的用户唯一标识符
	 * 预期结果: 返回false, 表示操作未成功完成
	 */
	@Test
	public void testAddProductTesterUsingNotExistingUser() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		User user = userDao.getUserUsingUid(0);
		Assert.assertNull(user);
		
		Assert.assertFalse(productDao.addProductTester(product, user));
	}

	/**
	 * 测试用例: 测试addProductTester()方法
	 * 测试数据: 使用存在的产品唯一标识符和用户唯一标识符
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testAddProductTesterAlreadyAttended() {
		Product product = productDao.getProductsUsingProductId(1001);
		Assert.assertNotNull(product);
		User user = userDao.getUserUsingUid(1001);
		Assert.assertNotNull(user);
		
		Assert.assertTrue(productDao.addProductTester(product, user));
	}
	
	/**
	 * 测试用例: 测试updateProduct()方法
	 * 测试数据: 使用合法的数据集且数据库中存在该产品
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testUpdateProductNormal() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		
		product.setProductName("New Product Name");
		Assert.assertTrue(productDao.updateProduct(product));
		
		product = productDao.getProductsUsingProductId(1000);
		Assert.assertEquals("New Product Name", product.getProductName());
	}
	
	/**
	 * 测试用例: 测试updateProduct()方法
	 * 测试数据: 使用合法的数据集但数据库中不存在该产品
	 * 预期结果: 返回false, 表示操作未成功完成
	 */
	@Test
	public void testUpdateProductNotExists() {
		Product product = productDao.getProductsUsingProductId(1000);
		product.setProductId(0);
		product.setProductName("New Product Name");
		Assert.assertFalse(productDao.updateProduct(product));
	}
	
	/**
	 * 测试用例: 测试updateProduct()方法
	 * 测试数据: 使用不合法的数据集(过长的新产品名称)
	 * 预期结果: 预期结果: 抛出DataException异常
	 */
	@Test(expected = org.hibernate.exception.DataException.class)
	public void testUpdateProductUsingIllegalProductName() {
		Product product = productDao.getProductsUsingProductId(1000);
		product.setProductName("Toooooooooo Long New Product Name");
		productDao.updateProduct(product);
	}
	
	/**
     * 测试用例: 测试deleteProduct()方法
     * 测试数据: 存在的产品唯一标识符
     * 预期结果: 返回true, 表示操作成功完成
     */
	@Test
	public void testDeleteProductExists() {
		Product product = productDao.getProductsUsingProductId(1000);
		Assert.assertNotNull(product);
		
		Assert.assertTrue(productDao.deleteProduct(1000));
		product = productDao.getProductsUsingProductId(1000);
		Assert.assertNull(product);
	}
	
	/**
     * 测试用例: 测试deleteProduct()方法
     * 测试数据: 不存在的产品唯一标识符
     * 预期结果: 返回false, 表示操作未成功完成
     */
	@Test
	public void testDeleteProductNotExists() {
		Product product = productDao.getProductsUsingProductId(0);
		Assert.assertNull(product);
		
		Assert.assertFalse(productDao.deleteProduct(0));
	}
	
	/**
	 * 待测试的ProductDao对象.
	 */
	@Autowired
	private ProductDao productDao;
	
	/**
	 * 自动注入的UserDao对象.
	 * 协助完成单元测试, 构建测试用例.
	 */
	@Autowired
	private UserDao userDao;
}
