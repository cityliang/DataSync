package com.huntto.hii.model.bgk;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据分发支持类型控制表
 * @author chen.ruisen
 */
//@Entity
//@Table(name = "BGK_DDS_TYPES")
//@IdClass(BgkDdsTypesPk.class)
@Data
@NoArgsConstructor
public class BgkDdsTypes implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 用户ID */
	private String USER_ID;
	/** 分发名称（表名等） */
	private String DDS_NAME;
	/** 类型 TABLE/VIEW/QUERY etc. */
	private String DDS_TYPE;
	/** 每次传输分页大小 */
	private int PAGE_SIZE;
	/** 附加标识（对DDS_NAME添加附加信息） */
	private String TAG;
	/** 备注说明 */
	private String MEMO;
	/** 是否有效（1：有效） */
	private String AVALIBLE;

}