package com.joolun.cloud.upms.admin.controller;

import com.joolun.cloud.upms.admin.service.MobileService;
import com.joolun.cloud.common.core.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.joolun.cloud.common.security.annotation.Inside;

/**
 * @author JL
 * @date 2018/11/14
 * <p>
 * 手机验证码
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mobile")
@Api(value = "mobile", tags = "手机管理模块")
public class MobileController {
	private final MobileService mobileService;

	@Inside(value = false)
	@GetMapping("/{mobile}")
	public R sendSmsCode(@PathVariable String mobile) {
		return mobileService.sendSmsCode(mobile);
	}
}
