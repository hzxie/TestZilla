package com.happystudio.testzilla.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.Bug;
import com.happystudio.testzilla.model.Product;
import com.happystudio.testzilla.model.User;

/**
 * Bug类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class BugDao {
	/**
	 * 获取某个产品下Bug的数量.
	 * @param product - 产品(Product)对象
	 * @return 某个产品下Bug的数量
	 */
	@Transactional
	public long getTotalBugsUsingProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM Bug WHERE product = ?0")
								.setParameter("0", product);
				
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取某个产品的Bug列表.
	 * @param product - 产品(Product)对象
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	@Transactional
	public List<Bug> getBugsUsingProduct(Product product, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Bug> bugs = (List<Bug>)session.createQuery("FROM Bug WHERE product = ?0 ORDER BY bugId DESC")
											.setParameter("0", product)
											.setFirstResult(offset)
											.setMaxResults(limit).list();
		return bugs;
	}
	
	/**
	 * 获取产品开发者所开发产品的Bug数量.
	 * @param developer - 产品开发者(User对象)
	 * @return 产品开发者所开发产品的Bug数量
	 */
	@Transactional
	public long getTotalBugsUsingDeveloper(User developer) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM Bug WHERE product.developer = ?0")
								.setParameter("0", developer);
				
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取某个用户所开发产品的Bug列表.
	 * @param developer - 产品开发者(User对象)
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	@Transactional
	public List<Bug> getBugsUsingDeveloper(User developer, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Bug> bugs = (List<Bug>)session.createQuery("FROM Bug WHERE product.developer = ?0 ORDER BY bugId DESC")
											.setParameter("0", developer)
											.setFirstResult(offset)
											.setMaxResults(limit).list();
		return bugs;
	}
	
	/**
	 * 获取Bug发现者所发现的Bug的数量.
	 * @param hunter - Bug发现者(hunter)对象
	 * @return 某个Bug发现者所发现的Bug的数量
	 */
	@Transactional
	public long getTotalBugsUsingHunter(User hunter) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM Bug WHERE hunter = ?0")
								.setParameter("0", hunter);
				
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 获取某个用户所提交的Bug列表.
	 * @param hunter - Bug提交者(User对象)
	 * @param offset - 筛选起始项的索引(Index)
	 * @param limit - 筛选结果最大数量
	 * @return 符合条件的Bug列表
	 */
	@Transactional
	public List<Bug> getBugsUsingHunter(User hunter, int offset, int limit) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Bug> bugs = (List<Bug>)session.createQuery("FROM Bug WHERE hunter = ?0 ORDER BY bugId DESC")
											.setParameter("0", hunter)
											.setFirstResult(offset)
											.setMaxResults(limit).list();
		return bugs;
	}
	
	/**
	 * 根据Bug的唯一标识符获取Bug对象.
	 * @param bugId - Bug的唯一标识符
	 * @return 一个Bug对象或空引用
	 */
	@Transactional
	public Bug getBugUsingBugId(long bugId) {
		Session session = sessionFactory.getCurrentSession();
		Bug bug = (Bug)session.get(Bug.class, bugId);
		return bug;
	}
	
	/**
	 * 创建新的Bug
	 * @param bug - 待创建的Bug对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean createBug(Bug bug) {
		Session session = sessionFactory.getCurrentSession();
		session.save(bug);
		session.flush();
		return true;
	}
	
	/**
	 * 更新Bug信息.
	 * @param bug - 待更新的Bug对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean updateBug(Bug bug) {
		Session session = sessionFactory.getCurrentSession();
		if ( session.get(Bug.class, bug.getBugId()) == null ) {
			return false;
		}
		session.merge(bug);
		session.flush();
		return true;
	}
	
	/**
	 * 通过Bug的唯一标识符删除Bug.
	 * @param bugId - Bug的唯一标识符
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean deleteBug(long bugId) {
		Session session = sessionFactory.getCurrentSession();
		Bug bug = (Bug)session.get(Bug.class, bugId);
		
		if ( bug == null ) {
			return false;
		}
		session.delete(bug);
		return true;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
