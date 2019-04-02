/*
 * 系统名称：卫生监督管理系統
 * 版权所有：(c)2012 浙江创得信息技术有限公司，所有版权保留
 * 版权声明：本软件所有权归浙江创得信息技术有限公司，
 *          未经浙江创得信息技术有限公司同意，禁止拷贝、修改和发布本系统代码。
 * 开发日期：2012年10月
 */
package com.huntto.hii.batch.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;



/**
 * 其他工具类<br/>
 * @author chen.ruisen
 *
 */
final public class Utils {
	private Utils(){}

	public static int intValue(Integer value){
		return value==null?0:value.intValue();
	}
	
	public static boolean isNullOrEmpty(String value){
		return (value == null || value.length()==0);
	}
	
	public static boolean isNullOrEmptyTrim(String value){
		return (value == null || value.trim().length()==0);
	}
	
	public static boolean isNotNullOrEmpty(String value){
		return !isNullOrEmpty(value);
	}
	
	public static boolean isNotNullOrEmptyTrim(String value){
		return !isNullOrEmptyTrim(value);
	}

	public static String trim(String value){
		return (value == null ? "": value.trim());
	}
	
	public static String nvl(Object value){
		return (value == null?"":value.toString());
	}
	
	/**
	 * 转化为yyyyMMddHHmmss格式
	 * @param date
	 * @return
	 */
	public static String toDT14(Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	public static String rfix(String value, int len, char defaultChar) {
		value = trim(value);
		if(value.length()==len) return value;
		if(value.length()>len) return value.substring(0, len);
		
		StringBuilder buff = new StringBuilder(value);
		for(int i=value.length(); i<len; i++) buff.append(defaultChar);
		return buff.toString();
	}

	public static String escapeSQL(String value) {
		if(value == null) return "";
		if(value.indexOf("'")==-1) return value;
		return value.replace("'", "''");
	}

	public static String toSQLString(String value) {
		value = escapeSQL(value);
		StringBuilder sb = new StringBuilder(value.length()+2);
		sb.append("'");
		sb.append(value);
		sb.append("'");
		return sb.toString();
	}
	
	/**
	 * 取JVM参数
	 * @return
	 */
	public static String getJvmParam(String key){
		return getJvmParam(key, null);
	}
	
	
	/**
	 * 取JVM参数
	 * @return
	 */
	public static String getJvmParam(String key, String defaultValue){
		return System.getProperties().getProperty(key, defaultValue);
	}
	
	/**
	 * 读取配置文件
	 * @param dir
	 * @param file
	 * @return
	 */
	public static Properties loadProperties(String filePath){
		Properties properties = new Properties();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(filePath);
			properties.load(new BufferedInputStream(fis));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(fis != null) try{fis.close();}catch(Exception ex){}
		}

		return properties;
	}

	/**
	 * 日期加减
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addSecond(Date date, int n) {
		Calendar cd = Calendar.getInstance();   
		cd.setTime(date);
		cd.add(Calendar.SECOND, n);
		return cd.getTime();
	}
}
