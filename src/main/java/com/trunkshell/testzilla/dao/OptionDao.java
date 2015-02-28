package com.trunkshell.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.model.Option;

/**
 * Option类的Data Access Object.
 * @author Zhou Yihao
 */
@Repository
public class OptionDao {
	/**
	 * 根据选项的Key获取选项的值.
	 * @param optionKey - 选项的Key
	 * @return 指定系统设置的Key-Value对
	 */
	@Transactional
	public Option getOption(String optionKey) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Option> options = (List<Option>)session.createQuery("FROM Option WHERE optionKey = ?0")
													.setString("0", optionKey)
													.list();
		for (Option option : options) {
			if (option.getOptionKey().equals(optionKey)) {
				return option;
			}
		}
		return null;
	}
	
	/**
	 * 更新系统设置信息.
	 * @param option - 待更新的Option对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean updateOption(Option option) {
		Session session = sessionFactory.getCurrentSession();
		if ( session.get(Option.class, option.getOptionId()) == null ) {
			return false;
		}
		session.merge(option);
		session.flush();
		return true;
	}
	
	/**
	 * 自动注入的SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;
}
