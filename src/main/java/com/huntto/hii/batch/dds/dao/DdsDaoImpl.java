package com.huntto.hii.batch.dds.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.huntto.hii.batch.util.SQLUtil;

import lombok.NoArgsConstructor;
@Component
@NoArgsConstructor
public class DdsDaoImpl extends DdsDaoBase {
	public boolean exists(String tableName, String keyName, String value) {
		String sql = String.format("select 1 from %s where %s = '%s'", tableName, keyName, SQLUtil.escapeSQL(value));
		Query query = getSesison().createSQLQuery(sql);
		return query.list().size()>0;
	}
}
