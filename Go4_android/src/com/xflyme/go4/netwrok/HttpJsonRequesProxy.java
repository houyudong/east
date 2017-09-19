package com.xflyme.go4.netwrok;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.xutil.Singlton;

import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.entity.OrderDetailItem;
import com.xflyme.go4.util.PhoneUtil;

/**
 * 
 * @Description:构建请求网络的JSON信息
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class HttpJsonRequesProxy {

	public static HttpJsonRequesProxy getInstance() {
		return Singlton.getInstance(HttpJsonRequesProxy.class);
	}

	/**
	 * 请求网络协议头语言设置
	 */
	public final static String LANGUAGE = "zh_cn";

	/**
	 * 请求网络协议头的版本设置
	 */
	// public final static String VERSION = "1.0";

	/**
	 * 
	 * 
	 * Description: 建立JSON请求数据头的公共类
	 * 
	 * @return JSONObject
	 */
	@SuppressLint("SimpleDateFormat")
	public JSONObject getContextJsonObj() {
		String release = android.os.Build.VERSION.RELEASE;
		String version = "";
		Context context = PropertyApplication.getContext();
		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		String mAppId = "android" + release + "_" + Build.MODEL;
		if (mAppId.length() > 30) {
			mAppId = mAppId.substring(0, 30);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String strData = formatter.format(curDate);
		String strData2 = formatter2.format(curDate) + PhoneUtil.getIMEI() + (int) (Math.random() * 1000000);
		JSONObject title = new JSONObject();
		try {

			title.put("version", version);
			title.put("appid", mAppId);
			title.put("reqtime", strData);
			title.put("token", PropertyApplication.mUserCache.getToken());
			title.put("transid", strData2);
			title.put("uuid", PhoneUtil.getIMEI());
			title.put("companyid", PropertyConstant.COMPANY_ID);
			title.put("communityId", PropertyApplication.mUserCache.getCommunityId());
			title.put("housesId", PropertyApplication.mUserCache.getHouseId());
			

			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;
	}

	/**
	 * \ 用户基本信息
	 * 
	 * @param token
	 * @param name
	 * @param sex
	 * @param birth
	 * @param height
	 * @param cardtype
	 * @param cardid
	 * @param mobile
	 * @param email
	 * @return
	 */
	public JSONObject getUerInfoReques(String birth, String mobile, String email, String name) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("name", name);
			mQrInfo.put("contactNo", mobile);
			mQrInfo.put("email", email);
			mQrInfo.put("birthday", birth);
			// mQrInfo.put("headimg", image);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		return title;
	}

	public JSONObject getAddActivity(int category,String title,String content) {
		JSONObject object= getContextJsonObj();
		
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("name", title);
			mQrInfo.put("srcType", 2);
			mQrInfo.put("content", content);
			mQrInfo.put("category", category);
			mQrInfo.put("startTime", System.currentTimeMillis());
			mQrInfo.put("endTime", System.currentTimeMillis());
			// mQrInfo.put("headimg", image);
			// 将用户和码值添加到整个Json中
			object.put("data", mQrInfo);
			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		return object;
	}
	
	/**
	 * 用户登录
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public JSONObject onLogin(String email, String password) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("email", email);
			mQrInfo.put("password", password);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	public JSONObject onLogout(String userid) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("userid", userid);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);
			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		
		return title;
		
	}
	
	/**
	 * change password
	 * 
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public JSONObject onChangePasswrod(String oldpwd, String newpwd) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("oldpwd", oldpwd);
			mQrInfo.put("newpwd", newpwd);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * homepage
	 * 
	 * @return
	 */
	public JSONObject onGetHomePage() {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * 用户找回密码
	 * 
	 * @param email
	 * @return
	 */
	public JSONObject onFindPassword(String email) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("email", email);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * Managerment team
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONObject onGetTeamInfo(int pageNo, int pageSize) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * about us
	 * 
	 * @param type
	 * @return
	 */
	public JSONObject onGetAboutUs(int type) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("type", type);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * bill
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param type
	 * @return
	 */
	public JSONObject onGetBill(int pageNo, int pageSize, int type) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			mQrInfo.put("type", type);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * notice board
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param category
	 * @param activityId
	 * @return
	 */
	public JSONObject onGetNoticeBoard(int pageNo, int pageSize, int category, int activityId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			mQrInfo.put("type", category);
			mQrInfo.put("noticeId", activityId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	public JSONObject onGetVote(int noticeId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("noticeId", noticeId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);
			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		
		return title;
		
	}
	
	public JSONObject onVote(int noticeId,int choiceId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("noticeId", noticeId);
			mQrInfo.put("choiceId", choiceId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);
			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		
		return title;
		
	}

	public JSONObject onGetMyBooking(int pageNo, int pageSize, int type) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			mQrInfo.put("type", type);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * feedback list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param feedbackId
	 * @return
	 */
	public JSONObject onGetFeedBack(int pageNo, int pageSize, int feedbackId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			mQrInfo.put("feedbackId", feedbackId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * BookFacilityList
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param feedbackId
	 * @return
	 */
	public JSONObject onGetBookFacilityList(int pageNo, int pageSize) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	public JSONObject onAddActivity(int category, String name ,String content) {
		JSONObject title = getContextJsonObj();
		
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("name", name);
			mQrInfo.put("category", category);
			mQrInfo.put("content", content);
			mQrInfo.put("startTime", "2016");
			mQrInfo.put("endTime", "2016");
			mQrInfo.put("srcType", 2);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);
			
		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}
		
		return title;
		
	}
	
	/**
	 * yuding
	 * 
	 * @param orderDetail
	 * @return
	 */
	public JSONObject onOrder(OrderDetailItem orderDetail) {
		JSONObject title = getContextJsonObj();
		try {

			JSONArray amArray = new JSONArray();
			if (orderDetail.getAmOrderTime()!=null && orderDetail.getAmOrderTime().size()>0) {
				for (int i = 0; i < orderDetail.getAmOrderTime().size(); i++) {
					amArray.put(orderDetail.getAmOrderTime().get(i));
				}
			}
			JSONArray pmArray = new JSONArray();
			if (orderDetail.getPmOrderTime()!=null && orderDetail.getPmOrderTime().size()>0) {
				for (int i = 0; i < orderDetail.getPmOrderTime().size(); i++) {
					pmArray.put(orderDetail.getPmOrderTime().get(i));
				}
			}


			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("id", orderDetail.getId());
			mQrInfo.put("siteId", orderDetail.getSiteId());
			mQrInfo.put("siteName", orderDetail.getSiteName());
			mQrInfo.put("companyId", orderDetail.getCompanyId());
			mQrInfo.put("communityId", orderDetail.getCommunityId());
			mQrInfo.put("communityName", orderDetail.getCommunityName());
			mQrInfo.put("housesId", orderDetail.getHousesId());
			mQrInfo.put("housesName", orderDetail.getHousesName());
			mQrInfo.put("type", orderDetail.getType());
			mQrInfo.put("state", orderDetail.getState());
			mQrInfo.put("tel", orderDetail.getTel());
			mQrInfo.put("orderDate", orderDetail.getOrderDate());
			mQrInfo.put("amOrderTime", amArray);
			mQrInfo.put("pmOrderTime", pmArray);
			mQrInfo.put("cost", orderDetail.getCost());
			mQrInfo.put("deposit", orderDetail.getDeposit());
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * sitedetail
	 * 
	 * @param seachDate
	 * @param siteId
	 * @return
	 */
	public JSONObject onGetSiteDetail(String seachDate, int siteId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("seachDate", seachDate);
			mQrInfo.put("siteId", siteId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * 回复一条内容
	 * 
	 * @param content
	 * @param feedbackId
	 * @return
	 */
	public JSONObject onSaveFeedBack(String content, int feedbackId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("feedbackId", feedbackId);
			mQrInfo.put("content", content);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	public JSONObject onReply(String content, int activityId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("activityId", activityId);
			mQrInfo.put("content", content);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * 活动列表
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public JSONObject onGetActivity(int pageNo, int pageSize, int category, int activityId) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			mQrInfo.put("category", category);
			mQrInfo.put("activityId", activityId);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	public JSONObject onGetMyFavorites(int pageNo, int pageSize) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("pageSize", pageSize);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * 回复列表
	 * 
	 * @param activityId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONObject onGetReplay(int activityId, int pageNo, int pageSize) {
		JSONObject title = getContextJsonObj();
		try {
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("pageNo", pageNo);
			mQrInfo.put("activityId", activityId);
			mQrInfo.put("pageSize", pageSize);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

	/**
	 * 关注取消关注
	 * 
	 * @param orgId
	 * @param type
	 * @return
	 */
	public JSONObject onAddFavorite(ArrayList<Integer> orgIds, int type) {
		JSONObject title = getContextJsonObj();
		try {
			JSONArray ids = new JSONArray();
			for (int i = 0; i < orgIds.size(); i++) {
				ids.put(orgIds.get(i));
			}
			
			
			// 另外一个Json对象需要新建
			JSONObject mQrInfo = new JSONObject();
			mQrInfo.put("orgIds", ids);
			mQrInfo.put("type", type);
			// 将用户和码值添加到整个Json中
			title.put("data", mQrInfo);

		} catch (JSONException e) {
			// 键为null或使用json不支持的数字格式(NaN, infinities)
			throw new RuntimeException(e);
		}

		return title;

	}

}
