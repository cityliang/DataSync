package com.huntto.hii.batch.util;

import java.math.BigDecimal;

public class Nulls {
	/**
	 * 校验是否为空
	 */
	public static boolean isEmpty(String data){
		boolean flag = false;		
		if (data == null || "".equals(data.trim()) || "null".equals(data) || "undefined".equals(data)) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 校验是否为空
	 */
	public static boolean isEmpty(Object data){
		if(data == null) return true;
		if(data instanceof String){
			if("".equals((String)data)) return true;
		}
		return false;
	}
	/**
	 * 校验是否为空，为空时抛出异常
	 * @param data：校验数�?
	 * @param errorMsg：异常信�?
	 */
	public static void isEmpty(String data, String errorMsg){
		if(data == null || "".equals(data)){
			throw new RuntimeException(errorMsg);
		}
	}
	
	/**
	 * 不为NULL
	 * @param data
	 * @return
	 */
	public static boolean isNotEmpty(String data){
		return !isEmpty(data);
	}
	
	/**
	 * 不为NULL，字符串时也不为“”
	 * @param data
	 * @return
	 */
	public static boolean isNotEmpty(Object data){
		return !isEmpty(data);
	}
	
	/**
	 * null object转换为“”，但是Integer的时候，如果为0，则也转为“”
	 * @param obj
	 * @return
	 */
	public static String nullToString(Object obj){
		if(obj == null){
			return "";
		}
		if((obj instanceof Integer) && ((Integer)obj)==0){
			return "";
		}
		return obj.toString();
	}
	
	public static double nullToDouble(Object obj){
		if(obj == null || Nulls.isEmpty(obj.toString())){
			return 0;
		}
		return Double.parseDouble(obj.toString());
	}
	
	//lt2013102503 增加处理整数变量的排空方法
	public static int nullToInt(Object obj){
		if(obj == null) return 0;
		if(obj instanceof Integer) return (Integer)obj;
		if(Nulls.isEmpty(obj.toString())){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	public static BigDecimal nullToBigDecimal(Object obj){
		if(obj == null){
			return new BigDecimal("0");
		}
		return (BigDecimal)obj;
	}
	public static Long nullToLong(Object obj){
		if(obj == null){
			return new Long("0");
		}
		return (Long)obj;
	}
	
	/**
	 * null转换为“”
	 * @param obj
	 * @return
	 */
	public static String nvl(String obj){
		return (obj != null ? obj : "");
	}
	
	/**
	 * null object转换为“”
	 * @param obj
	 * @return
	 */
	public static String nvl2(Object obj){
		return (obj != null ? obj.toString() : "");
	}
	
	/**
	 * null转换为默认值
	 * @param obj
	 * @return
	 */
	public static <C> C nvl(C obj, C defaultValue){
		return (obj != null ? obj : defaultValue);
	}
	
	public static String nvl3(Object obj){
		return (obj != null ? obj.toString() : "0");
	}
	
	public static String nullToStringName(Object obj){
		if(obj == null){
			return "";
		}
		if((obj instanceof Integer) && ((Integer)obj)==0){
			return "";
		}
		if (isNotEmpty(obj) && obj.toString().length() == 1) {
			obj = obj.toString() + "	";
		}
		return obj.toString();
	}
}
