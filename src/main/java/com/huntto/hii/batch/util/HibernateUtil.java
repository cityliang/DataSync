//package com.huntto.hii.batch.util;
//
//import java.io.File;
//import java.net.URL;
//import java.util.Properties;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * Hibernate工具类<br/>
// * 初始化SessionFactory, 管理Session的创建和关闭.
// * 
// * @author risen
// *
// */
//@Component
//public final class HibernateUtil {
//	
////	@PersistenceContext
////    private EntityManager entityManager;
//	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	private static final String CONFIG_FILE = "/jdbc.properties";
//	private static Configuration cfg = null;
//	private static final ThreadLocal<Session> sessionData = new ThreadLocal<Session>();
//	
//	 private static final SessionFactory factory = buildFactory();
//    //写一个buildFactory方法
//    private static SessionFactory buildFactory() {
//        //读取hibernate.cfg.xml配置，加载数据库和实体类
//        Configuration cfg = new Configuration().configure();
//        return  cfg.buildSessionFactory();
//    }
//
//	/** 是否显示show_sql */
//	public static boolean show_sql = false;
//	private HibernateUtil() {
//	}
//	public SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//
//	/**
//	 * 取得Session
//	 * 
//	 * @return
//	 * @throws HibernateException
//	 */
//	public Session currentSession() throws HibernateException {
//		Session session = (Session) sessionData.get();
//
//		// 如果该线程还没有Session,则创建一个新的Session
//		if (session == null) {
//			session = sessionFactory.openSession();
//			sessionData.set(session);
//		}
//
//		return session;
//	}
//
//	/**
//	 * 创建新Session
//	 * 
//	 * @return
//	 * @throws HibernateException
//	 */
//	public Session newSession() throws HibernateException {
//		Session session = sessionFactory.openSession();
//		return session;
//	}
//
//	/**
//	 * 关闭Session
//	 * 
//	 * @throws HibernateException
//	 */
//	public static void closeSession() throws HibernateException {
//		Session session = (Session) sessionData.get();
//		closeSession(session);
//		sessionData.set(null);
//	}
//
//	/**
//	 * 关闭Session
//	 * 
//	 * @throws HibernateException
//	 */
//	public static void closeSession(Session session) {
//		if (session != null) {
//			try {
//				session.close();
//			} catch (Exception e) {
//				// 忽略
//			}
//		}
//	}
//
//	/**
//	 * 获取实体对应的表名
//	 * 
//	 * @param clazz
//	 * @return
//	 */
////	public static String getTableName(Class<?> clazz) {
////		String clazzName = clazz.getName();
////		PersistentClass pc = cfg.getClassMapping(clazzName);
////		if(pc == null){
////			throw new RuntimeException("无法取得该实体的物理表名。 实体：" + clazzName);
////		}
////		return pc.getTable().getName();  
////	}
//
//}
