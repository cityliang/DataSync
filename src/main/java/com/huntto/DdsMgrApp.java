package com.huntto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@EnableScheduling
@SpringBootApplication
//(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class DdsMgrApp extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		for(String str:args) {
			log.info("输入参数字符串"+str.toString());
		}
        SpringApplication.run(DdsMgrApp.class, args);
        
//        int result = DdsMgr.execute(args);
//		
//		System.exit(result);
        
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DdsMgrApp.class);
	}

}
