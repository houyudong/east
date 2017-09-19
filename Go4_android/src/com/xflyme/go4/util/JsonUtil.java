package com.xflyme.go4.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @Description:Json工具类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class JsonUtil {

	// Google Gson类
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	/**
	 * 
	 * @Description: 将json字符串转为 java对象
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return
	 * @see:
	 * @since:
	 * @author: liguanghui
	 * @date:2012-5-23
	 */
	public static synchronized <T> T parse(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	/**
	 * 
	 * @Description:将obj对象转为json格式数据
	 * @param obj
	 * @return
	 * @see:
	 * @since:
	 * @author: liguanghui
	 * @date:2012-5-23
	 */
	public static synchronized String toJson(Object obj) {
		return gson.toJson(obj);
	}

}
