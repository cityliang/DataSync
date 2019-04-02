//package com.huntto.hii.batch.util;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.springframework.stereotype.Component;
//
//import javassist.expr.NewArray;
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//@Component
//public class HibernateUtil1 {
//	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
//	private static final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();// 创建一个ThreadLocal<Session>对象用来存放当前Session对象
//	private static Configuration configuration = new Configuration();
//	private static SessionFactory sessionFactory;
//	private static String configFile = CONFIG_FILE_LOCATION;
//
//	private static Object object = new Object();
//	static
//    {
//        try{
//            configuration.configure(configFile);//读取并解析hibernate.cfg.xml文件
//            //在Hibernate4.x会使用注册机来解析映射信息，所以会先创建ServiceRegistry对象
////            ServiceRegistry serviceRgistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
//            //在Hibernate5.x会使用注册机来解析映射信息，所以会先创建ServiceRegistry对象，使用StandardServiceRegistryBuilder()
//            ServiceRegistry serviceRgistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            //ServiceRegister对象作为参数，使用configuration对象创建sessionFactory对象，即将configuration对象里的信息copy到sessionFactory缓存中
//            sessionFactory = configuration.buildSessionFactory(serviceRgistry);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//	// 声明一个私有无参构造函数
//	private HibernateUtil1(){}
//
//	public static SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//
//	public static void rebuildSessionFactory() {
//		synchronized (object) {
//			try {
//				configuration.configure(configFile);
//				log.info("加载配置文件1"+configFile);
////				ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
//				//在Hibernate5.x会使用注册机来解析映射信息，所以会先创建ServiceRegistry对象，使用StandardServiceRegistryBuilder()
//	            ServiceRegistry serviceRgistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//				sessionFactory = configuration.buildSessionFactory(serviceRgistry);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	// 打开session
//	public static Session currentSession(){
//		//获取当前线程的session对象
//        Session session = sessionThreadLocal.get();
//        try{
//            if(session == null || !session.isOpen()){
//				if(sessionFactory == null){//如果sessionFactory为null，创建一个
//                    rebuildSessionFactory();
//                }
//				//如果session没有打开，就用sessionFactory打开
//                session = (sessionFactory!=null)?sessionFactory.openSession():null;
//                //将session对象放到ThreadLocal对象里，以便使用
//                sessionThreadLocal.set(session);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return session;
//    }
//
//	public static void closeSession() {
//		Session session = sessionThreadLocal.get();
//		sessionThreadLocal.set(null);
//		try {
//			if (session != null && session.isOpen()) {
//				session.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void setConfigFile(String configFile) {
//		HibernateUtil1.configFile = configFile;
//		sessionFactory = null;
//	}
//
//	public static Configuration getConfiguration() {
//		return configuration;
//	}
//}
