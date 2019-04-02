package com.huntto.hii.batch.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @ClassName  DdsConfig 
 * @Description 配置信息管理
 * @author 梁城市
 * @date  2019年3月25日 下午4:06:43
 */
@Data
@NoArgsConstructor
@Configuration
public class DdsConfig {
	
	@Value("${DDS_NAME}")
	private String DDS_NAME;
	@Value("${DDS_DATE}")
	private String DDS_DATE;
	
	/** 每日一次运行触发器时间  HHmm*/
	@Value("${dailyTriggerTime}")
	private String dailyTriggerTime;
	@Value("${wsdlUrl}")
	private String wsdlUrl;
	@Value("${user}")
	private String user;
	@Value("${password}")
	private String password;
	@Value("${targetDb}")
	private String targetDb;
	@Value("${maxPageNo}")
	private int maxPageNo;
	@Value("${ddsNames}")
	private String ddsNames;
	@Value("#{'${ddsNames1}'.split(',')}")
	private List<String> ddsNames1;
	private String DEFAULT_DDS_NAMES="MYCS_JBXX,XXWS_JBXX,YLWS_JBXX";
	@Value("${insertMode}")
	private String insertMode;//是否只插入参数
	/** 客户端是否记录日志到表 */
	@Value("${logEnabled}")
	private String logEnabled;
	/**
	 * 公共场所表 共有字段
	 * 省  MYCS_JBXX  
	 * 湖州  GGCS_BASEINFO    
	 */
	@Value("#{'${GGCSLIST}'.split(',')}")
	private List<String> GGCSLIST;
	
	/**
	 * 学校卫生表 共有字段 
	 * 省  XXWS_JBXX    
	 * 湖州  XXWS_BASEINFO    
	 */
	@Value("#{'${XXWSLIST}'.split(',')}")
	private List<String> XXWSLIST;
	
	/**
	 * 医疗卫生表 共有字段 
	 * 省  YLWS_JBXX  
	 * 湖州  YLWS_JBXX  
	 */
	@Value("#{'${YLWSLIST}'.split(',')}")
	private List<String> YLWSLIST;

}
