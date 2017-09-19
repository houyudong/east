package com.xflyme.go4.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.xflyme.go4.PropertyApplication;
import com.xflyme.go4.PropertyConstant;
import com.xflyme.go4.R;

/**
 * 
 * 
 * @Description:友盟分享
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014.8.28
 */
public class UMengShare {

	private Context mContext = null;
	private Activity mActivity = null;

	/** === SDK controller === */
	private UMSocialService mController = null;

	/** === 要分享的文字内容 === */
	private String mShareContent = "【慈云健康】APP,依托后台专业的健康顾问持续管理您的健康，为您量身定制健康改善方案并全程监督您的改进过程。URL=http://m.ciyun.cn/download";
	/** === 分享的图片 === */
	private UMImage mUMImgBitmap = null;
	/** === 分享内容链接之前的描述 === */
	private String mShareContentString = "";
	private String mShareContentSina = "";
	private String mShareContentQzone = "";

	SnsPostListener snsPostListener = new SnsPostListener() {
		@Override
		public void onStart() {

		}

		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode,
				SocializeEntity entity) {
			String showText = "分享成功";
            if (eCode != StatusCode.ST_CODE_SUCCESSED) {
                showText = "分享失败";
            }
            Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
		}
	};

	// wx31f3ff2feb694f38是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
	private String appID = "wx31f3ff2feb694f38";

	public UMengShare() {
	}

	/**
	 * 本构造方法只是适用于应用分享，其他的分享使用下面的构造方法
	 * 
	 * @param context
	 * @param appInfoStr
	 *            分享内容
	 */
	public UMengShare(Context context, String infoStr) {
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mContext = context;
		mActivity = (Activity) context;
		mShareContent = infoStr;
		mController.setShareContent(mShareContent);

		mUMImgBitmap = new UMImage(mContext, R.drawable.ic_launcher);// 设置分享的图片

		String title = "慈云健康";
		String url = "http://m.ciyun.cn/download/";

		/** === 微信分享设置的内容 === */
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		weixinContent.setShareImage(mUMImgBitmap);
		weixinContent.setShareContent(infoStr + url + "  ");
		weixinContent.setTargetUrl(url);
		mController.setShareMedia(weixinContent);

		/** === 微信朋友圈分享设置的内容 === */
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(infoStr + url + "  ");
		circleMedia.setTitle(title);
		circleMedia.setShareImage(mUMImgBitmap);
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		configSso();
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param url
	 * @param imgUrl
	 * @param type分享内容的类型
	 *            ：0 不归属任务业务1资讯2活动3话题
	 */
	public UMengShare(Context context, String title, String url, String imgUrl,
			int type) {
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");

		mContext = context;
		mActivity = (Activity) context;

		/*if (type == PropertyConstant.SHARE_TYPE_HEALTH_INFO) {// 健康资讯分享内容设置
			mShareContentString = title + "  ";
			mShareContentSina = title + "  " + url + "  "
					+ "@慈云健康[APP下载  https://m.ciyun.cn/download/" + " " + "]";
			mShareContentQzone = title + "  " + url + "  "
					+ " (来自慈云健康) [APP下载  https://m.ciyun.cn/download/" + " " + "]";
		} else if (type == PropertyConstant.SHARE_TYPE_ACTIVITY) {// 活动分享内容设置
			mShareContentString = "快来看，有活动了：" + title;
			mShareContentSina = "快来看，有活动了：" + title + "  " + url + "  "
					+ "@慈云健康[APP下载  https://m.ciyun.cn/download/" + " " + "]";
			mShareContentQzone = "快来看，有活动了：" + title + "  " + url + "  "
					+ " (来自慈云健康) [APP下载  https://m.ciyun.cn/download/" + " " + "]";
		} else if (type == PropertyConstant.SHARE_TYPE_TOPIC) {// 话题分享内容设置
			mShareContentString = "";
		} else if (type == PropertyConstant.SHARE_TYPE_EVALUATION) {// 健康评测内容设置
			mShareContentString = title + "  ";
			mShareContentSina = title + "  " + url + "  "
					+ "@慈云健康[APP 下载   https://m.ciyun.cn/download/" + " " + "]";
			mShareContentQzone = title + "  " + url + "  "
					+ " (来自慈云健康) [APP 下载   https://m.ciyun.cn/download/" + " " + "]";
			//title = PropertyApplication.getContext().getString(R.string.evaluation_share_title);
		} else if (type == 0) {// 不归属任何业务
			mShareContentString = title;
		}*/

		mController.setShareContent(mShareContentString);

		//mUMImgBitmap = new UMImage(mContext, imgUrl);// 设置分享的图片
		
		if (imgUrl != null) {
			mUMImgBitmap = new UMImage(mContext, imgUrl);
		}else {
			mUMImgBitmap = new UMImage(mContext, R.drawable.ic_launcher);
		}
		

		/** === 微信分享设置的内容 === */
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		weixinContent.setShareImage(mUMImgBitmap);
		weixinContent.setShareContent(mShareContentString);
		weixinContent.setTargetUrl(url);
		mController.setShareMedia(weixinContent);

		/** === 微信朋友圈分享设置的内容 === */
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(mShareContentString);
		circleMedia.setTitle(title);
		circleMedia.setShareImage(mUMImgBitmap);
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		
		configSso();
	}

	private void configSso() {
		mController.getConfig().closeToast();//去掉提示
		mController.getConfig().setDefaultShareLocation(false);// 设置不可以获取地理位置，若想设置，还必须修改umeng_socialize_post_share.xml设置android:visibility="gone"为显示状态；目前是获取地理位置失败

		// 去掉系统自带的人人、豆瓣、腾讯微博
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
				SHARE_MEDIA.DOUBAN, SHARE_MEDIA.TENCENT);


		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(mContext, appID);
		wxHandler.addToSocialSDK();
		wxHandler.showCompressToast(false);// 禁止显示图片大于32k时候的压缩
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(mContext, appID);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.showCompressToast(false);
		wxCircleHandler.addToSocialSDK();
		

	}

	/**
	 * 用来在Activity中使用
	 * 
	 * @return
	 */
	public void activityResult(int requestCode, int resultCode, Intent data) {
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		mController.getConfig().cleanListeners();// 在关闭分享面板的时候，添加此句代码用来防止出现该类型监听器已经超过最大使用量
	}

	public void startUMengShare() {
		mController.openShare(mActivity, false);
	}

	/**
	 * 微信分享
	 */
	public void startWeixinShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.WEIXIN, snsPostListener);
	}

	/**
	 * 微信朋友圈分享
	 */
	public void startWeixinCircleShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE,
				snsPostListener);
	}

	/**
	 * 新浪微博分享
	 */
	public void startSinaShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.SINA, snsPostListener);
	}

	/**
	 * QQ空间分享
	 */
	public void startQQZoneShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.QZONE, snsPostListener);
	}

	/**
	 * 邮箱分享
	 */
	public void startEmailShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.EMAIL, snsPostListener);
	}

	/**
	 * 短信分享
	 */
	public void startSmsShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.SMS, snsPostListener);
	}

	/**
	 * 腾讯微博分享
	 */
	public void startTencentWBShare() {
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(mContext, SHARE_MEDIA.TENCENT, snsPostListener);
	}
}
