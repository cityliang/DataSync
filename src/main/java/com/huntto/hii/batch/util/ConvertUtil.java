package com.huntto.hii.batch.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
public final class ConvertUtil {

	public static final char[] CHINESE_NUMBERS = {'〇','一','二','三','四','五','六','七','八','九'};
    private ConvertUtil() {
    }

    /**
	 * String 2 Long
	 */
	public static Long toLong(String str){
		Long i = null;
		try {
			if(Nulls.isNotEmpty(str))
				i = new Long(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	/**
	 * String 2 Long,有默认值
	 */
	public static Long toLong(String str, long defaultVal){
		Long i = new Long(defaultVal);
		try {
			if(Nulls.isNotEmpty(str)){
				i = new Long(str);
			}
		} catch (Exception e) {
		}
		return i;
	}
	
	/**
	 * Number -> Long类型转换
	 */
	public static Long toLong(Number value){
		if(value == null) return null;
		return value.longValue();
	}

	/**
	 * BigDecimal类型转换：转不了，返回null
	 */
	public static BigDecimal toBigDecimal(String str){
		BigDecimal bd = null;
		try {
			if(Nulls.isNotEmpty(str)){
				bd = new BigDecimal(str);
			}
		} catch (Exception e) {
		}
		return bd;
	}
	
	/**
	 * BigDecimal类型转换：转不了，抛异常
	 */
	public static BigDecimal toBigDecimal(String str, String expMsg){
		BigDecimal bd = null;
		try {
			if(Nulls.isNotEmpty(str)){
				bd = new BigDecimal(str);
			} else {
				throw new RuntimeException(expMsg);
			}
		} catch (Exception e) {
			throw new RuntimeException(expMsg);
		}
		return bd;
	}
	
	/**
	 * BigDecimal类型转换：转不了，默认值
	 */
	public static BigDecimal toBigDecimal(String str, double defaultVal){
		BigDecimal bd = new BigDecimal(defaultVal);
		try {
			if(Nulls.isNotEmpty(str)){
				bd = new BigDecimal(str);
			}
		} catch (Exception e) {
		}
		return bd;
	}
	
	/**
	 * Short类型转换
	 */
	public static Short toShort(String str){
		Short st = null;
		if(null != str && !"".equals(str)){
			st = new Short(str);
		}
		return st;
	}
	
	/**
	 * Integer类型转换
	 */
	public static Integer toInteger(String str){
		Integer i = null;
		if(null != str && !"".equals(str)){
			i = new Integer(str);
		}
		return i;
	}
	
	/**
	 * Number -> Integer类型转换
	 */
	public static Integer toInteger(Number value){
		if(value == null) return null;
		return value.intValue();
	}
	
	/**
	 * Float类型转换
	 */
	public static Float toFloat(String str){
		Float i = null;
		if(null != str && !"".equals(str)){
			i = new Float(str);
		}
		return i;
	}
	/**
	 * Float类型转换
	 */
	public static Float toFloat(String str,float defaultVal){
		Float i = defaultVal;
		if(null != str && !"".equals(str)){
			i = new Float(str);
		}
		return i;
	}
	
	/**
	 * Double类型转换
	 */
	public static Double toDouble(String str){
		Double i = null;
		if(null != str && !"".equals(str)){
			i = new Double(str);
		}
		return i;
	}
		
	/**
	 * 字符串转日期，优先考虑DateConverter
	 */
	public static Date toParseDate(String time)  {
		/** */
		/**
		 * 字符串转换为java.util.Date 支持格式为:
		 * yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD' 
		 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00' 
		 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm' 
		 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' 
		 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am'
		 * 
		 * @param time
		 *            String 字符串
		 * @return Date 日期
		 */
		try
		{
		SimpleDateFormat formatter;
		
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat(" yyyy.MM.dd G 'at' hh:mm:ss z ");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + " 公元 "
			+ time.substring(tempPos + " AD ".length()); // china
			formatter = new SimpleDateFormat(" yyyy.MM.dd G 'at' hh:mm:ss z ");
		}
		
		tempPos = time.indexOf("-");
		
		if (time.indexOf(".") != -1) {
			time = time.substring(0, time.indexOf("."));
		}
		
		if (tempPos > -1) {//包含“-”
			if(time.indexOf(":") == -1){
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			} else if (time.indexOf(":") == time.lastIndexOf(":")) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
		} else if (time.indexOf("/") > -1) {//包含“/”
			if(time.indexOf(":") == -1){
				formatter = new SimpleDateFormat("yyyy/MM/dd");
			}else{
				formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			}
		} else if (time.substring(0, 8).indexOf("-") == -1) {
			time = time.substring(0, 8);
			formatter = new SimpleDateFormat("yyyyMMdd");
		} else {
			formatter = new SimpleDateFormat("HH:mm");
		}
		
		ParsePosition pos = new ParsePosition(0);
		java.util.Date ctime = formatter.parse(time, pos);
		return ctime;
		
		}
		catch(Exception ex)
		{ return null;}
		
	}
	
	/**
	 * 数字类型的转换为字符型
	 * @param obj
	 * @return
	 */
	public static String toString(Number obj){
		if(obj == null){
			return null;
		}
		return obj.toString();
	}

	/**
	 * 格式化日期
	 */
	public static String formatDate(Date date, String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.format(date);
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 取汉字年：2014:二零一四
	 * @param date
	 * @return
	 */
	public static String toCYear(Date date){
		String year = formatDate(date, "yyyy");
		return toChineseNumberString(year);
	}
	
	/**
	 * 取汉字月。
	 * @param date
	 * @return
	 */
	public static String toCMonth(Date date){
		String month = formatDate(date, "M");
		
		if(month.length()==0) {
			return "";
		}
		
		if(month.length()==1) {
			return toChineseNumberString(month);
		}
		
		String c2 = month.substring(1);
		c2 = ("0".equals(c2) ? "":toChineseNumberString(c2));
		
		return ( "十"+c2 );
	}
	
	/**
	 * 取汉字日
	 * @param date
	 * @return
	 */
	public static String toCDay(Date date){
		String day = formatDate(date, "d");
		
		if(day.length()==0) {
			return "";
		}
	
		if(day.length()==1) {
			return toChineseNumberString(day);
		}
		
		String c1 = day.substring(0,1);
		String c2 = day.substring(1);
		
		c1 = ("1".equals(c1) ? "":toChineseNumberString(c1));
		c2 = ("0".equals(c2) ? "":toChineseNumberString(c2));
		
		return (c1 + "十" + c2);
	}
	
	/**
	 * 取汉字日期（到日）: 
	 * @param date
	 * @return
	 */
	public static String toCDateString(Date date){
		if(date == null) return "　　　　年　　月　　日";
		
		String year = toCYear(date);
		String month = toCMonth(date);
		String day = toCDay(date);
		return year + "年" + month + "月" + day + "日";
	}
	
	/**
	 * 转为汉字：2014:二0一四
	 * @param numbers
	 * @return
	 */
	public static String toChineseNumberString(String numbers){
		if(numbers == null || numbers.length()==0) return "";
		StringBuilder sb = new StringBuilder(numbers.length());
		for(int i=0; i<numbers.length(); i++){
			sb.append(CHINESE_NUMBERS[Integer.parseInt(String.valueOf(numbers.charAt(i)))]);
		}
		return sb.toString();
	}
	
	/**
	 * 格式化日期<br />
	 * @deprecated 作废（函数名字错误）
	 * @see #formatDate
	 */
	@Deprecated
	public static String dataFormat(Date date, String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 获取当前日期第一天
	 */
	public static Date getFirstDayByMonth(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();   
		cal.setTime(date);   
		int stDate = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-");
		String startDate1 = sdf.format(date).concat(String.valueOf(stDate));
		return toParseDate(startDate1);
	}
	
	/**
	 * 获取当前提前最后一天
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDayByMonth(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();   
		cal.setTime(date);   
		int enDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-");
		String endDate1 = sdf.format(date).concat(String.valueOf(enDate));
		return toParseDate(endDate1);
	}
	
	/**
	 * 获取当前日期下一天
	 */
	public static Date getNexDate(Date date)throws Exception{
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date);   
		int day=calendar.get(Calendar.DATE);   
		calendar.set(Calendar.DATE,day+1);
		Date dat = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String  date1   = sdf.format(dat) ; 
		Date d = sdf.parse(date1);
		return d;
	}
	/**
	 * 获取当前日期前一天
	 */
	public static Date getForwardOneDate(Date date){
		try {
			Calendar calendar = Calendar.getInstance();   
			calendar.setTime(date);   
			int day=calendar.get(Calendar.DATE);   
			calendar.set(Calendar.DATE,day - 1);
			Date dat = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
			String  date1  = sdf.format(dat) ; 
			Date d = sdf.parse(date1);
			return d;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取当前日期 N年后 的前一天
	 * @param year	几年后
	 * @param date	当前日期
	 * @return
	 */
	public static Date getForwardOneDateAfterNYear(int year,Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
			Calendar calendar = Calendar.getInstance();   
			calendar.setTime(date); 
			String thisYear = sdf.format(date).substring(0,4);
			int day=calendar.get(Calendar.DATE);   
			calendar.set(Calendar.DATE,day - 1);
			calendar.set(Calendar.YEAR,Integer.valueOf(thisYear)+year);
			Date dat = calendar.getTime();
			String  date1  = sdf.format(dat) ; 
			Date d = sdf.parse(date1);
			return d;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取当前日期前10天
	 */
	public static Date getForwardTenDate(Date date){
	  try {
	    Calendar calendar = Calendar.getInstance();   
	    calendar.setTime(date);   
	    int day=calendar.get(Calendar.DATE);   
	    calendar.set(Calendar.DATE,day - 10);
	    Date dat = calendar.getTime();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    String  date1  = sdf.format(dat) ; 
	    Date d = sdf.parse(date1);
	    return d;
    } catch (Exception e) {
      return null;
    }
		
	}
	/**
	 * 获取当前日期前几天
	 */
	public static Date getForwardSumeDate(Date date,int days){
		try {
			Calendar calendar = Calendar.getInstance();   
			calendar.setTime(date);   
			int day=calendar.get(Calendar.DATE);   
			calendar.set(Calendar.DATE,day - days);
			Date dat = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
			String  date1  = sdf.format(dat) ; 
			Date d = sdf.parse(date1);
			return d;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取当前日期后7天
	 */
	public static Date getSevenDate(Date date){
		 try {
		    Calendar calendar = Calendar.getInstance();   
		    calendar.setTime(date);   
		    int day=calendar.get(Calendar.DATE);   
		    calendar.set(Calendar.DATE,day + 6);
		    Date dat = calendar.getTime();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		    String  date1  = sdf.format(dat) ; 
		    Date d = sdf.parse(date1);
		    return d;
	    } catch (Exception e) {
	      return null;
	    }
	}
	
	/**
	 * 日期计算，加碱年或曰或日
	 * @param type Y:年 M 月 D 日
	 * @param date
	 * @return
	 */
	public static Date addDate(char type, Date date, int offset){
		int nType = 0;
		
		if(type == 'Y') nType = Calendar.YEAR;
		else if(type == 'M') nType = Calendar.MONTH;
		else if(type == 'D') nType = Calendar.DATE;
		else throw new RuntimeException("类型不支持[" + type + "]");
		
	    Calendar calendar = Calendar.getInstance();   
	    calendar.setTime(date);
	    calendar.add(nType, offset);
	    
	    date = calendar.getTime();
		return date;
	}
	
	/**
	 * 日期计算，同时加碱年曰日
	 * @return
	 */
	public static Date addDate(Date date, int year, int month, int day){
		
	    Calendar calendar = Calendar.getInstance();   
	    calendar.setTime(date);
	    
	    if(year != 0 ) calendar.add(Calendar.YEAR, year);
	    if(month != 0 ) calendar.add(Calendar.MONTH, month);
	    if(day != 0 ) calendar.add(Calendar.DATE, day);
	    
	    date = calendar.getTime();
		return date;
	}
	
	/**
	 * 处理当前日期去掉小时分钟秒
	 */
	public static Date getCurrentDate(Date date)throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
		String  date1   = sdf.format(date) ; 
		Date d = sdf.parse(date1);
		return d;
	}
	
	/**
	 * 处理行政区划代码
	 * 只取行政编码的有效位
	 */
	public static String handleXZQH(String qhdm){
		StringBuffer sb = new StringBuffer();
		if(qhdm.length() == 9){
			String sheng = qhdm.substring(0,2);//省
			String shi = qhdm.substring(2,4);//市
			String xq = qhdm.substring(4,6);//县区
			String jd = qhdm.substring(6,9);//乡镇街道
			if(sheng.indexOf("00") == -1){
				sb.append(sheng);
			}
			if(shi.indexOf("00") == -1){
				sb.append(shi);
			}
			if(xq.indexOf("00") == -1){
				sb.append(xq);
			}
			if(jd.indexOf("000") == -1){
				sb.append(jd);
			}
			return sb.toString();
		}else{
			return SQLUtil.escapeSQL(qhdm);
		}
	}
	/**
	 * 处理行政区划代码
	 * 将行政区划代码补齐九位
	 */
	public static String handleXZQH2Nine(String qhdm){
		StringBuffer sb = new StringBuffer();
		int len = qhdm.length();
		if(len != 9){
			sb.append(qhdm);
			for (int i = 0; i < 9 - len; i++) {
				sb.append("0");
			}
			return sb.toString();
		}else{
			return qhdm;
		}
	}
	/**
	 * 处理行政区划代码
	 * 将行政区划代码补齐6位
	 */
	public static String handleXZQH2Six(String qhdm){
		StringBuffer sb = new StringBuffer();
		int len = qhdm.length();
		if(len < 6){
			sb.append(qhdm);
			for (int i = 0; i < 6 - len; i++) {
				sb.append("0");
			}
			return sb.toString();
		}else{
			return qhdm.substring(0,6);
		}
	}
	
	/**
	 * 生成sql select语句中的regioncode
	 * @param regionCode 行政区划代码，9位
	 * @param regionCol 表中的根据行政区划查询的列名
	 * @return
	 */
	public static String generateSelectRegionCode(String regionCode,String regionCol){
		String str = handleXZQH(regionCode);
		switch (str.length()) {
		case 2:
			return "concat(substr("+regionCol+",1,4),'00000')";
		case 4:
			return "concat(substr("+regionCol+",1,6),'000')";
		case 6:
			return "substr("+regionCol+",1,9)";
		default:
			return "substr("+regionCol+",1,9)";
		}
	}
	
	/**
	 * 生成sql where like语句中的regioncode
	 * @param regionCode 行政区划代码，9位
	 * @return
	 */
	public static String generateWhereLikeRegionCode(String regionCode){
		String str = SQLUtil.escapeSQL(handleXZQH(regionCode));
		switch (str.length()) {
		case 2:
			return "'"+str+"%00000'";
		case 4:
			return "'"+str+"%000'";
		case 6:
			return "'"+str+"%'";
		default:
			return "'"+str+"'";
		}
	}
	
	/**
	 * 生成sql where <>语句中的regioncode
	 * @param regionCode 行政区划代码，9位
	 * @return
	 */
	public static String generateWhereNQRegionCode(String regionCode){
		String str = SQLUtil.escapeSQL(handleXZQH(regionCode));
		switch (str.length()) {
		case 2:
			return "'"+str+"0000000'";
		case 4:
			return "'"+str+"00000'";
		case 6:
			return "'"+str+"000'";
		default:
			return "'"+str+"'";
		}
	}
	
	/**
	 * 生成sql group by语句中的regioncode
	 * @param regionCode 行政区划代码，9位
	 * @return
	 */
	public static String generateGroupByRegionCode(String regionCode,String regionCol){
		String str = SQLUtil.escapeSQL(handleXZQH(regionCode));
		switch (str.length()) {
		case 2:
			return "substr("+regionCol+",1,4)";
		case 4:
			return "substr("+regionCol+",1,6)";
		case 6:
			return "substr("+regionCol+",1,9)";
		default:
			return "substr("+regionCol+",1,9)";
		}
	}
	
	public static String queryAreaCode(String areaCode) {
		String QU = "000";
		String SHI = "00000";
		String SHENG = "0000000";
		if (areaCode != null) {
			if (areaCode.endsWith(SHENG)) {
				areaCode = areaCode.replace(SHENG, "");
			} else if (areaCode.endsWith(SHI)) {
				areaCode = areaCode.replace(SHI, "");
			} else if (areaCode.endsWith(QU)) {
				areaCode = areaCode.replace(QU, "");
			} else {

			}
		}
		return areaCode;
	}
	
	/**
	 * 系统生成标识管理相对人的唯一编号
	 * 推荐使用S18，管理相对人所属行政区划（精确至区县，六位）的编码和系统日期（六位）、系统自动生成的流水号（六位）
	 * @param qhdm 地区编码 （精确至区县，六位）
	 * @deprecated 18位作废，国家支持32位，改用32位
	 * @see #generateCOMP_NO32
	 */
	public static String generateCOMP_NO(String qhdm){
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		sb.append(qhdm.substring(0, 6));
		String dateTimeStr = formatDate(now,"yyMMddhhmmss");
		sb.append(dateTimeStr);
		return sb.toString();
	}
	
	/**
	 * 系统生成标识管理相对人的唯一编号
	 * 不使用S18，使用32位，管理相对人所属行政区划（精确至区县，六位）的编码和系统日期（六位）、时间（六位）、毫秒（3位）+6位随机数
	 * @param qhdm 地区编码 （精确至区县，六位）
	 */
	public static String generateCOMP_NO32(String qhdm){
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		sb.append(rfix(qhdm, 6, '0')); //6位
		String dateTimeStr = formatDate(now,"yyMMddhhmmssSSS");//15位
		sb.append(dateTimeStr);
		sb.append("00000"); //6位
		sb.append(getRandomNum(6));
		return sb.toString();
	}
	
	/**
	 * 系统生32位唯一编号
	 */
	public static String generateID(){
		String uuid=java.util.UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
	/**
	 *生成一个时间戳字符串，用于附件上传 
	 * @return 时间戳字符串，例如“20130101090101”
	 */
	public static String generateTimeStamp(){
		Date today = new Date();
		String year = String.valueOf(today.getYear()+1900);
		int mm = today.getMonth()+1;
		String month = String.valueOf(mm);
		if(mm<10){
			month = "0"+month;
		}
		int dd = today.getDate();
		String day = String.valueOf(dd);
		if(dd<10){
			day = "0"+day;
		}
		int hh=today.getHours();
		String hour = String.valueOf(hh);
		if(hh<10){
			hour = "0"+hour;
		}
		int min = today.getMinutes();
		String minutes = String.valueOf(min);
		if(min<10){
			minutes = "0"+minutes;
		}
		int sec = today.getSeconds();
		String seconds = String.valueOf(sec);
		if(sec<10){
			seconds = "0"+seconds;
		}
		String time = year+month+day+hour+minutes+seconds;
		return time;
	}
	/**
	 * 生成一个当前时间字符串，格式是"yyyy-MM-dd hh:mm:ss"
	 * @return 当前时间字符串
	 */
	public static String getTimeOfNow(){
		Date today = new Date();
		String year = String.valueOf(today.getYear()+1900);
		int mm = today.getMonth()+1;
		String month = String.valueOf(mm);
		if(mm<10){
			month = "0"+month;
		}
		int dd = today.getDate();
		String day = String.valueOf(dd);
		if(dd<10){
			day = "0"+day;
		}
		int hh=today.getHours();
		String hour = String.valueOf(hh);
		if(hh<10){
			hour = "0"+hour;
		}
		int min = today.getMinutes();
		String minutes = String.valueOf(min);
		if(min<10){
			minutes = "0"+minutes;
		}
		int sec = today.getSeconds();
		String seconds = String.valueOf(sec);
		if(sec<10){
			seconds = "0"+seconds;
		}
		String time = year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+seconds;
		return time;
	}
	
	/**
	 * 返回一个当前日期的字符串，格式是yyyy-MM-dd
	 * @return
	 */
	public static String getDateOfNow(){
		Date today = new Date();
		String year = String.valueOf(today.getYear()+1900);
		int mm = today.getMonth()+1;
		String month = String.valueOf(mm);
		if(mm<10){
			month = "0"+month;
		}
		int dd = today.getDate();
		String day = String.valueOf(dd);
		if(dd<10){
			day = "0"+day;
		}
		String time = year+"-"+month+"-"+day;
		return time;
	}
	/**
	 * 返回一个当前日期的字符串，格式是yyyy-MM-dd
	 * @return
	 */
	public static String getDateOfNow2(){
		Date today = new Date();
		String year = String.valueOf(today.getYear()+1900);
		int mm = today.getMonth()+1;
		String month = String.valueOf(mm);
		if(mm<10){
			month = "0"+month;
		}
		int dd = today.getDate();
		String day = String.valueOf(dd);
		if(dd<10){
			day = "0"+day;
		}
		String time = year+"年  "+month+"月  "+day+"日";
		return time;
	}
	/**
	 * 生成附件上传的唯一标示，每个pojo类统一字段FJUPLOADID
	 * @return
	 */
	public static String generateFJUploadID(){
		return ConvertUtil.generateID();
	}
	
	/**
	 * 生成报告卡头
	 */
	public static String prepBGKXML(String bizType,String speCode,
			String operate,String regionCode,String sendTime,String sendCode){
		StringBuffer sb = new StringBuffer();
		sb.append("<DSCR>\n");
		sb.append("<BIZTYPE>"+bizType+"</BIZTYPE>\n");
		sb.append("<SPECODE>"+speCode+"</SPECODE>\n");
		sb.append("<OPERATE>"+operate+"</OPERATE>\n");
		sb.append("<REGIONCODE>"+regionCode+"</REGIONCODE>\n");
		sb.append("<SENDTIME>"+sendTime+"</SENDTIME>\n");
//		sb.append("<VERSIONS>2012000002</VERSIONS>\n"); //2014/8/1开始生效
		sb.append("<VERSIONS>2018000001</VERSIONS>\n"); //2018/01/01开始生效
		sb.append("<SENDCODE>"+sendCode+"</SENDCODE>\n");
		sb.append("</DSCR>\n");
		return sb.toString();
	}

	/**
	 * 转化为字符串（可设定小数点位数以及舍入模式）
	 * @param value
	 * @param maxFraDigits
	 * @param roundingMode
	 * @return
	 */
	public static String toString(Double value, int maxFraDigits, RoundingMode roundingMode) {
		if(value == null) return "0";
		DecimalFormat df = new DecimalFormat("0.#");
		df.setRoundingMode(roundingMode);
		df.setMaximumFractionDigits(maxFraDigits);
		String result = df.format(value);
		return result;
	}

	/**
	 * 许可证里取流水号
	 * @param hEALTH_LICENSE
	 * @return
	 */
	public static String getLicenseNum(String hEALTH_LICENSE) {
		return getLicenseNum(hEALTH_LICENSE, "第", "号");
	}

	/**
	 * 许可证里取流水号
	 * @param hEALTH_LICENSE
	 * @return
	 */
	private static String getLicenseNum(String hEALTH_LICENSE, String begin,String end) {
		if(hEALTH_LICENSE == null) return "";
		
		int beginIndex = hEALTH_LICENSE.lastIndexOf(begin);
		int endIndex = hEALTH_LICENSE.lastIndexOf(end);
		
		if(beginIndex==-1 || endIndex==-1 || beginIndex > endIndex){
			return "";
		}
		
		return hEALTH_LICENSE.substring(beginIndex+1, endIndex);
	}

	/**
	 * 为空时，取第二个值
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Date nvl(Date value, Date defaultValue) {
		return (value!=null?value:defaultValue);
	}
	
	/**
	 * 取固定长度，不足右边补字符
	 * @param value
	 * @param len
	 * @param defaultChar
	 * @return
	 */
	public static String rfix(String value, int len, char defaultChar) {
		value = (value == null ? "": value);
		if(value.length()==len) return value;
		if(value.length()>len) return value.substring(0, len);
		
		StringBuilder buff = new StringBuilder(value);
		for(int i=value.length(); i<len; i++) buff.append(defaultChar);
		return buff.toString();
	}

	/**
	 * 转换为大写字符
	 * @param value
	 * @return
	 */
	public static String toUpperCase(String value){
		return (value != null ? value.toUpperCase():"");
	}

	/**
	 * 涉水产品批件号格式化
	 */
	public static String formatSccpPjLicense(String year, String pjNo) {
		return String.format("浙卫水字（%s）第%s号", year, pjNo);
	}
	
	/**
	 * 涉水进口产品批件号格式化，模板：浙卫水进字（年份）XXXX号
	 */
	public static String formatjkSccpPjLicense(String year, String pjNo) {
		return String.format("浙卫水进字（%s）%s号", year, pjNo);
	}
	
	/**
	 * 消毒产品生产企业许可证格式化
	 */
	public static String formatXdcpLicense(String commaValues) {
		try {
			String[] values = commaValues.split(",");
			return String.format("%s卫消证字[%s]第%s号", values[0].trim(), values[1].trim(), values[2].trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commaValues;
	}
	
	/**
	 * 放射卫生许可证格式化
	 */
	public static String formatFswsLicense(String commaValues) {
		try {
			String[] values = commaValues.split(",");
			return String.format("%s(%s)卫放证字(%s)第(%s)号", values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commaValues;
	}

	/**
	 * 餐饮具集中消毒单位许可证格式化
	 */
	public static String formatCyjxdLicense(String commaValues) {
		try {
			String[] values = commaValues.split(",");
			return String.format("%s卫餐消证字[%s]第%s号", values[0].trim(), values[1].trim(), values[2].trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commaValues;
	}

	/**
	 * 转换为下载显示文件名（为了避免乱码）
	 * @return
	 */
	public static String toDownloadFileName(String filename) {
		try {
			return new String(filename.getBytes(),"ISO8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 转换为检查表的1 "√":-1 "╳"
	 * @param object
	 * @param string
	 * @return
	 */
	public static Object toJcbOkNg(Object src, String dest) {
		String srcValue = (src==null?"":src.toString());
		//if(Nulls.isEmpty(srcValue) || "0".equals(srcValue)) return "";
		if(Nulls.isEmpty(srcValue)) return "";
		if("3".equals(srcValue)) return "未检查";
		if("4".equals(srcValue)) return "合理缺项";
		return (dest.equals(srcValue)?"√":"╳");
	}
	
	public static String toZywsCheck(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("1".equals(srcValue)) return "符合";
		if ("2".equals(srcValue)) return "基本符合";
		if ("3".equals(srcValue)) return "不符合";
		
		return srcValue;
	}
	public static String toCARDTYPE(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("01".equals(srcValue)) return "居民身份证";
		if ("02".equals(srcValue)) return "护照";
		if ("03".equals(srcValue)) return "港澳台居民通行证";
		if ("04".equals(srcValue)) return "回乡证";
		if ("05".equals(srcValue)) return "军官证";
		if ("1".equals(srcValue)||"2".equals(srcValue)) return "";
		return srcValue;
	}
	/**
	 * 消毒方式
	 * @param src
	 * @return
	 */
	public static String toANTISEPSISWAY(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("01".equals(srcValue)) return "氯化消毒";
		if ("02".equals(srcValue)) return "二氧化氯消毒";
		if ("03".equals(srcValue)) return "臭氧消毒";
		if ("04".equals(srcValue)) return "紫外线消毒";
		if ("99".equals(srcValue)) return "其他";
		return srcValue;
	}
	/**
	 * 加药方式
	 * @param src
	 * @return
	 */
	public static String toPLUSWAY(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("01".equals(srcValue)) return "机械加药";
		if ("02".equals(srcValue)) return "部分机械加药";
		if ("03".equals(srcValue)) return "人工加药";	
		return srcValue;
	}
	public static String toWATERTYPE(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("020101".equals(srcValue)) return "公共供水";
		if ("020102".equals(srcValue)) return "自建设施供水";
		if ("020103".equals(srcValue)) return "分质供水";	
		return srcValue;
	}
	public static String toWATERFINETYPE(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("02010101".equals(srcValue)) return "城市";
		if ("02010102".equals(srcValue)) return "乡镇";
		return srcValue;
	}
	public static String toWATERFINETYPE_SEC(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("0201010101".equals(srcValue)) return "县政府所在地";
		if ("0201010102".equals(srcValue)) return "地级以上城市";
		return srcValue;
	}
	public static String toWATERTYPES(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("020301".equals(srcValue)) return "输配水设备";
		if ("020302".equals(srcValue)) return "防护材料";
		if ("020303".equals(srcValue)) return "水处理材料";
		if ("020304".equals(srcValue)) return "化学处理剂";
		if ("020305".equals(srcValue)) return "水质处理器";
		return srcValue;
	}
	/**
	 * 水源水类型
	 * @param src
	 * @return
	 */
	public static String toSURFACE(Object src){
		String srcValue = (src==null?"":src.toString());
		if ("0101".equals(srcValue)) return "地表水（江河）";
		if ("0102".equals(srcValue)) return "地表水（湖泊）";
		if ("0103".equals(srcValue)) return "地表水（水库）";
		if ("0104".equals(srcValue)) return "地表水（窖水）";
		if ("0109".equals(srcValue)) return "地表水（其他）";
		if ("0201".equals(srcValue)) return "地下水（浅层）";
		if ("0202".equals(srcValue)) return "地下水（深层）";
		if ("0203".equals(srcValue)) return "地下水（泉水）";
		if ("0209".equals(srcValue)) return "地下水（其他）";	
		return srcValue;
	}
	/**
	 * 制水工艺
	 * @param src
	 * @return
	 */
	public static String toMAKEWATER(String src) {
		String srcValue = (src == null ? "" : src.toString());
		String[] values = srcValue.replaceAll(" ", "").split(",");
		String valueString = "", srcString = "";
		for (int i = 0; i < values.length; i++) {
			if ("01".equals(values[i]))
				valueString = "混凝沉淀";
			if ("02".equals(values[i]))
				valueString = "过滤";
			if ("03".equals(values[i]))
				valueString = "消毒";
			if ("04".equals(values[i]))
				valueString = "深度处理";
			if ("05".equals(values[i]))
				valueString = "特殊处理";
			srcString=srcString+valueString+",";
		}
		return srcString.substring(0, srcString.length()-1);
	}
	
	/**
	 * 水质处理方式
	 */
	public static String toSZCLFS(Object src,String value){
		String srcValue = (src == null ? "" : src.toString());
		return (value.equals(srcValue)?"√":"╳");	
	}
	
	/**
	 * 经济类型
	 */
	public static String toECONOMIC_CODE(Object str){
		String strValue = (str == null?"":str.toString());
		if("11".equals(strValue)) strValue = "国有全资";
		if("12".equals(strValue)) strValue = "集体全资";
		if("13".equals(strValue)) strValue = "股份合作";
		if("14".equals(strValue)) strValue = "联营";
		if("15".equals(strValue)) strValue = "有限责任（公司）";
		if("16".equals(strValue)) strValue = "股份有限（公司）";
		if("17".equals(strValue)) strValue = "私有";
		if("19".equals(strValue)) strValue = "其它内资";
		if("20".equals(strValue)) strValue = "港、澳、台投资";
		if("21".equals(strValue)) strValue = "内地和港澳台合资";
		if("22".equals(strValue)) strValue = "内地和港澳台合作";
		if("23".equals(strValue)) strValue = "港、澳、台独资";
		if("24".equals(strValue)) strValue = "港澳台投资股份有限（公司）";
		if("29".equals(strValue)) strValue = "其它港澳台投资";
		if("30".equals(strValue)) strValue = "国外投资";
		if("31".equals(strValue)) strValue = "中外合资";
		if("32".equals(strValue)) strValue = "中外合作";
		if("33".equals(strValue)) strValue = "外资";
		if("34".equals(strValue)) strValue = "国外投资股份有限（公司）";
		if("39".equals(strValue)) strValue = "其它国外投资";
		if("41".equals(strValue)) strValue = "个体";
		if("90".equals(strValue)) strValue = "其他";
		return strValue;
	}
	/**
	 *消毒产品生产企业 产品种类 转换 
	 */
	public static String toXDCP_PRODUCT_TYPE(String code){
		if(code == null) return "";
		code = code.trim();
		
		if("070101".equals(code)) return "消毒剂";
		if("070102".equals(code)) return "消毒器械";
		if("070103".equals(code)) return "卫生用品";
		return "";
	}
	/**
	 *消毒产品生产企业 兼营项目 转换 
	 */
	public static String toSEC_PRODUCT_TYPE(String str){
		String strValue = (str == null?"":str.toString());
		if("070101".equals(strValue)) strValue = "消毒剂";
		if("070102".equals(strValue)) strValue = "消毒器械";
		if("07010215".equals(strValue)) strValue = "生物指示物";
		if("07010216".equals(strValue)) strValue = "化学指示物";
		if("07010217".equals(strValue)) strValue = "灭菌包装物";
		if("07010301".equals(strValue)) strValue = "纸巾（纸）";
		if("07010302".equals(strValue)) strValue = "卫生巾/护垫/尿布等排泄物卫生用品";
		if("07010303".equals(strValue)) strValue = "纸质餐饮具";
		if("07010304".equals(strValue)) strValue = "抗（抑）菌制剂";
		if("07010305".equals(strValue)) strValue = "隐形眼镜护理用品";
		if("07010306".equals(strValue)) strValue = "卫生棉/化妆棉";
		if("07010307".equals(strValue)) strValue = "湿巾/卫生湿巾";
		if("07010308".equals(strValue)) strValue = "手（指）套";
		return strValue;
	}
	/**
	 * 消毒产品生产企业 主营项目转换
	 */
	public static String toMAIN_PRODUCT_TYPE(String str){
		String strValue = (str == null?"":str.toString());
		if("070101".equals(strValue)) strValue = "消毒剂";
		if("070102".equals(strValue)) strValue = "消毒器械";
		if("07010215".equals(strValue)) strValue = "生物指示物";
		if("07010216".equals(strValue)) strValue = "化学指示物";
		if("07010217".equals(strValue)) strValue = "灭菌包装物";
		if("07010301".equals(strValue)) strValue = "纸巾（纸）";
		if("07010302".equals(strValue)) strValue = "卫生巾/护垫/尿布等排泄物卫生用品";
		if("07010303".equals(strValue)) strValue = "纸质餐饮具";
		if("07010304".equals(strValue)) strValue = "抗（抑）菌制剂";
		if("07010305".equals(strValue)) strValue = "隐形眼镜护理用品";
		if("07010306".equals(strValue)) strValue = "卫生棉/化妆棉";
		if("07010307".equals(strValue)) strValue = "湿巾/卫生湿巾";
		if("07010308".equals(strValue)) strValue = "手（指）套";
		return strValue;
	}
	/**
	 * 消毒产品经营单位产品类别转换
	 */
	public static String toCPLB(String str){
		String strValue = (str == null?"":str.toString());
		if("07010101".equals(strValue)) strValue = "粉剂";
		if("07010102".equals(strValue)) strValue = "片剂";
		if("07010103".equals(strValue)) strValue = "颗粒剂";
		if("07010104".equals(strValue)) strValue = "液体";
		if("07010105".equals(strValue)) strValue = "喷雾剂";
		if("07010106".equals(strValue)) strValue = "凝胶";
		if("07010201".equals(strValue)) strValue = "压力蒸汽灭菌器";
		if("07010202".equals(strValue)) strValue = "环氧乙烷灭菌器";
		if("07010203".equals(strValue)) strValue = "戊二醛灭菌柜";
		if("07010204".equals(strValue)) strValue = "等离子体灭菌器";
		if("07010205".equals(strValue)) strValue = "臭氧消毒柜";
		if("07010206".equals(strValue)) strValue = "电热消毒柜";
		if("07010207".equals(strValue)) strValue = "静电空气消毒机";
		if("07010208".equals(strValue)) strValue = "紫外线杀菌灯";
		if("07010209".equals(strValue)) strValue = "紫外线消毒器";
		if("07010210".equals(strValue)) strValue = "甲醛消毒器";
		if("07010211".equals(strValue)) strValue = "酸性氧化电位水生成器";
		if("07010212".equals(strValue)) strValue = "次氯酸钠发生器";
		if("07010213".equals(strValue)) strValue = "二氧化氯发生器";
		if("07010214".equals(strValue)) strValue = "臭氧发生器、臭氧水发生器";
		if("07010215".equals(strValue)) strValue = "生物指示物";
		if("07010216".equals(strValue)) strValue = "化学指示物";
		if("07010217".equals(strValue)) strValue = "灭菌包装物";
		if("07010299".equals(strValue)) strValue = "其他消毒器械";
		if("07010301".equals(strValue)) strValue = "纸巾（纸）";
		if("07010302".equals(strValue)) strValue = "卫生巾/护垫/尿布等排泄物卫生用品";
		if("07010303".equals(strValue)) strValue = "纸质餐饮具";
		if("07010304".equals(strValue)) strValue = "抗（抑）菌制剂";
		if("07010305".equals(strValue)) strValue = "隐形眼镜护理用品";
		if("07010306".equals(strValue)) strValue = "卫生棉/化妆棉";
		if("07010307".equals(strValue)) strValue = "湿巾/卫生湿巾";
		if("07010308".equals(strValue)) strValue = "手（指）套";
		return strValue;
	}
	/**
	 * 其他传染病防治 单位类别转换
	 */
	public static String toCOMP_TYPE(String COMP_TYPE,String YLJG_INFO, String HOSPTIAL_LEVEL,String JBYFKZJG_ADDR){
		String comp_type = (COMP_TYPE == null?"":COMP_TYPE.toString());                //单位类别
		String yljg_info = (YLJG_INFO == null?"":YLJG_INFO.toString());                //机构信息
		String hosptial_level = (HOSPTIAL_LEVEL == null?"":HOSPTIAL_LEVEL.toString()); //医院等级
		String jbyfkzjg_addr = (JBYFKZJG_ADDR == null?"":JBYFKZJG_ADDR.toString());    //疾病预防控制机构
		
		if("0703".equals(comp_type)){
			if("070301".equals(jbyfkzjg_addr)) comp_type = "省级疾病预防控制机构";
			if("070302".equals(jbyfkzjg_addr)) comp_type = "市级疾病预防控制机构";
			if("070303".equals(jbyfkzjg_addr)) comp_type = "县级疾病预防控制机构";
			if("".equals(jbyfkzjg_addr)) comp_type = "疾病预防控制机构";
		}
		if("06".equals(comp_type)){
			if("0601".equals(yljg_info)) yljg_info = "医院";
			if("0602".equals(yljg_info)) yljg_info = "妇幼保健院";
			if("0603".equals(yljg_info)) yljg_info = "社区卫生服务机构";
			if("0604".equals(yljg_info)) yljg_info = "卫生院";
			if("0605".equals(yljg_info)) yljg_info = "疗养院";
			if("0606".equals(yljg_info)) yljg_info = "门诊部";
			if("0607".equals(yljg_info)) yljg_info = "诊所";
			if("0608".equals(yljg_info)) yljg_info = "村卫生室（所）";
			if("0609".equals(yljg_info)) yljg_info = "急救中心（站）";
			if("0610".equals(yljg_info)) yljg_info = "临床检验中心";
			if("0611".equals(yljg_info)) yljg_info = "专科疾病防治机构";
			if("0612".equals(yljg_info)) yljg_info = "护理院（站）";
			if("0613".equals(yljg_info)) yljg_info = "健康体检机构";
			if("0699".equals(yljg_info)) yljg_info = "其他";
			
			if("1".equals(hosptial_level)) hosptial_level = "三甲";
			if("2".equals(hosptial_level)) hosptial_level = "三乙";
			if("3".equals(hosptial_level)) hosptial_level = "二甲";
			if("4".equals(hosptial_level)) hosptial_level = "二乙";
			if("5".equals(hosptial_level)) hosptial_level = "一级及以下";
			hosptial_level = "".equals(hosptial_level)?"":("医院等级为"+hosptial_level);
			comp_type = "医疗机构："+hosptial_level+yljg_info;
		}
		if("08".equals(comp_type)) comp_type = "血液安全血液安全";
		if("01".equals(comp_type)) comp_type = "公共场所";
		if("02".equals(comp_type)) comp_type = "生活饮用水";
		if("05".equals(comp_type)) comp_type = "学校";
		if("0799".equals(comp_type)) comp_type = "其他有关单位";
		if("0798".equals(comp_type)) comp_type = "个人";
		return comp_type;
	}
	/**
	 * 统计时 专业类别编码号  转变为 中文 
	 */
	public static String toLocal_BizType(String local_bizType){
		String strValue = (local_bizType == null?"":local_bizType.toString());
		if("0109".equals(strValue)) local_bizType = "沐浴场所";
		if("0118".equals(strValue)) local_bizType = "游泳场所";
		if("0191".equals(strValue)) local_bizType = "住宿场所";
		if("0192".equals(strValue)) local_bizType = "理发美容场所";
		if("0193".equals(strValue)) local_bizType = "其他公共场所";
		if("0201".equals(strValue)) local_bizType = "集中式供水";
		if("0202".equals(strValue)) local_bizType = "二次供水";
		if("0203".equals(strValue)) local_bizType = "涉水产品生产企业";
		if("0204".equals(strValue)) local_bizType = "涉水产品经营单位";
		if("0301".equals(strValue)) local_bizType = "化学品毒性鉴定机构";
		if("0302".equals(strValue)) local_bizType = "放射卫生技术服务机构";
		if("0303".equals(strValue)) local_bizType = "职业健康检查机构";
		if("0304".equals(strValue)) local_bizType = "职业病诊断机构";
		if("0400".equals(strValue)) local_bizType = "放射卫生";
		if("0500".equals(strValue)) local_bizType = "学校卫生";
		if("0701".equals(strValue)) local_bizType = "消毒产品生产企业";
		if("0704".equals(strValue)) local_bizType = "餐饮具集中消毒单位";
		if("0799".equals(strValue)) local_bizType = "其他传染病防治监督";
		if("0600".equals(strValue)) local_bizType = "医疗机构";
		if("0608".equals(strValue)) local_bizType = "血液安全血液安全";
		if("0628".equals(strValue)) local_bizType = "无证行医";
		if("9000".equals(strValue)) local_bizType = "建设项目";
		if("9291".equals(strValue)) local_bizType = "机构信息";
		if("9292".equals(strValue)) local_bizType = "部门信息";
		if("9293".equals(strValue)) local_bizType = "人员信息";
		return local_bizType;
	}
	/**
	 * 统计时 业务类型编码 转变为中文
	 */
	public static String toBizType(String BizType){
		String strValue = (BizType == null?"":BizType.toString());
		if("02".equals(strValue)) BizType = "基本信息";
		if("03".equals(strValue)) BizType = "监督信息";
		if("05".equals(strValue)) BizType = "监测信息";
		if("04".equals(strValue)) BizType = "行政处罚信息";
		if("11".equals(strValue)) BizType = "数据字典";
		return BizType;
	}
	
	/**
	 * 统计时 将专业类型代码转换为  中文表示形式
	 */
	public static String toZhuanYe(String zhuanye){
		String strValueString = (zhuanye == null?"":zhuanye.toString());
		if("01".equals(strValueString)) zhuanye="公共场所卫生";
		if("02".equals(strValueString)) zhuanye="生活饮用水卫生";
		if("03".equals(strValueString)) zhuanye="职业卫生";
		if("04".equals(strValueString)) zhuanye="放射卫生";
		if("05".equals(strValueString)) zhuanye="学校卫生";
		if("06".equals(strValueString)) zhuanye="医疗卫生";
		if("07".equals(strValueString)) zhuanye="传染病防治管理";
		if("90".equals(strValueString)) zhuanye="建设项目";
		return zhuanye;
	}
	
	/**
	 * 公共场所主兼营代码名称转换
	 */
	public static String toGgcsCompTypeName(String code){
		String typeName = "";
		if("0101".equals(code)) typeName = "宾馆";
		if("0102".equals(code)) typeName = "饭馆";
		if("0103".equals(code)) typeName = "旅店";
		if("0104".equals(code)) typeName = "招待所";
		if("0105".equals(code)) typeName = "车马店";
		if("0106".equals(code)) typeName = "咖啡馆";
		if("0107".equals(code)) typeName = "酒吧";
		if("0108".equals(code)) typeName = "茶座";
		if("0109".equals(code)) typeName = "公共浴室";
		if("0110".equals(code)) typeName = "理发店";
		if("0111".equals(code)) typeName = "美容店";
		if("0112".equals(code)) typeName = "影剧院";
		if("0113".equals(code)) typeName = "录像厅（室）";
		if("0114".equals(code)) typeName = "游艺厅（室）";
		if("0115".equals(code)) typeName = "舞厅";
		if("0116".equals(code)) typeName = "音乐厅";
		if("0118".equals(code)) typeName = "游泳场（馆）";
		if("0120".equals(code)) typeName = "展览馆";
		if("0121".equals(code)) typeName = "博物馆";
		if("0122".equals(code)) typeName = "美术馆";
		if("0123".equals(code)) typeName = "图书馆";
		if("0124".equals(code)) typeName = "商场（店）";
		if("0125".equals(code)) typeName = "书店";
		if("0126".equals(code)) typeName = "候诊室";
		if("0127".equals(code)) typeName = "候车（机、船）室";
		if("0117".equals(code)) typeName = "体育场（馆）";
		if("0119".equals(code)) typeName = "公园";
		if("0128".equals(code)) typeName = "公共交通工具";
		if("0129".equals(code)) typeName = "民宿";
		return typeName;
	}
	
	/**
	 * 处理量化等级
	 * @param type
	 * @return
	 */
	public static String getLhfj(Map<String, Object> entity, String type) {
		String level = null;
		if (type.equals("0101")) {
			level = (String)entity.get("PROJECT_LEVEL1");
		} else if (type.equals("0102")) {
			level = (String)entity.get("PROJECT_LEVEL2");
		} else if (type.equals("0103")) {
			level = (String)entity.get("PROJECT_LEVEL3");
		} else if (type.equals("0104")) {
			level = (String)entity.get("PROJECT_LEVEL4");
		} else if (type.equals("0105")) {
			level = (String)entity.get("PROJECT_LEVEL5");
		} else if (type.equals("0106")) {
			level = (String)entity.get("PROJECT_LEVEL6");
		} else if (type.equals("0107")) {
			level = (String)entity.get("PROJECT_LEVEL7");
		} else if (type.equals("0108")) {
			level = (String)entity.get("PROJECT_LEVEL8");
		} else if (type.equals("0109")) {
			level = (String)entity.get("PROJECT_LEVEL9");
		} else if (type.equals("0110")) {
			level = (String)entity.get("PROJECT_LEVEL10");
		} else if (type.equals("0111")) {
			level = (String)entity.get("PROJECT_LEVEL11");
		} else if (type.equals("0112")) {
			level = (String)entity.get("PROJECT_LEVEL12");
		} else if (type.equals("0113")) {
			level = (String)entity.get("PROJECT_LEVEL13");
		} else if (type.equals("0114")) {
			level = (String)entity.get("PROJECT_LEVEL14");
		} else if (type.equals("0115")) {
			level = (String)entity.get("PROJECT_LEVEL15");
		} else if (type.equals("0116")) {
			level = (String)entity.get("PROJECT_LEVEL16");
		} else if (type.equals("0118")) {
			level = (String)entity.get("PROJECT_LEVEL17");
		} else if (type.equals("0120")) {
			level = (String)entity.get("PROJECT_LEVEL18");
		} else if (type.equals("0121")) {
			level = (String)entity.get("PROJECT_LEVEL19");
		} else if (type.equals("0122")) {
			level = (String)entity.get("PROJECT_LEVEL20");
		} else if (type.equals("0123")) {
			level = (String)entity.get("PROJECT_LEVEL21");
		} else if (type.equals("0124")) {
			level = (String)entity.get("PROJECT_LEVEL22");
		} else if (type.equals("0125")) {
			level = (String)entity.get("PROJECT_LEVEL23");
		} else if (type.equals("0126")) {
			level = (String)entity.get("PROJECT_LEVEL24");
		} else if (type.equals("0127")) {
			level = (String)entity.get("PROJECT_LEVEL25");
		} else if (type.equals("0117")) {
			level = (String)entity.get("PROJECT_LEVEL26");
		} else if (type.equals("0119")) {
			level = (String)entity.get("PROJECT_LEVEL27");
		} else if (type.equals("0128")) {
			level = (String)entity.get("PROJECT_LEVEL28");
		} else if (type.equals("0129")) {
			level = (String)entity.get("PROJECT_LEVEL29");
		}
		if(Nulls.isEmpty(level)){
			level = "09"; // 默认为未分级
		}
		return level;
	}
	
	/**
	 * 许可类别代码名称转换
	 */
	public static String toRequestTypeName(String code){
		String typeName = "";
		if("1".equals(code)||"01".equals(code)) typeName = "新发";
		if("2".equals(code)||"02".equals(code)) typeName = "变更";
		if("3".equals(code)||"03".equals(code)) typeName = "延续";
		if("4".equals(code)||"04".equals(code)) typeName = "注销";
		return typeName;
	}
	
	/**
	 * 数据库的CLOB转换为字符串
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String toString(Clob clob) throws Exception {
		if(clob == null) return "";
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		StringBuffer sb = new StringBuffer();
		String s;
		while ((s=br.readLine()) != null) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	/**
	 * 学校供水类别
	 * @param code
	 * @return
	 */
	public static String toXxwsGslx(String code){
		String typeName = "";
		if("010101".equals(code)) typeName = "市政供水";
		if("010102".equals(code)) typeName = "乡镇水厂供水";
		if("010103".equals(code)) typeName = "村级水站供水";
		if("0102".equals(code)) typeName = "自建设施供水";
		if("02".equals(code)) typeName = "二次供水";
		if("03".equals(code)) typeName = "分散式供水";
		if("09".equals(code)) typeName = "其他";
		return typeName;
	}
	
	/**
	 * 学校所在区域
	 * @param code
	 * @return
	 */
	public static String toXxwsSzqy(String code){
		String typeName = (code == null?"":code.toString());
		if("01".equals(typeName)) typeName = "城区";
		if("02".equals(typeName)) typeName = "镇区";
		if("03".equals(typeName)) typeName = "乡村";
		if("04".equals(typeName)) typeName = "设区市主城区";
		if("05".equals(typeName)) typeName = "县城";
		if("06".equals(typeName)) typeName = "乡镇中心区（政府所在地）";
		return typeName;
	}
	
	public static String toXxwsSzqy(Object code){
		String typeName = (code == null?"":code.toString());
		if("01".equals(typeName)) typeName = "城区";
		if("02".equals(typeName)) typeName = "镇区";
		if("03".equals(typeName)) typeName = "乡村";
		if("04".equals(typeName)) typeName = "设区市主城区";
		if("05".equals(typeName)) typeName = "县城";
		if("06".equals(typeName)) typeName = "乡镇中心区（政府所在地）";
		return typeName;
	}
	
	public static String toTyjgServeType(Object code){
		String typeName = (code == null?"":code.toString());
		if("01".equals(typeName)) typeName = "全日制";
		else if("02".equals(typeName)) typeName = "半日制";
		else if("03".equals(typeName)) typeName = "寄宿制";
		else if("04".equals(typeName)) typeName = "其他";
		return typeName;
	}
	
	public static String toTyjgLevel(Object code){
		String typeName = (code == null?"":code.toString());
		if("1".equals(typeName)) typeName = "一级";
		else if("2".equals(typeName)) typeName = "二级";
		else if("3".equals(typeName)) typeName = "三级";
		else if("0".equals(typeName)) typeName = "未定";
		return typeName;
	}
	
	/**
	 * 学校办学性质
	 * @param str
	 * @return
	 */
	public static String toPROPERTY(Object str){
		String strValue = (str == null?"":str.toString());
		if("01".equals(strValue)) strValue = "公办";
		if("02".equals(strValue)) strValue = "民办";
		if("03".equals(strValue)) strValue = "其他";
		if("05".equals(strValue)) strValue = "教育部门办";
		if("04".equals(strValue)) strValue = "集体办";
		
		return strValue;
	}
	/**
	 * 学校卫生学校类别
	 * @param str
	 * @return
	 */
	public static String toCompType(Object str){
		String strValue = (str == null?"":str.toString());
		if("0501".equals(strValue)) strValue = "小学";
		if("0502".equals(strValue)) strValue = "初级中学";
		if("0503".equals(strValue)) strValue = "高级中学";
		if("0504".equals(strValue)) strValue = "普通高校";
		return strValue;
	}
	
	/**
	 * 高级中学类别
	 * @param str
	 * @return
	 */
	public static String toGaoJi(Object str){
		String strValue = (str == null?"":str.toString());
		if("050301".equals(strValue)) strValue = "十二年一贯制";
		if("050302".equals(strValue)) strValue = "完全中学";
		if("050303".equals(strValue)) strValue = "职业中学";
		return strValue;
	}
	
	public static String toPROPERTY_CODE(Object str){
		String strValue = (str == null?"":str.toString());
		if("00".equals(strValue)) strValue = "主校";
		else strValue = "分校";
		return strValue;
	}
	
	/**
	 * 学校类别
	 * @param str
	 * @param nine
	 * @param gaoji
	 * @return
	 */
	public static String toCompType(Object str,Object nine,Object gaoji){
		String strValue = (str == null ? "" : str.toString());
		if ("0501".equals(strValue)) strValue = "小学";
		if ("0502".equals(strValue) && Nulls.isEmpty(nine)) strValue = "初级中学";
		if ("050201".equals(nine)) strValue = "初级中学(九年一贯制)";
		if ("0503".equals(strValue) &&  Nulls.isEmpty(gaoji)) strValue = "高级中学";
		if ("0503".equals(strValue) && "050301".equals(gaoji)) strValue = "高级中学(十二年一贯制)";
		if ("0503".equals(strValue) && "050302".equals(gaoji)) strValue = "高级中学(完全中学)";
		if ("0503".equals(strValue) && "050303".equals(gaoji)) strValue = "高级中学(职业中学)";
		if ("0504".equals(strValue)) strValue = "普通高校";
		if ("0505".equals(strValue)) strValue = "托幼机构";
		return strValue;
	}
	/**
	 * Object转化为String
	 */
	public static String ObjctToString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}
	
	/**
	 * 导出excel时判断
	 * @param src
	 * @param value
	 * @return
	 */
	public static String toYesOrNo(Object src,String value){
		String srcValue = (src == null ? "" : src.toString());
		return (value.equals(srcValue)?"是":"否");	
	}
	
	/**
	 * 学校卫生监督采取行为
	 * @param str
	 * @return
	 */
	public static String toCqxw(Object str){
		String strValue = (str == null?"":str.toString());
		if("jcbl".equals(strValue)) strValue = "检查笔录";
		if("jdyj".equals(strValue)) strValue = "监督意见";
		if("dccf".equals(strValue)) strValue = "当场处罚";
		if("xzcs".equals(strValue)) strValue = "行政措施";
		if("lacl".equals(strValue)) strValue = "立案处理";
		return strValue;
	}
	
	public static String toJcjg(Object str){
		String strValue = (str == null?"":str.toString());
		if("01".equals(strValue)) strValue = "未发现问题";
		if("02".equals(strValue)) strValue = "发现问题经责令已改正";
		if("03".equals(strValue)) strValue = "未发现本次抽查涉及的生产经营活动";
		if("04".equals(strValue)) strValue = "不配合检查情节严重";
		if("05".equals(strValue)) strValue = "注销";
		if("06".equals(strValue)) strValue = "暂停营业";
		if("07".equals(strValue)) strValue = "吊证";
		if("08".equals(strValue)) strValue = "关闭";
		if("09".equals(strValue)) strValue = "发现问题待后续处理";
		
		if("10".equals(strValue)) strValue = "通过登记的住所（经营场所）无法联系";
		if("11".equals(strValue)) strValue = "被撤销";
		if("12".equals(strValue)) strValue = "迁出";
		if("13".equals(strValue)) strValue = "未按规定公示应当公示的信息";
		if("14".equals(strValue)) strValue = "公示信息隐瞒真实情况弄虚作假";
		return strValue;
	}
	/**
	 * 导出word模板时，判断数据 
	 * @param value
	 * @return
	 */
	public static String toYesOrNo(Object value){
		String strValue = (value == null?"":value.toString());
		if ("1".equals(strValue)) 
			strValue= "√";
		if ("0".equals(strValue)) 
			strValue= "╳";
		if ("3".equals(strValue)) 
			strValue= "未检查";
		if ("4".equals(strValue)) 
			strValue= "合理缺项";
		return strValue;
	}
	
	/**
	 * 导出word模板时，判断数据 （是、否、未检查、合理缺项）
	 * @param value
	 * @return
	 */
	public static String toCheckResult(Object value){
		String strValue = (value == null?"":value.toString());
		if ("1".equals(strValue)) 
			strValue= "是";
		if ("0".equals(strValue)) 
			strValue= "否";
		if ("3".equals(strValue)) 
			strValue= "未检查";
		if ("4".equals(strValue)) 
			strValue= "合理缺项";
		return strValue;
	}
	
	/**
	 * 导出word模板时，判断数据 (检查、未检查)
	 * @param value
	 * @return
	 */
	public static String toCheckResult2(Object value){
		String strValue = (value == null?"":value.toString());
		if ("1".equals(strValue)) 
			strValue= "检查";
		if ("0".equals(strValue)) 
			strValue= "未检查";
		return strValue;
	}
	
	/**
	 * 导出word模板时，判断数据 （全部、部分）
	 * @param value
	 * @return
	 */
	public static String toAllOrPart(Object value){
		String strValue = (value == null?"":value.toString());
		if ("1".equals(strValue)) 
			strValue= "全部";
		if ("0".equals(strValue)) 
			strValue= "部分";
		return strValue;
	}
	
	/**
	 * 导出word模板时，判断数据 （有、无）
	 * @param value
	 * @return
	 */
	public static String toCheckResult3(Object value){
		String strValue = (value == null?"":value.toString());
		if ("1".equals(strValue)) 
			strValue= "有";
		if ("0".equals(strValue)) 
			strValue= "无";
		return strValue;
	}
	

	/**
	 * 根据量化得分计算信誉度等级计算<br/>
	 * A:90—100; B:70—89; C:60—69
	 * @param lhdf
	 * @return
	 */
	public static Integer toXYDDJ(Double lhdf) {
		if(lhdf == null || lhdf < 60) return 4;
		if(lhdf >= 60 && lhdf < 70) return 3;
		if(lhdf >= 70 && lhdf < 90) return 2;
		if(lhdf >= 90) return 1;
		return 4;
	}
	public static String toXYDDJ(Object lhdf) {
		String srcValue = ((lhdf == null) ? "" : lhdf.toString());
		if ("1".equals(srcValue)) srcValue="A";
		if ("2".equals(srcValue)) srcValue="B";
		if ("3".equals(srcValue)) srcValue="C";
		if ("4".equals(srcValue)) srcValue="不予评级";
		return srcValue;
	}
	public static String toYesShow(Object src){
		String srcValue = ((src == null||src=="") ? "" : src.toString());
		if ("1".equals(srcValue)) {
			srcValue="是";
		}
		if ("0".equals(srcValue)) 
			srcValue= "否";
		return srcValue;	
	}
	
	public static String toWSPJ(Object src){
		String srcValue = ((src == null||src=="") ? "" : src.toString());
		if ("1".equals(srcValue)) srcValue="优秀";
		if ("2".equals(srcValue)) srcValue="合格";
		if ("3".equals(srcValue)) srcValue="不合格";
		return srcValue;	
	}
	
	public static String toNvl(Object src){
		String srcValue = ((src == null||src=="") ? "0" : src.toString());
		return srcValue;	
	}
	
	/**
	 * 医疗机构基本信息  医疗机构类别
	 * @param src
	 * @return
	 */
	public static String ylwsComptype(Object src){
		String srcValue = ((src == null||src=="") ? "" : src.toString());
		if ("0601-1".equals(srcValue))	srcValue = "综合医院";
		if ("0601-2".equals(srcValue))	srcValue = "中医医院";
		if ("0601-3".equals(srcValue))	srcValue = "中西医结合医院";
		if ("0601-4".equals(srcValue))	srcValue = "民族医医院";
		if ("0601-5".equals(srcValue))	srcValue = "专科医院";
		if ("0601-6".equals(srcValue))	srcValue = "康复医院";
		if ("0602".equals(srcValue))	srcValue = "妇幼保健院";
		if ("0603-1".equals(srcValue))	srcValue = "社区卫生服务中心";
		if ("0603-2".equals(srcValue))	srcValue = "社区卫生服务站";
		if ("0604-1".equals(srcValue))	srcValue = "中心卫生院";
		if ("0604-2".equals(srcValue))	srcValue = "乡（镇）卫生院";
		if ("0604-3".equals(srcValue))	srcValue = "街道卫生院";
		if ("0605".equals(srcValue))	srcValue = "疗养院";
		if ("0606-1".equals(srcValue))	srcValue = "综合门诊部";
		if ("0606-2".equals(srcValue))	srcValue = "专科门诊部";
		if ("0606-3".equals(srcValue))	srcValue = "中医门诊部";
		if ("0606-4".equals(srcValue))	srcValue = "中西医结合门诊部";
		if ("0606-5".equals(srcValue))	srcValue = "民族医门诊部";
		if ("0607-1".equals(srcValue))	srcValue = "诊所";
		if ("0607-2".equals(srcValue))	srcValue = "中医诊所";
		if ("0607-3".equals(srcValue))	srcValue = "民族医诊所";
		if ("0607-4".equals(srcValue))	srcValue = "卫生所";
		if ("0607-5".equals(srcValue))	srcValue = "医务室";
		if ("0607-6".equals(srcValue))	srcValue = "卫生保健所";
		if ("0607-7".equals(srcValue))	srcValue = "卫生站";
		if ("0608".equals(srcValue))	srcValue = "村卫生室（所）";
		if ("0609-1".equals(srcValue))	srcValue = "急救中心";
		if ("0609-2".equals(srcValue))	srcValue = "急救站";
		if ("0610".equals(srcValue))	srcValue = "临床检验中心";
		if ("0611-1".equals(srcValue))	srcValue = "专科疾病防治院";
		if ("0611-2".equals(srcValue))	srcValue = "专科疾病防治所";
		if ("0611-3".equals(srcValue))	srcValue = "专科疾病防治站";
		if ("0612-1".equals(srcValue))	srcValue = "护理院";
		if ("0612-2".equals(srcValue))	srcValue = "护理站";
		if ("0613".equals(srcValue))	srcValue = "健康体检机构";
		if ("0699".equals(srcValue))	srcValue = "其他";
		return srcValue;
	}
	
	/**
	 * 医疗机构基本信息  经营性质
	 * @param src
	 * @return
	 */
	public static String ylwsCompNature(Object src){
		String srcValue = ((src == null||src=="") ? "" : src.toString());
		if ("01".equals(srcValue)) 		srcValue="营利";
		if ("02".equals(srcValue)) 		srcValue="非营利性（政府）";
		if ("03".equals(srcValue)) 		srcValue="非营利性（非政府） ";
		return srcValue;
	}
	
	/**
	 * 医疗机构基本信息   服务对象
	 * @param src
	 * @return
	 */
	public static String ylwsServerObject(Object src){
		String srcValue = ((src == null||src=="") ? "" : src.toString());
		if ("01".equals(srcValue)) 		srcValue="社会";
		if ("02".equals(srcValue)) 		srcValue="内部";
		if ("03".equals(srcValue)) 		srcValue="境外人员 ";
		if ("04".equals(srcValue)) 		srcValue="社会+境外人员";
		if ("05".equals(srcValue)) 		srcValue="社会+内部";
		return srcValue;
	}
	
	public static String ylwsShowData(Integer src){
		String srcValue = ((src == null) ? "—" : src.toString());
		if ("0".equals(srcValue))	return "—";
		else return srcValue;
	}
	
	/**
	 * 身份证号11-14用*表示
	 */
	public static String idCardChange(Object idcard){
		String srcValue = ((idcard == null||idcard=="") ? "" : idcard.toString());
		if (!"".equals(srcValue)) {
			if (srcValue.length()==18) {
				String top10=srcValue.substring(0,10);
				String middle4=srcValue.substring(10,14);
				String end4=srcValue.substring(14,18);
				return top10+middle4.replace(middle4, "****")+end4;
			}else{
				return srcValue.substring(0,srcValue.length()-5)+"***"+srcValue.substring(srcValue.length()-2,srcValue.length());
			}
		}else {
			return "";
		}
	}
	
	/**
	 * 0 男，2女
	 * @param str
	 * @return
	 */
	public static String toSex(Object str){
		String strValue = (str == null?"":str.toString());
		if("0".equals(strValue)) strValue = "男";
		else strValue = "女";
		return strValue;
	}
	
	
	public static String toXDCP_TYPE(String src){
		String srcValue = (src==null?"":src.toString());
		if ("1".equals(srcValue)) return "第一类消毒产品";
		if ("2".equals(srcValue)) return "第二类消毒产品";
		return srcValue;
	}
	
	public static String toPRODUCT_TYPE(String src){
		String srcValue = (src==null?"":src.toString());
		if("1001".equals(srcValue)) return "消毒剂";
		if("1002".equals(srcValue)) return "消毒器械";
		if("1003".equals(srcValue)) return "消毒器械（指示物及包装物）";
		if("1004".equals(srcValue)) return "抗(抑)菌制剂";
		if("1005".equals(srcValue)) return "二氧化氯消毒剂";
		if("1006".equals(srcValue)) return "胍类消毒剂";
		if("1007".equals(srcValue)) return "含碘消毒剂";
		if("1008".equals(srcValue)) return "季铵盐类消毒剂";
		if("1009".equals(srcValue)) return "含溴消毒剂";
		if("1010".equals(srcValue)) return "过氧化物类消毒剂";
		if("1011".equals(srcValue)) return "醛类消毒剂";
		if("1012".equals(srcValue)) return "乙醇消毒剂";
		if("1013".equals(srcValue)) return "酚类消毒剂";
		if("1014".equals(srcValue)) return "其他";
		if("1015".equals(srcValue)) return "压力蒸汽灭菌器";
		if("1016".equals(srcValue)) return "环氧乙烷灭菌器";
		if("1017".equals(srcValue)) return "戊二醛灭菌柜";
		if("1018".equals(srcValue)) return "等离子体灭菌器";
		if("1019".equals(srcValue)) return "臭氧消毒柜";
		if("1020".equals(srcValue)) return "电热消毒柜";
		if("1021".equals(srcValue)) return "静电空气消毒机";
		if("1022".equals(srcValue)) return "紫外线杀菌灯";
		if("1023".equals(srcValue)) return "紫外线消毒器";
		if("1024".equals(srcValue)) return "甲醛消毒器";
		if("1025".equals(srcValue)) return "酸性氧化电位水生成器";
		if("1026".equals(srcValue)) return "次氯酸钠发生器";
		if("1027".equals(srcValue)) return "二氧化氯发生器";
		if("1028".equals(srcValue)) return "臭氧发生器、臭氧水发生器";
		if("1029".equals(srcValue)) return "其他的消毒器械（注明消毒灭菌因子）";
		if("1030".equals(srcValue)) return "用于测定压力蒸汽灭菌效果的生物指示物";
		if("1031".equals(srcValue)) return "用于测定环氧乙烷灭菌效果的生物指示物";
		if("1032".equals(srcValue)) return "用于测定紫外线消毒效果的生物指示物";
		if("1033".equals(srcValue)) return "用于测定干热灭菌效果的生物指示物";
		if("1034".equals(srcValue)) return "用于测定甲醛灭菌效果的生物指示物";
		if("1035".equals(srcValue)) return "用于测定电离辐射灭菌效果的生物指示物";
		if("1036".equals(srcValue)) return "用于测定等离子体灭菌效果的生物指示物";
		if("1037".equals(srcValue)) return "用于测定压力蒸汽灭菌的化学指示物";
		if("1038".equals(srcValue)) return "用于测定环氧乙烷灭菌的化学指示物（指示卡、指示胶带、指示标签）";
		if("1039".equals(srcValue)) return "用于测定紫外线消毒的化学指示物";
		if("1040".equals(srcValue)) return "用于测定干热灭菌效果的化学指示物";
		if("1041".equals(srcValue)) return "用于测定电离辐射灭菌效果的化学指示物";
		if("1042".equals(srcValue)) return "用于测定化学消毒剂浓度的化学指示物";
		if("1043".equals(srcValue)) return "用于测定等离子体灭菌效果的化学指示物";
		if("1044".equals(srcValue)) return "用于压力蒸汽灭菌且带有灭菌标识的包装物";
		if("1045".equals(srcValue)) return "用于环氧乙烷灭菌且带有灭菌标识的包装物";
		if("1046".equals(srcValue)) return "用于甲醛灭菌且带有灭菌标识的包装物";
		if("1047".equals(srcValue)) return "用于等离子体灭菌且带有灭菌标识的包装物";
		if("1048".equals(srcValue)) return "抗菌制剂";
		if("1049".equals(srcValue)) return "抑菌制剂";
		if("1050".equals(srcValue)) return "二氧化氯消毒剂";
		if("1051".equals(srcValue)) return "醋酸氯已定";
		if("1052".equals(srcValue)) return "葡萄糖醋酸氯已定";
		//卫计局要求修改为“聚六亚甲基胍” 2016/05/17
		//if("1053".equals(srcValue)) return "聚氯亚甲基胍";
		if("1053".equals(srcValue)) return "聚六亚甲基胍";
		
		if("1054".equals(srcValue)) return "碘酊";
		if("1055".equals(srcValue)) return "碘伏";
		if("1056".equals(srcValue)) return "氯型季铵盐（洁尔灭）";
		if("1057".equals(srcValue)) return "溴型季铵盐（新洁尔灭）";
		if("1058".equals(srcValue)) return "溴氯海因";
		if("1059".equals(srcValue)) return "二溴海因";
		if("1060".equals(srcValue)) return "过氧化氢";
		if("1061".equals(srcValue)) return "过氧乙酸";
		if("1062".equals(srcValue)) return "过氧化氢+过氧乙酸";
		if("1063".equals(srcValue)) return "戊二醛";
		if("1064".equals(srcValue)) return "戊二醛+洁尔灭";
		if("1065".equals(srcValue)) return "戊二醛+新洁尔灭";
		if("1066".equals(srcValue)) return "邻苯二甲醛";
		if("1067".equals(srcValue)) return "乙醇";
		if("1068".equals(srcValue)) return "乙醇+表面活性剂";
		if("1069".equals(srcValue)) return "苯酚";
		if("1070".equals(srcValue)) return "甲酚";
		if("1071".equals(srcValue)) return "对氯间二甲基苯酚";
		if("1072".equals(srcValue)) return "DP-300（三氯羟基二苯醚）";
		if("1073".equals(srcValue)) return "压力蒸汽灭菌器";
		if("1074".equals(srcValue)) return "大型环氧乙烷灭菌器";
		if("1075".equals(srcValue)) return "中型环氧乙烷灭菌器";
		if("1076".equals(srcValue)) return "小型环氧乙烷灭菌器";
		if("1077".equals(srcValue)) return "过氧化氢气体等离子低温灭菌装置";
		if("1078".equals(srcValue)) return "臭氧消毒柜";
		if("1079".equals(srcValue)) return "电热消毒柜";
		if("1080".equals(srcValue)) return "普通直管热阴极低压汞紫外线消毒灯";
		if("1081".equals(srcValue)) return "高强度紫外线消毒灯";
		if("1082".equals(srcValue)) return "低臭氧紫外线消毒灯";
		if("1083".equals(srcValue)) return "高臭氧紫外线消毒灯";
		if("1084".equals(srcValue)) return "紫外线空气消毒器";
		if("1085".equals(srcValue)) return "紫外线表面消毒器";
		if("1086".equals(srcValue)) return "紫外线消毒箱";
		if("1087".equals(srcValue)) return "低温蒸汽甲醛灭菌器";
		if("1088".equals(srcValue)) return "酸性氧化电位水生成器";
		if("1089".equals(srcValue)) return "次氯酸钠发生器";
		if("1090".equals(srcValue)) return "二氧化氯发生器";
		if("1091".equals(srcValue)) return "臭氧发生器";
		if("1092".equals(srcValue)) return "臭氧水发生器";
		if("1093".equals(srcValue)) return "电离辐射灭菌或消毒";
		if("1094".equals(srcValue)) return "微波消毒灭菌柜";
		if("1095".equals(srcValue)) return "指示卡";
		if("1096".equals(srcValue)) return "指示胶带";
		if("1097".equals(srcValue)) return "指示标签";
		if("1098".equals(srcValue)) return "BD试纸";
		if("1099".equals(srcValue)) return "BD包";
		if("1100".equals(srcValue)) return "指示卡";
		if("1101".equals(srcValue)) return "指示胶带";
		if("1102".equals(srcValue)) return "指示标签";
		if("1103".equals(srcValue)) return "辐照强度指示卡";
		if("1104".equals(srcValue)) return "消毒效果指示卡";
		if ("1105".equals(srcValue) || "1106".equals(srcValue)
				|| "1107".equals(srcValue) || "1108".equals(srcValue)
				|| "1109".equals(srcValue) || srcValue.startsWith("111")
				|| srcValue.startsWith("112") || srcValue.startsWith("113")) {
			return "其他";
		}
		return srcValue;
	}
	
	/**
	 * 特殊转义字符的处理
	 * @param src
	 * @return
	 */
	public static String toZyzf(String value){
		String srcValue = (value==null?"":value.toString());
		if (srcValue.indexOf("®")>-1) {
			srcValue = srcValue.replace("®", "(C)");
		}
		return srcValue;
	}
	
	public static String zhZyzf(String value){
		String srcValue = (value==null?"":value.toString());
		if (srcValue.indexOf("(C)")>-1) {
			srcValue = srcValue.replace("(C)", "®");
		}
		return srcValue;
	}
	
	public static int stringLength(String value) {
		int temp_int = 0;
		byte[] b = value.getBytes();

		for (int i = 0; i < b.length; i++) {
			if (b[i] >= 0) {
				temp_int = temp_int + 1;
			} else {
				temp_int = temp_int + 2;
				i++;
			}
		}
		return temp_int;
	}
	
	public static String toJdjc(String src){
		String srcValue = (src==null?"":src.toString());
		if ("1".equals(srcValue)) return "是";
		if ("2".equals(srcValue)) return "否";
		if ("3".equals(srcValue)) return "未检查";
		if ("4".equals(srcValue)) return "合理缺项";
		return srcValue;
	}
	
	/**
	 * 复制属性
	 * @param src
	 * @param dest
	 */
	public static void copyProperties(Object src, Object dest) {
		try {
			BeanUtils.copyProperties(dest,src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 复制属性
	 * @param src
	 * @param dest
	 */
	public static void copyProperties(Object src, Object dest, String[] igonreProperties) {
		//org.springframework.beans.BeanUtils.copyProperties 这个拷贝，源和目标字段类型必须一致！
		org.springframework.beans.BeanUtils.copyProperties(src, dest, igonreProperties);
	}
	
	public static String toJdxxValue(Object code){
		String typeName = "";
		if("1".equals(code)) typeName = "1";//是
		if("-1".equals(code)||"0".equals(code)) typeName = "2";//否
		if (Nulls.isEmpty(code) || "3".equals(code)|| "9".equals(code)) typeName="3";//未检查
		if("4".equals(code)) typeName = "4";//合理缺项
		return typeName;
	}
	
	public static String toHgValue(Object code){
		String typeName = code + "";
		if("1".equals(code)) typeName = "3";//合格
		if("0".equals(code)) typeName = "4";//不合格
		return typeName;
	}
	
	public static String toYNValue(Object code){
		String typeName = "";
		if("1".equals(code)) typeName = "1";//是
		if("0".equals(code)) typeName = "2";//否
		return typeName;
	}
	
	public static String toJdxxJzsgs(Object code){
		String typeName = "";
		if("1".equals(code)) typeName = "1";//是
		if("-1".equals(code)||"0".equals(code)) typeName = "2";//否
		if (Nulls.isEmpty(code) || "3".equals(code)) typeName="3";//未检查
		if("4".equals(code)) typeName = "4";//合理缺项
		return typeName;
	}
	
	public static String toJdxxJzktValue(String code){
		String typeName = "";
		if("1".equals(code)) typeName = "1";
		if("-1".equals(code)||"0".equals(code)) typeName = "2";
		if (Nulls.isEmpty(code) || "3".equals(code)) typeName="3";
		return typeName;
	}
	public static Integer toJdxxDataSource(Integer code){
		Integer typeName = null;
		if(Nulls.isEmpty(code))  typeName = 0;
		if(Nulls.isNotEmpty(code)) typeName = code;
		return typeName;
	}
	public static String cgxCompType(String code){
		String typeName = "";
		if("080101".equals(code)) typeName = "08010101";
		if("080102".equals(code)) typeName = "08010102";
		if("080103".equals(code)) typeName = "08010103";
		if("0802".equals(code)) typeName = "080102";
		if("0803".equals(code)) typeName = "080103";
		if("0899".equals(code)) typeName = "080199";
		return typeName;
	}
	
	public static Integer crbfzDiseases(Integer diseases,String value){
		Integer typeName = 0;
		if(value.equals(diseases)){
			typeName = 1;
		}
		return typeName;
	}
	
	public static String getFxsjAndBgsj(String xzqh){
		if(Nulls.isNotEmpty(xzqh)&&xzqh.startsWith("330109")){
			return " COUNT(D.ID) AS FXSJ,sum(case when D.is_report>0 then 1 else 0 end) AS BGSJ ";
		}else{
			return " SUM(A.IS_QUESTION) AS FXSJ, SUM( case when A.IS_REPORT > 0 then 1 else 0 end ) AS BGSJ ";
		}
	}
	
	public static String toPHYSICAL_TYPE(String src){
		String srcValue = (src==null?"":src.toString());
		if ("1".equals(srcValue)) return "上崗前";
		if ("2".equals(srcValue)) return "在岗期间";
		if ("3".equals(srcValue)) return "离岗";
		return srcValue;
	}
	
	public static String toJhsyServiceTypeName(String src){
		String srcValue = (src==null?"":src.toString());
		if ("090101".equals(srcValue)) return "婚前医学检查";
		if ("090102".equals(srcValue)) return "遗传病诊断";
		if ("090103".equals(srcValue)) return "产前诊断";
		if ("09010301".equals(srcValue)) return "遗传咨询";
		if ("09010302".equals(srcValue)) return "医学影像";
		if ("09010303".equals(srcValue)) return "细胞遗传";
		if ("09010304".equals(srcValue)) return "分子遗传";
		if ("09010305".equals(srcValue)) return "生化免疫";
		if ("090104".equals(srcValue)) return "产前筛查";
		if ("090105".equals(srcValue)) return "助产技术";
		if ("090106".equals(srcValue)) return "终止妊娠手术";
		if ("090107".equals(srcValue)) return "结扎手术";
		if ("090108".equals(srcValue)) return "新生儿疾病筛查";
		if ("090109".equals(srcValue)) return "家庭接生";
		if ("090199".equals(srcValue)) return "有关生育、节育、不育的其他生殖保健服务";
		if ("090201".equals(srcValue)) return "计划生育技术指导、咨询和随访";
		if ("090202".equals(srcValue)) return "避孕药具服务";
		if ("090203".equals(srcValue)) return "避孕和节育的医学检查";
		if ("090204".equals(srcValue)) return "放置（取出）宫内节育器（IUD）";
		if ("090205".equals(srcValue)) return "人工流产术";
		if ("09020501".equals(srcValue)) return "吸宫术";
		if ("09020502".equals(srcValue)) return "钳刮术";
		if ("09020503".equals(srcValue)) return "药物流产";
		if ("090206".equals(srcValue)) return "输精（卵）管绝育术";
		if ("090207".equals(srcValue)) return "引产术";
		if ("090208".equals(srcValue)) return "输卵（精）管复通手术";
		if ("090209".equals(srcValue)) return "皮下埋植避孕术";
		if ("090210".equals(srcValue)) return "不育症诊治";
		if ("090211".equals(srcValue)) return "计划生育手术并发症和计划生育药具不良反应的诊断、治疗";
		if ("090299".equals(srcValue)) return "其它生殖保健服务项目";
		if ("090301".equals(srcValue)) return "供精人工授精技术";
		if ("090302".equals(srcValue)) return "夫精人工授精技术";
		if ("090303".equals(srcValue)) return "体外受精-胚胎移植技术";
		if ("090304".equals(srcValue)) return "卵胞浆内单精子显微注射技术";
		if ("090305".equals(srcValue)) return "植入前胚胎遗传学诊断技术";
		if ("090306".equals(srcValue)) return "人类精子库";
		if ("090399".equals(srcValue)) return "其他辅助生殖衍生技术";
		return srcValue;
	}
	public static String toSSCPLBTypeName(String src){
		String srcValue = (src==null?"":src.toString());
	    if ("0101".equals(srcValue)) return "管材、管件";
		else if ("0102".equals(srcValue)) return "蓄水容器";
		else if ("0103".equals(srcValue)) return "无负压供水设备";
		else if ("0104".equals(srcValue)) return "饮水机";
		else if ("0105".equals(srcValue)) return "密封胶条、密封圈";
		else if ("0106".equals(srcValue)) return "其他输配水设备";
		else if ("0201".equals(srcValue)) return "环氧树脂涂料类";
		else if ("0202".equals(srcValue)) return "聚酯涂料（含醇酸树脂）类 ";
		else if ("0203".equals(srcValue)) return "丙烯酸树脂涂料类 ";
		else if ("0204".equals(srcValue)) return "聚氨酯涂料类";
		else if ("0205".equals(srcValue)) return "其他防护材料";
		else if ("0301".equals(srcValue)) return "活性炭、活性炭滤芯";
		else if ("0302".equals(srcValue)) return "陶瓷滤芯";
		else if ("0303".equals(srcValue)) return "微滤膜";
		else if ("0304".equals(srcValue)) return "超滤膜";
		else if ("0305".equals(srcValue)) return "纳滤膜";
		else if ("0306".equals(srcValue)) return "反渗透膜";
		else if ("0308".equals(srcValue)) return "离子交换树脂";
		else if ("0309".equals(srcValue)) return "复合滤芯";
		else if ("0310".equals(srcValue)) return "其他水处理材料";
		else if ("040101".equals(srcValue)) return "聚合氯化铝";
		else if ("040102".equals(srcValue)) return "硫酸铝";
		else if ("040103".equals(srcValue)) return "聚丙烯酰胺";
		else if ("040104".equals(srcValue)) return "铁盐类}";
		else if ("040105".equals(srcValue)) return "其他絮凝剂、助凝剂";
		else if ("040201".equals(srcValue)) return "磷酸盐类及其复配产品";
		else if ("040202".equals(srcValue)) return "硅酸盐类及其复配产品";
		else if ("040203".equals(srcValue)) return "其他阻垢剂";
		else if ("040301".equals(srcValue)) return "次氯酸钠";
		else if ("040302".equals(srcValue)) return "二氧化氯";
		else if ("040303".equals(srcValue)) return "高锰酸钾";
		else if ("040304".equals(srcValue)) return "过氧化氢";
		else if ("040305".equals(srcValue)) return "其他饮用水消毒剂";
		else if ("0404".equals(srcValue)) return "其他化学处理剂";
		else if ("050101".equals(srcValue)) return "一般净水器（小型，非超滤）";
		else if ("050102".equals(srcValue)) return "超滤净水器（小型）";
		else if ("050103".equals(srcValue)) return "反渗透净水器（小型）";
		else if ("050104".equals(srcValue)) return "纳滤净水器（小型）";
		else if ("050105".equals(srcValue)) return "一般净水器（大型，非超滤）";
		else if ("050106".equals(srcValue)) return "超滤净水器（大型）";
		else if ("050107".equals(srcValue)) return "反渗透净水器（大型）";
		else if ("050108".equals(srcValue)) return "纳滤净水器（大型）";
		else if ("050109".equals(srcValue)) return "软水器";
		else if ("050110".equals(srcValue)) return "其他以市政自来水为原水的水质处理器";
		else if ("0502".equals(srcValue)) return "以地下水或地表水为水源的水质处理设备";
		else if ("050301".equals(srcValue)) return "二氧化氯发生器";
		else if ("050302".equals(srcValue)) return "臭氧发生器";
		else if ("050303".equals(srcValue)) return "次氯酸发生器";
		else if ("050304".equals(srcValue)) return "紫外线消毒器";
		else if ("0504".equals(srcValue)) return "其他水质处理器";
		else return "";
	}
	
	public static String toSSCPTypeName(String src){
		String srcValue = (src==null?"":src.toString());
		if("01".equals(srcValue)){
			return "输配水设备";
		}else if("02".equals(srcValue)){
			return "防护材料";
		}else if("03".equals(srcValue)){
			return "水处理材料";
		}else if("04".equals(srcValue)){
			return "化学处理剂";
		}else if("05".equals(srcValue)){
			return "水质处理器";
		}
		else return "";
	}
	public static String toSSCPStatusName(String src){
		String srcValue = (src==null?"":src.toString());
		if("0".equals(srcValue)){
			return "未生产";
		}else if("1".equals(srcValue)){
			return "已生产";
		}else if("2".equals(srcValue)){
			return "已注销";
		}else return "";
	}
	/**
	 * 判断两个是否相等
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static <C> boolean eq(C value1, C value2) {
		if(value1 == null || value2 == null) return false;
		return value1.equals(value2);
	}
	
	/**
	 * 系统生成标识管理相对人的唯一编号
	 * 推荐使用S18，管理相对人所属行政区划（精确至区县，六位）的编码和系统日期（六位）、系统自动生成的流水号（六位)、两位随机码
	 * @param qhdm 地区编码 （精确至区县，六位）
	 */
	public static String generateCOMP_NO(int qhdm){
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(qhdm).substring(0, 6));
		String dateStr = dataFormat(new Date(),"yyMMdd");
		sb.append(dateStr);
		String timeStr = dataFormat(new Date(),"HHmmss");
//		timeStr=timeStr.substring(timeStr.length()-9,timeStr.length()-3);
		sb.append(timeStr);
		//------------------防止重复最后追加两位随机数-------------2014/05/16---曹加入---------------
		sb.append(getRandomNum(2));
		return sb.toString();
	}
	/** 
     * 获取指定位数的随机数(纯数字) 
     * @param length 随机数的位数 
     * @return String 
     */  
    public static String getRandomNum(int length) {  
        if (length <= 0) {  
            length = 1;  
        }  
        StringBuilder res = new StringBuilder();  
        Random random = new Random();  
        int i = 0;  
        while (i < length) {  
            res.append(random.nextInt(10));  
            i++;  
        }  
        return res.toString();  
    }  
    /**
	 * 在字符串前面补0  
	 * @param lsh 目标字符串
	 * @param length 补足0后字符串长度
	 */
	public static String  qianBuZero(String lsh,int length){
		StringBuffer sb=new StringBuffer();
		for(int i=lsh.length();i<length;i++){
			sb.append("0");
		}
		sb.append(lsh);
		return sb.toString();
	}
}

