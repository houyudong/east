package com.xflyme.go4.cache;

import com.google.gson.Gson;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.entity.UserInfoEntity;
import com.xflyme.go4.util.AESSecurity;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @description:用户个人信息缓存 可存取用户基本信息、问卷调查结果、未读消息数量及用户token管理
 * @author :lxf	
 * 
 */
/**
 * @author apple
 *
 */
public class UserCache {

	/**
	 * 用户信息缓存文件名称
	 */
	private final String PREFERENCE_USERINFO = "cache_user";

	private final String KEY_TOKEN = "token";
	private final String KEY_HOUSEID = "curHousesId";
	private final String KEY_COMMUNITYID = "curCommunityId";
	private final String KEY_USER_INFO = "userInfo";
	private final String KEY_TOKEN_EXPTIME = "tokenExpTime";
	private final String USERID = "userid";
	private final String EMAIL = "email";
	private final String COMPANYID = "companyId";
	private final String COMPANYNAME = "companyName";
	private final String USER_NAME = "userName";
	private final String USER_PIC = "picUrl";
	private final String USER_ID_NO = "idNo";
	private final String BIRTHDAY = "birthday";
	private final String GENDER = "gender";
	private final String TELEPHONE = "telephone";
	private final String CURR_COMMUNITY_ID = "curCommunityId";
	private final String CURR_COMMUNITY_NAME = "curCommunityName";
	private final String CURR_HOUSES_ID = "curHousesId";
	private final String CURR_HOUSES_UNITNO = "curHousesUnitNo";
	private final String CURR_RELATION_TYPE = "curRelationType";

	/**
	 * 是否是已经登录的一个状态
	 */
	private static final String IS_LOGINED = "is_logined";
	private static final String IS_FIRST = "is_first";

	private static UserCache mUserCache;
	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	/**
	 * 用户基本信息
	 */
	private long tokenExpTime;
	private long userid;
	private String email;
	private long companyId;
	private String companyName;
	private String userName;
	private String pic;
	private String picUrl;
	private String idNo;
	private String birthday;
	private Integer gender;
	private String telphone;
	private Long curCommunityId;
	private String curCommunityName;
	private Long curHousesId;
	private String curHousesUnitNo;
	private Integer curRelationType;

	/**
	 *  当前用户token
	 */
	private String token;

	private UserCache() {
		context = PropertyApplication.getContext();
		preferences = context.getSharedPreferences(PREFERENCE_USERINFO, Context.MODE_PRIVATE);
		editor = preferences.edit();
		initialize();
	}

	/**
	 * 获取UserCache实例，用于存取用户基本信息
	 * 
	 * @return UserCache实例
	 */
	public static UserCache getInstance() {
		if (mUserCache == null) {
			mUserCache = new UserCache();
		}
		return mUserCache;
	}

	private void initialize() {
	}

	/**
	 * @return 当前用户是否已经登录
	 */
	public boolean isLogined() {

		return preferences.getBoolean(IS_LOGINED, false);
	}

	public void setLogined(boolean isLogined) {
		editor.putBoolean(IS_LOGINED, isLogined);
		editor.commit();
	}

	/**
	 * @return 当前用户是否已经登录
	 */
	public boolean isFirst() {

		return preferences.getBoolean(IS_FIRST, true);
	}

	public void setFirst(boolean isFirst) {
		editor.putBoolean(IS_FIRST, isFirst);
		editor.commit();
	}

	/**
	 * @return 获取当前用户上次服务器返回的token字符串
	 */
	public String getToken() {
		return AESSecurity.decrypt(preferences.getString(KEY_TOKEN, ""));
	}

	/**
	 * 设置并缓存当前用户访问服务器所获取到的token字符串
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		editor.putString(KEY_TOKEN, AESSecurity.encrypt(token));
		editor.commit();
		this.token = token;
		if (token == null || token.equals("")) {
			this.changeTokenState(false);
		}
	}

	public int getHouseId() {
		return preferences.getInt(KEY_HOUSEID, 0);
	}

	public void setHouseId(int houseid) {
		editor.putInt(KEY_HOUSEID, houseid);
		editor.commit();
	}

	public int getCommunityId() {
		return preferences.getInt(KEY_COMMUNITYID, 0);
	}

	public void setCommunityId(int communityId) {
		editor.putInt(KEY_COMMUNITYID, communityId);
		editor.commit();
	}

	/**
	 * 设置token状态，当后台返回token失效错误时，可调用此方法， 自动设为未登录状态，避免下次使用过期token访问服务器
	 * 
	 * @param isValid
	 *            token是否已失效
	 */
	public void changeTokenState(boolean isValid) {
		if (!isValid) {
			this.clear();
		}
	}

	/**
	 * 退出登录（注销用户）时调用此方法
	 */
	public void clear() {
		editor.clear();
		editor.commit();
		initialize();
	}

	/**
	 * @return 获取当前用户信息
	 */
	public UserInfoEntity getUserInfo() {
		String userInfo = AESSecurity.decrypt(preferences.getString(KEY_USER_INFO, ""));
		return new Gson().fromJson(userInfo, UserInfoEntity.class);
	}

	/**
	 * 保存当前用户信息
	 * 
	 * @param token
	 */
	public void setUserInfo(String userInfo) {
		editor.putString(KEY_USER_INFO, AESSecurity.encrypt(userInfo));
		editor.commit();
	}

}
