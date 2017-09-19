package com.xflyme.go4.controller.observer;

/**
 * 
 * @Description:版本升级观察者接口
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月25日
 */
public interface CheckVersionObserver {	
	/**
	 * 检测新版本失败
	 * @param errorCode
	 * @param msg
	 */
	public void onCheckVersionFail(int errorCode,String msg);
	
	/**
	 * 检测新版本成功
	 * @param token
	 * @param msg
	 */
	//public void onCheckVersionSucc(CheckUpdataEntity newEntity);
}
