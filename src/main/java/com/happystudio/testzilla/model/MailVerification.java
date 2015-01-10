package com.happystudio.testzilla.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电子邮件验证的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_mail_verification")
public class MailVerification implements Serializable {
	/**
	 * MailVerification的默认构造函数.
	 */
	public MailVerification() { }
	
	
	/**
	 * MailVerification的构造函数.
	 * @param email - 待验证的电子邮件地址
	 * @param code - 随机生成的验证码
	 */
	public MailVerification(String email, String code) { 
		this.email = email;
		this.code = code;
	}
	
	/**
	 * 获取待验证的电子邮件地址.
	 * @return 待验证的电子邮件地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置待验证的电子邮件地址.
	 * @param email - 待验证的电子邮件地址
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取随机生成的验证码.
	 * @return 随机生成的验证码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置随机生成的验证码.
	 * @param code - 随机生成的验证码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 待验证的电子邮件地址.
	 */
	@Id
	@Column(name = "email")
	private String email;
	
	/**
	 * 随机生成的验证码.
	 */
	@Column(name = "code")
	private String code;
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -977158924167965036L;
}
