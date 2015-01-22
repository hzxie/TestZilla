package com.happystudio.testzilla.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统设置的Model.
 * @author Xie Haozhe
 */
@Entity
@Table(name = "tz_options")
public class Option implements Serializable {
	/**
	 * Option的默认构造函数. 
	 */
	public Option() {}
	
	/**
	 * Option的构造函数.
	 * @param optionId - 系统设置项的唯一标识符
	 * @param optionKey - 系统设置项的键
	 * @param optionValue - 系统设置项的值
	 */
	public Option(int optionId, String optionKey, String optionValue) {
		this.optionId = optionId;
		this.optionKey = optionKey;
		this.optionValue = optionValue;
	}
	
	/**
	 * 获取系统设置项的唯一标识符.
	 * @return 系统设置项的唯一标识符
	 */
	public int getOptionId() {
		return optionId;
	}

	/**
	 * 设置系统设置项的唯一标识符.
	 * @param optionId - 系统设置项的唯一标识符
	 */
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	/**
	 * 获取系统设置项的键.
	 * @return 系统设置项的键.
	 */
	public String getOptionKey() {
		return optionKey;
	}

	/**
	 * 设置系统设置项的键.
	 * @param optionKey - 系统设置项的键
	 */
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	/**
	 * 获取系统设置项的值.
	 * @return 系统设置项的值
	 */
	public String getOptionValue() {
		return optionValue;
	}

	/**
	 * 设置系统设置项的值.
	 * @param optionValue - 系统设置项的值
	 */
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof Option) ) {
			return false;
		}
		return this.optionId == ((Option)o).optionId; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Option [ID=%d, Key=%s, Value=%s]",
				new Object[] { optionId, optionKey, optionValue });
	}
	
	/**
	 * 系统设置项的唯一标识符.
	 */
	@Id
	@GeneratedValue
	@Column(name = "option_id")
	private int optionId;
	
	/**
	 * 系统设置项的键.
	 */
	@Column(name = "option_key")
	private String optionKey;
	
	/**
	 * 系统设置项的值.
	 */
	@Column(name = "option_value")
	private String optionValue;
	
	/**
	 * 唯一的序列化标识符.
	 */
	private static final long serialVersionUID = -5886423512621279809L;

}
