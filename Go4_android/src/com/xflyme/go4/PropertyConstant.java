package com.xflyme.go4;

import android.os.Environment;

/**
 * 
 * @Description:公共常量类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class PropertyConstant {
	/**
	 * 服务访问连接
	 */

	 public final static String HOSTURL ="http://120.27.53.77/api/";
	 //public final static String HOSTURL ="http://192.168.1.101:8080/";
	 public final static int COMPANY_ID =2;
	/** 标识是否研发版本，false代表正式版本 */
	public static final boolean IS_DEVELOPMODE = true;
	/** 保存文件的主目录 */
	public static final String FILE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/ciyun/";
	/** crash日志目录及文件名 */
	public static final String CRASH_PATH = "crash";
	/** 程序运行日志目录及文件名 */
	public static final String LOG_PATH = "runlog/log.txt";
	/** 同步数据文件名 */
	public static final String UPDATAFILENAME = "lovehealth";

	public static final int BILL_TYPE_RECENT = 1;
	public static final int BILL_TYPE_HISTORY = 2;

	public static final int TEAM_TYPE_MANAGERMENT = 1;
	public static final int TEAM_TYPE_CANDO = 2;

	public static final int NOTICE_TYPE_RECENT = 1;
	public static final int NOTICE_TYPE_POST = 2;

}
