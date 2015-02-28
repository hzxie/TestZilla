package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.BugSeverity;

/**
 * BugSeverity类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class BugSeverityDao {
	/**
	 * 获取全部的Bug严重性对象.
	 * @return 全部Bug严重性对象的列表
	 */
	@Transactional
	public List<BugSeverity> getAllBugSeverity() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugSeverity> bugSeverityList = (List<BugSeverity>)session
			.createQuery("FROM BugSeverity").list();
		return bugSeverityList;
	}
	
	/**
	 * 通过Bug严重性的唯一标识符获取Bug严重性对象.
	 * @param bugSeverityId - Bug严重性的唯一标识符
	 * @return 对应的Bug严重性对象或空引用
	 */
	@Transactional
	public BugSeverity getBugSeverityUsingId(int bugSeverityId) {
		Session session = sessionFactory.getCurrentSession();
		BugSeverity bugSeverity = (BugSeverity)session.get(BugSeverity.class, bugSeverityId);
		return bugSeverity;
	}
	
	/**
	 * 通过Bug严重性的唯一英文简写获取Bug严重性对象.
	 * @param bugSeveritySlug - Bug严重性的唯一英文简写
	 * @return 对应的Bug严重性对象或空引用
	 */
	@Transactional
	public BugSeverity getBugSeverityUsingSlug(String bugSeveritySlug) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugSeverity> bugSeverityList = (List<BugSeverity>)session
			.createQuery("FROM BugSeverity WHERE bugSeveritySlug = ?0")
			.setString("0", bugSeveritySlug).list();
		
		for (BugSeverity bugSeverity : bugSeverityList ) {
			if ( bugSeverity.getBugSeveritySlug().equals(bugSeveritySlug) ) {
				return bugSeverity;
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
