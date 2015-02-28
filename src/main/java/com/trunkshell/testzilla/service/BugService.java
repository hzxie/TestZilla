package com.trunkshell.testzilla.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.BugCategoryDao;
import com.trunkshell.testzilla.dao.BugDao;
import com.trunkshell.testzilla.dao.BugSeverityDao;
import com.trunkshell.testzilla.dao.BugStatusDao;
import com.trunkshell.testzilla.model.Bug;
import com.trunkshell.testzilla.model.BugCategory;
import com.trunkshell.testzilla.model.BugSeverity;
import com.trunkshell.testzilla.model.BugStatus;
import com.trunkshell.testzilla.model.Product;
import com.trunkshell.testzilla.model.User;
import com.trunkshell.testzilla.util.HtmlTextFilter;

/**
 * Bug Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class BugService {
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
	 * 获取产品开发者所开发产品的Bug数量.
	 * @param developer - 产品开发者(User对象)
	 * @return 产品开发者所开发产品的Bug数量
	 */
	public long getTotalBugsUsingDeveloper(User developer) {
		long totalBugs = bugDao.getTotalBugsUsingDeveloper(developer);
		return totalBugs;
	}
	
	/**
	 * 获取某个用户所开发产品的Bug列表.
	 * @param developer - 产品开发者(User对象)
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	public List<Bug> getBugsUsingDeveloper(User developer, int offset, int limit) {
		List<Bug> bugs = bugDao.getBugsUsingDeveloper(developer, offset, limit);
		return bugs;
	}
	
	/**
	 * 获取Bug发现者所发现的Bug的数量.
	 * @param hunter - Bug发现者(hunter)对象
	 * @return 某个Bug发现者所发现的Bug的数量
	 */
	public long getTotalBugsUsingHunter(User hunter) {
		long totalBugs = bugDao.getTotalBugsUsingHunter(hunter);
		return totalBugs;
	}

	/**
	 * 获取某个用户所提交的Bug列表.
	 * @param hunter - Bug提交者(User对象)
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	public List<Bug> getBugsUsingHunter(User hunter, int offset, int limit) {
		List<Bug> bugs = bugDao.getBugsUsingHunter(hunter, offset, limit);
		return bugs;
	}
	
	/**
	 * 根据Bug的唯一标识符获取Bug对象.
	 * @param bugId - Bug的唯一标识符
	 * @return 一个Bug对象或空引用
	 */
	public Bug getBugUsingBugId(long bugId) {
		Bug bug = bugDao.getBugUsingBugId(bugId);
		return bug;
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
	 * 获取全部的Bug状态对象
	 * @return 全部的Bug状态对象的列表
	 */
	public List<BugStatus> getBugStatusList() {
		return bugStatusDao.getAllBugStatus();
	}

	/**
	 * 处理开发者用户编辑Bug的请求.
	 * @param bugId - Bug的唯一标识符
	 * @param user - 提出编辑请求的用户
	 * @param bugStatusSlug - Bug状态的唯一标识符
	 * @param bugCategorySlug - Bug分类的唯一标识符
	 * @param bugSeveritySlug - Bug严重性的唯一标识符
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	public HashMap<String, Boolean> editBug(long bugId, User user, 
			String bugStatusSlug, String bugCategorySlug, String bugSeveritySlug) {
		BugCategory bugCategory = getBugCategory(bugCategorySlug);
		BugStatus bugStatus = getBugStatus(bugStatusSlug);
		BugSeverity bugSeverity = getBugSeverity(bugSeveritySlug);
		
		Bug bug = bugDao.getBugUsingBugId(bugId);
		bug.setBugCategory(bugCategory);
		bug.setBugStatus(bugStatus);
		bug.setBugSeverity(bugSeverity);
		HashMap<String, Boolean> result = getEditBugResult(bug, user);
		
		if ( result.get("isSuccessful") ) {
    		boolean isSuccessful = bugDao.updateBug(bug);
    		result.put("isSuccessful", isSuccessful);
    	}
		return result;
	}
	
	/**
	 * 检查数据合法性并编辑Bug对象.
	 * @param bug - 欲编辑的Bug对象
	 * @param user - 提出编辑请求的用户
	 * @return 一个包含若干标志位的HashMap<String, Boolean>对象
	 */
	private HashMap<String, Boolean> getEditBugResult(Bug bug, User user) {
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isAllowedEdit", isAllowedEdit(bug, user));
		result.put("isStatusEmpty", isBugStatusEmpty(bug, bug.getBugStatus()));
		result.put("isCategoryEmpty", isBugCategoryEmpty(bug, bug.getBugCategory()));
		result.put("isSeverityEmpty", isBugSeverityEmpty(bug, bug.getBugSeverity()));
		
		boolean isSuccessful =  result.get("isAllowedEdit")   && !result.get("isStatusEmpty") &&
				               !result.get("isCategoryEmpty") && !result.get("isSeverityEmpty");
		result.put("isSuccessful", isSuccessful);
		return result;
	}
	
	/**
	 * 检查用户是否有权限编辑Bug信息.
	 * 规则: Bug所属产品的开发者和Bug的提出者均有权限编辑Bug信息.
	 * @param bug - 欲编辑的Bug对象
	 * @param user - 提出编辑请求的用户
	 * @return 用户是否有权限编辑Bug信息
	 */
	private boolean isAllowedEdit(Bug bug, User user) {
		if ( bug == null ) {
			return false;
		}
		User developer = bug.getProduct().getDeveloper();
		User hunter = bug.getHunter();
		
		if ( user.equals(developer) || user.equals(hunter) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查Bug状态合法性.
	 * @param bug - 欲编辑的Bug对象
	 * @param bugStatus - Bug状态对象
	 * @return Bug状态的合法性
	 */
	private boolean isBugStatusEmpty(Bug bug, BugStatus bugStatus) {
		return bug == null || bugStatus == null;
	}
	
	/**
	 * 检查Bug分类合法性.
	 * @param bug - 欲编辑的Bug对象
	 * @param bugCategory - Bug分类对象
	 * @return Bug分类合法性
	 */
	private boolean isBugCategoryEmpty(Bug bug, BugCategory bugCategory) {
		return bug == null || bugCategory == null;
	}
	
	/**
	 * 检查Bug严重性合法性.
	 * @param bug - 欲编辑的Bug对象
	 * @param bugSeverity - Bug严重性对象
	 * @return Bug严重性合法性
	 */
	private boolean isBugSeverityEmpty(Bug bug, BugSeverity bugSeverity) {
		return bug == null || bugSeverity == null;
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
		Bug bug = new Bug(product, HtmlTextFilter.filter(version), category, status, severity, 
							createTime, hunter, HtmlTextFilter.filter(title), 
							HtmlTextFilter.filter(description), screenshots);
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
	 * 通过Bug状态的唯一标识符获取Bug状态对象.
	 * @param bugStatusSlug - Bug状态的唯一标识符
	 * @return Bug状态对象或空引用
	 */
	private BugStatus getBugStatus(String bugStatusSlug) {
		BugStatus bugStatus = bugStatusDao.getBugStatusUsingSlug(bugStatusSlug);
		return bugStatus;
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
