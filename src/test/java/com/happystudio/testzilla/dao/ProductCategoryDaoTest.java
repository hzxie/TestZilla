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

import com.happystudio.testzilla.model.ProductCategory;

/**
 * ProductCategoryDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class ProductCategoryDaoTest {
	/**
	 * 测试用例: 测试getAllProductCategory()方法
	 * 测试数据: N/a
	 * 预期结果: 返回所有ProductCategory对象的列表
	 */
	@Test
	public void testGetAllProductCategory() {
		List<ProductCategory> productCategories = productCategoryDao.getAllProductCategory();
		Assert.assertNotNull(productCategories);
		Assert.assertEquals(7, productCategories.size());
		
		ProductCategory productCategory = productCategories.get(0);
		Assert.assertEquals("web", productCategory.getProductCategorySlug());
	}
	
	/**
	 * 测试用例: 测试getProductCategoryUsingId()方法
	 * 测试数据: 使用Web应用的productCategoryId
	 * 预期结果: 返回Web应用的ProductCategory对象
	 */
	@Test
	public void testGetProductCategoryUsingIdExists() {
		ProductCategory productCategory = productCategoryDao.getProductCategoryUsingId(1);
		Assert.assertNotNull(productCategory);
		
		String productCategorySlug = productCategory.getProductCategorySlug();
		Assert.assertEquals("web", productCategorySlug);
	}
	
	/**
	 * 测试用例: 测试getProductCategoryUsingId()方法
	 * 测试数据: 使用不存在的productCategoryId
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetProductCategoryUsingIdNotExists() {
		ProductCategory productCategory = productCategoryDao.getProductCategoryUsingId(0);
		Assert.assertNull(productCategory);
	}
	
	/**
	 * 测试用例: 测试getProductCategoryUsingSlug()方法
	 * 测试数据: 使用Web应用的productCategorySlug
	 * 预期结果: 返回Web应用的ProductCategory对象
	 */
	@Test
	public void testGetProductCategoryUsingSlugExists() {
		ProductCategory productCategory = productCategoryDao.getProductCategoryUsingSlug("web");
		Assert.assertNotNull(productCategory);
		
		int productCategoryId = productCategory.getProductCategoryId();
		Assert.assertEquals(1, productCategoryId);
	}
	
	/**
	 * 测试用例: 测试getProductCategoryUsingSlug()方法
	 * 测试数据: 使用不存在的productCategorySlug
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetProductCategoryUsingSlugNotExists() {
		ProductCategory productCategory = productCategoryDao.getProductCategoryUsingSlug("Not Exists");
		Assert.assertNull(productCategory);
	}
	
	/**
	 * 待测试的ProductCategoryDao对象.
	 */
	@Autowired
	private ProductCategoryDao productCategoryDao;
}
