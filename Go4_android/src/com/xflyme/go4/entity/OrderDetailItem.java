package com.xflyme.go4.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetailItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Id;
	private int siteId;
	private String siteName;
	private int companyId;
	private int communityId;
	private String communityName;
	private int housesId;
	private String housesName;
	private int type;
	private int state;
	private String tel;
	private String orderDate;
	private ArrayList<String> amOrderTime;
	private ArrayList<String> pmOrderTime;
	private double cost;
	private double deposit;
	private String orderTime;
	
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public int getHousesId() {
		return housesId;
	}
	public void setHousesId(int housesId) {
		this.housesId = housesId;
	}
	public String getHousesName() {
		return housesName;
	}
	public void setHousesName(String housesName) {
		this.housesName = housesName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public ArrayList<String> getAmOrderTime() {
		return amOrderTime;
	}
	public void setAmOrderTime(ArrayList<String> amOrderTime) {
		this.amOrderTime = amOrderTime;
	}
	public ArrayList<String> getPmOrderTime() {
		return pmOrderTime;
	}
	public void setPmOrderTime(ArrayList<String> pmOrderTime) {
		this.pmOrderTime = pmOrderTime;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}
