package com.tfsp.library.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * 一个对{@link java.util.Date}对象进行各种转换的工具类. 可以快捷的在{@link java.util.Date}对象和{@link java.lang.String}对象之间进行转换.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.DateUtils.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-16			Shmilycharlene				初始化版本	
 * 
 */
public abstract class DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/** 系统默认的完整日期格式 yyyy-MM-dd HH:mm:ss:SSS, 包含时间, 精确到毫秒*/
	public static final java.lang.String DATE_LONG_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";
	/** 系统默认的短日期格式yyyy-MM-dd, 只包含年-月-日 */
    public static final java.lang.String DATE_PATTERN = "yyyy-MM-dd";
    public static final java.lang.String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final java.lang.String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期工具类中, 方法返回值为 :{@link java.lang.String}的集合, 用于日期格式转换, 快捷获取相关日期的操作
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class String{
    	/**
    	 * 将一个日期对象按给定的格式转换成字符串表达形式. 默认Pattern = yyyy-MM-dd HH:mm:ss
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.lang.String dateString(java.util.Date date){
    		return dateString(date, DATE_TIME_PATTERN);
    	}
    	
    	/**
    	 * 将一个日期对象按给定的格式转换成字符串表达形式.
    	 * @param date
    	 * @param pattern
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.lang.String dateString(java.util.Date date, java.lang.String pattern){
    		if(date == null) return "";
    		SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
    	}
    
    	/**
    	 *  将一个长整形数据转换为一个日期格式的字符串. 默认使用系统提供的完整格式: {@link #DATE_LONG_TIME_PATTERN};
    	 * @param dateValue
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.lang.String longString(long dateValue){
            return longString(dateValue, DATE_LONG_TIME_PATTERN);
    	}
    
    	/**
    	 * 将一个长整形数据转换为一个日期格式的字符串. 日期格式由调用者指定.
    	 * @param dateValue
    	 * @param pattern
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.lang.String longString(long dateValue, java.lang.String pattern){
    		Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateValue);
            return dateString(calendar.getTime(), pattern);
    	}
   
    	/**
    	 * 以字符串形式获取系统时间. <br>
    	 * 使用系统默认格式:{@link #DATE_LONG_TIME_PATTERN}返回.
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.lang.String systemDateString(){
    		 return systemDateString(DATE_LONG_TIME_PATTERN);
    	}
    	
    	public static java.lang.String systemDateString(java.lang.String pattern){
    		java.util.Date date = new java.util.Date();
   	     	return dateString(date, pattern);
    	}
    
    	/**
    	 *  转换日期格式. 将一个字符串形式的日期格式, 转换为指定的日期格式.
    	 * @param date
    	 * @param inPattern
    	 * @param outPattern
    	 * @return
    	 * <期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
		public static java.lang.String dateString(java.lang.String date, java.lang.String inPattern, java.lang.String outPattern) {
			if(date == null) return "";
			SimpleDateFormat format = new SimpleDateFormat(inPattern);
			SimpleDateFormat format1 = new SimpleDateFormat(outPattern);
			java.lang.String newDate = "";
			try{
				java.util.Date d = format.parse(date);
				newDate = format1.format(d);
			}catch (Exception ex){
				logger.error("Exception:{0}", ex);
			}
			return newDate;
		}
    
		/**
		 * 向指定日期添加指定（有符号的）天数
		 * @param date
		 * @param inPattern
		 * @param amount
		 * @return
		 * @
		 * <br>
		 * 创建日期: 2016年5月5日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static java.lang.String add(java.lang.String date, java.lang.String inPattern, int amount) {
			return add(date, inPattern, Calendar.DAY_OF_YEAR, amount);
		}
		
		public static java.lang.String add(java.lang.String date, java.lang.String inPattern, int field, int amount) {
			if(date == null) return "";
			java.util.Date d = Date.stringDate(date, inPattern);
			return dateString(DateUtils.add(d, field, amount), inPattern);
		}
		
		public static java.lang.String add(java.util.Date date, int field, int amount){
			if(date == null) return "";
			return dateString(DateUtils.add(date, field, amount));
		}
    
		/**
		 * 将给定的两个日期字符串按照指定的格式拼接起来, 拼接符用delimiter表示
		 * @param date1
		 * @param date2
		 * @param outPattern
		 * @param delimiter
		 * @return
		 * <br>
		 * 创建日期: 2016年5月5日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static java.lang.String concatenate(java.lang.String date1, java.lang.String date2, java.lang.String outPattern, java.lang.String delimiter){
			StringBuilder builder = new StringBuilder();
			try{
				builder.append(dateString(date1, DATE_PATTERN, outPattern));
				if(!StringUtils.equals(date1, date2)){
					builder.append(delimiter).append(dateString(date2, DATE_PATTERN, outPattern));
				}
			}catch (Exception e) {
				builder.delete(0, builder.length());//清空缓存
				builder.append(date1).append(delimiter).append(date2);
			}
			return builder.toString();
		}
   
		/**
		 * 以yyyy-MM-dd的格式返回昨天的日期表示
		 * @return
		 * @throws Exception
		 * <br>
		 * 创建日期: 2016年8月17日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static java.lang.String yesterday(){
			try{
				return dateString(DateUtils.add(Date.stringDate(Date.systemDate(), DATE_PATTERN), Calendar.DAY_OF_YEAR, -1), DATE_PATTERN);
			}catch (Exception e){
				logger.error("Exception: {0}", e);
			}
			return null;
		}
		
		public static java.lang.String yesterday(java.lang.String pattern){
			return dateString(DateUtils.Date.add(Date.systemDate(), Calendar.DAY_OF_YEAR, -1), pattern);
		}
    }
    
    /**
     * 日期工具类中, 方法返回值为 :{@link java.util.Date}的集合, 用于日期转换相关操作
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class Date{
    	/**
    	 * 将一个字符串形式的日期, 转换为{@link java.util.Date}对象. 默认Patter = yyyy-MM-dd HH:mm:ss
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 * @ 
    	 */
    	public static java.util.Date stringDate(java.lang.String date) {
    		return stringDate(date, DATE_TIME_PATTERN);
    	}
    	
    	/**
    	 * 将一个字符串形式的日期, 转换为{@link java.util.Date}对象.
    	 * @param date
    	 * @param pattern
    	 * @return
    	 * @
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.util.Date stringDate(java.lang.String date, java.lang.String pattern) {
    		try{
				if(StringUtils.isBlank(date)) return null;
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				return format.parse(date);
			}catch (ParseException e){
				logger.error("Exception: {0}", e);
				return null;
			}
    	}
    	
    	public static java.util.Date stringDate(java.util.Date date, java.lang.String pattern) {
    		try{
				if(date == null) return null;
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				return format.parse(String.dateString(date));
			}catch (ParseException e){
				logger.error("Exception: {0}", e);
				return null;
			}
    	}
    
    	public static java.util.Date systemDate(){
    		Calendar calendar = Calendar.getInstance(Locale.CHINA);
        	return calendar.getTime();
    	}
    	
    	/**
    	 * 向指定日期添加指定（有符号的）天数
    	 * @param date
    	 * @param inPattern
    	 * @param amount
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static java.util.Date add(java.lang.String date, java.lang.String inPattern, int amount) {
    		return add(date, inPattern, Calendar.DAY_OF_YEAR, amount);
    	}
    	
    	public static java.util.Date add(java.lang.String date, java.lang.String inPattern, int field, int amount) {
    		java.util.Date d = stringDate(date, inPattern);
    		return DateUtils.add(d, field, amount);
    	}
    	
        /**
         * 在给定的日期和日期字段上, 加上有符号的数量.
         * @param date
         * @param field
         * @param amount
         * @return date
         * @throws ParseException
         * <br>
         * 创建日期: 2013-1-17 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date add(java.util.Date date, int field, int amount) {
        	return DateUtils.add(date, field, amount);
        }
        
        public static java.util.Date addDays(java.util.Date date, int amount){
        	return add(date, Calendar.DAY_OF_YEAR, amount);
        }
    
        /**
         * 获取本月的第一天
         * @return
         * <br>
         * 创建日期: 2016年5月5日 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date firstDayOfMonth(){
        	Calendar calendar = Calendar.getInstance(Locale.CHINA);
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
        	return calendar.getTime();
        }
    
        /**
         * 获取给定日期所在月的第一天, 是否保留时, 分, 秒等信息由retainOther指定.
         * @param date
         * @param retainOther
         * @return
         * <br>
         * 创建日期: 2016年5月5日 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date firstDayOfMonth(java.util.Date date, boolean retainOther){
        	Calendar calendar = Calendar.getInstance();
    		calendar.setTime(date);
    		calendar.set(Calendar.DAY_OF_MONTH, 1);
    		if(!retainOther){
    			calendar.set(Calendar.HOUR_OF_DAY, 0);
    			calendar.set(Calendar.MINUTE, 0);
    			calendar.set(Calendar.SECOND, 0);
    			calendar.set(Calendar.MILLISECOND, 0);
    		}
    		return calendar.getTime();
        }
        
        /**
         * 获取给定日期所在月的第一天, 清除时, 分, 秒, 毫秒数据.
         * @param date
         * @return
         * <br>
         * 创建日期: 2016年5月5日 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date firstDayOfMonth(java.util.Date date){
        	return firstDayOfMonth(date, false);
        }
        
        /**
         * 获取指定日期所在月的最后一天, 不包含时, 分, 秒, 毫秒.
         * @param date
         * @return
         * <br>
         * 创建日期: 2016年5月5日 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date lastDayOfMonth(java.util.Date date){
        	return lastDayOfMonth(date, false);
        }
        
        /**
         * 获取指定日期当月的最后一天, 是否保留时, 分, 秒等信息有retainOther指定.
         * @param date
         * @param retainOther
         * @return
         * <br>
         * 创建日期: 2016年5月5日 <br>
         * 创  建  人: Shmilycharlene
         */
        public static java.util.Date lastDayOfMonth(java.util.Date date, boolean retainOther){
        	Calendar calendar = Calendar.getInstance();
    		calendar.setTime(date);
    		calendar.set(Calendar.DAY_OF_MONTH, 1);
    		calendar.add(Calendar.MONTH, 1);
    		calendar.add(Calendar.DAY_OF_MONTH, -1);
    		if(!retainOther){
    			calendar.set(Calendar.HOUR_OF_DAY, 0);
    			calendar.set(Calendar.MINUTE, 0);
    			calendar.set(Calendar.SECOND, 0);
    			calendar.set(Calendar.MILLISECOND, 0);
    		}
    		return calendar.getTime();
        }
        
        public static java.util.Date yesterday(){
        	return addDays(systemDate(), -1);
        }
    }
    
    /**
     * 日期工具类中, 方法返回值为 :{@link java.sql.Timestamp}的集合
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class Timestamp{
    	public static java.sql.Timestamp systemTimestamp(){
    		return new java.sql.Timestamp(System.currentTimeMillis());
    	}
    	
    	public static java.sql.Timestamp stringTimestamp(java.lang.String date, java.lang.String pattern) {
    		try{
				return new java.sql.Timestamp(Number.timeMillis(date, pattern));
			}catch (Exception e){
				logger.error("Exception: {0}", e);
				return null;
			}
    	}
    }

    /**
     * 日期工具类中, 方法返回值为 :int, long的方法集合
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class Number{
    	public static long currentTimeMillis() {
    		return System.currentTimeMillis();
    	}
    	
    	public static long timeMillis(java.lang.String date, java.lang.String pattern) {
    		java.util.Date d = Date.stringDate(date, pattern);
    		return d.getTime();
    	}
    	
    	/**
    	 * 获取指定日期与系统当前日期的天数偏移量的绝对值
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetDays(java.util.Date date) {
    		return (int)(DateUtils.offsetMilliSecond(date, Date.systemDate())/1000/60/60/24);
    	}
    	
    	/**
    	 * 计算给定两个日期之间的天数偏移量的绝对值.
    	 * @param date
    	 * @param date2
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetDays(java.util.Date date, java.util.Date date2) {
    		return (int)(DateUtils.offsetMilliSecond(date, date2)/1000/60/60/24);
    	}
    	
    	/**
    	 * 获取指定日期与系统当前日期的小时数偏移量的绝对值
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetHours(java.util.Date date) {
    		return (int)(DateUtils.offsetMilliSecond(date, Date.systemDate())/1000/60/60);
    	}
    	
    	/**
    	 * 计算给定两个日期之间的小时数偏移量的绝对值.
    	 * @param date
    	 * @param date2
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetHours(java.util.Date date, java.util.Date date2) {
    		return (int)(DateUtils.offsetMilliSecond(date, date2)/1000/60/60);
    	}
    	
    	/**
    	 * 获取指定日期与系统当前日期的分钟数偏移量的绝对值
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetMinutes(java.util.Date date) {
    		return (int)(DateUtils.offsetMilliSecond(date, Date.systemDate())/1000/60);
    	}
    	
    	/**
    	 * 计算给定两个日期之间的分钟数偏移量的绝对值.
    	 * @param date
    	 * @param date2
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetMinutes(java.util.Date date, java.util.Date date2) {
    		return (int)(DateUtils.offsetMilliSecond(date, date2)/1000/60);
    	}
    	
    	/**
    	 * 获取指定日期与系统当前日期的秒数偏移量的绝对值
    	 * @param date
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetSeconds(java.util.Date date) {
    		return (int)(DateUtils.offsetMilliSecond(date, Date.systemDate())/1000);
    	}
    	
    	/**
    	 * 计算给定两个日期之间的秒数偏移量的绝对值.
    	 * @param date
    	 * @param date2
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static int offsetSeconds(java.util.Date date, java.util.Date date2) {
    		return (int)(DateUtils.offsetMilliSecond(date, date2)/1000);
    	}
    }
    
    /**
     * 日期工具类中, 方法返回值为 :{@link java.util.List}的方法集合, 用于计算两个日期之间的日期列表相关操作
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class Collections{
    	/**
    	 * 计算两个日期之间所有的日期
    	 * @param start
    	 * @param end
    	 * @return
    	 * <br>
    	 * 创建日期: 2016年5月5日 <br>
    	 * 创  建  人: Shmilycharlene
    	 */
    	public static List<java.lang.String> listDate(java.lang.String start, java.lang.String end) {
    		return DateUtils.listDate(start, end, DATE_PATTERN);
    	}
    	
    	public static List<java.lang.String> listDate(java.lang.String startDateStr, java.lang.String endDateStr, java.lang.String pattern) {
    		return DateUtils.listDate(startDateStr, endDateStr, pattern);
    	}
    }
    
    
    /**
     * 日期工具类中, 方法返回值为 :boolean值的方法集合, 用于进行日期检查相关的操作
     * @author Shmilycharlene
     * @version 1.0
     */
    public static class Checker{
    	public static boolean beforeDate(java.util.Date date1, java.util.Date date2) {
    		return before(date1, date2, false);
    	}
    	
    	public static boolean afterDate(java.util.Date date1, java.util.Date date2) {
    		return after(date1, date2, false);
    	}
    	
    	public static boolean beforeDay(java.util.Date date1, java.util.Date date2) {
    		return before(date1, date2, true);
    	}
    	
    	public static boolean afterDay(java.util.Date date1, java.util.Date date2) {
    		return after(date1, date2, true);
    	}
    	
    	public static boolean beforeToday(java.util.Date date) {
    		return before(date, Date.systemDate(), true);
    	}
    	
    	public static boolean afterToday(java.util.Date date) {
    		return after(date, Date.systemDate(), true);
    	}
    }
    
    /**
     * 在给定的日期和日期字段上, 加上有符号的数量.
     * @param date
     * @param field
     * @param amount
     * @return date
     * @throws ParseException
     * <br>
     * 创建日期: 2013-1-17 <br>
     * 创  建  人: Shmilycharlene
     */
    private static java.util.Date add(java.util.Date date, int field, int amount) {
    	Calendar calendar = Calendar.getInstance(Locale.CHINA);
    	calendar.setTime(date);
    	calendar.add(field, amount);
    	return calendar.getTime();
    }
    
    /**
     * 计算给定两个日期之间的毫秒偏移量的绝对值.
     * @param date
     * @return days
     * <br>
     * 创建日期: 2013-1-17 <br>
     * 创  建  人: Shmilycharlene
     */
    private static long offsetMilliSecond(java.util.Date date, java.util.Date endDate){
    	Calendar calendar = Calendar.getInstance(Locale.CHINA);
    	calendar.setTime(date);
    	long start = calendar.getTimeInMillis();
    	
    	calendar.setTime(endDate);
    	long end = calendar.getTimeInMillis();
    	
    	long offsetInMillis = Math.abs(end - start);
    	return offsetInMillis;
    }

	private static List<java.lang.String> listDate(java.lang.String startDateStr, java.lang.String endDateStr, java.lang.String pattern) {
		List<java.lang.String> dateList = new ArrayList<java.lang.String>();
		if(StringUtils.equals(pattern, "yyyy-MM")){
			startDateStr += "-01";
			endDateStr += "-01";
		}
		java.util.Date startDate = Date.stringDate(startDateStr, DATE_PATTERN);
		java.util.Date endDate = Date.stringDate(endDateStr, DATE_PATTERN);
		if(startDate.getTime() > endDate.getTime()){
			dateList.add(startDateStr);
			return dateList;
		}
		while(startDate.getTime() <= endDate.getTime()){
			dateList.add(String.dateString(startDate, pattern));
			if(StringUtils.equals(pattern, "yyyy-MM")){
				startDate = Date.add(startDate, Calendar.MONTH, 1);
			}else{
				startDate = Date.addDays(startDate, 1);
			}
		}
		return dateList;
	}
	
	/**
	 * 检查给定的两个日期, 比较第一个日期是否在第二个日期之前
	 * dayOnly用于指示在计算时, 是否包含时, 分, 秒, 毫秒等精确数据.
	 * @param date1
	 * @param date2
	 * @param dayOnly
	 * @return
	 * <br>
	 * 创建日期: 2016年5月5日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static boolean before(java.util.Date date1, java.util.Date date2, boolean dayOnly) {
		if(dayOnly) {
			try{
				date1 = Date.stringDate(String.dateString(date1, DATE_PATTERN));
				date2 = Date.stringDate(String.dateString(date2, DATE_PATTERN));
			}catch (Exception e){
			}
		}
		return date1.getTime() <= date2.getTime();
	}
	
	/**
	 * 检查给定的两个日期, 比较第一个日期是否在第二个日期之后
	 * dayOnly用于指示在计算时, 是否包含时, 分, 秒, 毫秒等精确数据.
	 * @param date1
	 * @param date2
	 * @param dayOnly
	 * @return
	 * <br>
	 * 创建日期: 2016年5月5日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private static boolean after(java.util.Date date1, java.util.Date date2, boolean dayOnly) {
		return !before(date1, date2, dayOnly);
	}
	
}

