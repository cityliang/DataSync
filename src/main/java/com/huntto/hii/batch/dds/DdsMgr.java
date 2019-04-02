package com.huntto.hii.batch.dds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.huntto.hii.batch.config.DdsConfig;
import com.huntto.hii.batch.util.HibernateUtil2;
import com.huntto.hii.batch.util.ValidateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据分发客户端主模块<br />
 */
@Slf4j
public final class DdsMgr {
	
	@Autowired
	private DdsConfig ddsConfig;
	
	private static DdsMgr instance = null;
	
	/** 关闭标识 */
	private boolean cancelled = false;
	/** 启动标识 */
	private boolean started = false;
	/** 休息对象 */
	private Object waitObj = new Object();
	/** 最后运行的日期 */
	private String lastExecutedDay = null;

	private DdsWorker worker = null; 
	
	protected DdsMgr(){
		init();
	}
	
	public static DdsMgr getInstance(){
		if(instance == null){
			instance = new DdsMgr();
		}
		return instance;
	}

	private void init() {
		worker = new DdsWorkerImpl(); 
	}

	/**
	 * 启动单次报卡<br/>
	 * 手动执行，则根据指定的业务和id进行报卡
	 */
	public synchronized void start(HashMap<String, String> params) {
		execute(params);
	}
	
	/**
	 * 启动循环同步
	 */
	public synchronized void start() {
		if(started){
			log.warn("数据同步已经开始，不能重复启动。");
			return;
		}

		log.info("数据同步服务开始。");
		started = true;
		cancelled = false;
		HashMap<String, String> params = new HashMap<String, String>();
		
		while(!cancelled){
			try {
				//检查是否有报卡指示
				if(getTrigger(params)){
					execute(params);
				}
				
				//每天一次执行
				if(isDayTrigger()){
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					String[] args = {df.format(new Date())};
					execute(args);
				}
			} catch (Exception ex) {
				log.error("执行发生错误。", ex);
				HibernateUtil2.closeSession();
			}
			
			if(!cancelled){
				// 最少10秒间隔循环运行
				try {
					synchronized (waitObj) {
						waitObj.wait(10000);
					}
				} catch (Throwable e) {
					log.error("休息10秒失败, 即将退出程序。");
					break;
				}
			}
			HibernateUtil2.closeSession();
		}
		
		started = false;
		log.info("数据同步服务结束。");
	}

	/**
	 * 每天一次执行
	 * @return
	 */
	private boolean isDayTrigger() {
		Date now = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmm");
		String nowDayTime = df1.format(now);
		String nowDay = nowDayTime.substring(0, 8);
		String nowTime = nowDayTime.substring(8);
		String triggerTime = ddsConfig.getDailyTriggerTime(); //默认：晚上10点运行
		
		if(lastExecutedDay == null || nowDay.compareTo(lastExecutedDay)>0){
			if(nowTime.compareTo(triggerTime)>0){
				lastExecutedDay = nowDay;
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 触发器执行
	 * @param params
	 * @return
	 */
	private boolean getTrigger(HashMap<String, String> params) {
		params.clear();
		return false;
	}

	/**
	 * 停止报卡
	 */
	public void stop() {
		log.info("正在停止报卡管理器。");
		cancelled = true;
		DdsWorker.cancel(); //停止所有报卡模块
		
		try {
			synchronized (waitObj) {
				waitObj.notify();
			}
		} catch (Throwable e) {
			log.error(String.valueOf(e));
		}
		log.debug("停止执行完毕。");
	}

	/**
	 * 启动
	 */
	private void execute(HashMap<String, String> params) {
		String ddsDate = params.get(DdsWorker.PARAM_DDS_DATE);
		String ddsName = params.get(DdsWorker.PARAM_DDS_NAME);
		
		worker.reset();
		worker.addParams(DdsWorker.PARAM_DDS_DATE, ddsDate);
		worker.addParams(DdsWorker.PARAM_DDS_NAME, ddsName);
		execute(worker);
	}
	
	/**
	 * 执行报卡
	 * @param worker
	 */
	private void execute(DdsWorker worker) {
		try {
			worker.execute();
		} catch (Exception e) {
			log.error("执行发生错误。错误信息："+e.getMessage(), e);
		}
	}
	
	/**
	 * 手动执行时候mian函数<br />
	 * 服务模式不调用该行函数<br />
	 * <pre>
	 * 使用方法：
	 * java DdsMgr &lt;同步日期yyyyMMdd&gt; [同步名称]
	 * 例子：
	 * 1) 同步所有类型数据
	 *    java DdsMgr 20151206
	 * 2) 同步指定类型数据
	 *    java DdsMgr 20151206 mycs_jbxx,yycs_jbxx
	 * </pre>
	 * @param args
	 */
	public static void main(String[] args) {
		DdsMgr ddsMgr = DdsMgr.getInstance();
		int result = ddsMgr.execute(args);
		
		System.exit(result);
	}

	/**
	 * 执行报卡操作
	 * @param args
	 * @return
	 */
	public int execute(String[] args) {
		if((args.length < 1) || (args.length > 2)){
			log.info("使用方法：java DdsMgr <同步日期yyyyMMdd> [同步名称]");
			log.info("例子：");
			log.info("  1) 同步所有类型数据");
			log.info("     java DdsMgr 20151206");
			log.info("  2) 同步指定类型数据");
			log.info("     java DdsMgr 20151206 mycs_jbxx,yycs_jbxx");
			log.info("  3) 日期可以为00000000，表示整张表数据(第一次同步用)");
			log.info("     java DdsMgr 00000000 mycs_jbxx");
			
			return 1;
		}

		String ddsDate = args[0];
		if(!ValidateUtil.isDate8(ddsDate) && !"00000000".equals(ddsDate)){
			log.error("同步日期必须为yyyyMMdd格式");
			return 1;
		}
		
		try {
			//启动报卡
			DdsMgr bgkMgr = DdsMgr.getInstance();
			
			//可以逗号分隔多个
			String ddsNames =  (args.length>=2 ? args[1] : ddsConfig.getDdsNames());
	
			//设置运行参数
			HashMap<String, String> params = new HashMap<String, String>();
			params.put(DdsWorker.PARAM_DDS_DATE, ddsDate);
			
			String[] ddsNamesArray = ddsNames.split(",");
//			List<String> list = ddsConfig.getDdsNames();
//			if(!list.isEmpty() && list.size() > 0) {
//				for(String dds_name : list) {
//					params.put(DdsWorker.PARAM_DDS_NAME, dds_name);
//					bgkMgr.start(params);
//				}
//				
//			}
			if(ddsNamesArray.length > 0){
				for (String dds_name : ddsNamesArray) {
					params.put(DdsWorker.PARAM_DDS_NAME, dds_name);
					bgkMgr.start(params);
				}
			}
		} catch (Exception ex) {
			log.error("运行失败。", ex);
		}
		
		return 0;
	}

}
