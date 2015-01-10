package com.happystudio.testzilla.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.happystudio.testzilla.model.MailVerification;


/**
 * MailVerificationDao测试类.
 * @author Xie Haozhe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class MailVerificationDaoTest {
	/**
	 * 测试用例: 测试getMailVerification()方法
	 * 测试数据: 使用有效的验证凭据
	 * 预期结果: 返回预期的MailVerification对象
	 */
	@Test
	public void testGetMailVerificationExists() {
		MailVerification verification = mailVerificationDao.getMailVerification("zjhzxhz@qq.com");
		Assert.assertNotNull(verification);
		
		String email = verification.getEmail();
		Assert.assertEquals("zjhzxhz@qq.com", email);
	}
	
	/**
	 * 测试用例: 测试getMailVerification()方法
	 * 测试数据: 使用无效的验证凭据
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetMailVerificationNotExists() {
		MailVerification verification = mailVerificationDao.getMailVerification("zjhzxhz@testzilla.org");
		Assert.assertNull(verification);
	}
	
	/**
	 * 测试用例: 测试createMailVerification()方法
	 * 测试数据: 合法的数据集, 且数据表中不存在该电子邮件地址
	 * 预期结果: 返回true, 表示操作成功完成
	 */
	@Test
	public void testCreateMailVerificationNormal() {
		MailVerification verification = new MailVerification("zjhzxhz@testzilla.org", "code");
		Assert.assertTrue(mailVerificationDao.createMailVerification(verification));
		
		verification = mailVerificationDao.getMailVerification("zjhzxhz@testzilla.org");
		Assert.assertNotNull(verification);
		
		String email = verification.getEmail();
        Assert.assertEquals("zjhzxhz@testzilla.org", email);
	}
	
	/**
	 * 测试用例: 测试createMailVerification()方法
	 * 测试数据: 合法的数据集, 但数据表中存在该电子邮件地址
	 * 预期结果: 抛出ConstraintViolationException异常
	 */
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void testCreateMailVerificationUsingExistingEmail() {
		MailVerification verification = new MailVerification("zjhzxhz@qq.com", "code");
		Assert.assertFalse(mailVerificationDao.createMailVerification(verification));
	}
	
	/**
	 * 测试用例: 测试deleteMailVerification()方法
	 * 测试数据: 存在的电子邮件地址
	 * 预期结果: 返回true, 表示操作成功完成.
	 */
	@Test
	public void testDeleteMailVerificationExists() {
		MailVerification verification = mailVerificationDao.getMailVerification("zjhzxhz@qq.com");
        Assert.assertNotNull(verification);
        
        Assert.assertTrue(mailVerificationDao.deleteMailVerification("zjhzxhz@qq.com"));
        
        verification = mailVerificationDao.getMailVerification("zjhzxhz@qq.com");
        Assert.assertNull(verification);
	}
	
	/**
	 * 测试用例: 测试deleteMailVerification()方法
	 * 测试数据: 不存在的电子邮件地址
	 * 预期结果: 返回false, 表示操作未成功完成.
	 */
	@Test
	public void testDeleteMailVerificationNotExists() {
		MailVerification verification = mailVerificationDao.getMailVerification("zjhzxhz@testzilla.org");
        Assert.assertNull(verification);
        
        Assert.assertFalse(mailVerificationDao.deleteMailVerification("zjhzxhz@testzilla.org"));
	}
	
	/**
	 * 待测试的UserDAO对象.
	 */
	@Autowired
	private MailVerificationDao mailVerificationDao;
}
