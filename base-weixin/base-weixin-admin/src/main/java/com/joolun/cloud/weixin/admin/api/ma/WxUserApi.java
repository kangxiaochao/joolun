/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.api.ma;

import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.common.dto.LoginMaDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信用户
 *
 * @author JL
 * @date 2019-08-25 15:39:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/wxuser")
public class WxUserApi {

	private final WxUserService wxUserService;
	/**
	 * 小程序用户登录
	 * @param request
	 * @param loginMaDTO
	 * @return
	 */
	@PostMapping("/login")
	public R login(HttpServletRequest request,
				   @RequestBody LoginMaDTO loginMaDTO){
		try {
			WxApp wxApp = WxMaConfiguration.getApp(request);
			WxUser wxUser = wxUserService.loginMa(wxApp,loginMaDTO.getJsCode());
			return R.ok(wxUser);
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}
}
