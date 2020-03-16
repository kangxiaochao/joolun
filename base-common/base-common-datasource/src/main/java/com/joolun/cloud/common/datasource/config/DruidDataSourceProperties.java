package com.joolun.cloud.common.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 参考DruidDataSourceWrapper
 */
@Data
@Component
@ConfigurationProperties("spring.datasource.druid")
public class DruidDataSourceProperties {
	private String username;
	private String password;
	private String url;
	private String driverClassName;
}
