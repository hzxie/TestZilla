package com.happystudio.testzilla.util;

import java.util.Properties;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sun.mail.smtp.SMTPTransport;

/**
 * 邮件发送服务.
 * @author Xie Haozhe
 */
@Component
public class MailSender {
	/**
	 * 发送电子邮件到指定收件人.
	 * @param recipient - 收件人的电子邮件地址
	 * @param subject - 邮件的主题
	 * @param templatePath - 邮件的模板
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void sendMail(String recipient, String subject, String text) 
			throws AddressException, MessagingException {
		Properties props = System.getProperties();
        props.put("mail.smtps.host", smtpHost);
        props.put("mail.smtps.auth", "true");
        
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("noreply@testzilla.org"));
        msg.setRecipients(Message.RecipientType.TO, 
        					InternetAddress.parse(recipient, false));
        msg.setSubject(subject);
        msg.setText(text);
        msg.setSentDate(new Date());
        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
        t.connect(smtpHost, smtpUsername, smtpPassword);
        t.sendMessage(msg, msg.getAllRecipients());
        
        logger.info(String.format("An Email{Recipient: %s, Subject: %s} has been sent with server response %s.", 
        			new Object[] {recipient, subject, t.getLastServerResponse()}));
        t.close();
	}
	
	/**
	 * 设置Smtp服务器地址.
	 * @param smtpHost - Smtp服务器地址
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	
	/**
	 * 设置Smtp服务器登录用户名.
	 * @param smtpUsername - Smtp服务器登录用户名
	 */
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	/**
	 * 设置Smtp服务器登录密码.
	 * @param smtpPassword - Smtp服务器登录密码
	 */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	/**
	 * Smtp服务器地址.
	 */
	private String smtpHost;
	
	/**
	 * Smtp服务器登录用户名.
	 */
	private String smtpUsername;
	
	/**
	 * Smtp服务器登录密码.
	 */
	private String smtpPassword;
	
	/**
     * 日志记录器.
     */
    private Logger logger = LogManager.getLogger(MailSender.class);
}
