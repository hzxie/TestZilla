package com.happystudio.testzilla.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.happystudio.testzilla.exception.ResourceNotFoundException;
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
	 * 显示待测试的产品页面.
	 * @param request - HttpRequest对象
	 * @return 一个包含待测试的产品列表内容的ModelAndView对象
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView productsView(HttpServletRequest request) {
		List<ProductCategory> categories = getProductCategories();
		
		ModelAndView view = new ModelAndView("products/products");
		view.addObject("productCategories", categories);
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
	 * 获取待测试的产品列表.
	 * @param page - 分页的页码
	 * @param category - 产品所属分类
	 * @param sortBy - 产品排序依据
	 * @param request - HttpRequest对象
	 * @return 一个包含待测试的产品列表JSON数组
	 */
	@RequestMapping(value = "/getProducts.action", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getProductsAction(
			@RequestParam(value="page", required=false, defaultValue="1") int pageNumber,
			@RequestParam(value="category", required=false) String productCategorySlug,
			@RequestParam(value="sortBy", required=false, defaultValue="latest") String sortBy,
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ProductCategory category = getProductCategoryUsingSlug(productCategorySlug);
		List<Product> products = getProducts(category, pageNumber, sortBy.equals("latest"));
		long totalPages = getProductTotalPages(category);
		
		result.put("isSuccessful", products.size() != 0);
		result.put("products", products);
		result.put("totalPages", totalPages);
		return result;
	}
	
	/**
	 * 根据筛选条件获取待测试的产品列表.
	 * @param category - 产品所属分类
	 * @param pageNumber - 分页的页码
	 * @param isSortedByTime - 是否按照发布时间排序
	 * @return 待测试的产品列表
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
	 * 获取全部产品类别的列表.
	 * @return 全部产品类别的列表
	 */
	private List<ProductCategory> getProductCategories() {
		return productService.getProductCategories();
	}
	
	/**
	 * 显示产品详细信息页面.
	 * @param productId - 产品的唯一标识符
	 * @param request - HttpRequest请求
	 * @return 一个包含产品详细信息的ModelAndView对象
	 */
	@RequestMapping(value = "/{productId}")
	public ModelAndView productView(
			@PathVariable("productId") int productId,
    		HttpServletRequest request) {
		Product product = productService.getProductsUsingProductId(productId);
		if ( product == null ) {
			throw new ResourceNotFoundException();
		}
		
		List<Product> relatedProducts = getRelatedProducts(product.getProductCategory());
		ModelAndView view = new ModelAndView("products/product");
        view.addObject("product", product);
        view.addObject("relatedProducts", relatedProducts);
        return view;
	}
	
	/**
	 * 为用户推荐相似的待测试产品.
	 * @param category - 产品所属的产品类别
	 * @return 待测试的产品列表
	 */
	private List<Product> getRelatedProducts(ProductCategory category) {
		int offset = 0;
		return productService.getLatestProductsUsingCategory(category, offset, NUMBER_OF_PRODUCTS_PER_PAGE);
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
