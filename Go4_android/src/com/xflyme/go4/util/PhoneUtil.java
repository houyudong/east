package com.xflyme.go4.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import com.xflyme.go4.PropertyApplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
/**
 * 
 * 
 * @Description:获取手机相关信息工具类
 * @author:温楠
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-11-26
 */
public class PhoneUtil {

	/**
	 * 获取手机IMEI
	 * @return
	 */
	public static String getIMEI() {

		TelephonyManager mTelephonyMgr = (TelephonyManager) PropertyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		if (imei == null) {
			//imei = HealthApplication.mAPPCache.getVirtualIMEI();
		}
		return imei;
	}
	
	/**
	 * 获取当前MAC地址
	 * 
	 * @return 
	 */
	public static String getLocalMacAddress() {  
        WifiManager wifi = (WifiManager) PropertyApplication.getContext().getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    } 

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param ipv4
	 *            true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	public static String getIPAddress() {
		try {
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf
						.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);

						if (isIPv4) {
							return sAddr;
						} else {
							int delim = sAddr.indexOf('%'); // drop ip6 port
															// suffix
							return delim < 0 ? sAddr : sAddr
									.substring(0, delim);
						}

					}
				}
			}
		} catch (Exception ex) {
		}
		return "";
	}
	
	/**
	 * 获取本机Ip地址的方法
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}
	
	/**
	 * Check if external storage is built-in or removable.
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	public static boolean isExternalStorageRemovable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
/**
 * 判断手机是否处于联网状态
 * @param c
 * @return
 */
//	public static boolean isConn(Context c){
//		ConnectivityManager m = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
//		if(m != null){
//			NetworkInfo[] networkInfo = m.getAllNetworkInfo();
//			for (NetworkInfo info : networkInfo) {
//				if(info != null && info.isConnected()){
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
}
