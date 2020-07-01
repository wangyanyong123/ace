package com.github.wxiaoqi.security.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.util.StringUtils;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:09 2018/12/25
 * @Modified By:
 */
public class DateUtils {
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	public static final String YM = "yyyyMM";
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_HHMM_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_ALL = "yyyyMMddHHmmssS";

	// private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 根据指定格式化将字符串转换为日期格式
	 *
	 * @param paramString1
	 *            字符串日期格式
	 * @param paramString2
	 *            格式化模板
	 * @return
	 * @throws Exception
	 */
	public static Date stringToDate(String paramString1, String paramString2) throws ParseException {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString1);
		} catch (ParseException localParseException) {
			throw new ParseException("解析日期字符串时出错！", localParseException.getErrorOffset());
		}
	}

	/**
	 * 根据指定格式化将日期格式转换为字符串格式
	 *
	 * @param paramDate
	 *            日期格式
	 * @param paramString
	 *            格式化模板
	 * @return
	 */
	public static String dateToString(Date paramDate, String paramString) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	/**
	 * 计算两个日期相差天数
	 *
	 * @param paramDate1
	 *            开始时间
	 * @param paramDate2
	 *            结束时间
	 * @return
	 * @throws Exception
	 */
	public static int getDaysBetween(Date paramDate1, Date paramDate2) throws Exception {
		Calendar localCalendar1 = Calendar.getInstance();
		Calendar localCalendar2 = Calendar.getInstance();
		localCalendar1.setTime(paramDate1);
		localCalendar2.setTime(paramDate2);
		if (localCalendar1.after(localCalendar2))
			throw new Exception("起始日期小于终止日期!");
		int i = localCalendar2.get(6) - localCalendar1.get(6);
		int j = localCalendar2.get(1);
		if (localCalendar1.get(1) != j) {
			localCalendar1 = (Calendar) localCalendar1.clone();
			do {
				i += localCalendar1.getActualMaximum(6);
				localCalendar1.add(1, 1);
			} while (localCalendar1.get(1) != j);
		}
		return i;
	}

	/**
	 * Add by lijiajie 20160708 计算两个日期之间相差的天数
	 *
	 * @param startDate
	 *            较小的时间
	 * @param endDate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 指定日期增加天数
	 *
	 * @param paramDate
	 *            日期格式
	 * @param paramInt
	 *            加天数
	 * @return
	 * @throws Exception
	 */
	public static Date addDays(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 指定日期增加天数
	 *
	 * @param paramString1
	 *            字符串日期格式
	 * @param paramString2
	 *            格式化模板（yyyy-MM-dd）
	 * @param paramInt
	 *            加天数
	 * @return
	 * @throws ParseException
	 */
	public static Date addDays(String paramString1, String paramString2, int paramInt) throws ParseException {
		Calendar localCalendar = Calendar.getInstance();
		java.util.Date localDate = stringToDate(paramString1, paramString2);
		localCalendar.setTime(localDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 指定时间加减小时
	 *
	 * @param paramDate
	 *            时间
	 * @param paramInt
	 *            加减数（加一小时传1，减一小时传-1）
	 * @return
	 * @throws Exception
	 */
	public static Date addHours(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(Calendar.HOUR_OF_DAY, paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 指定时间加减分钟
	 *
	 * @param paramDate
	 *            时间
	 * @param paramInt
	 *            加减数（加一分钟传1，减一分钟传-1）
	 * @return
	 * @throws Exception
	 */
	public static Date addMinites(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(Calendar.MINUTE, paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 指定时间加减秒数
	 *
	 * @param paramDate
	 *            时间
	 * @param paramInt
	 *            加减数（加一秒传1，减一秒传-1）
	 * @return
	 * @throws Exception
	 */
	public static Date addSeconds(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(Calendar.SECOND, paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 指定时间加减月份
	 *
	 * @param paramDate
	 *            时间
	 * @param paramInt
	 *            加减数（加一个月传1，减一个月传-1）
	 * @return
	 * @throws Exception
	 */
	public static Date addMonths(Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(Calendar.MONTH, paramInt);
		return localCalendar.getTime();
	}

	/**
	 * 将日期格式化为字符串格式(yyyy-MM-dd HH:mm:ss)
	 *
	 * @param paramDate
	 *            日期格式
	 * @return
	 */
	public static String formatDateTime(Date paramDate) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	/**
	 * 将日期格式化为字符串格式
	 *
	 * @param paramDate
	 *            日期格式
	 * @param format
	 *            指定格式
	 * @return
	 */
	public static String formatDateTime(Date paramDate, String format) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(format);
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	/**
	 * 将数据类型为TimeStamp的日期格式化为字符串格式
	 *
	 * @author zhongziyang 20161214
	 * @param paramDate
	 * @param format
	 * @return
	 */
	public static String formatDateTime(Timestamp paramDate, String format) {
		if (paramDate == null) {
			return null;
		}
		Date dateTime = new Date(paramDate.getTime());
		return formatDateTime(dateTime, format);
	}

	/**
	 * 将日期格式化为(yyyy-MM-dd)
	 *
	 * @param paramString
	 *            日期格式
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String paramString) throws ParseException {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new ParseException("日期解析出错！", localParseException.getErrorOffset());
		}
	}

	/**
	 * 将字符串日期格式化为(yyyy-MM-dd HH:mm:ss)
	 *
	 * @param paramString
	 *            字符串日期格式
	 * @return
	 * @throws Exception
	 */
	public static Date parseDateTime(String paramString) throws ParseException {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new ParseException("时间解析异常！", localParseException.getErrorOffset());
		}
	}

	/**
	 * 将字符串日期格式化为(yyyyMMddHHmmss)
	 *
	 * @param paramString
	 *            字符串日期格式
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDateTime(String paramString, String patterns) throws ParseException {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(patterns);
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.parse(paramString);

	}

	/**
	 * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
	 *
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}

	/**
	 * 比较指定日期是否在指定日期区间内
	 *
	 * @author zhongziyang 20160819
	 * @param date
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isInTimeInterval(Date date, Date from, Date to) {
		if (date.after(from) || date.equals(from)) {
			if (date.before(to) || date.equals(to)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 获取时分秒为0的日期
	 *
	 * @author zhongziyang 2016-08-19
	 * @param date
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date getTimeIntervalDate(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String dateStr = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
		Date d = parseDate(dateStr);
		return d;
	}

	/**
	 * 判断传入的时分是否在当前时分之后 time格式 xx:xx 如果传入在当前以后，返回true 如果传入等于当前或在当前之前，返回false
	 *
	 * @param time
	 * @return
	 */
	public static boolean judgeTheTime(String time) {
		int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
		int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1));
		Calendar c = Calendar.getInstance();
		int nowHour = c.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY是24小时制的，HOUR是12小时制的
		int nowMinute = c.get(Calendar.MINUTE);
		int timeInt = hour * 60 + minute;
		int nowTimeInt = nowHour * 60 + nowMinute;
		if (nowTimeInt < timeInt) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断传入年月日是否在当前年月日之后 date格式：yyyy-MM-dd 如果传入在当前之后，返回2,如果传入等于当前，返回1，
	 * 如果传入在当前之前，返回0
	 *
	 * @param date
	 * @return
	 */
	public static int judgeTheDate(String date) {
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		int day = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1));
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;
		int nowDay = c.get(Calendar.DATE);
		if (nowYear <= year) {
			int nowDateInt = nowMonth * 30 + nowDay;
			int dateInt = month * 30 + day;
			if (nowDateInt < dateInt) {
				return 2;
			} else if (nowDateInt == dateInt) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 把inFormat格式的时间字符串转成outFormat格式的时间字符串
	 *
	 * @param formatTime
	 *            输入字符串
	 * @param inFormat
	 *            传入格式
	 * @param outFormat
	 *            输出格式
	 * @return
	 * @throws ParseException
	 */
	public static String formatTimeStr(String formatTime, String inFormat, String outFormat) throws ParseException {
		String resultTime = "";

		Date date = DateUtils.parseDateTime(formatTime, inFormat);
		resultTime = DateUtils.formatDateTime(date, outFormat);

		return resultTime;
	}

	/**
	 * Add by huangxl 20170105 计算两个日期之间相差的秒数
	 *
	 * @param startDate
	 *            较小的时间
	 * @param endDate
	 *            较大的时间
	 * @return 相差秒数
	 * @throws ParseException
	 */
	public static int secondBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / 1000;

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static Date getMonthOfFirst(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 将小时至0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		// 将分钟至0
		calendar.set(Calendar.MINUTE, 0);
		// 将秒至0
		calendar.set(Calendar.SECOND, 0);
		// 将毫秒至0
		calendar.set(Calendar.MILLISECOND, 0);
		// 获得当前月最后一天
		Date edate = calendar.getTime();
		return edate;

	}

	public static Date getMonthOfLast(Date date) {
		// 月最后一天
		date = getMonthOfFirst(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.add(Calendar.MILLISECOND, -1); // 再减一毫秒最后一天
		return calendar.getTime();

	}

	public static String getWeek(Date date){
		String[] weekOfDays = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		//一周第一天是否为星期天
		boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
			if(weekDay == 0){
				weekDay = 7;
			}
		}
		return weekOfDays[weekDay-1];
	}

	/**
	 * 返回星期
	 * @author Li Jiajie 20180130
	 * @param date
	 * @return
	 * */
	public static String getWeekName(Date date){
		String[] weekOfDays = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		//一周第一天是否为星期天
		boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
			if(weekDay == 0){
				weekDay = 7;
			}
		}
		return weekOfDays[weekDay-1];
	}

	/**
	 * 获取日期中文描述
	 * @author Li Jiajie 20180130
	 * @param sDate(格式：yyyy-MM-dd)
	 * @param isYear 是否返回年：Y=返回，N=不返回
	 * @param isMonth 是否返回月：Y=返回，N=不返回
	 * @param isDate 是否返回日：Y=返回，N=不返回
	 * @return
	 * */
	public static String getDateChineseDesc(String sDate, String isYear, String isMonth, String isDate){
		String dateChineseName = "";
		if(!StringUtils.isEmpty(sDate)){
			String strYear = sDate.substring(0, 4); // 年份
			String strMonth = sDate.substring(5, 7); // 月份
			String strDate = sDate.substring(8, 10); // 日期
			if ("Y".equals(isYear)) {
				dateChineseName = strYear + "年";
			}
			if("Y".equals(isMonth)){
				dateChineseName += strMonth + "月";
			}
			if("Y".equals(isDate)){
				dateChineseName += strDate + "日";
			}
		}
		return dateChineseName;
	}

	/**
	 * 解密加密数据
	 * @param key
	 * @return
	 */
	public static Date dencodeDate(String key) {
		/**
		 *  C#加密算法
		 var time = System.DateTime.Now;
		 System.DateTime startTime = TimeZone.CurrentTimeZone.ToLocalTime(new System.DateTime(1970, 1, 1, 0, 0, 0, 0));
		 long t = (time.Ticks - startTime.Ticks) / 10000;   //除10000调整为13位
		 long val = 2568 * t;
		 string key = val.ToString();
		 char[] arr = key.ToCharArray();
		 Array.Reverse(arr);
		 key = new string(arr);
		 */
		//1.倒排，2.除以2568
		char[] arr = key.toCharArray();
		StringBuffer aa = new StringBuffer();
		for (int i = arr.length-1; i >= 0; i--) {
			aa.append(arr[i]);
		}
		Long val = Long.valueOf(aa.toString());
		long t = val/2568;
		return new Date(t);
	}

	/**
	 * 时间戳转换成时间
	 * @param s 传入的时间戳
	 * @param dateFormat 传入的时间戳
	 * @return 返回格式化时间
	 */
	public static String timeStampToTime(String s,String dateFormat){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		long lt = new Long(s);
		Date date = new Date(lt);
		String res = simpleDateFormat.format(date);
		return res;
	}

	// 判断是否当天
	public static boolean isDay(Date orderTime){
		String today = DateUtils.formatDateTime(new Date(),DateUtils.NORMAL_DATE_FORMAT);
		String orderTimeStr = DateUtils.formatDateTime(orderTime,DateUtils.NORMAL_DATE_FORMAT);
		return today.equals(orderTimeStr);
	}

	// 判断是上午还是下午
	public static Integer isAmOrPm(Date orderTime){
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(orderTime);
		if (ca.get(GregorianCalendar.AM_PM) == 0) {
			return 1;
		} else {
			return 2;
		}
	}


	public static Date getTime(String dateTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.parse(dateTime);

	}

	public static Date getTime(Date date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.parse(df.format(date));

	}
	/**
	 * 判断时间是否在时间段内
	 *
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime,
										 Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		return isInTimeInterval(nowTime,beginTime,endTime);
	}

	public static void main(String[] args) throws Exception {

//		System.out.println(getMonthOfFirst(new Date()));
//		System.out.println(getMonthOfFirst(new Date()));
//		System.out.println(DateUtils.dateToString(new Date(), "HH:mm:ss.SSS"));
//
//		System.out.println(getWeek(new Date()));


//		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//		Date d = df.parse("08:45");
//		System.out.printf("d"+d);
//		System.out.printf("d"+df.format(d));

		String date = "2020-06-12 13:44";
		Date sdate = DateUtils.parseDateTime(date,DateUtils.DATETIME_HHMM_FORMAT);
		Date redate = getTime(sdate);
		System.out.println("redate"+DateUtils.formatDateTime(redate));
		Date amsTime = DateUtils.getTime("08:45");
		Date ameTime = DateUtils.getTime("11:45");
		Date pmsTime = DateUtils.getTime("13:45");
		Date pmeTime = DateUtils.getTime("15:45");
		System.out.println(DateUtils.belongCalendar(redate,amsTime,ameTime));
		System.out.println(DateUtils.belongCalendar(redate,pmsTime,pmeTime));
//		 Date date = Calendar.getInstance().getTime();
//		 logger.info(DateUtils.dateToString(date , DATETIME_FORMAT));
	}
}
