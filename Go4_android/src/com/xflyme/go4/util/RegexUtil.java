package com.xflyme.go4.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.xutil.Singlton;

/**
 * 
 * 
 * @Description:正则表达式工具类
 * @author:LiuQingJie
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2015-1-6
 */
public class RegexUtil {
	private String regExName = "[a-zA-Z0-9_\u4e00-\u9fa5]+";// 只能匹配数字、下划线、大小写字母、汉字（eg.用户名、昵称需要使用）
	private static String EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*$";// 检查电子邮件
	private static Pattern P_EMAIL = Pattern.compile(EMAIL);

	public static RegexUtil getInstance() {
		return Singlton.getInstance(RegexUtil.class);
	}

	/**
	 * 匹配传入的字符串是否合法
	 * 
	 * @param pName
	 * @return
	 */
	public boolean isLegalName(String pName) {
		Matcher matcherName = Pattern.compile(regExName).matcher(pName);
		return matcherName.matches();
	}

	public static boolean isEmail(String email) {
		if (StringUtil.isEmpty(email, true)) {
			return false;
		}
		Matcher m = P_EMAIL.matcher(email);
		return m.find();
	}
}
