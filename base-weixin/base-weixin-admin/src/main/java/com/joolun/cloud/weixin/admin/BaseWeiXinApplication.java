/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin;

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
 * @author JL
 * @date 2019/02/29
 * 微信公众号模块
 */
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BaseWeiXinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseWeiXinApplication.class, args);
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
