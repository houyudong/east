package com.xflyme.go4.netwrok;

import java.util.ArrayList;

import org.json.JSONObject;

import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.entity.AboutUsEntity;
import com.xflyme.go4.entity.BaseEntity;
import com.xflyme.go4.entity.BillEntity;
import com.xflyme.go4.entity.FeedBackEntity;
import com.xflyme.go4.entity.HomePageEntity;
import com.xflyme.go4.entity.MyBookingEntity;
import com.xflyme.go4.entity.NoticeBoardDetailEntity;
import com.xflyme.go4.entity.NoticeBoardEntity;
import com.xflyme.go4.entity.OrderDetailItem;
import com.xflyme.go4.entity.ReplyEntity;
import com.xflyme.go4.entity.ServerBookFacilityEntity;
import com.xflyme.go4.entity.SiteDetailEntity;
import com.xflyme.go4.entity.TeamInfoEntity;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.entity.VoteEntity;
import com.xflyme.go4.entity.activity.ActivityResp;
import com.xflyme.go4.util.HttpUtil;
import com.xflyme.go4.util.JsonUtil;

import android.util.Log;
import android.xutil.Singlton;

/**
 * 
 * @Description:http协议 主要完成http请求操作,所有请求网络的操作写在此类
 * @author:wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月18日
 */
public class ProtocolImpl {

	// 网络访问工具类
	private HttpUtil httpUtil = HttpUtil.getInstance();

	public static ProtocolImpl getInstance() {
		return Singlton.getInstance(ProtocolImpl.class);
	}

	/**
	 * @param email
	 * @param password
	 * @return
	 */
	public TeamInfoEntity onGetTeamInfo(int pageNo, int pageSize) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetTeamInfo(pageNo, pageSize);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "teamList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("teamInfo", j.toString());
		Log.e("teamInfo", jsonData);

