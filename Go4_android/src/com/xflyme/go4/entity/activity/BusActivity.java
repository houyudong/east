package com.xflyme.go4.entity.activity;

/**
 * BusActivity entity. @author MyEclipse Persistence Tools
 */

public class BusActivity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int activityId;
	private int companyId;
	private int communityId;
	private String name;
	private Integer srcType;
	private String content;
	private String wwwLinkUrl;
	private String appLinkUrl;
	private String iconPicUrl;
	private Integer state;
	private long startTime;
	private String startTimeStr;
	private long endTime;
	private String endTimeStr;
	private Integer isPublic;
	private Integer isEnroll;
	private Integer userType;
	private String iconPic;
	private Integer isFullscreen;
	private String fullscreenPic;
	private Integer isAppIndex;
	private String appIndexPic;
	private Integer isAppSubject;
	private String appSubjectPic;
	private String wwwIndexPic;
	private Integer providerType;
	private String providerOrg;
	private int clickCnt;
	private int shareCnt;
	private int goodCnt;
	private int enrooCnt;
	private int discussCnt;
	private String createUser;
	private long createTime;
	private String modifyUser;
	private long modifyTime;
	private String remarks;
	private Integer remindState;
	private Integer hotActivity;
	private long hotTime;
	private int joinScore;
	private int shareScore;
	private Integer limitType;
	private int dayMaxCount;
	private int monthMaxCount;
	private Integer channel;
	private Integer category;
	private int favorite;

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	// Constructors

	/** default constructor */
	public BusActivity() {
	}

	public int getActivityId() {
		return this.activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSrcType() {
		return this.srcType;
	}

	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWwwLinkUrl() {
		return this.wwwLinkUrl;
	}

	public void setWwwLinkUrl(String wwwLinkUrl) {
		this.wwwLinkUrl = wwwLinkUrl;
	}

	public String getAppLinkUrl() {
		return this.appLinkUrl;
	}

	public void setAppLinkUrl(String appLinkUrl) {
		this.appLinkUrl = appLinkUrl;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getIsEnroll() {
		return this.isEnroll;
	}

	public void setIsEnroll(Integer isEnroll) {
		this.isEnroll = isEnroll;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getHotTime() {
		return hotTime;
	}

	public void setHotTime(long hotTime) {
		this.hotTime = hotTime;
	}

	public String getIconPic() {
		return this.iconPic;
	}

	public void setIconPic(String iconPic) {
		this.iconPic = iconPic;
	}

	public Integer getIsFullscreen() {
		return this.isFullscreen;
	}

	public void setIsFullscreen(Integer isFullscreen) {
		this.isFullscreen = isFullscreen;
	}

	public String getFullscreenPic() {
		return this.fullscreenPic;
	}

	public void setFullscreenPic(String fullscreenPic) {
		this.fullscreenPic = fullscreenPic;
	}

	public Integer getIsAppIndex() {
		return this.isAppIndex;
	}

	public void setIsAppIndex(Integer isAppIndex) {
		this.isAppIndex = isAppIndex;
	}

	public String getAppIndexPic() {
		return this.appIndexPic;
	}

	public void setAppIndexPic(String appIndexPic) {
		this.appIndexPic = appIndexPic;
	}

	public Integer getIsAppSubject() {
		return this.isAppSubject;
	}

	public void setIsAppSubject(Integer isAppSubject) {
		this.isAppSubject = isAppSubject;
	}

	public String getAppSubjectPic() {
		return this.appSubjectPic;
	}

	public void setAppSubjectPic(String appSubjectPic) {
		this.appSubjectPic = appSubjectPic;
	}

	public String getWwwIndexPic() {
		return this.wwwIndexPic;
	}

	public void setWwwIndexPic(String wwwIndexPic) {
		this.wwwIndexPic = wwwIndexPic;
	}

	public Integer getProviderType() {
		return this.providerType;
	}

	public void setProviderType(Integer providerType) {
		this.providerType = providerType;
	}

	public String getProviderOrg() {
		return this.providerOrg;
	}

	public void setProviderOrg(String providerOrg) {
		this.providerOrg = providerOrg;
	}

	public int getClickCnt() {
		return this.clickCnt;
	}

	public void setClickCnt(int clickCnt) {
		this.clickCnt = clickCnt;
	}

	public int getShareCnt() {
		return this.shareCnt;
	}

	public void setShareCnt(int shareCnt) {
		this.shareCnt = shareCnt;
	}

	public int getGoodCnt() {
		return this.goodCnt;
	}

	public void setGoodCnt(int goodCnt) {
		this.goodCnt = goodCnt;
	}

	public int getEnrooCnt() {
		return this.enrooCnt;
	}

	public void setEnrooCnt(int enrooCnt) {
		this.enrooCnt = enrooCnt;
	}

	public int getDiscussCnt() {
		return this.discussCnt;
	}

	public void setDiscussCnt(int discussCnt) {
		this.discussCnt = discussCnt;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getRemindState() {
		return this.remindState;
	}

	public void setRemindState(Integer remindState) {
		this.remindState = remindState;
	}

	public Integer getHotActivity() {
		return this.hotActivity;
	}

	public void setHotActivity(Integer hotActivity) {
		this.hotActivity = hotActivity;
	}

	public int getJoinScore() {
		return this.joinScore;
	}

	public void setJoinScore(int joinScore) {
		this.joinScore = joinScore;
	}

	public int getShareScore() {
		return this.shareScore;
	}

	public void setShareScore(int shareScore) {
		this.shareScore = shareScore;
	}

	public Integer getLimitType() {
		return this.limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}

	public int getDayMaxCount() {
		return this.dayMaxCount;
	}

	public void setDayMaxCount(int dayMaxCount) {
		this.dayMaxCount = dayMaxCount;
	}

	public int getMonthMaxCount() {
		return this.monthMaxCount;
	}

	public void setMonthMaxCount(int monthMaxCount) {
		this.monthMaxCount = monthMaxCount;
	}

	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public String getIconPicUrl() {
		return iconPicUrl;
	}

	public void setIconPicUrl(String iconPicUrl) {
		this.iconPicUrl = iconPicUrl;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

}