package com.huntto.hii.batch.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
//	@Bean
//    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
//        return hemf.getSessionFactory();
//    }
	
	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	
//	@Bean
////	@Bean(name="openSession")
//    public Session openSession(){
//        //StandardServiceRegistryBuilder默认从resource下加载hibernate.cfg.xml
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
//        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
//        Session openSession = sessionFactory.openSession();
//        return openSession;
//    }

}
