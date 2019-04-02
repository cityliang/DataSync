package com.huntto.hii.batch.dds;

import java.util.HashMap;

import com.huntto.hii.batch.util.Utils;

public abstract class DdsWorker {
	/** 分发表/视图名称 */
	public final static String PARAM_DDS_NAME = "PARAM_DDS_NAME";
	/** 日期 */
	public final static String PARAM_DDS_DATE = "PARAM_DDS_DATE";

	/** 关闭标识 */
	private static boolean cancelled = false;

	/** 运行参数 */
	protected HashMap<String, String> params = new HashMap<String, String>();

	protected DdsWorker(){
	}

	/**
	 * 进行报卡
	 */
	abstract public void execute();
	
	/**
	 * 设置运行参数
	 * @param params
	 */
	public void addParams(String key, String value) {
		if(Utils.isNotNullOrEmpty(value)){
			params.put(key, value);
		}
	}

	/**
	 * 重置
	 */
	public void reset() {
		params.clear();
	}

	/**
	 * 中止运行
	 */
	public static void cancel(){
		cancelled = true;
	}
	
	/**
	 * 是否取消
	 * @return
	 */
	protected boolean isCancelled(){
		return cancelled;
	}

}
