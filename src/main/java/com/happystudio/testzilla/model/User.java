package com.happystudio.testzilla.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 用户的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_users")
public class User implements Serializable {
	/**
	 * User类的默认构造函数. 
	 */
	public User() { }

	/**
	 * User类的构造函数.
	 * @param username - 用户名
	 * @param password - 密码
	 * @param userGroup - 用户组
	 * @param realName - 用户真实姓名或公司名称
	 * @param email - 电子邮件地址
	 * @param country - 用户所在国家
	 * @param province - 用户所在省份
	 * @param city - 用户所在城市
	 * @param phone - 用户的联系电话
	 * @param website - 用户的个人主页
	 * @param isIndividual - 是否为个人用户
	 * @param isEmailVerified - 是否验证了电子邮件地址
	 * @param isInspected - 用户资料是否被审核
	 * @param isApproved - 用户资料是否通过审核
	 */
	public User(String username, String password, UserGroup userGroup, String realName, 
				String email, String country, String province, String city, String phone, 
				String website, boolean isIndividual, boolean isEmailVerified) {
		this.username = username;
		this.password = password;
		this.userGroup = userGroup;
		this.realName = realName;
		this.email = email;
		this.country = country;
		this.province = province;
		this.city = city;
		this.phone = phone;
		this.website = website;
		this.isIndividual = isIndividual;
		this.isEmailVerified = isEmailVerified;
	}
	
	/**
	 * User类的构造函数(用于单元测试).
	 * @param uid - 用户唯一标识符
	 * @param username - 用户名
	 * @param password - 密码
	 * @param userGroup - 用户组
	 * @param realName - 用户真实姓名或公司名称
	 * @param email - 电子邮件地址
	 * @param country - 用户所在国家
	 * @param province - 用户所在省份
	 * @param city - 用户所在城市
	 * @param phone - 用户的联系电话
	 * @param website - 用户的个人主页
	 * @param isIndividual - 是否为个人用户
	 * @param isEmailVerified - 是否验证了电子邮件地址
	 * @param isInspected - 用户资料是否被审核
	 * @param isApproved - 用户资料是否通过审核
	 */
	public User(long uid, String username, String password, UserGroup userGroup, String realName, 
			String email, String country, String province, String city, String phone, String website,
			boolean isIndividual, boolean isEmailVerified) {
		this(username, password, userGroup, realName, email, country, province, city, 
				phone, website, isIndividual, isEmailVerified);
		this.uid = uid;
	}
	
	/**
	 * 获取用户唯一标识符.
	 * @return 用户唯一标识符
	 */
	public long getUid() {
		return uid;
	}
	
	/**
	 * 设置用户唯一标识符.
	 * @param uid - 用户唯一标识符
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	/**
	 * 获取用户名.
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名.
	 * @param Username - 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 获取密码(已采用MD5加密).
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码.
	 * @param password - 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取用户组.
	 * @return 用户组对象
	 */
	public UserGroup getUserGroup() {
		return userGroup;
	}

	/**
	 * 设置用户组.
	 * @param userGroup - 用户组对象
	 */
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * 获取用户的真实姓名或公司名称.
	 * @return 用户的真实姓名或公司名称
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置用户的真实姓名或公司名称.
	 * @param realName - 用户的真实姓名或公司名称
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取电子邮件地址.
	 * @return 电子邮件地址
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置电子邮件地址
	 * @param email - 电子邮件地址
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取用户所在国家.
	 * @return 用户所在国家
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 设置用户所在国家.
	 * @param country - 用户所在国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 获取用户所在省份.
	 * @return 用户所在省份
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置用户所在省份.
	 * @param province - 用户所在省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 获取用户所在城市.
	 * @return 用户所在城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置用户所在城市.
	 * @param city - 用户所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 获取用户的联系电话.
	 * @return 用户的联系电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置用户的联系电话.
	 * @param phone - 用户的联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取用户的个人主页.
	 * @return 用户的个人主页
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * 设置用户的个人主页.
	 * @param website - 用户的个人主页
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * 获取是否为个人用户.
	 * @return 是否为个人用户
	 */
	public boolean isIndividual() {
		return isIndividual;
	}

