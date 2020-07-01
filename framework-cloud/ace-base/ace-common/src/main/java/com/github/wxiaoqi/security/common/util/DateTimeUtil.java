package com.github.wxiaoqi.security.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @Author: Lzx
 * @Description: 时间工具类
 * @Date: Created in 11:37 2018/11/20
 * @Modified By:
 */
public class DateTimeUtil {
	private static final int CODE_LOSE_TIME = 60 * 30;

	private static final int CODE_ONESECOND_LOSE_TIME = 60;

	private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	public static Date getLocalTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		return rightNow.getTime();
	}
	public static Date getLoseTimes(int num) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.SECOND, +num);
		return rightNow.getTime();
	}

	public static Date getLoseTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.SECOND, +CODE_LOSE_TIME);
		return rightNow.getTime();
	}

	public static Date getOneSecondLoseTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.SECOND,CODE_ONESECOND_LOSE_TIME);
		return rightNow.getTime();
	}

	public static Date getLoseTime(int loseData) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DATE, +loseData);
		return rightNow.getTime();
	}

	public static boolean compare_date(Date date1, Date date2) {
		long result = date1.getTime() - date2.getTime();
		return result > 0 ? true : false;
	}


	public static String shortDateString() {
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(currentTime);
		return dateString;
	}

	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			logger.error("字符转日期异常:{}" , str , e);
		}
		return date;
	}

	public static int getBetweenDay(Date date1, Date date2) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	public static String getStartDayOfWeekNo(int year,int weekNo){
		Calendar cal = getCalendarFormYear(year);
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
				cal.get(Calendar.DAY_OF_MONTH);

	}
	private static Calendar getCalendarFormYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		return cal;
	}

	public static String getStartAndEndByWeek(Integer year,Integer week){
		return getStartDayOfWeekNo(year, week)+"~"+getEndDayOfWeekNo(year, week);
	}


	public static String getEndDayOfWeekNo(int year,int weekNo){
		Calendar cal = getCalendarFormYear(year);
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
				cal.get(Calendar.DAY_OF_MONTH);
	}
	public static Date getTodayStartTime(){
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH,0);
		//一天的开始时间 yyyy:MM:dd 00:00:00
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}

}
