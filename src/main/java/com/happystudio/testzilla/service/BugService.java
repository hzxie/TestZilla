package com.happystudio.testzilla.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * 验证数据并创建Bug.
	 * @param product - 产品对象
	 * @param version - Bug所在的软件版本
	 * @param bugCategorySlug - Bug分类的唯一英文缩写
	 * @param bugStatusSlug - Bug状态的唯一英文缩写
	 * @param bugSeveritySlug - Bug严重程度的唯一英文缩写 
	 * @param hunter - Bug提交者的用户对象
	 * @param title - Bug的标题
	 * @param description - Bug的详细描述
	 * @return 一个包含若干标志位的HashMap对象
	 */
	public HashMap<String, Boolean> createBug(Product product, String version, String bugCategorySlug, 
			String bugStatusSlug, String bugSeveritySlug, User hunter, String title, String description) {
		return null;
	}
}
