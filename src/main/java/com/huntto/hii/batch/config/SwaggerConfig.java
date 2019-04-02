package com.huntto.hii.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerConfig {
	// controller接口所在的包
	private String basePackage = "com.huntto.server.service";

	// 创建人
	private String name = "CITY";
	
	// 创建人
	private String url = "";
	
	// 创建人
	private String email = "city_wangyi@163.com";

	// 当前文档的标题
	private String title = "湖州数据交换";

	// 当前文档的详细描述
	private String description = "省里数据和湖州数据交换";

	// 当前文档的版本
	private String version = "V1.0";

	// swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 为当前包路径
				.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
	}

	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)// 页面标题
				.contact(new Contact(name, url, email))// 创建人
				.version(version)// 版本号
				.description(description)// 描述
				.build();
	}
}
