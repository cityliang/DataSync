package com.huntto.hii.batch.dds.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.huntto.hii.batch.dds.model.BgkDdsLog;
import com.huntto.hii.batch.util.HibernateUtil2;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public abstract class DdsDaoBase {
	protected SessionFactory getSessionFactory(){
		return HibernateUtil2.getSessionFactory();
	}
	protected Session getSesison(){
		return HibernateUtil2.currentSession();
	}

	public int executeUpdate(String sql) {
		Query query = getSesison().createSQLQuery(sql);
		return query.executeUpdate();
	}

	public void insertDdsLog(BgkDdsLog ddsLog) {
		getSesison().save(ddsLog);
	}
}
