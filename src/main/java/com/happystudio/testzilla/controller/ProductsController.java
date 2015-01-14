package com.happystudio.testzilla.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.ProductCategory;
import com.happystudio.testzilla.service.ProductService;

/**
 * 待测试产品的Controller.
 * @author Xie Haozhe
 */
@Controller
@RequestMapping(value = "/products")
public class ProductsController {
	/**
	 * 显示待测试的产品列表.
	 * @param request - HttpRequest对象
	 * @return 一个包含待测试的产品列表内容的ModelAndView对象
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView productsView(
    		@RequestParam(value="page", required=false, defaultValue="1") int pageNumber,
			@RequestParam(value="category", required=false) String productCategorySlug,
			@RequestParam(value="sortBy", required=false, defaultValue="latest") String sortBy,
			HttpServletRequest request) {
		ProductCategory category = getProductCategoryUsingSlug(productCategorySlug);
		List<Product> products = getProducts(category, pageNumber, sortBy.equals("latest"));
		long totalPages = getProductTotalPages(category);
		
		ModelAndView view = new ModelAndView("products/products");
		view.addObject("isSuccessful", products.size() != 0);
		view.addObject("products", products);
		view.addObject("currentPage", pageNumber);
		view.addObject("totalPages", totalPages);
        return view;
    }
	
	/**
	 * 通过产品分类的唯一英文简写获取产品分类对象.
	 * @param productCategorySlug - 产品分类的唯一英文简写
	 * @return 对应的产品分类对象或空引用
	 */
	private ProductCategory getProductCategoryUsingSlug(String productCategorySlug) {
		return productService.getProductCategoryUsingSlug(productCategorySlug);
	}
	
	/**
	 * 获取某个产品分类中产品的总分页页数.
	 * @param category - 产品分类的对象
	 * @return 某个产品分类中产品的总分页页数
	 */
	private long getProductTotalPages(ProductCategory category) {
		return (long)Math.ceil((double)productService.getTotalProducts(category) / NUMBER_OF_PRODUCTS_PER_PAGE);
	}
	
	/**
	 * @param category
	 * @param pageNumber
	 * @param isSortedByTime
	 * @return
	 */
	private List<Product> getProducts(ProductCategory category, int pageNumber, boolean isSortedByTime) {
		int offset = (pageNumber - 1) * NUMBER_OF_PRODUCTS_PER_PAGE;
		List<Product> products = null;
		
		if ( isSortedByTime ) {
			products = productService.getLatestProductsUsingCategory(category, offset, NUMBER_OF_PRODUCTS_PER_PAGE);
		} else {
			products = productService.getHotestProductsUsingCategory(category, offset, NUMBER_OF_PRODUCTS_PER_PAGE);
		}
		return products;
	}
	
	/**
	 * 产品列表页面每页所显示的产品数量.
	 */
	private final int NUMBER_OF_PRODUCTS_PER_PAGE = 10;
	
	/**
     * 自动注入的ProductService对象.
     */
	@Autowired
	private ProductService productService;
}
