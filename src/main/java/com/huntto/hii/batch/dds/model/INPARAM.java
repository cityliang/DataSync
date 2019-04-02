package com.huntto.hii.batch.dds.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 入参
 */
@XmlRootElement(name="DATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class INPARAM implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String DDS_NAME;
	private String DDS_DATE;
	private String PAGE_NO;

	public String getDDS_NAME() {
		return DDS_NAME;
	}

	public void setDDS_NAME(String dDS_NAME) {
		DDS_NAME = dDS_NAME;
	}

	public String getDDS_DATE() {
		return DDS_DATE;
	}

	public void setDDS_DATE(String dDS_DATE) {
		DDS_DATE = dDS_DATE;
	}

	public String getPAGE_NO() {
		return PAGE_NO;
	}

	public void setPAGE_NO(String pAGE_NO) {
		PAGE_NO = pAGE_NO;
	}
}
