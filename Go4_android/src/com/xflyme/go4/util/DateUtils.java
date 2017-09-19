package com.xflyme.go4.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.xutil.DateUtil;


/**
 * 日期工具类
 * 
 * @Description:
 * @author:侯玉东
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014-11-28
 */
public class DateUtils {
	
	/** 
	 * @Description: 返回当前时间和周期之间的所有日期
	 * @param startDate
	 * @param length
	 * @return    
	 * @author:刘祥飞
	 */
	public static final String[] getCircleDate(int length){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String[] dates = new String[length];
		dates[0] = sdf.format(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		for (int i = 1; i < length; i++) {
			calendar.add(Calendar.DATE, 1);
			dates[i] = sdf.format(calendar.getTime());
			CLog.d("riqi==", dates[i]);
		}
		return dates;
	}
	
	/**
	 * 获得最近xx天的日期
	 * @param length
	 * @return
	 */
	public static final String[] getRecentDates(int length){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String[] dates = new String[length];
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -(length-1));
		dates[0] = sdf.format(calendar.getTime());
		for (int i = 1; i <length; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			dates[i] = sdf.format(calendar.getTime());
		}
		return dates;
	}

	/**
	 *  判断当前日期是星期几
	 * 
	 * @param time
	 * @return
	 */
	public static int dayForWeek(Date time) {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int weekDay = 0;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			weekDay = 0;
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				weekDay = 7;
			} else {
				int i = calendar.get(Calendar.DAY_OF_WEEK);
				weekDay = i - 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weekDay;
	}

	/**
	 * 将给定的字符串解析成Date对象
	 * 
	 * @param date
	 *            字符串形式的date
	 * @return
	 */
	public static Date stringForDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		try {
			date2 = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}

	/**
	 * @Description: 计算两个日期之间的差
	 * @param startdate
	 * @param enddate
	 * @return
	 * @author:刘祥飞
	 */
	@SuppressLint("SimpleDateFormat")
	public static int getGap(String startdate, String enddate) {
		if (startdate == null || enddate == null || startdate.equals("") || enddate.equals("")) {
			return 0;
		}else {
			Date startDate = DateUtils.stringForDate(startdate);
			Date endDate = DateUtils.stringForDate(enddate);
			int day = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
			return day + 1;
		}
	}

	/**
	 * 传入一个开始日期和一个结束日期 返回这个区间的data集合
	 * 
	 * @param start
	 * @param end
	 * @param calendarType
	 * @return
	 */
	public static Date[] getDateArrays(Date start, Date end, int calendarType) {
		ArrayList ret = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		Date tmpDate = calendar.getTime();
		long endTime = end.getTime();
		while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
			ret.add(calendar.getTime());
			calendar.add(calendarType, 1);
			tmpDate = calendar.getTime();
		}
		Date[] dates = new Date[ret.size()];
		return (Date[]) ret.toArray(dates);

	}

	/**
	 * 传入一个开始日期和一个结束日期 返回这个区间的list集合
	 * 
	 * @param start
	 * @param end
	 * @param calendarType
	 * @return
	 */
	public static List getDateArrayLists(Date start, Date end, int calendarType) {
		ArrayList ret = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		Date tmpDate = calendar.getTime();
		long endTime = end.getTime();
		while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
			ret.add(calendar.getTime());
			calendar.add(calendarType, 1);
			tmpDate = calendar.getTime();
		}

		return ret;

	}

	/**
	 * 取得距离今天 day 日的日期
	 * 
	 * @param day
	 *            负数表示之前，否则表示之后
	 * @param format
	 *            格式
	 * @return
	 */
	public static String nextDay(int day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, day);
		return DateUtil.dateToString(cal.getTime(), format);

	}

	/**
	 * 判断日期与给定日期的关系
	 * 
	 * @param t1
	 *            给定日期
	 * @param t2
	 *            相对于给定日期判断是在前还是在后
	 * @return 0 今天 -1 昨天 1 明天
	 */
	public static int compareDate(String t1, String t2) {
		String y1 = t1.substring(0, 4);
		String m1 = t1.substring(5, 6);
		String d1 = t1.substring(8);
		
		int y11 = Integer.valueOf(y1);
		int m11 = Integer.valueOf(m1);
		int d11 = Integer.valueOf(d1);

		String y2 = t2.substring(0, 4);
		String m2 = t2.substring(5, 6);
		String d2 = t2.substring(8);
		
		int y22 = Integer.valueOf(y2);
		int m22 = Integer.valueOf(m2);
		int d22 = Integer.valueOf(d2);
		if(y11 < y22){
			return 1;
		}else if(y11 > y22){
			return -1;
		}else{
			if(m11 <m22){
				return 1;
			} else if(m11 > m22){
				return -1;
			}else {
				if(d11 <d22){
					return 1;
				} else if(d11 > d22){
					return -1;
				}else{
					return 0;
				}
			}
		}
	}
}
