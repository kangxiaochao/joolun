package com.joolun.cloud.auth;

import com.joolun.cloud.common.security.annotation.EnableBaseFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author
 * 认证授权中心
 */
@SpringCloudApplication
@EnableBaseFeignClients
public class BaseAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseAuthApplication.class, args);
	}
}
