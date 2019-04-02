package com.huntto.hii.model.bgk;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据分发支持类型控制表主键
 * @author chen.ruisen
 */
@Data
@NoArgsConstructor
public class BgkDdsTypesPk implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private String USER_ID;
	
	/** 分发名称（表名等） */
	private String DDS_NAME;
}