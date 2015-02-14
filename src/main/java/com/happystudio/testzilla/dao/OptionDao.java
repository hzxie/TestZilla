package com.happystudio.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.Option;

/**
 * Option类的Data Access Object.
 * @author Zhou Yihao
 */
@Repository
public class OptionDao {
	/**
	 * 获取某项系统设置
	 * @return 指定系统设置的Key-Value对
	 */
	@Transactional
	public Option getOption(String Key) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>)session.createQuery("FROM Option WHERE optionKey = ?0")
													.setString("0", Key)
													.list();
		for (Option option : options) {
			if (option.getOptionKey().equals(Key)) {
				return option;
			}
		}
		return null;
	}
	
	/**
	 * 更新系统设置信息
	 * @param option - 待更新的Option对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean updateOption(Option option) {
		Session session = sessionFactory.getCurrentSession();
		if ( session.get(Option.class, option.getOptionId()) == null ) {	//不存在的设置无法更新
			return false;
		}
		session.merge(option);
		session.flush();
		return true;
	}
	
	/**
	 * 创建新的系统设置Option
	 * @param option - 待创建的Option对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean createOption(Option option) {
		if (option == null || option.getOptionKey() == null || option.getOptionValue() == null) {
			return false;
		}
		Session session = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>)session.createQuery("FROM Option WHERE optionKey = ?0")
													.setString("0", option.getOptionKey())
													.list();
		if ( options.size() > 0 ) {	//已存在的 系统设置项无法创建
			return false;
		}
		session.save(option);
		session.flush();
		return true;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
