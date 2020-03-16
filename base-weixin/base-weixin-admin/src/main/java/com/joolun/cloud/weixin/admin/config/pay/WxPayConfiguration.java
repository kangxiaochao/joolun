/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.config.pay;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.common.entity.WxApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 微信支付Configuration
 * @author JL
 *
 */
@Slf4j
@Configuration
public class WxPayConfiguration {
	/**
	 * 全局缓存WxPayService
	 */
	private static Map<String, WxPayService> payServices = Maps.newHashMap();

	private static WxAppService wxAppService;

	@Autowired
	public WxPayConfiguration( WxAppService wxAppService) {
		this.wxAppService = wxAppService;
	}

	/**
	 *  获取全局缓存WxMpService
	 * @param appId
	 * @return
	 */
	public static WxPayService getPayService(String appId) {
		WxPayService wxPayService = payServices.get(appId);
        if(wxPayService == null) {
        	WxApp wxApp = wxAppService.findByAppId(appId);
        	if(wxApp!=null) {
        		if(StrUtil.isNotBlank(wxApp.getMchId()) && StrUtil.isNotBlank(wxApp.getMchKey())){
					WxPayConfig payConfig = new WxPayConfig();
					payConfig.setAppId(wxApp.getId());
					payConfig.setMchId(wxApp.getMchId());
					payConfig.setMchKey(wxApp.getMchKey());
					payConfig.setKeyPath(wxApp.getKeyPath());
					// 可以指定是否使用沙箱环境
					payConfig.setUseSandboxEnv(false);
					wxPayService = new WxPayServiceImpl();
					wxPayService.setConfig(payConfig);
					payServices.put(appId, wxPayService);
				}
        	}
        }
		return wxPayService;
    }

	/**
	 * 移除WxPayService缓存
	 * @param appId
	 */
	public static void removeWxPayService(String appId){
		payServices.remove(appId);
	}
}
