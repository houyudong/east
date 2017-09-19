package com.xflyme.go4.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;

/**
 * 
 * @Description:网络工具类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class NetUtil {
	/**
	 * 网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取网络连接方式：优先WIFI然后手机卡网络
	 * 
	 * @param context
	 * @param defaultType
	 * @return
	 */
	public static int getNetworkConnectivity(Context context, int defaultType) {
		int networkType = defaultType;
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (wifi == State.CONNECTED) {
			networkType = ConnectivityManager.TYPE_WIFI;
			return networkType;
		}

		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (mobile == State.CONNECTED) {
			networkType = ConnectivityManager.TYPE_MOBILE;
		}
		return networkType;
	}

	public static int lookupHost(String hostname) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			return -1;
		}
		byte[] addrBytes;
		int addr;
		addrBytes = inetAddress.getAddress();
		addr = ((addrBytes[3] & 0xff) << 24) | ((addrBytes[2] & 0xff) << 16)
				| ((addrBytes[1] & 0xff) << 8) | (addrBytes[0] & 0xff);
		return addr;
	}

	public static String ensureRouteToMMsHost(Context mContext, String url,
			boolean isProxySet, String proxyAddress) {
		ConnectivityManager connMgr = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		String errorInfo = "";
		int inetAddr;
		if (isProxySet && proxyAddress != null && !proxyAddress.equals("")) {
			String proxyAddr = proxyAddress;
			inetAddr = lookupHost(proxyAddr);
			errorInfo = routeTo(connMgr, inetAddr);
		} else {
			Uri uri = Uri.parse(url);
			inetAddr = lookupHost(uri.getHost());
			errorInfo = routeTo(connMgr, inetAddr);
		}
		return errorInfo;
	}

	private static String routeTo(ConnectivityManager connMgr, int inetAddr) {
		if (inetAddr == -1) {
			return "Cannot establish route : Unknown host";
		} else {
			if (connMgr.requestRouteToHost(ConnectivityManager.TYPE_MOBILE_MMS,
					inetAddr)) {

				return "";
			} else {
				return "Cannot establish route to proxy " + inetAddr;
			}
		}
	}
}