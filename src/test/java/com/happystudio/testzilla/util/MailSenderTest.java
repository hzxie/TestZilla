package com.happystudio.testzilla.util;

import java.util.HashMap;
import java.util.Map;

// import org.junit.Test;
// import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 电子邮件发送测试类.
 * 注意: 仅供开发调试使用.
 * @author Xie Haozhe
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({"classpath:test-spring-context.xml"})
public class MailSenderTest {
	@Autowired
	private MailSender mailSender;
	
	/**
	 * 测试用例: 测试发送电子邮件.
	 * 注意: 测试前, 请修改配置文件中mailgun.org的Confidential.
	 */
	// @Test
	public void testSendMail() {
		String templatePath = "/verifyEmail.vm";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", "zjhzxhz");
		model.put("email", "zjhzxhz@gmail.com");
		model.put("code", "hello");
		
		String subject = "Activate Your Account";
		String body = mailSender.getMailContent(templatePath, model);
		
		mailSender.sendMail("zjhzxhz@gmail.com", subject, body);
	}
}
