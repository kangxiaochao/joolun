package com.joolun.cloud.upms.admin;

import com.joolun.cloud.common.security.annotation.EnableBaseFeignClients;
import com.joolun.cloud.common.security.annotation.EnableBaseResourceServer;
import com.joolun.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author
 * 用户管理系统
 */
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BaseUpmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseUpmsApplication.class, args);
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(DataSize.ofMegabytes(10));
		// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(10));
		return factory.createMultipartConfig();
	}
}