		return JsonUtil.parse(jsonData, TeamInfoEntity.class);
	}

	public UserInfoEntity updateUserInfo(String birth, String mobile, String email, String name, String[] image) {
		JSONObject j = HttpJsonRequesProxy.getInstance().getUerInfoReques(birth, mobile, email, name);

		String jsonData = null;
		try {
			jsonData = httpUtil.upload(PropertyConstant.HOSTURL + "updateUserinfo", image, j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("user", j.toString());
		Log.e("user", jsonData);
		if (jsonData == null) {
			return null;
		}
		return JsonUtil.parse(jsonData, UserInfoEntity.class);
	}

	public BaseEntity onAddActivity(int category, String name, String content, String[] image) {
		JSONObject j = HttpJsonRequesProxy.getInstance().getAddActivity(category, name, content);

		String jsonData = null;
		try {
			jsonData = httpUtil.upload(PropertyConstant.HOSTURL + "addActivity", image, j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("user", j.toString());
		Log.e("user", jsonData);
		if (jsonData == null) {
			return null;
		}
		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	/**
	 * @param orderDetail
	 * @return
	 */
	public BaseEntity onOrder(OrderDetailItem orderDetail) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onOrder(orderDetail);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "scheduleOrder", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("order", j.toString());
		Log.e("order", jsonData);

		if (jsonData == null) {
			return null;
		}

		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	/**
	 * about us
	 * 
	 * @param type
	 * @return
	 */
	public AboutUsEntity onGetAboutUs(int type) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetAboutUs(type);
		String jsonData = null;
		Log.e("about us", "1");
		try {
			Log.e("about us", "2");

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "managermentInfos", j.toString());
		} catch (Exception e) {
			Log.e("about us", "3");
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("about us", jsonData);
		Log.e("about us", j.toString());

		return JsonUtil.parse(jsonData, AboutUsEntity.class);
	}

	/**
	 * bill
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public BillEntity onGetBill(int pageNo, int pageSize, int type) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetBill(pageNo, pageSize, type);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "feeList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("bill", jsonData);
		Log.e("bill", j.toString());

		return JsonUtil.parse(jsonData, BillEntity.class);
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param category
	 * @param activityId
	 * @return
	 */
	public NoticeBoardEntity onGetNoticeBoardList(int pageNo, int pageSize, int category, int activityId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetNoticeBoard(pageNo, pageSize, category, activityId);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "noticeList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("onGetNoticeBoard", j.toString());
		Log.e("onGetNoticeBoard", jsonData);

		if (jsonData == null) {
			return null;
		}

		return JsonUtil.parse(jsonData, NoticeBoardEntity.class);
	}

	public VoteEntity onGetVote(int noticeId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetVote(noticeId);
		String jsonData = null;
		try {
			
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "noticeVoteList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.e("onGetVote", j.toString());
		Log.e("onGetVote", jsonData);
		
		if (jsonData == null) {
			return null;
		}
		
		return JsonUtil.parse(jsonData, VoteEntity.class);
	}
	
	
	public BaseEntity onVote(int noticeId,int choiceId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onVote(noticeId, choiceId);
		String jsonData = null;
		try {
			
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "vote", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.e("onGetVote", j.toString());
		Log.e("onGetVote", jsonData);
		
		if (jsonData == null) {
			return null;
		}
		
		return JsonUtil.parse(jsonData, BaseEntity.class);
	}
	
	public NoticeBoardDetailEntity onGetNoticeBoardDetail(int pageNo, int pageSize, int category, int activityId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetNoticeBoard(pageNo, pageSize, category, activityId);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "noticeList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("onGetNoticeBoard", j.toString());
		Log.e("onGetNoticeBoard", jsonData);
		if (jsonData == null) {
			return null;
		}

		Log.e("onGetNoticeBoard", jsonData);

		return JsonUtil.parse(jsonData, NoticeBoardDetailEntity.class);
	}

	/**
	 * 我的预定
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public MyBookingEntity onGetMyBooking(int pageNo, int pageSize, int type) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetMyBooking(pageNo, pageSize, type);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "myschedule", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("myschedule", j.toString());
		Log.e("myschedule", jsonData);
		if (jsonData == null) {
			return null;
		}

		return JsonUtil.parse(jsonData, MyBookingEntity.class);
	}

	/**
	 * feedback list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param feedbackId
	 * @return
	 */
	public FeedBackEntity onGetFeedBack(int pageNo, int pageSize, int feedbackId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetFeedBack(pageNo, pageSize, feedbackId);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "feedbackList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("feedback", jsonData);
		Log.e("feedback", j.toString());

		return JsonUtil.parse(jsonData, FeedBackEntity.class);
	}

	/**
	 * BookFacilityList
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ServerBookFacilityEntity onGetBookFacilityList(int pageNo, int pageSize) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetBookFacilityList(pageNo, pageSize);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "schedulesiteList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("bookfacility", j.toString());
		Log.e("bookfacility", jsonData);

		if (jsonData == null) {
			return null;
		}

		Log.e("bookfacility", jsonData);

		return JsonUtil.parse(jsonData, ServerBookFacilityEntity.class);
	}

	/**
	 * site detail
	 * 
	 * @param searchDate
	 * @param siteId
	 * @return
	 */
	public SiteDetailEntity onGetSiteDetail(String searchDate, int siteId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetSiteDetail(searchDate, siteId);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "scheduleMouthByDetail", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("sitedetail", j.toString());
		Log.e("sitedetail", jsonData);

		if (jsonData == null) {
			return null;
		}

		return JsonUtil.parse(jsonData, SiteDetailEntity.class);
	}

	/**
	 * 回复一条内容
	 * 
	 * @param content
	 * @param feedbackId
	 * @return
	 */
	public BaseEntity onSaveFeedBack(String content, int feedbackId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onSaveFeedBack(content, feedbackId);
		String jsonData = null;
		Log.e("savefeedback", "1");
		try {

			Log.e("savefeedback", "2");
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "saveFeedback", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("savefeedback", "3");
		}

		Log.e("savefeedback", j.toString());
		if (jsonData == null) {
			return null;
		}

		Log.e("savefeedback", jsonData + "----" + 4);

		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	public BaseEntity onReply(String content, int activityId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onReply(content, activityId);
		String jsonData = null;
		Log.e("onReply", "1");
		try {

			Log.e("onReply", "2");
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "replyActivity", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("onReply", "3");
		}

		Log.e("onReply", j.toString());
		Log.e("onReply", jsonData);
		if (jsonData == null) {
			return null;
		}

		Log.e("onReply", jsonData + "----" + 4);

		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	/**
	 * 用户登录
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public UserInfoEntity onLogin(String email, String password) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onLogin(email, password);

		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "login", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("login", jsonData);
		Log.e("login", j.toString());

		return JsonUtil.parse(jsonData, UserInfoEntity.class);
	}

	public BaseEntity onLogout(String userid) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onLogout(userid);

		String jsonData = null;
		try {
			Log.e("onLogout", "2");
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "logout", j.toString());
		} catch (Exception e) {
			Log.e("onLogout", "3");
			e.printStackTrace();
		}

		Log.e("onLogout", jsonData);
		Log.e("onLogout", j.toString());
		if (jsonData == null) {
			return null;
		}


		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	/**
	 * 修改密码
	 * 
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public BaseEntity onChangePassword(String oldpwd, String newpwd) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onChangePasswrod(oldpwd, newpwd);
		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "changePwd", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("login", jsonData);
		Log.e("login", j.toString());

		return JsonUtil.parse(jsonData, UserInfoEntity.class);
	}

	public HomePageEntity onGetHomePage() {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetHomePage();

		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "home", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("home", jsonData);
		Log.e("home", j.toString());

		return JsonUtil.parse(jsonData, HomePageEntity.class);
	}

	/**
	 * 用户找回密码
	 * 
	 * @param email
	 * @return
	 */
	public BaseEntity onFindPassword(String email) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onFindPassword(email);

		String jsonData = null;
		try {

			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "forgetPassword", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonData == null) {
			return null;
		}

		Log.e("forgetPassword", jsonData);
		Log.e("forgetPassword", j.toString());

		return JsonUtil.parse(jsonData, BaseEntity.class);
	}

	public ActivityResp onGetActivity(int pageNo, int pageSize, int category, int activityId) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetActivity(pageNo, pageSize, category, activityId);
		// Log.e("activityList", "1");

		String jsonData = null;
		try {
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "activityList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == jsonData || "".equals(jsonData.trim())) {
			return null;
		}

		Log.e("activityList", category + "----" + jsonData);

		return JsonUtil.parse(jsonData, ActivityResp.class);
	}

	public ActivityResp onGetMyFavorites(int pageNo, int pageSize) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetMyFavorites(pageNo, pageSize);
		// Log.e("activityList", "1");

		String jsonData = null;
		try {
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "myFavorites", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == jsonData || "".equals(jsonData.trim())) {
			return null;
		}

		Log.e("myFavorites", "----" + jsonData);

		return JsonUtil.parse(jsonData, ActivityResp.class);
	}

	/**
	 * 回复列表
	 * 
	 * @param activityId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ReplyEntity onGetReplay(int activityId, int pageNo, int pageSize) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetReplay(activityId, pageNo, pageSize);
		// Log.e("activityList", "1");

		String jsonData = null;
		try {
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "activityReplyList", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == jsonData || "".equals(jsonData.trim())) {
			return null;
		}

		Log.e("onGetReplay", "----" + jsonData);

		return JsonUtil.parse(jsonData, ReplyEntity.class);
	}

	/**
	 * 关注取消关注
	 * 
	 * @param orgId
	 * @param type
	 * @return
	 */
	public BaseEntity onAddFavorite(ArrayList<Integer> orgIds, int type) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onAddFavorite(orgIds, type); // Log.e("activityList",

		String jsonData = null;
		Log.e("myFavorites", "----");
		try {
			Log.e("myFavorites", "----" + 2);
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "addFavorites", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("myFavorites", "----" + 3);
		}

		if (null == jsonData || "".equals(jsonData.trim())) {
			return null;
		}

		Log.e("myFavorites", "----" + jsonData);
		Log.e("myFavorites", "----" + j.toString());

		return JsonUtil.parse(jsonData, ActivityResp.class);
	}

	public ActivityResp onGetMyActivities(int pageNo, int pageSize) {
		JSONObject j = HttpJsonRequesProxy.getInstance().onGetMyFavorites(pageNo, pageSize);
		// Log.e("activityList", "1");

		String jsonData = null;
		try {
			jsonData = httpUtil.sendDataToServer(PropertyConstant.HOSTURL + "myactivity", j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == jsonData || "".equals(jsonData.trim())) {
			return null;
		}

		Log.e("myFavorites", "----" + jsonData);

		return JsonUtil.parse(jsonData, ActivityResp.class);
	}
}
