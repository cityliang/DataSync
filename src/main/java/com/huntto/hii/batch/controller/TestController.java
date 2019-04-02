package com.huntto.hii.batch.controller;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.xml.ws.WebServiceException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huntto.hii.batch.config.DdsConfig;
import com.huntto.hii.batch.dds.DdsMgr;
import com.huntto.hii.batch.dds.DdsWorker;
import com.huntto.hii.batch.dds.DdsWorkerImpl;
import com.huntto.hii.batch.dds.dao.DdsDaoImpl;
import com.huntto.hii.batch.dds.gen.DdService;
import com.huntto.hii.batch.dds.gen.DdServiceManager;
import com.huntto.hii.batch.dds.model.BgkDdsLog;
import com.huntto.hii.batch.dds.model.DDSHII;
import com.huntto.hii.batch.dds.model.FIELD;
import com.huntto.hii.batch.dds.model.RECORDSET;
import com.huntto.hii.batch.dds.model.RESULT;
import com.huntto.hii.batch.dds.model.ROW;
import com.huntto.hii.batch.util.ConvertUtil;
import com.huntto.hii.batch.util.HibernateUtil2;
import com.huntto.hii.batch.util.Nulls;
import com.huntto.hii.batch.util.SQLUtil;
import com.huntto.hii.batch.util.ValidateUtil;
import com.huntto.hii.batch.util.XmlUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	@Autowired
	private DdsConfig ddsConfig;
//	@Autowired
//	private HibernateUtil hibernateUtil;
	
//	@Autowired
//	@Qualifier("primaryJdbcTemplate")
//	private DataSource datasource;
	
