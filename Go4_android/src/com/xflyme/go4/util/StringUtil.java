package com.xflyme.go4.util;


/**
 * @Description:字符串相关方法
 * @author: wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2012-3-13
 */
public class StringUtil {


	
	/**
	 * 判断字符串是否为空
	 * @Description:
	 * @param str
	 * @param trim 是否去掉前后空格
	 * @return
	 * @see: 
	 * @since: 
	 * @author: huangyongxing
	 * @date:2012-7-2
	 */
	public static boolean isEmpty(String str, boolean trim){
		if(str == null){
			return true;
		}
		if(trim){
			if("".equals(str.trim())){
				return true;
			}
		}else{
			if(str.length() == 0){
				return true;
			}
		}
		return false;
	}
	

}
