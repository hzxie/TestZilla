package com.happystudio.testzilla.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.dao.BugCategoryDao;
import com.happystudio.testzilla.dao.BugDao;
import com.happystudio.testzilla.dao.BugSeverityDao;
import com.happystudio.testzilla.dao.BugStatusDao;
import com.happystudio.testzilla.model.Bug;
import com.happystudio.testzilla.model.BugCategory;
import com.happystudio.testzilla.model.BugSeverity;
import com.happystudio.testzilla.model.BugStatus;
import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.User;

/**
 * Bug Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class BugService {
	/**
	 * 获取某个产品的Bug列表.
	 * @param product - 产品(Product)对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	public List<Bug> getBugsUsingProduct(Product product, int offset, int limit) {
		List<Bug> bugs = bugDao.getBugsUsingProduct(product, offset, limit);
		return bugs;
	}
	
	/**
	 * 获取某个产品下Bug的数量.
	 * @param product - 产品(Product)对象
	 * @return 某个产品下Bug的数量
	 */
	public long getTotalBugsUsingProduct(Product product) {
		long totalBugs = bugDao.getTotalBugsUsingProduct(product);
		return totalBugs;
	}
	
	/**
	 * 获取全部的Bug分类对象.
	 * @return 全部Bug分类对象的列表
	 */
	public List<BugCategory> getBugCategories() {
		return bugCategoryDao.getAllBugCategory();
	}
	
	/**
	 * 获取全部的Bug严重性对象.
	 * @return 全部Bug严重性对象的列表
	 */
	public List<BugSeverity> getBugSeverityList() {
		return bugSeverityDao.getAllBugSeverity();
	}
	
	/**
	 * 验证数据并创建Bug.
	 * @param product - 产品对象
	 * @param version - Bug所在的软件版本
	 * @param bugCategorySlug - Bug分类的唯一英文缩写
	 * @param bugSeveritySlug - Bug严重程度的唯一英文缩写 
	 * @param hunter - Bug提交者的用户对象
	 * @param title - Bug的标题
	 * @param description - Bug的详细描述
	 * @param screenshots - 应用程序截图的相对路径列表
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	public HashMap<String, Boolean> createBug(Product product, String version, String bugCategorySlug, 
			String bugSeveritySlug, User hunter, String title, String description, String screenshots) {
		BugCategory category = getBugCategory(bugCategorySlug);
		BugStatus status = bugStatusDao.getBugStatusUsingSlug("unconfirmed");
		BugSeverity severity = getBugSeverity(bugSeveritySlug);
		Date createTime = new Date();
		Bug bug = new Bug(product, version, category, status, severity, 
							createTime, hunter, title, description, screenshots);
		HashMap<String, Boolean> result = getCreateBugResult(bug);
		
		if ( result.get("isSuccessful") ) {
    		boolean isSuccessful = bugDao.createBug(bug);
    		result.put("isSuccessful", isSuccessful);
    	}
		return result;
	}
	
	/**
	 * 检查用户所提交的Bug信息是否合法.
	 * @param bug - 待添加的Bug
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	private HashMap<String, Boolean> getCreateBugResult(Bug bug) {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isProductExists", bug.getProduct() != null);
		result.put("isProductVersionEmpty", bug.getProductVersion().isEmpty());
		result.put("isProductVersionLegal", bug.getProductVersion().length() <= 24);
		result.put("isCategoryEmpty", bug.getBugCategory() == null);
		result.put("isSeverityEmpty", bug.getBugSeverity() == null);
		result.put("isHunterEmpty", bug.getHunter() == null);
		result.put("isTitleEmpty", bug.getTitle().isEmpty());
		result.put("isTitleLegal", bug.getTitle().length() <= 64);
		result.put("isDescriptionEmpty", bug.getDescription().isEmpty());
		
		boolean isSuccessful =  result.get("isProductExists")       && !result.get("isProductVersionEmpty") &&
				                result.get("isProductVersionLegal") && !result.get("isCategoryEmpty")       &&
				               !result.get("isSeverityEmpty")       && !result.get("isHunterEmpty")         &&
				               !result.get("isTitleEmpty")          &&  result.get("isTitleLegal")          &&
				               !result.get("isDescriptionEmpty");
		result.put("isSuccessful", isSuccessful);
		return result;
	}
	
	/**
	 * 通过Bug分类的唯一英文缩写获取Bug分类对象.
	 * @param bugCategorySlug - Bug分类的唯一英文缩写
	 * @return Bug分类对象或空引用
	 */
	private BugCategory getBugCategory(String bugCategorySlug) {
		BugCategory bugCategory = bugCategoryDao.getBugCategoryUsingSlug(bugCategorySlug);
		return bugCategory;
	}
	
	/**
	 * 通过Bug严重性的唯一英文缩写获取Bug严重性对象.
	 * @param bugSeveritySlug - Bug严重性的唯一英文缩写 
	 * @return Bug严重性对象或空引用
	 */
	private BugSeverity getBugSeverity(String bugSeveritySlug) {
		BugSeverity bugSeverity = bugSeverityDao.getBugSeverityUsingSlug(bugSeveritySlug);
		return bugSeverity;
	}
	
	/**
     * 自动注入的BugDao对象.
     */
    @Autowired
    private BugDao bugDao;
    
    /**
     * 自动注入的BugCategoryDao对象.
     */
    @Autowired
    private BugCategoryDao bugCategoryDao;
    
    /**
     * 自动注入的BugStatusDao对象.
     */
    @Autowired
    private BugStatusDao bugStatusDao;
    
    /**
     * 自动注入的BugSeverityDao对象.
     */
    @Autowired
    private BugSeverityDao bugSeverityDao;
}
