package com.huntto.hii.batch.dds.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 结果信息实体
 */
@XmlRootElement(name="DATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class RESULT implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 发送成功 */
	public static final String OK = "00000000";
	/** 文档格式错误 */
	public static final String ERROR_COMM_FORMAT = "00000001";
	/** 逻辑校验错误 */
	public static final String ERROR_COMM_LOGICAL_VALIDATE = "00000004";
	/** 处理异常 */
	public static final String ERROR_COMM_UNKOWN = "00999999";
	/** 认证失败（保留给省级交换用） */
	public static final String ERROR_AUTH_FAILD = "99000000";
	public static final String ERROR_AUTH_FAILD_JG_ERROR = "99000001";
	public static final String ERROR_DDS_NAME_UNKOWN = "99000002";

	protected RESULT() {
	}

	/** 结果信息 */
	@XmlElement(name="RESULT")
	private String RESULT;
	
	@XmlElement(name="RS")
	private RECORDSET RS;

	public String getRESULT() {
		return RESULT;
	}

	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}

	public RECORDSET getRS() {
		return RS;
	}

	public void setRS(RECORDSET rs) {
		RS = rs;
	}
	
	@Override
	public String toString() {
		return RESULT;
	}
}
