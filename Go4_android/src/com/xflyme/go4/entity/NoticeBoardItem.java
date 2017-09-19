package com.xflyme.go4.entity;

import android.R.integer;

public class NoticeBoardItem {

	private int id;
	private int companyId;
	private int communityId;
	private String title;
	private String content;
	private int state;
	private String startTime;
	private String endTime;
	private int clickCnt;
	private int type;
	private String contentType;
	private String linkUrl;
	private String targetSys;
	private String createTime;
	private String createUser;
	private String remarks;
	private String provider;
	private int provideType;
	private String providerOrgId;

	private int isVote;

	public int getIsVote() {
		return isVote;
	}

	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTargetSys() {
		return targetSys;
	}

	public void setTargetSys(String targetSys) {
		this.targetSys = targetSys;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getProvideType() {
		return provideType;
	}

	public void setProvideType(int provideType) {
		this.provideType = provideType;
	}

	public String getProviderOrgId() {
		return providerOrgId;
	}

	public void setProviderOrgId(String providerOrgId) {
		this.providerOrgId = providerOrgId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getClickCnt() {
		return clickCnt;
	}

	public void setClickCnt(int clickCnt) {
		this.clickCnt = clickCnt;
	}
}
