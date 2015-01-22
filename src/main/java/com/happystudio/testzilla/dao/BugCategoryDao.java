package com.happystudio.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.BugCategory;

/**
 * BugCategory类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class BugCategoryDao {
	/**
	 * 获取全部的Bug分类对象.
	 * @return 全部Bug分类对象的列表
	 */
	@Transactional
	public List<BugCategory> getAllBugCategory() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugCategory> bugCategories = (List<BugCategory>)session
			.createQuery("FROM BugCategory").list();
		return bugCategories;
	}
	
	/**
	 * 通过Bug分类的唯一标识符获取Bug分类对象.
	 * @param bugCategoryId - Bug分类的唯一标识符
	 * @return 对应的Bug分类对象或空引用
	 */
	@Transactional
	public BugCategory getBugCategoryUsingId(int bugCategoryId) {
		Session session = sessionFactory.getCurrentSession();
		BugCategory bugCategory = (BugCategory)session.get(BugCategory.class, bugCategoryId);
		return bugCategory;
	}
	
	/**
	 * 通过Bug分类的唯一英文简写获取Bug分类对象.
	 * @param bugCategorySlug - Bug分类的唯一英文简写
	 * @return 对应的Bug分类对象或空引用
	 */
	@Transactional
	public BugCategory getBugCategoryUsingSlug(String bugCategorySlug) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugCategory> bugCategories = (List<BugCategory>)session
			.createQuery("FROM BugCategory WHERE bugCategorySlug = ?0")
			.setString("0", bugCategorySlug).list();
		
		for (BugCategory bugCategory : bugCategories ) {
			if ( bugCategory.getBugCategorySlug().equals(bugCategorySlug) ) {
				return bugCategory;
			}
		}
		return null;
	}
	
	/**
     * 自动注入的SessionFactory.
     */
    @Autowired
    private SessionFactory sessionFactory;
}
