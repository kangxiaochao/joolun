package com.joolun.cloud.lianghao;

import com.joolun.cloud.common.security.annotation.EnableBaseResourceServer;
import com.joolun.cloud.common.security.annotation.EnableBaseFeignClients;
import com.joolun.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JL
 * @date 2018/07/29
 * 代码生成模块
 */
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BaseLiangHaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseLiangHaoApplication.class, args);
	}
}
