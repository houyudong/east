package com.xflyme.go4.util;

import android.util.Log;

/**
 * 
 * 
 * @Description:Log打印（控制研发版本和正式版本的日志打印）
 * @author:张志建
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-11-28
 */
public final class CLog {

	private static boolean logable;
	private static int logLevel;

	/**
	 * 
	 * @description: 日志等级枚举
	 * @author :zzj
	 * @copyright:@ciyun.cn
	 * @date:2014-11-15 上午11:22:16
	 */
	public static enum LogPriority {
		VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
	}

	/**
	 * 同Log.v
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int v(String tag, String msg) {
		return println(LogPriority.VERBOSE, tag, msg);
	}

	/**
	 * 同Log.d
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int d(String tag, String msg) {
		return println(LogPriority.DEBUG, tag, msg);
	}

	/**
	 * 同Log.i
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int i(String tag, String msg) {
		return println(LogPriority.INFO, tag, msg);
	}

	/**
	 * 同Log.w
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int w(String tag, String msg) {
		return println(LogPriority.WARN, tag, msg);
	}

	/**
	 * 同Log.e
	 * 
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static int e(String tag, String msg) {
		return println(LogPriority.ERROR, tag, msg);
	}

	/**
	 * 获取是否可打印日志
	 * 
	 * @return
	 */
	public static boolean isLogable() {
		return logable;
	}

	/**
	 * 设置是否打印日志
	 * 
	 * @param logable
	 */
	public static void setLogable(boolean logable) {
		CLog.logable = logable;
	}

	/**
	 * 设置日志等级
	 * 
	 * @param level
	 */
	public static void setLogLevel(LogPriority level) {
		CLog.logLevel = level.ordinal();
	}

	private static int println(LogPriority priority, String tag, String msg) {
		int level = priority.ordinal();
		if (logable && logLevel <= level) {
			return Log.println(level + 2, tag, msg);
		} else {
			return -1;
		}
	}

}
