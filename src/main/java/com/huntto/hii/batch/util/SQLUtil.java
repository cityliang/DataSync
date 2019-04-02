package com.huntto.hii.batch.util;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Query;


/**
 * SQL工具，用于拼接带参数的sql
 */
public final class SQLUtil {

	private SQLUtil() {}

	//========================================================================
	/**
	 * 添加=查询条件
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void eq(StringBuilder sqlWhere, String fieldName, String paramName, String value) {
		where(sqlWhere, fieldName, paramName, value, "=");
	}

	/**
	 * 添加 like 查询条件
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void like(StringBuilder sqlWhere, String fieldName, String paramName, String value) {
		where(sqlWhere, fieldName, paramName, value, "like");
	}
	
	/**
	 * 添加 like '%x%' 查询条件
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void contains(StringBuilder sqlWhere, String fieldName, String paramName, String value) {
		where(sqlWhere, fieldName, paramName, value, "like");
	}
	
	/**
	 * 添加 like 'x%' 查询条件
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void startsWith(StringBuilder sqlWhere, String fieldName, String paramName, String value) {
		where(sqlWhere, fieldName, paramName, value, "like");
	}
	
	/**
	 * 添加 like '%x' 查询条件
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void endWith(StringBuilder sqlWhere, String fieldName, String paramName, String value) {
		where(sqlWhere, fieldName, paramName, value, "like");
	}

	/**
	 * 添加where条件参数
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public static void where(StringBuilder sqlWhere, String fieldName, String paramName, String value, String type) {
		if(Nulls.isNotEmpty(value)){
			sqlWhere.append(sqlWhere.length()>0 ? " and " : " where ");
			sqlWhere.append(fieldName);
			sqlWhere.append(" ");
			sqlWhere.append(type);
			sqlWhere.append(" :");
			sqlWhere.append(paramName);
		}
	}

	/**
	 * 添加=查询条件值
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void eq(Query query, String paramName, String value) {
		where(query, paramName, value);
	}

	/**
	 * 添加 like '%x%' 查询条件值
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void contains(Query query, String paramName, String value) {
		if(Nulls.isNotEmpty(value)){
			where(query, paramName, "%"+value+"%");
		}
	}

	/**
	 * 添加 like 'x%' 查询条件值
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void startsWith(Query query, String paramName, String value) {
		if(Nulls.isNotEmpty(value)){
			where(query, paramName, value+"%");
		}
	}

	/**
	 * 添加 like '%x' 查询条件值
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 */
	public static void endWith(Query query, String paramName, String value) {
		if(Nulls.isNotEmpty(value)){
			where(query, paramName, "%"+value);
		}
	}

	/**
	 * 添加where条件参数值
	 * @param sqlWhere
	 * @param fieldName
	 * @param value
	 * @param type
	 */
	public static void where(Query query, String paramName, Object value) {
		if(Nulls.isNotEmpty(value)){
			if(value instanceof String){
				query.setString(paramName, (String)value);
			}
			else if(value instanceof Integer){
				query.setInteger(paramName, (Integer)value);
			}
			else if(value instanceof Long){
				query.setLong(paramName, (Long)value);
			}
			else if(value instanceof Float){
				query.setFloat(paramName, (Float)value);
			}
			else if(value instanceof Double){
				query.setDouble(paramName, (Double)value);
			}
			else if(value instanceof BigDecimal){
				query.setBigDecimal(paramName, (BigDecimal)value);
			}
			else if(value instanceof Date){
				query.setDate(paramName, (Date)value);
			}
			else{
				throw new RuntimeException("不支持数据类型");
			}
		}
	}

	//========================================================================
	/**
	 * 处理SQL字符串单引号问题
	 * @param value
	 * @return
	 */
	public static String escapeSQL(String value) {
		if(value == null) return "";
		if(value.indexOf("'")==-1) return value;
		return value.replace("'", "''");
	}
	/**
	 * 处理SQL字符串单引号问题
	 * @param value
	 * @return
	 */
	public static Object escapeSQL(Object value) {
		if(value == null) return "";
		if(value instanceof String) return escapeSQL((String)value);
		return value;
	}
	
	/**
	 * in 的数字类型变换为字符类型(已经删除了单引号，不需要再escapeSQL)<br/>
	 * (1,2,3,4) -> ('1','2','3','4')<br/>
	 * IN中一般都是KEY，所以在假设不可能单引号情况下，直接删除了字符串中的单引号
	 * @param value
	 * @return
	 */
	public static String toSQLCharList(String value) {
		if(value == null) return "";
		
		//如果有单引号，则先删除（为了防止SQL注入）
		if(value.indexOf("'")!=-1) {
			value = value.replace("'", "");
		}
		//删除多余空格
		value = value.replace(" ", "");
		
		StringBuilder sb = new StringBuilder();
		sb.append("'");
		sb.append(value.replace(",", "','"));
		sb.append("'");
		
		String result = sb.toString();
		return result;
	}
	
	/**
	 * 字符串型数值，变换为数据库带单引号的字符串
	 * @param value
	 * @return
	 */
	public static String toSQLString(String value) {
		StringBuilder sb = new StringBuilder(value.length()+2);
		sb.append("'");
		sb.append(escapeSQL(value));
		sb.append("'");
		return sb.toString();
	}
}
