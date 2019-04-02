package com.huntto.hii.batch.dds.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RS")
@XmlAccessorType(XmlAccessType.FIELD)
public class RECORDSET implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 表名 */
	@XmlAttribute(name = "NAME")
	private String NAME;
	/** 行 */
	@XmlElement(name = "R")
	private List<ROW> ROWS;

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public List<ROW> getROWS() {
		return ROWS;
	}

	public void setROWS(List<ROW> rOWS) {
		ROWS = rOWS;
	}
}
