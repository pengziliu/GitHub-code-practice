
package com.lzn.mybatisplus.codegenerator.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	private static final ThreadLocal<DateFormat> COMMOM_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 一秒的毫秒数
	 */
	public static final long ONE_SECONDS_MICROSECONDS = 1000;

	/**
	 * 一小时秒数
	 */
	public static final int ONE_HOUR_SECONDS = 60 * 60;

	/**
	 * 一小时毫秒数
	 */
	public static final int ONE_HOUR_MICROSECONDS = 60 * 60 * 1000;

	/**
	 * 一小时的分钟数
	 */
	public static final int ONE_HOUR_MINUTES = 60;

	/**
	 * 一分钟的秒数
	 */
	public static final int ONE_MINUTE_SECONDS = 60;

	/**
	 * 一分钟的毫秒数
	 */
	public static final long ONE_MINUTE_MICROSECONDS = 60 * 1000;

	/**
	 * 一天的秒数
	 */
	public static int ONE_DAY_SECONDS = 60 * 24 * 60;

	/**
	 * 一天的毫秒
	 */
	public static long ONE_DAY_MICROSECONDS = 60 * 24 * 60 * 1000;

	/**
	 * 计算两个时间差，格式为hh:mm:ss
	 */
	public static String getDateDiff(Date beginDate, Date endDate) {
		DecimalFormat df = new DecimalFormat("00");

		long nd = 1000 * 60 * 60 * 24;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		// long ns = 1000;// 一秒钟的毫秒数

		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - beginDate.getTime();
		if (diff <= 0) {
			return null;
		}

		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long minute = diff % nd % nh / nm;// 计算差多少分钟
		// long second = diff % nd % nh % nm / ns;// 计算差多少秒

		StringBuffer buffer = new StringBuffer();
		if (day > 0) {
			buffer.append(day).append("天").append(df.format(hour)).append("小时").append(df.format(minute)).append("分");
		} else {
			buffer.append(hour).append("小时").append(df.format(minute)).append("分");
		}
		return buffer.toString();
	}
	
	
	public static String getDateDiff1(Date beginDate, Date endDate) {
		if(beginDate.before(endDate)){
			return getDateDiff(beginDate, endDate);
		}else{
			return "已超时" + getDateDiff(endDate, beginDate);
		}
	}

	/**
	 * 获取某个时间的本周一零点 注意 周日是一周的起点
	 */
	public static Date getMondayOfWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取某个时间的上周一零点 注意 周日是一周的起点
	 */
	public static Date getMondayOfLastWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		return calendar.getTime();
	}
	/**
	 * 获取某个时间点所在的周一  注意：周日算在本周  不算下一周起点
	 */
	public static Date getMondayOfChinaWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		if (i == 1) {//因国外周日是第一天  i==1 代表的是周日 在国内不符合  改变一下
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
		} else {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		return calendar.getTime();
	}

	/**
	 * 获取当前月份1号的 零点
	 */
	public static Date getStartOfMouth(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取上一月份1号的 零点
	 */
	public static Date getStartOfLastMouth(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH,-1);
		return calendar.getTime();
	}
	
	/**
	 * 获取某个时间的上一个零点
	 */
	public static Date getLastZeroTime(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取某个时间的下一个零点
	 */
	public static Date getNextZeroTime(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取昨天的零点
	 */
	public static Date getYesterdayZeroTime(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}
	
	/**
	 * 得到按天离传入时间最近的一次凌晨时间(即上一个凌晨)
	 * 
	 * @return
	 */
	public static long getLastWeeHoursByDay(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}

	/**
	 * 获取一月前的本日
	 */
	public static Date getLastMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.MONTH,-1);
		return c.getTime();
	}
	
	/**
	 * time1和time2的间隔天数 如果time1<time2则算出的天数为0
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getIntervalMoreAndEqualZeroDay(long time1, long time2) {
		long weeHour1 = getLastWeeHoursByDay(time1);
		long weeHour2 = getLastWeeHoursByDay(time2);
		int returnValue = (int) ((weeHour1 - weeHour2) / (ONE_DAY_SECONDS * 1000));
		if (returnValue < 0) {
			returnValue = 0;
		}
		return returnValue;
	}
	
	public static int getIntervalMoreAndEqualZeroDay(Date date1, Date date2) {
		long weeHour1 = getLastWeeHoursByDay(date1.getTime());
		long weeHour2 = getLastWeeHoursByDay(date2.getTime());
		int returnValue = (int) ((weeHour1 - weeHour2) / (ONE_DAY_SECONDS * 1000));
		if (returnValue < 0) {
			returnValue = 0;
		}
		return returnValue;
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return COMMOM_FORMAT.get().format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param fmt
	 * @return
	 */
	public static String formatDate(Date date, DateFormat fmt) {
		return fmt.format(date);
	}
	
	public static Date parseDate(String dateStr, DateFormat fmt) throws ParseException {
		return fmt.parse(dateStr);
	}
}
