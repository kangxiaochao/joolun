/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import com.joolun.cloud.mall.common.feign.FeignWxUserService;
import com.joolun.cloud.mall.common.dto.WxOpenDataDTO;
import com.joolun.cloud.weixin.common.entity.WxUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户
 *
 * @author JL
 * @date 2019-08-25 15:39:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/wxuser")
public class WxUserApi {

	private final FeignWxUserService feignWxUserService;
	private final UserInfoService userInfoService;

	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@GetMapping
	public R getById(HttpServletRequest request){
		WxUser wxUser = new WxUser();
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(wxUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return feignWxUserService.getById(wxUser.getId(), SecurityConstants.FROM_IN);
	}

	/**
	 * 保存用户信息
	 * @param wxOpenDataDTO
	 * @return
	 */
	@PostMapping
	public R save(HttpServletRequest request, @RequestBody WxOpenDataDTO wxOpenDataDTO){
		WxUser wxUser = new WxUser();
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(wxUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		wxOpenDataDTO.setAppId(wxUser.getAppId());
		wxOpenDataDTO.setUserId(wxUser.getId());
		wxOpenDataDTO.setSessionKey(wxUser.getSessionKey());
		return userInfoService.saveByWxUser(wxOpenDataDTO);
	}
}