//	@Autowired
//	private HibernateUtil2 hibernateUtil;
//	@Autowired
//	private SessionFactory sessionFactory;
	/** 报卡用Dao */
	protected DdsDaoImpl ddsDao = new DdsDaoImpl();
	
	/** 内部每个报卡执行环境(内部参数)，执行前需重置 */
	protected HashMap<String, String> ctx = new HashMap<String, String>();
	
	/** 错误发生源：本地 */
	public final static String SRC_LOCAL = "1";
	/** 错误发生源：服务器 */
	public final static String SRC_SERVER = "2";
	
	/** 字段名检查用正则表达式 */
	private Pattern FieldNamePattern = Pattern.compile("^[0-9a-zA-Z_]+$");
	
	/** 分发表/视图名称 */
	public final static String PARAM_DDS_NAME = "PARAM_DDS_NAME";
	/** 日期 */
	public final static String PARAM_DDS_DATE = "PARAM_DDS_DATE";
	
	/** 关闭标识 */
	private static boolean cancelled = false;
	
	/** 运行参数 */
	protected HashMap<String, String> params = new HashMap<String, String>();

	/** 入参模板 */
	private final static String InParamTemplate = "<DDSHII><DSCR><SENDCODE>%s</SENDCODE><SENDTIME>%s</SENDTIME><VERSIONS>0100</VERSIONS></DSCR>"
			+ "<DATA><DDS_NAME>%s</DDS_NAME><DDS_DATE>%s</DDS_DATE><PAGE_NO>%d</PAGE_NO></DATA></DDSHII>";
	
	@RequestMapping("/tbTable")
	public String start(String DDS_DATE,String DDS_NAME) {
		log.info("数据同步开始。");
		log.info("DDS_DATE: "+DDS_DATE);
		log.info("DDS_NAME: "+DDS_NAME);
		if(Nulls.isNotEmpty(ddsConfig.getDDS_DATE())) {
			log.info(("DDS_DATE: "+ddsConfig.getDDS_DATE()));
		}
		if(Nulls.isNotEmpty(ddsConfig.getDDS_NAME())) {
			log.info(("DDS_NAME: "+ddsConfig.getDDS_NAME()));
		}
		String ddsDate = "00000000";
		String ddsName = "MYCS_JBXX";
		if(Nulls.isNotEmpty(DDS_NAME)) {
			ddsName = DDS_NAME;
			log.info("DDS_NAME: "+DDS_NAME);
			if(!ddsConfig.getDdsNames1().contains(DDS_NAME)) {
				return "此程序只能同步固定表："+ddsConfig.getDdsNames1().toString();
			}
		}else {
			log.info("DDS_NAME IS NULL");
		}
		if(Nulls.isNotEmpty(DDS_DATE) && !ValidateUtil.isDate8(DDS_DATE) && !"00000000".equals(DDS_DATE)) {
			log.error("同步日期必须为yyyyMMdd格式");
			ddsDate = DDS_DATE;
			log.info("DDS_DATE: "+DDS_DATE);
			return "同步日期必须为yyyyMMdd格式";
		}else {
			log.info("DDS_DATE IS NULL");
		}
		
		log.info(String.format("DDS_DATE=[%s],DDS_NAME=[%s]", ddsDate, ddsName));
		
		String sendCode = ConvertUtil.generateID();
		String userId = ddsConfig.getUser();
		
		int totalCount=0;
		int okCount=0;
		Date beginTime = new Date();
		Date endTime = null;
		
		String memo = "执行完成";
		
		//调用服务
		try {
			URL wsdlUrl = new URL(ddsConfig.getWsdlUrl());
			DdService service = new DdService(wsdlUrl);
			DdServiceManager mgr = service.getDdServiceManagerImplPort();
			
			int pageNo = 0;
			int serverPageSize=0;
			
			while(true){
				pageNo ++;
				log.info("获取第" + pageNo + "页...");
				
				if(pageNo>ddsConfig.getMaxPageNo()) {
					log.warn("超出最大页数，不再继续执行！最大页数:"+ddsConfig.getMaxPageNo());
					break;
				}
				String inParam = makeInParam(sendCode, ddsName, ddsDate, pageNo);
				String xml = mgr.dds(userId, ddsConfig.getPassword(), inParam);
				
				log.trace(xml);
				
				log.info(" 获取成功，正在解析处理...");
				
				DDSHII ddshii = (DDSHII)XmlUtil.deserialize(xml, DDSHII.class, RESULT.class);
				RESULT result = (RESULT)ddshii.getData();
				
				if(result !=null && result.getRESULT() != null && result.getRESULT().startsWith(RESULT.OK)){
					//读取成功
					RECORDSET rs = result.getRS();
					
					if(rs == null){
						log.error("未返回RS！");
						break;
					}
					if(rs == null || !ddsName.equals(rs.getNAME())){
						log.error("返回的DDS_NAME与参数不一致！");
						break;
					}
					
					if(rs.getROWS() != null && rs.getROWS().size()>0){
						log.info(" 解析成功"+rs.getROWS().size()+"条(第"+ (totalCount+1) + "-" + (totalCount+rs.getROWS().size()) +"), 更新到本地数据库...");
						
						totalCount += rs.getROWS().size();
						okCount += executeUpdate(ddsDate, rs);
						
						if(pageNo == 0){
							serverPageSize = rs.getROWS().size();
						} else {
							//最后一页数量减少，表示没有数据了
							if(rs.getROWS().size() < serverPageSize){
								break;
							}
						}
					}else{
						break;
					}
				}else{
					log.error("执行失败，结果："+ (result==null?"":result.getRESULT()));
					break;
				}
			}
		}
		catch (ConnectException e) {
			e.printStackTrace();
			log.error("省网接口调用失败，连接超时，请查看省网url配置，或看下省网服务是否宕机");
		}catch (MalformedURLException e) {
			e.printStackTrace();
			log.error("new URL()的时候传进去的值为空，未加载到配置文件！");
		} catch (WebServiceException e) {
			e.printStackTrace();
			log.error("省网接口调用失败，连接超时，请查看省网url配置，或看下省网服务是否宕机");
			memo = e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			memo = e.getMessage();
		}
		
		endTime = new Date();
		
		//记录日志
		insertDdsLog(sendCode, ddsDate, ddsName, userId, totalCount, okCount, beginTime, endTime, memo);
		
		log.info("数据同步结束(共" + totalCount + "条记录，成功" + okCount + "条，失败" +(totalCount-okCount)+ "条。)");
		
		return "数据同步结束(共" + totalCount + "条记录，成功" + okCount + "条，失败" +(totalCount-okCount)+ "条。)";
	}
	
	/**
	 * 生成入参
	 * @param sendcode
	 * @param ddsName
	 * @param date
	 * @param pageNo
	 * @return
	 */
	private String makeInParam(String sendcode, String ddsName, String date, int pageNo) {
		String sendtime = ConvertUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		return String.format(InParamTemplate,sendcode,sendtime,ddsName,date,pageNo);
	}

	/**
	 * 处理整个表
	 * @param rs
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private int executeUpdate(String date, RECORDSET rs) throws Exception {
		
		String ddsName = rs.getNAME();
		List<ROW> rows = rs.getROWS();
	
		int okCount = 0;
		int errCount = 0;
		int i=0;
		Transaction tx = null;
		Statement stmt = null;
		
		try {
			tx = HibernateUtil2.currentSession().beginTransaction();
			stmt = HibernateUtil2.currentSession().doReturningWork(new ReturningWork<Statement>() {
				@Override
				public Statement execute(Connection connection) throws SQLException {
					return connection.createStatement();
				}
            });
			
			for (ROW row : rows) {
				i++;
				if(log.isDebugEnabled()){
					log.debug("处理第" + i + "行...");
				}
				if(row.getFIELDS() != null && row.getFIELDS().size() == 0){
					log.warn("第" + i + "行数据非法，字段数为0！(NO=["+row.getNO()+"])");
					continue;
				}
				if(executeUpdate(ddsName, date, row, stmt) ==1){
					okCount++;
				} else {
					log.warn("第" + i + "行处理失败！");
					errCount++;
					
					if(errCount > 10){
						throw new Exception("单页错误次数超过10次，执行终止");
					}
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			okCount = 0;
			if(tx != null) {
				tx.rollback();
			}
			throw e;
		} finally{
			if(stmt != null ){
				try { stmt.close(); } catch (Exception e2) {}
			}
		}

		return okCount;
	}

	/**
	 * 处理一条记录
	 * @param ddsName
	 * @param row
	 * @return
	 */
	private int executeUpdate(String ddsName, String date, ROW row, Statement stmt) {
		List<FIELD> fields = row.getFIELDS();
		String sql = null;
		FIELD keyField = getKeyField(fields);
		if("MYCS_JBXX".equals(ddsName)) {
			ddsName = "GGCS_BASEINFO";
		}else if("XXWS_JBXX".equals(ddsName)) {
			ddsName = "XXWS_BASEINFO";
		}
		if(ddsConfig.getInsertMode().equals("0") && ddsDao.exists(ddsName, keyField.getNAME(), keyField.getVALUE())){
			sql = makeUpdateSQL(ddsName, fields, keyField);//执行更新操作
		}else{
			sql = makeInsertSQL(ddsName, fields);
		}
		
		if(sql == null){
			return 0;
		}
		
		if(log.isTraceEnabled()){
			log.trace(sql);
		}
		
		try {
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			log.error(sql);
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	/**
	 * 取主键值(支持一个主键，且主键名为ID)
	 * @param fields
	 * @return
	 */
	private FIELD getKeyField(List<FIELD> fields) {
		for (FIELD field : fields) {
			if("ID".equalsIgnoreCase(field.getNAME())){
				return field;
			}
		}
		return null;
	}

	private String makeInsertSQL(String ddsName, List<FIELD> fields) {
		StringBuilder sql = new StringBuilder();
		StringBuilder values = new StringBuilder();
		
		sql.append("insert into ");
		sql.append(ddsName);
		sql.append(" (");
		
		int i=0;
		if("MYCS_JBXX".equals(ddsName) || "GGCS_BASEINFO".equals(ddsName)) {
			List<String> list = ddsConfig.getGGCSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("REGION_CODE".equals(field.getNAME().toUpperCase())) {
					field.setNAME("BUS_ADDR_CODE");
				}
				if(i++ > 0) {
					sql.append(",");
					values.append(",");
				}
				sql.append(field.getNAME());
				if ("1".equals(field.getNIL())){
					values.append("NULL");
				} else {
					addSQLValue(values,field);
				}
			}
			sql.append(") values(");
			sql.append(values);
			sql.append(")");
			
			return sql.toString();
		}else if("XXWS_JBXX".equals(ddsName) || "XXWS_BASEINFO".equals(ddsName)) {
			List<String> list = ddsConfig.getXXWSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				// TODO 该表字段待替换
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("WORK_TEL".equals(field.getNAME().toUpperCase())) {
					field.setNAME("WORK_LINK_TEL");
				}else if("ISMAINCAMPUS".equals(field.getNAME().toUpperCase())) {
					field.setNAME("SCHOOL_NATURE");
				}

				if(i++ > 0) {
					sql.append(",");
					values.append(",");
				}

				sql.append(field.getNAME());
				
				if ("1".equals(field.getNIL())){
					values.append("NULL");
				} else {
					addSQLValue(values,field);
				}
			}
			sql.append(") values(");
			sql.append(values);
			sql.append(")");
			
			return sql.toString();
		}else if("YLWS_JBXX".equals(ddsName)) {
			List<String> list = ddsConfig.getYLWSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				// TODO 该表字段待替换
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("WORK_TEL".equals(field.getNAME().toUpperCase())) {
					field.setNAME("WORK_LINK_TEL");
				}else if("SPECIALTIES_NAME".equals(field.getNAME().toUpperCase())) {
					field.setNAME("PROJECTS_CODE");
				}

				if(i++ > 0) {
					sql.append(",");
					values.append(",");
				}
				sql.append(field.getNAME());
				if ("1".equals(field.getNIL())){
					values.append("NULL");
				} else {
					addSQLValue(values,field);
				}
			}
			sql.append(") values(");
			sql.append(values);
			sql.append(")");
			return sql.toString();
		}
		return sql.toString();
	}

	private String makeUpdateSQL(String ddsName, List<FIELD> fields, FIELD keyField) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("update ");
		sql.append(ddsName);
		sql.append(" set ");
		
		int i=0;
		if("MYCS_JBXX".equals(ddsName) || "GGCS_BASEINFO".equals(ddsName)) {
			List<String> list = ddsConfig.getGGCSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("REGION_CODE".equals(field.getNAME().toUpperCase())) {
					field.setNAME("BUS_ADDR_CODE");
				}
				if(i++ > 0) {
					sql.append(",");
				}
				sql.append(field.getNAME());
				sql.append("=");
				if ("1".equals(field.getNIL())){
					sql.append("NULL");
				} else {
					addSQLValue(sql, field);
				}
			}
			sql.append(" where ");
			sql.append(keyField.getNAME());
			sql.append("=");
			addSQLValue(sql, keyField);
			return sql.toString();
		}else if("XXWS_JBXX".equals(ddsName) || "XXWS_BASEINFO".equals(ddsName)) {
			List<String> list = ddsConfig.getXXWSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				// TODO 该表字段待替换
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("WORK_TEL".equals(field.getNAME().toUpperCase())) {
					field.setNAME("WORK_LINK_TEL");
				}else if("ISMAINCAMPUS".equals(field.getNAME().toUpperCase())) {
					field.setNAME("SCHOOL_NATURE");
				}
//				else if("WATER_TYPE".equals(field.getNAME().toUpperCase())) {
//					field.setNAME("ECGS_SY");
//				}
				if(i++ > 0) {
					sql.append(",");
				}
				sql.append(field.getNAME());
				sql.append("=");
				if ("1".equals(field.getNIL())){
					sql.append("NULL");
				} else {
					addSQLValue(sql, field);
				}
			}
			sql.append(" ,NRWSJDXG = 1 ");
			sql.append(" where ");
			sql.append(keyField.getNAME());
			sql.append("=");
			addSQLValue(sql, keyField);
			
			return sql.toString();
		}else if("YLWS_JBXX".equals(ddsName)) {
			List<String> list = ddsConfig.getYLWSLIST();
			for (FIELD field : fields) {
				if(!checkFieldName(field.getNAME())){
					log.error("字段名非法："+field.getNAME());
					return null;
				}
				// TODO 该表字段待替换
				if(!list.contains(field.getNAME().toUpperCase())) {
					continue;
				}else if("GAOJI_TYPE".equals(field.getNAME().toUpperCase())) {
					field.setNAME("COMP_TYPE_SEC");
				}else if("SPECIALTIES_NAME".equals(field.getNAME().toUpperCase())) {
					field.setNAME("PROJECTS_CODE");
				}
				
				
				if(i++ > 0) {
					sql.append(",");
				}
				sql.append(field.getNAME());
				sql.append("=");
				if ("1".equals(field.getNIL())){
					sql.append("NULL");
				} else {
					addSQLValue(sql, field);
				}
			}
			sql.append(" where ");
			sql.append(keyField.getNAME());
			sql.append("=");
			addSQLValue(sql, keyField);
			return sql.toString();
		}
		
		return sql.toString();
	}
	
	private boolean checkFieldName(String fieldName){
		return (fieldName != null) && FieldNamePattern.matcher(fieldName).matches();
	}

	private void addSQLValue(StringBuilder buf, FIELD field) {
		String value = SQLUtil.escapeSQL(field.getVALUE());
		
		if("D".equals(field.getTYPE())) {
			buf.append("to_date('" + value + "','YYYYMMDDHH24MISS')");
		} else{
			buf.append("'");
			buf.append(value);
			buf.append("'");
		}
	}

	//------------------------------------------------------
	/**
	 * 插入日志
	 */
	private void insertDdsLog(String sendCode, String ddsDate, String ddsName
			, String userId, int totalCount, int okCount, Date beginTime, Date endTime, String memo) {
		if(!ddsConfig.getLogEnabled().equals("1")) return;
		
		Transaction tx = null;
		try{
			BgkDdsLog ddsLog = new BgkDdsLog();
			
			ddsLog.setID(ConvertUtil.generateID());
			ddsLog.setSENDCODE(sendCode);
			ddsLog.setDDS_DATE(ddsDate);
			ddsLog.setDDS_NAME(ddsName);
			ddsLog.setCLIENT_SERVER("CLIENT");
			ddsLog.setUSER_ID(userId);
			ddsLog.setTOTAL_COUNT(totalCount);
			ddsLog.setOK_COUNT(okCount);
			ddsLog.setBEGIN_TIME(beginTime);
			ddsLog.setEND_TIME(endTime);
			
			if(memo==null) {
				memo="";
			} else {
				if(memo.length()>100) {
					memo = memo.substring(0,100);
				}
			}
			ddsLog.setMEMO(memo);

			tx = HibernateUtil2.currentSession().beginTransaction();
			
			ddsDao.insertDdsLog(ddsLog);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null) tx.rollback();
			log.error("添加日志失败。");
			throw e;
		}
	}
}
