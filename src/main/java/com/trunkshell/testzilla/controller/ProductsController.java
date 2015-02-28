package com.trunkshell.testzilla.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trunkshell.testzilla.exception.ResourceNotFoundException;
import com.trunkshell.testzilla.model.Bug;
import com.trunkshell.testzilla.model.BugCategory;
import com.trunkshell.testzilla.model.BugSeverity;
import com.trunkshell.testzilla.model.Product;
import com.trunkshell.testzilla.model.ProductCategory;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.service.BugService;
import com.trunkshell.testzilla.service.ProductService;
import com.trunkshell.testzilla.util.HttpRequestParser;

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
		
		List<BugCategory> bugCategories = getBugCategories();
		List<BugSeverity> bugSeverityList = getBugSeverityList();
		List<Product> relatedProducts = getRelatedProducts(product.getProductCategory());
		ModelAndView view = new ModelAndView("products/product");
        view.addObject("product", product);
        view.addObject("bugCategories", bugCategories);
        view.addObject("bugSeverityList", bugSeverityList);
        view.addObject("relatedProducts", relatedProducts);
        return view;
	}

	/**
	 * 获取产品的详细信息.
	 * @param productId - 产品的唯一标识符
	 * @param request - HttpRequest请求
	 * @return 一个包含产品详细信息的JSON数组
	 */
	@RequestMapping(value = "/getProduct.action", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getProductAction(
			@RequestParam(value="productId", required=true) long productId,
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Product product = productService.getProductsUsingProductId(productId);
		
		result.put("isSuccessful", product != null);
		result.put("product", product);
		return result;
	}

	/**
	 * 获取全部的Bug分类对象.
	 * @return 全部Bug分类对象的列表
	 */
	private List<BugCategory> getBugCategories() {
		return bugService.getBugCategories();
	}
	
	/**
	 * 获取全部的Bug严重性对象.
	 * @return 全部Bug严重性对象的列表
	 */
	private List<BugSeverity> getBugSeverityList() {
		return bugService.getBugSeverityList();
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
	 * 获取某个产品的Bug列表.
	 * @param productId - 产品的唯一标识符
	 * @param pageNumber - 分页的页码
	 * @param request - HttpRequest对象
	 * @return 某个产品的Bug列表
	 */
	@RequestMapping(value = "/getBugs.action", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getBugsAction(
			@RequestParam(value="productId", required=true) long productId,
			@RequestParam(value="page", required=false, defaultValue="1") int pageNumber,
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Product product = productService.getProductsUsingProductId(productId);
		if ( product == null ) {
			throw new ResourceNotFoundException();
		}
		List<Bug> bugs = getBugsUsingProduct(product, pageNumber);
		long totalPages = getBugTotalPagesUsingProduct(product);
		
		result.put("isSuccessful", bugs.size() != 0);
		result.put("bugs", bugs);
		result.put("totalPages", totalPages);
		return result;
	}
	
	
	/**
	 * 获取某个产品的Bug列表.
	 * @param product - 产品(Product)的对象
	 * @param page - 分页的页码
	 * @return 某个产品的Bug列表
	 */
	private List<Bug> getBugsUsingProduct(Product product, int page) {
		int offset = (page - 1) * NUMBER_OF_BUGS_PER_PAGE;
		List<Bug> bugs = bugService.getBugsUsingProduct(product, offset, NUMBER_OF_BUGS_PER_PAGE);
		return bugs;
	}
	
	/**
	 * 获取某个产品中Bug的总分页页数
	 * @param product - 产品(Product)的对象
	 * @return Bug的总分页页数
	 */
	private long getBugTotalPagesUsingProduct(Product product) {
		return (long)Math.ceil((double)bugService.getTotalBugsUsingProduct(product) / NUMBER_OF_BUGS_PER_PAGE);
	}
	
	/**
	 * 处理用户提交新Bug的请求.
	 * @param productId - 产品的唯一标识符
	 * @param version - Bug所在的软件版本
	 * @param bugCategorySlug - Bug分类的唯一英文缩写
	 * @param bugStatusSlug - Bug状态的唯一英文缩写
	 * @param bugSeveritySlug - Bug严重程度的唯一英文缩写
	 * @param title - Bug的标题
	 * @param description - Bug的详细描述
	 * @param screenshots - 应用程序截图的相对路径列表
	 * @param request - HttpRequest对象
	 * @return 一个包含若干标志位的JSON数据
	 */
	@RequestMapping(value = "/createBug.action", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Boolean> createBugAction(
			@RequestParam(value="productId", required=true) long productId,
			@RequestParam(value="version", required=true) String version,
			@RequestParam(value="bugCategory", required=true) String bugCategorySlug,
			@RequestParam(value="bugSeverity", required=true) String bugSeveritySlug,
			@RequestParam(value="title", required=true) String title,
			@RequestParam(value="description", required=true) String description,
			@RequestParam(value="screenshots", required=false) String screenshots,
			HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		Product product = productService.getProductsUsingProductId(productId);
		String ipAddress = HttpRequestParser.getRemoteAddr(request);
		
		HashMap<String, Boolean> result = bugService.createBug(product, version, bugCategorySlug, 
											bugSeveritySlug, user, title, description, screenshots);
		if ( result.get("isSuccessful") ) {
			logger.info(String.format("User {%s} created a bug for Product {%s} at %s.", new Object[] {user, product, ipAddress}));
		}
		return result;
	}
	
	/**
	 * 产品列表页面每页所显示的产品数量.
	 */
	private final int NUMBER_OF_PRODUCTS_PER_PAGE = 10;
	
	/**
	 * 产品详情页面每页所显示的Bug数量.
	 */
	private final int NUMBER_OF_BUGS_PER_PAGE = 10;
	
	/**
     * 自动注入的ProductService对象.
     */
	@Autowired
	private ProductService productService;
	
	/**
     * 自动注入的BugService对象.
     */
	@Autowired
	private BugService bugService;
	
	/**
     * 日志记录器.
     */
    private Logger logger = LogManager.getLogger(ProductsController.class);
}
