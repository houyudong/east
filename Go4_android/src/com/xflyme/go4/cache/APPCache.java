package com.xflyme.go4.cache;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import com.xflyme.go4.PropertyApplication;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @description :用户健康数据相关的缓存：如体检报告、健康资讯、消息、同步时间等
 * @author :zzj
 * @copyright :@ciyun.cn
 * @date :2014-8-21 17:00:10
 */
public class APPCache {

	/**
	 * 用户健康数据相关缓存文件名称
	 */
	private static final String PREFERENCE_APPCACHE = "cache_app";

	/**
	 * 体检报告-按体检时间-缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_REPORT_YEARS = "report_year";
	/**
	 * 体检报告-原始图片版-缓存KEY值
	 */
	private static final String KEY_REPORT_PHOTO = "report_photo";
	/**
	 * 体检报告-按体检项目-缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_REPORT_ITEMS = "report_items";
	/**
	 * 体检报告-按体检时间-某年体检报告KEY值（未按用户和年份组装前）
	 */
	private static final String KEY_REPORT_YEAR_DETAIL = "report_year_detail";

	/**
	 * 体检报告-是否第一次打开
	 */
	private static final String KEY_REPORT_FIRST_OPEN = "report_first_open";
	/**
	 * 体检报告-按体检项目-某项缓存KEY值（未按用户和体检项目组装前）
	 */
	private static final String KEY_REPORT_ITEM_DETAIL = "report_item_detail";

	/**
	 * 健康资讯-栏目分类缓存KEY值
	 */
	private static final String KEY_NEWS_CATEGORYS = "news_categorys";
	/**
	 * 健康资讯-某栏目下列表数据缓存KEY值（未按栏目组装前）
	 */
	private static final String KEY_NEWS_LIST = "news_list";

	/**
	 * 个人中心-我的消息列表缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_MYMSG_LIST = "mymsg_list";
	/**
	 * 个人中心-我的消息-某项消息详情的KEY值（未按用户和消息id组装前）
	 */
	private static final String KEY_MYMSG_DETAIL = "mymsg_detail";

	/**
	 * 系统公告-列表缓存KEY值
	 */
	private static final String KEY_SYSTEM_NOTICE_LIST = "system_notice_list";

	/**
	 * 高压上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_BP_HIGH = "memory_bp_high";
	/**
	 * 低压上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_BP_LOW = "memory_bp_low";
	/**
	 * 心率上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_BP_HEART = "memory_bp_heart";
	/**
	 * 血糖上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_GLU = "memory_glu";
	/**
	 * 尿酸上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_AU = "memory_au";
	/**
	 * 体重上次录入值KEY（未按用户组装前）
	 */
	private static final String KEY_MEMORY_BMI = "memory_bmi";
	/**
	 * 运动记录上次录入值KEY（未按用户和运动类型组装前）
	 */
	private static final String KEY_MEMORY_SPORT = "memory_sport";

	/**
	 * 活动列表缓存KEY
	 */
	private static final String KEY_ACT_LIST = "act_list";

	/**
	 * 话题广场版块列表KEY
	 */
	private static final String KEY_SUBJECT_SECTION_LIST = "subject_section_list";
	/**
	 * 话题列表KEY（未按版块id和排序方式组装前）
	 */
	private static final String KEY_SUBJECT_LIST = "subject_list";

	/**
	 * 用户最近一次使用的健康工具KEY值（未按用户组装前）1—体重 2-血压 3-血糖 4-尿酸
	 */
	private static final String KEY_MEMORY_RECENT_TOOL1 = "memory_recent_tool_1";
	/**
	 * 用户最近一次的上一次使用的健康工具KEY值（未按用户组装前）1—体重 2-血压 3-血糖 4-尿酸
	 */
	private static final String KEY_MEMORY_RECENT_TOOL2 = "memory_recent_tool_2";

	/**
	 * 用户上一次的同步时间KEY值（未按用户组装前）
	 */
	private static final String KEY_RECORDSYNC_TIME = "lastsynctime";

	/**
	 * 最新的咨询类型列表KEY值
	 */
	private static final String KEY_CONSULT_TYPES = "consult_type";

	/**
	 * 咨询列表中某条得回复列表KEY值（未按用户和此条id组装前）
	 */
	private static final String KEY_CONSULT_REPLY_LIST = "consult_reply_list";

