package com.joolun.cloud.codegen;

import com.joolun.cloud.common.datasource.annotation.EnableDynamicDataSource;
import com.joolun.cloud.common.security.annotation.EnableBaseResourceServer;
import com.joolun.cloud.common.security.annotation.EnableBaseFeignClients;
import com.joolun.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author JL
 * @date 2018/07/29
 * 代码生成模块
 */
@EnableDynamicDataSource
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BaseCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseCodeGenApplication.class, args);
	}
}
