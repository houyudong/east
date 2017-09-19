package com.xflyme.go4.entity;


import java.util.ArrayList;

import com.xflyme.go4.entity.BaseEntity;

public class BillEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<BillItem> data;

	public ArrayList<BillItem> getData() {
		return data;
	}

	public void setData(ArrayList<BillItem> data) {
		this.data = data;
	}

	public class BillItem {
		private int id;
		private String housesUnitNo;
		private String communityName;
		private String endDate;
		private int type;
		private int maintanceMonth;
		private double maintanceFund;
		private double sinkingFund;
		private double tax;
		private double taxRate;
		private double totalFund;
		private String createTime;
		private String createUser;
		private String attachmentUrl;
		public String getAttachmentUrl() {
			return attachmentUrl;
		}
		public void setAttachmentUrl(String attachmentUrl) {
			this.attachmentUrl = attachmentUrl;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getHousesUnitNo() {
			return housesUnitNo;
		}
		public void setHousesUnitNo(String housesUnitNo) {
			this.housesUnitNo = housesUnitNo;
		}
		public String getCommunityName() {
			return communityName;
		}
		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getMaintanceMonth() {
			return maintanceMonth;
		}
		public void setMaintanceMonth(int maintanceMonth) {
			this.maintanceMonth = maintanceMonth;
		}
		public double getMaintanceFund() {
			return maintanceFund;
		}
		public void setMaintanceFund(double maintanceFund) {
			this.maintanceFund = maintanceFund;
		}
		public double getSinkingFund() {
			return sinkingFund;
		}
		public void setSinkingFund(double sinkingFund) {
			this.sinkingFund = sinkingFund;
		}
		public double getTax() {
			return tax;
		}
		public void setTax(double tax) {
			this.tax = tax;
		}
		public double getTaxRate() {
			return taxRate;
		}
		public void setTaxRate(double taxRate) {
			this.taxRate = taxRate;
		}
		public double getTotalFund() {
			return totalFund;
		}
		public void setTotalFund(double totalFund) {
			this.totalFund = totalFund;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getCreateUser() {
			return createUser;
		}
		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}
		
	}


}
