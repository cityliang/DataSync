package com.huntto.hii.batch.dds.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "R")
@XmlAccessorType(XmlAccessType.FIELD)
public class ROW implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 行号 */
	@XmlAttribute(name = "NO", required = true)
	private int NO;

	/** 字段 */
	@XmlElement(name = "F", required = true)
	private List<FIELD> FIELDS;

	public int getNO() {
		return NO;
	}

	public void setNO(int nO) {
		NO = nO;
	}

	public List<FIELD> getFIELDS() {
		return FIELDS;
	}

	public void setFIELDS(List<FIELD> fIELDS) {
		FIELDS = fIELDS;
	}
}
