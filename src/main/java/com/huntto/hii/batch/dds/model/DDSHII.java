package com.huntto.hii.batch.dds.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 标准xml实体类
 */
@XmlRootElement(name="DDSHII")
@XmlAccessorType(XmlAccessType.FIELD)
public class DDSHII implements Serializable {

	private static final long serialVersionUID = -8308891453166645778L;

	/** 头信息 */
	@XmlElement(name="DSCR")
	private DSCR dscr;
	
	/** 内容 */
	@XmlAnyElement(lax=true)
	private Object data;

	public DSCR getDscr() {
		return dscr;
	}

	public void setDscr(DSCR dscr) {
		this.dscr = dscr;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
