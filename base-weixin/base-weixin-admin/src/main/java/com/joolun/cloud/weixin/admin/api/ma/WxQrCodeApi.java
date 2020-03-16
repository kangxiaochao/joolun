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
import com.joolun.cloud.weixin.common.dto.LoginMaDTO;
import com.joolun.cloud.weixin.common.dto.MaQrCodeDTO;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;

/**
 * 小程序码
 *
 * @author JL
 * @date 2019-10-25 15:39:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/wxqrcode")
public class WxQrCodeApi {

	/**
	 * 生成小程序码
	 * @param request
	 * @param maQrCodeDTO
	 * @return
	 */
	@PostMapping("/unlimited")
	public R getUnlimited(HttpServletRequest request,
				   @RequestBody MaQrCodeDTO maQrCodeDTO){
		try {
			String wxAppId = WxMaConfiguration.getAppId(request);
			File fileImg = WxMaConfiguration.getMaService(wxAppId).getQrcodeService()
					.createWxaCodeUnlimit(maQrCodeDTO.getScene(), maQrCodeDTO.getPage(),100, true, null, false);
			FileInputStream inputFile = new FileInputStream(fileImg);
			byte[] buffer = new byte[(int)fileImg.length()];
			inputFile.read(buffer);
			inputFile.close();
			String rs =  new BASE64Encoder().encode(buffer);
			return R.ok(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}
}
