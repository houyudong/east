package com.xflyme.go4.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfoItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * token失效时间
	 */
	private long tokenExpTime;
	
	/**
	 * 用户id
	 */
	private int userid;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 物业公司id
	 */
	private int companyId;
	/**
	 * 物业公司名称
	 */
	private String companyName;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 头像
	 */
	private String pic;
	/**
	 * 头像地址
	 */
	private String picUrl;
	/**
	 * 身份证id
	 */
	private String idNo;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 电话
	 */
	private String telphone;
	/**
	 * 当前小区id
	 */
	private int curCommunityId;
	/**
	 * 当前小区名称
	 */
	private String curCommunityName;
	/**
	 * 当前房屋id
	 */
	private int curHousesId;
	/**
	 * 当前房屋门牌号
	 */
	private String curHousesUnitNo;
	/**
	 * 当前关系
	 */
	private Integer curRelationType;
	
	private ArrayList<Relation> relations;

	public ArrayList<Relation> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<Relation> relations) {
		this.relations = relations;
	}

	public long getTokenExpTime() {
		return tokenExpTime;
	}

	public void setTokenExpTime(long tokenExpTime) {
		this.tokenExpTime = tokenExpTime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public int getCurCommunityId() {
		return curCommunityId;
	}

	public void setCurCommunityId(int curCommunityId) {
		this.curCommunityId = curCommunityId;
	}

	public String getCurCommunityName() {
		return curCommunityName;
	}

	public void setCurCommunityName(String curCommunityName) {
		this.curCommunityName = curCommunityName;
	}

	public int getCurHousesId() {
		return curHousesId;
	}

	public void setCurHousesId(int curHousesId) {
		this.curHousesId = curHousesId;
	}

	public String getCurHousesUnitNo() {
		return curHousesUnitNo;
	}

	public void setCurHousesUnitNo(String curHousesUnitNo) {
		this.curHousesUnitNo = curHousesUnitNo;
	}

	public Integer getCurRelationType() {
		return curRelationType;
	}

	public void setCurRelationType(Integer curRelationType) {
		this.curRelationType = curRelationType;
	}

}
