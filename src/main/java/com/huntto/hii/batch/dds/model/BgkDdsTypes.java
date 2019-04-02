package com.huntto.hii.batch.dds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.huntto.hii.model.bgk.BgkDdsTypesPk;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据分发支持类型控制表
 * @author chen.ruisen
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "BGK_DDS_TYPES")
@IdClass(BgkDdsTypesPk.class)
public class BgkDdsTypes implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Id
	@Column(nullable = false)
	private String USER_ID;
	
	/** 分发名称（表名等） */
	@Id
	@Column(nullable = false)
	private String DDS_NAME;
	
	/** 类型 TABLE/VIEW/QUERY etc. */
	@Column(nullable = false)
	private String DDS_TYPE;
	
	/** 每次传输分页大小 */
	@Column(nullable = false)
	private int PAGE_SIZE;
	
	/** 附加标识（对DDS_NAME添加附加信息） */
	@Column
	private String TAG;

	/** 备注说明 */
	@Column
	private String MEMO;

	/** 是否有效（1：有效） */
	@Column
	private String AVALIBLE;
}
