package com.xflyme.go4.entity;

import java.io.Serializable;

public class UserInfoEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserInfoItem data;

	public UserInfoItem getData() {
		return data;
	}

	public void setData(UserInfoItem data) {
		this.data = data;
	}
	
}
