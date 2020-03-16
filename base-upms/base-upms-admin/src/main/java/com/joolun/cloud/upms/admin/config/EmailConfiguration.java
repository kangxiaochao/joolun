/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.upms.admin.config;

import io.github.biezhi.ome.OhMyEmail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * 邮箱配置初始化
 * @author JL
 *
 */
@Slf4j
@Configuration
public class EmailConfiguration {

	private static EmailConfigProperties emailConfigProperties;
	public EmailConfiguration(EmailConfigProperties emailConfigProperties){
		this.emailConfigProperties = emailConfigProperties;
	}

	/**
	 * 邮箱配置初始化
	 */
	@PostConstruct
	public void init() {
		String mailHost = emailConfigProperties.getMailSmtpHost();
		String mailUsername = emailConfigProperties.getMailSmtpUsername();
		String mailPassowrd = emailConfigProperties.getMailSmtpPassword();

		if (StringUtils.isNoneBlank(mailHost, mailUsername, mailPassowrd)) {
			final Properties properties = OhMyEmail.defaultConfig(false);
			properties.setProperty("mail.smtp.host", mailHost);
			OhMyEmail.config(properties, mailUsername, mailPassowrd);
		} else {
			log.error("邮件服务配置信息未设置, 请在后台系统配置中进行设置");
		}
	}
}
