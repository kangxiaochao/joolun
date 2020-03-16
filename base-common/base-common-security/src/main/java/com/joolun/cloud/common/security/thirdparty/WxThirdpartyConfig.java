package com.joolun.cloud.common.security.thirdparty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * 微信登录配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "thirdparty.wx")
public class WxThirdpartyConfig {
	private String appid;
	private String secret;
}
