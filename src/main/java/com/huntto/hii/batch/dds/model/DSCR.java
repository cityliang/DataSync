package com.huntto.hii.batch.dds.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 传输xml标准头信息(DSCR)
 */
@XmlRootElement(name="DSCR")
@XmlAccessorType(XmlAccessType.FIELD)
public class DSCR implements Serializable {
	private static final long serialVersionUID = 2270744972198589577L;
	
	private String SENDTIME;
	private String SENDCODE;
	private String VERSIONS;

	public String getSENDTIME() {
		return SENDTIME;
	}

	public void setSENDTIME(String sENDTIME) {
		SENDTIME = sENDTIME;
	}

	public String getSENDCODE() {
		return SENDCODE;
	}

	public void setSENDCODE(String sENDCODE) {
		SENDCODE = sENDCODE;
	}

	public String getVERSIONS() {
		return VERSIONS;
	}

	public void setVERSIONS(String vERSIONS) {
		VERSIONS = vERSIONS;
	}

}
