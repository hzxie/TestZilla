package com.happystudio.testzilla.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.UserGroup;

/**
 * UserGroup类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class UserGroupDao {
	/**
	 * 通过用户组的唯一标识符获取用户组对象.
	 * @param userGroupId - 用户组的唯一标识符
	 * @return 对应的用户组对象或空引用
	 */
	@Transactional
	public UserGroup getUserGroupUsingId(int userGroupId) {
		Session session = sessionFactory.getCurrentSession();
		UserGroup userGroup = (UserGroup)session.get(UserGroup.class, userGroupId);
		return userGroup;
	}
	
	/**
	 * 通过用户组的唯一英文简写获取用户组对象.
	 * @param userGroupSlug - 用户组的唯一英文简写
	 * @return 对应的用户组对象或空引用
	 */
	@Transactional
	public UserGroup getUserGroupUsingSlug(String userGroupSlug) {
		Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<UserGroup> userGroups = (List<UserGroup>)session
        	.createQuery("FROM UserGroup WHERE userGroupSlug = ?0")
        	.setString("0", userGroupSlug).list();
        
        for (UserGroup userGroup : userGroups ) {
            if ( userGroup.getUserGroupSlug().equals(userGroupSlug) ) {
                return userGroup;
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
