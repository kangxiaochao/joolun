package com.joolun.cloud.common.security.component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author
 * 接口权限判断
 */
@Slf4j
@Component("ato")
public class AuthorityService {
	/**
	 * 判断接口是否有ato:xxx权限
	 *
	 * @param authority 权限
	 * @return {boolean}
	 */
	public boolean hasAuthority(String authority) {
		if (StrUtil.isBlank(authority)) {
			return false;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.filter(StringUtils::hasText)
			.anyMatch(x -> PatternMatchUtils.simpleMatch(authority, x));
	}
}
