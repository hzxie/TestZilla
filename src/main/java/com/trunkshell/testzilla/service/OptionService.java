package com.trunkshell.testzilla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trunkshell.testzilla.dao.OptionDao;
import com.trunkshell.testzilla.model.Option;

/**
 * Option Service. 为Controller提供服务.
 * @author Xie Haozhe
 */
@Service
@Transactional
public class OptionService {
	/**
	 * 获取系统选项的值(Value).
	 * @param optionKey - 系统选项的键(Key)
	 * @return 系统选项的值(Value)
	 */
	public String getOption(String optionKey) {
		Option option = optionDao.getOption(optionKey);
		
		if ( option == null ) {
			return null;
		}
		return option.getOptionValue();
	}
	
	/**
     * 自动注入的OptionDao对象.
     */
    @Autowired
    private OptionDao optionDao;
}
