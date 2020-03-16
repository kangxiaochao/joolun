package com.joolun.cloud.common.security.thirdparty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * qq登录配置信息
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "thirdparty.qq")
public class QqThirdpartyConfig {
	private String appid;
	private String secret;
}
