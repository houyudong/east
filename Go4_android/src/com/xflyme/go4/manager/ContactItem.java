package com.xflyme.go4.manager;

/**
 * @Description:
 * @author:刘祥飞
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @version 2014-11-28
 */
public class ContactItem {
	private int typeid;
	private String typename;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}