package com.huntto.hii.batch.dds.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Table(name = "BGK_DDS_LOG")
@Data
@NoArgsConstructor
@Entity
@Table(name = "BGK_DDS_LOG")
public class BgkDdsLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** ID */
	@Id
	@Column(nullable = false)
	private String ID;
	/** 发送编码 */
	@Column(nullable = false)
	private String SENDCODE;
	/** 同步日期条件yyyymmdd */
	@Column(nullable = false)
	private String DDS_DATE;
	/** 同步名称（table/view名称） */
	@Column(nullable = false)
	private String DDS_NAME;
	/** 客户端/服务器(CLIENT/SERVER) */
	@Column(nullable = false)
	private String CLIENT_SERVER;
	/** 用户id */
	@Column(nullable = false)
	private String USER_ID;
	/** 数据总量 */
	@Column
	private int TOTAL_COUNT;
	/** 成功条数 */
	@Column
	private int OK_COUNT;
	/** 开始时间 */
	@Column
	private Date BEGIN_TIME;
	/** 结束时间 */
	@Column
	private Date END_TIME;
	/** 备注 */
	@Column
	private String MEMO;
}