	/**
	 * 主页面接口的缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_MAIN_ACTIVITY = "main_activity";
	/**
	 * 健康任务缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_HEALTH_TASK = "health_task";

	/**
	 * 精品推荐缓存KEY值
	 */
	private static final String KEY_APP_RECOMMEND = "app_recommend";
	/**
	 * 未知
	 */
	private static final String KEY_SERVICE_TIME = "service_time";
	private static final String KEY_NOTIFY_TIME = "notify_time";
	/**
	 * 虚拟IMEI号（当取不到系统IMEI号时生成）KEY值
	 */
	private static final String KEY_VIRTUAL_IMEI = "imei_virtual";
	/**
	 * 启动页缓存KEY值
	 */
	private static final String KEY_SPLASH_ACTIVITY = "allData";
	/**
	 * 是不是首次启动
	 */
	private static final String KEY_IS_FIRST_IN = "isFirstIn";
	/**
	 * 是否点击活动广告
	 */
	private static final String KEY_CLICK_AD = "isClickAd";
	/**
	 * 是否第一次点击活动广告
	 */
	private static final String KEY_FIRST_CLICK = "firstClick";
	/**
	 * 广告显示次数
	 */
	private static final String KEY_AD_SHOW_COUNT = "adShowCount";
	/**
	 * 上次启动APP的时间
	 */
	private static final String KEY_LAST_OPEN_TIME = "lastOpenTime";
	/**
	 * 最新发布的活动
	 */
	private static final String KEY_NEW_ALL_DATA = "newAllData";
	/**
	 * 目前位置图片接口的数据
	 */
	private static final String KEY_NOW_ALL_DATA = "nowAllData";
	/**
	 * 我发布的话题缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_MY_PUBLISH_TOPIC = "myTopic";
	/**
	 * 发布话题界面的缓存
	 */
	private static final String KEY_SUBJECT_PUBLIC_CACHE = "subjectPubCache";
	/**
	 * 话题详情缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_TOPIC_DETAIL = "topicDetail";
	/**
	 * 话题详情上次获取的时间缓存KEY值（未按用户组装前）
	 */
	private static final String KEY_TOPIC_DETAIL_LAST_TIME = "topicDetailLastTime";
	private static final String KEY_LAST_LOGIN_NAME = "last_login_name";
	private static final String KEY_LAST_LOGIN_PWD = "last_login_pwd";
	private static final String KEY_INUPT_BOX_CONTENT = "input_box_content";

	/**
	 * 是否设置健康任务提醒
	 */
	private static final String IS_ALERT_HEALTH_TASK = "isAlertHealthTask";

	private static final String IS_REMMEMBER_PWD = "isRemmemberPwd";
	private static final String BLOODPRESSURE_TYPE = "bloodpressuretype";

	/**
	 * 健康放案是否被终止
	 */
	private static final String IS_PLAN_STOPED = "isPlanStoped";
	/**
	 * 闹钟缓存
	 */
	private static final String KEY_ALARMS = "alarms";
	/**
	 * 运动记录的运动号
	 */
	private static final String SPORT_ID_LAST_RECORD = "sportIdLastRecord";

	/**
	 * pdf路径
	 */
	private static final String PDF_PATH = "pdfPath";
	/**
	 * 运动强度
	 */
	private static final String SPORT_STRENGTH = "sportStrength";

	/**
	 * 未读消息个数
	 */
	private static final String UN_READ_MSG_CNT = "unReadMsgCnt";

	/**
	 * 是否第一次进入
	 */
	private static final String APP_FIRST = "appFirst";

	/**
	 * 是否第一次进入首页
	 */
	private static final String APP_FIRST_MAIN = "appFirstMain";

	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private UserCache userCache;

	private static APPCache mAPPCache;

	private APPCache() {
		context = PropertyApplication.getContext();
		preferences = context.getSharedPreferences(PREFERENCE_APPCACHE,
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		userCache = UserCache.getInstance();
	}

	/**
	 * 获取APPCache实例，用于存取用户健康数据相关的缓存等
	 * 
	 * @return
	 */
	public static APPCache getInstance() {
		if (mAPPCache == null) {
			mAPPCache = new APPCache();
		}
		return mAPPCache;
	}

	/**
	 * 清空app缓存
	 */
	public void clear() {
		editor.clear();
		editor.commit();
	}

}
