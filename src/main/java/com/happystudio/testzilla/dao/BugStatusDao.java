package com.happystudio.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.BugStatus;

/**
 * BugStatus类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class BugStatusDao {
	/**
	 * 获取全部的Bug状态对象.
	 * @return 全部Bug状态对象的列表
	 */
	@Transactional
	public List<BugStatus> getAllBugStatus() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugStatus> bugStatusList = (List<BugStatus>)session
			.createQuery("FROM BugStatus").list();
		return bugStatusList;
	}
	
	/**
	 * 通过Bug状态的唯一标识符获取Bug状态对象.
	 * @param bugStatusId - Bug状态的唯一标识符
	 * @return 对应的Bug状态对象或空引用
	 */
	@Transactional
	public BugStatus getBugStatusUsingId(int bugStatusId) {
		Session session = sessionFactory.getCurrentSession();
		BugStatus bugStatus = (BugStatus)session.get(BugStatus.class, bugStatusId);
		return bugStatus;
	}
	
	/**
	 * 通过Bug状态的唯一英文简写获取Bug状态对象.
	 * @param bugStatusSlug - Bug状态的唯一英文简写
	 * @return 对应的Bug状态对象或空引用
	 */
	@Transactional
	public BugStatus getBugStatusUsingSlug(String bugStatusSlug) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BugStatus> bugStatusList = (List<BugStatus>)session
			.createQuery("FROM BugStatus WHERE bugStatusSlug = ?0")
			.setString("0", bugStatusSlug).list();
		
		for (BugStatus bugStatus : bugStatusList ) {
			if ( bugStatus.getBugStatusSlug().equals(bugStatusSlug) ) {
				return bugStatus;
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
