package com.joolun.cloud.upms.admin.controller;

import com.joolun.cloud.upms.common.feign.FeignTokenService;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author
 * getTokenPage 管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
@Api(value = "token", tags = "令牌管理模块")
public class TokenController {
	private final FeignTokenService feignTokenService;

	/**
	 * 分页token 信息
	 *
	 * @param params 参数集
	 * @return token集合
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_token_index')")
	public R getTokenPage(@RequestParam Map<String, Object> params) {
		return feignTokenService.getTokenPage(params, SecurityConstants.FROM_IN);
	}

	/**
	 * 删除
	 *
	 * @param token getTokenPage
	 * @return ok/false
	 */
	@SysLog("删除用户token")
	@DeleteMapping("/{token}")
	@PreAuthorize("@ato.hasAuthority('sys_token_del')")
	public R removeById(@PathVariable String token) {
		return feignTokenService.removeTokenById(token, SecurityConstants.FROM_IN);
	}
}
