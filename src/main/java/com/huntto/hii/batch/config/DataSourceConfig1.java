package com.huntto.hii.batch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig1 {
	@Bean(name = "primaryDataSource")
	@Qualifier("primaryDataSource") // 该注解指定注入的Bean的名称，Spring框架使用byName方式寻找合格的 bean，这样就消除了byType方式产生的歧义
	@ConfigurationProperties(prefix = "spring.datasource.primary") // 读取配置文件里前缀 为"spring.datasource.primary"的语句
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

//	@Bean(name = "secondaryDataSource")
//	@Qualifier("secondaryDataSource")
//	@Primary // 有时候我们能保证同一个类型在spring容器中只有一个实例，有时候我们保证不了，此时不讨论by name注入。这个时候@Primary注解就非常重要了
//	@ConfigurationProperties(prefix = "spring.datasource.secondary")
//	public DataSource secondaryDataSource() {
//		return DataSourceBuilder.create().build();
//	}

	@Bean(name = "primaryJdbcTemplate")
	public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

//	@Bean(name = "secondaryJdbcTemplate")
//	public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}
}
