package com.joolun.cloud.common.security.util;


import cn.hutool.core.util.StrUtil;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.security.entity.BaseUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author
 */
@UtilityClass
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return BaseUser
	 * <p>
	 * 获取当前用户的全部信息 EnableBaseResourceServer true
	 * 获取当前用户的用户名 EnableBaseResourceServer false
	 */
	public BaseUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof BaseUser) {
			return (BaseUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public BaseUser getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 *
	 * @return 角色集合
	 */
	public List<String> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roleIds = new ArrayList<>();
		authorities.stream()
				.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
				.forEach(granted -> {
					String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
					roleIds.add(id);
				});
		return roleIds;
	}

}
