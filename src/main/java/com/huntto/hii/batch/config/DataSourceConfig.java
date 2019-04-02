package com.huntto.hii.batch.config;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Description: 类描述 <br/>
 * Copyright: 版权归浙江创得信息技术有限公司所有<br/>
 * Project_name jkzjk<br/>
 * Package_name com.huntto.config<br/>
 * Author 梁城市<br/>
 * Email city_wangyi@163.com<br/>
 * Create_time 2018/12/9 15:54<br/>
 */
@Slf4j
@Configuration
@Profile({ "dev", "prod" })
public class DataSourceConfig {

//	@Value("${spring.datasource.driverClassName}")
//	String driverClassName;
//
//	@Value("${spring.datasource.url}")
//	String url;
//
//	@Value("${spring.datasource.username}")
//	String username;
//
//	@Value("${spring.datasource.password}")
//	String password;

//	@Bean(initMethod = "init", destroyMethod = "close", name = "dataSource")
//	public DruidDataSource ruidDataSourceBean() {
//		log.info("-------------------init alibaba datasource config-------------------------");
//
//		DruidDataSource druidDataSource = new DruidDataSource();
//
//		druidDataSource.setDriverClassName(driverClassName);
//		druidDataSource.setUrl(url);
//		druidDataSource.setUsername(username);
//		druidDataSource.setPassword(password);
//
//		log.info("-------------------init alibaba datasource config done-------------------------");
//
//		return druidDataSource;
//	}
	
	/**
	 * 
	 * @Title  getDatabaseIdProvider 
	 * @Description  自动识别使用的数据库类型<br>
	 *   在mapper.xml中databaseId的值就是跟这里对应，如果没有databaseId选择则说明该sql适用所有数据库
	 * @return  DatabaseIdProvider DatabaseIdProvider
	 *
	 */
//    @Bean
//    public DatabaseIdProvider getDatabaseIdProvider(){
//        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
//        Properties properties = new Properties();
//        properties.setProperty("Oracle","oracle");
//        properties.setProperty("MySQL","mysql");
//        properties.setProperty("DB2","db2");
//        properties.setProperty("Derby","derby");
//        properties.setProperty("H2","h2");
//        properties.setProperty("HSQL","hsql");
//        properties.setProperty("Informix","informix");
//        properties.setProperty("MS-SQL","ms-sql");
//        properties.setProperty("PostgreSQL","postgresql");
//        properties.setProperty("Sybase","sybase");
//        properties.setProperty("Hana","hana");
//        databaseIdProvider.setProperties(properties);
//        return databaseIdProvider;
//    }
}
