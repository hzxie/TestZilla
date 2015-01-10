package com.happystudio.testzilla.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.MailVerification;

/**
 * MailVerification类的Data Access Object.
 * @author Xie Haozhe
 */
@Repository
public class MailVerificationDao {
	/**
	 * 通过电子邮件地址获取MailVerification对象.
	 * @param email - 待验证的电子邮件地址
	 * @return 匹配该电子邮件地址的MailVerification对象
	 */
	@Transactional
	public MailVerification getMailVerification(String email) {
		Session session = sessionFactory.getCurrentSession();
		MailVerification verification = (MailVerification)session.get(MailVerification.class, email);
        return verification;
	}
	
	/**
	 * 创建MailVerification验证记录.
	 * @param mailVerification - MailVerification对象
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean createMailVerification(MailVerification mailVerification) {
		Session session = sessionFactory.getCurrentSession();
        String email = mailVerification.getEmail();
        if ( email.isEmpty() ) {
            return false;
        }
        
        session.save(mailVerification);
        session.flush();
        return true;
	}
	
	/**
	 * 验证完成后, 或创建新的验证记录时用于删除之前创建的验证记录.
	 * @param email - 待验证的电子邮件地址
	 * @return 操作是否成功完成
	 */
	@Transactional
	public boolean deleteMailVerification(String email) {
		Session session = sessionFactory.getCurrentSession();
		MailVerification verification = (MailVerification)session.get(MailVerification.class, email);
        
        if ( verification == null ) {
            return false;
        }
        session.delete(verification);
        return true;
	}
	
	/**
     * 自动注入的SessionFactory.
     */
    @Autowired
    private SessionFactory sessionFactory;
}
