package com.huntto.hii.batch.util;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HibernateUtil2 {
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();// 创建一个ThreadLocal<Session>对象用来存放当前Session对象
	private static Configuration configuration = new Configuration();
	private static String configFile = CONFIG_FILE_LOCATION;
	
	private static SessionFactory sessionFactory;
	private static Session session;
	@Autowired
	private SessionFactory sFactory;
	@PostConstruct
	public void initSessionFactory() {
		sessionFactory = sFactory;
	}

	// 声明一个私有无参构造函数
	private HibernateUtil2(){}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void rebuildSessionFactory() {
		synchronized (sessionFactory) {
			try {
				configuration.configure(configFile);
				log.info("加载配置文件2"+configFile);
//				ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
				//在Hibernate5.x会使用注册机来解析映射信息，所以会先创建ServiceRegistry对象，使用StandardServiceRegistryBuilder()
	            ServiceRegistry serviceRgistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRgistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 打开session
	public static Session currentSession(){
		//获取当前线程的session对象
        session = sessionThreadLocal.get();
        try{
            if(session == null || !session.isOpen()){
				if(sessionFactory == null){//如果sessionFactory为null，创建一个
                    rebuildSessionFactory();
                }
				//如果session没有打开，就用sessionFactory打开
                session = (sessionFactory!=null)?sessionFactory.openSession():null;
                //将session对象放到ThreadLocal对象里，以便使用
                sessionThreadLocal.set(session);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return session;
    }

	public static void closeSession() {
		Session session = sessionThreadLocal.get();
		sessionThreadLocal.set(null);
		try {
			if (session != null && session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setConfigFile(String configFile) {
		HibernateUtil2.configFile = configFile;
		sessionFactory = null;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}
}
