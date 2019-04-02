package com.huntto.hii.batch.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CxzdbController {
	@Autowired

	@Qualifier("primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;

	
	@Qualifier("primaryJdbcTemplate")
	protected HibernateTemplate hibernateTempplate;
	
	
//	@Qualifier("secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;
	@RequestMapping("/test3")
	public String test3() {
		
		System.out.println(hibernateTempplate.toString());
		System.out.println(jdbcTemplate1.toString());
        return "";
	}
	
	
	
	@RequestMapping("/test11")
	public List<Map<String, Object>> getCxzdb11() {
		// String sql = "SELECT * FROM sys_user";
		final String sql = "SELECT * FROM XXWS_BASEINFO ";
		List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate1
				.execute(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						return con.prepareStatement(sql);
					}
				}, new PreparedStatementCallback<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.execute();
						ResultSet rs = ps.getResultSet();
						while (rs.next()) {
							System.out.println("==" + rs.getString(1));
							System.out.println("==" + rs.getString(2));
							System.out.println("==" + rs.getString(3));
							System.out.println("==" + rs.getString(4));
							System.out.println("==" + rs.getString(5));
							// Map<String, Object> map = new HashMap<>();
							// map.put("id", rs.getString("id"));
						}
						return null;
					}
				});
		return resObj;
	}
	
	
	
	
	
	
	
	
	
	// @Autowired
	// protected JdbcTemplate jdbcTemplate;
	@RequestMapping("/test1")
	public List<Map<String, Object>> getCxzdb1() {
		// String sql = "SELECT * FROM sys_user";
		final String sql = "SELECT * FROM XXWS_BASEINFO ";
		List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate1
				.execute(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						return con.prepareStatement(sql);
					}
				}, new PreparedStatementCallback<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.execute();
						ResultSet rs = ps.getResultSet();
						while (rs.next()) {
							System.out.println("==" + rs.getString(1));
							System.out.println("==" + rs.getString(2));
							System.out.println("==" + rs.getString(3));
							System.out.println("==" + rs.getString(4));
							System.out.println("==" + rs.getString(5));
							// Map<String, Object> map = new HashMap<>();
							// map.put("id", rs.getString("id"));
						}
						return null;
					}
				});
		return resObj;
	}
	
	@RequestMapping("/test2")
	public List<Map<String, Object>> getCxzdb2() {
		// String sql = "SELECT * FROM sys_user";
		final String sql = "SELECT * FROM xxws_jbxx";
		List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate2.execute(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						return con.prepareStatement(sql);
					}
				}, new PreparedStatementCallback<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.execute();
						ResultSet rs = ps.getResultSet();
						while (rs.next()) {
							System.out.println("==" + rs.getString(1));
							System.out.println("==" + rs.getString(2));
							System.out.println("==" + rs.getString(3));
							System.out.println("==" + rs.getString(4));
							System.out.println("==" + rs.getString(5));
						}
						return null;
					}
				});
		return resObj;
	}
}
