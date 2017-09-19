package com.xflyme.go4.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * 
 * @Description:AES加密
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class AESSecurity {
	public static String KEY = "lovehealth&ciyun";

	/**
	 * 加密
	 * @param input
	 * @return
	 */
	public static String encrypt(String input) {
		if(input == null || input.equals("")){
			return "";
		}
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			CLog.e("AESSecurity", e.toString());
		}
		return new String(Base64.encode(crypted, Base64.NO_WRAP));
	}

	/**
	 * 解密
	 * @param input
	 * @return
	 */
	public static String decrypt(String input) {
		if(input == null || input.equals("")){
			return "";
		}
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decode(input, Base64.NO_WRAP));
		} catch (Exception e) {
			CLog.e("AESSecurity", e.toString());
		}
		return new String(output);
	}
	
	
	public static void main(String[] args) {//测试用
		String t1= "wennan123456";
		String t2= encrypt(t1);
		String t3= decrypt(t2);
		CLog.e("AESSecurity 加密前", t1);
		CLog.e("AESSecurity 加密后", t2);
		CLog.e("AESSecurity 解密后", t3);
	}
}