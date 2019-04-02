package com.huntto.hii.batch.dds.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
@XmlRootElement(name="F")
@XmlAccessorType(XmlAccessType.FIELD)
public class FIELD implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 字段名 */
	@XmlAttribute(name="N",required=true)
	private String NAME;
	/** 字段类型 */
	@XmlAttribute(name="T",required=true)
	private String TYPE;
	/** 是否为空，只有值没有内容时候需要填写 */
	@XmlAttribute(name="NIL")
	private String NIL;
	/** 值 */
	@XmlValue
	private String VALUE;
	
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getNIL() {
		return NIL;
	}
	public void setNIL(String nIL) {
		NIL = nIL;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}
}