	/**
	 * 设置是否为个人用户.
	 * @param isIndividual - 是否为个人用户
	 */
	public void setIndividual(boolean isIndividual) {
		this.isIndividual = isIndividual;
	}

	/**
	 * 获取是否验证了电子邮件地址.
	 * @return 是否验证了电子邮件地址
	 */
	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	/**
	 * 设置是否验证了电子邮件地址.
	 * @param isEmailVerified - 是否验证了电子邮件地址
	 */
	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	/**
	 * 获取产品列表(用于1-N关联).
	 * @return 产品列表
	 */
	public List<Product> getProducts() {
		return developedProducts;
	}
	
	/**
	 * 设置产品列表.
	 * @param products - 产品列表
	 */
	public void setProducts(List<Product> products) {
		this.developedProducts = products;
	}
	
	/**
	 * 获取Bug列表(用于1-N关联).
	 * @return 产品列表
	 */
	public List<Bug> getBugs() {
		return bugs;
	}
	
	/**
	 * 设置产品列表.
	 * @param products - 产品列表
	 */
	public void setBugs(List<Bug> bugs) {
		this.bugs = bugs;
	}

	/**
	 * 获取积分记录列表(用于1-N关联).
	 * @return 积分记录列表
	 */
	public List<PointsLog> getPointsLogs() {
		return pointsLogs;
	}

	/**
	 * 设置积分记录列表.
	 * @param pointsLogs - 积分记录列表
	 */
	public void setPointsLogs(List<PointsLog> pointsLogs) {
		this.pointsLogs = pointsLogs;
	}

	/**
	 * 获取用户所参与测试产品列表.
	 * @return 用户所参与测试产品列表
	 */
	public List<Product> getTestedProducts() {
		return testedProducts;
	}

	/**
	 * 设置用户所参与测试产品列表.
	 * @param testedProducts - 用户所参与测试产品列表
	 */
	public void setTestedProducts(List<Product> testedProducts) {
		this.testedProducts = testedProducts;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("User: [Uid=%s, Username=%s, UserGroup={%s}, RealName=%s, Email=%s]", 
				new Object[] { uid, username, userGroup, realName, email });
	}
	
	/**
	 * 用户的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "uid")
	private long uid;
	
	/**
	 * 用户名.
	 */
	@Column(name = "username")
	private String username;

	/**
	 * 密码(已采用MD5加密).
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 用户组对象.
	 */
	@ManyToOne(targetEntity = UserGroup.class)
	@JoinColumn(name = "user_group_id")
	private UserGroup userGroup;

	/**
	 * 用户的真实姓名或公司名称.
	 */
	@Column(name = "real_name")
	private String realName;
	
	/**
	 * 电子邮件地址.
	 */
	@Column(name = "email")
	private String email;
	
	/**
	 * 用户所在国家.
	 */
	@Column(name = "country")
	private String country;
	
	/**
	 * 用户所在省份.
	 */
	@Column(name = "province")
	private String province;
	
	/**
	 * 用户所在城市.
	 */
	@Column(name = "city")
	private String city;
	
	/**
	 * 用户的联系电话.
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 用户的个人主页.
	 */
	@Column(name = "website")
	private String website;
	
	/**
	 * 是否为个人用户.
	 */
	@Column(name = "is_individual")
	private boolean isIndividual;
	
	/**
	 * 是否验证了电子邮件地址.
	 */
	@Column(name = "is_email_verified")
	private boolean isEmailVerified;
	
	/**
	 * 用户所开发的产品列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Product.class, 
				fetch = FetchType.LAZY, mappedBy = "developer")
	private List<Product> developedProducts = new ArrayList<Product>();
	
	/**
	 * Bug列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = Bug.class, 
				fetch = FetchType.LAZY, mappedBy = "hunter")
	private List<Bug> bugs = new ArrayList<Bug>();
	
	/**
	 * 积分记录列表(以便1-N关联).
	 */
	@OneToMany(targetEntity = PointsLog.class, 
				fetch = FetchType.LAZY, mappedBy = "user")
	private List<PointsLog> pointsLogs = new ArrayList<PointsLog>();
	
	/**
	 * 用户所参与测试产品列表(以便N-N关联).
	 */
	@ManyToMany(mappedBy = "testers")
	private List<Product> testedProducts = new ArrayList<Product>();
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -8408754534627167252L;
}
