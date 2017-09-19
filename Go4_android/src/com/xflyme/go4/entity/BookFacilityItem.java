package com.xflyme.go4.entity;

import java.io.Serializable;

public class BookFacilityItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int type;
	private int communityId;
	private String community;
	private String name;
	private String longitude;
	private String latitude;
	private String descs;
	private double deposit;
	private double cost;
	private String rangePm;
	private String rangeAm;
	private int companyId;
	private String typeName;
	private String communityName;
	private int housesId;
	private String housesName;
	private int siteId;
	private String siteName;
	private String orderDate;
	private String orderTime;

	private String orderTimeDesc;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getRangePm() {
		return rangePm;
	}

	public void setRangePm(String rangePm) {
		this.rangePm = rangePm;
	}

	public String getRangeAm() {
		return rangeAm;
	}

	public void setRangeAm(String rangeAm) {
		this.rangeAm = rangeAm;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderTimeDesc() {
		return orderTimeDesc;
	}

	public void setOrderTimeDesc(String orderTimeDesc) {
		this.orderTimeDesc = orderTimeDesc;
	}
}
