package com.trunkshell.testzilla.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * PointsLog类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class PointsLogDao {
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
