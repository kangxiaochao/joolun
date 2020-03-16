package com.joolun.cloud.auth.handler;

import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.joolun.cloud.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author
 */
@Slf4j
@Component
public class BaseAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		TenantContextHolder.setTenantId(SecurityUtils.getUser(authentication).getTenantId());
		log.info("用户：{} 登录成功", authentication.getPrincipal());
	}
}
