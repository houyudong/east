package com.xflyme.go4.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

/**
 * 
 * @Description:
 * @author:刘祥飞
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2015-1-5
 */
public class AppUtil {
	
	/** 
	 * @Description: 判断app是否在运行
	 * @param context
	 * @return    
	 * @author:刘祥飞
	 */
	public static boolean isAppRunning(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		boolean isAppRunning = false;
		String MY_PKG_NAME = "com.ciyun.lovehealth";
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(MY_PKG_NAME) && info.numRunning >= 3) {
				isAppRunning = true;
				
				break;
			}
		}
		return isAppRunning;
	}
}