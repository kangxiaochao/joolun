package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.handler.LoginHandler;
import com.joolun.cloud.upms.admin.mapper.SysThirdPartyMapper;
import com.joolun.cloud.upms.admin.mapper.SysUserMapper;
import com.joolun.cloud.upms.admin.service.SysThirdPartyService;
import com.joolun.cloud.upms.common.dto.UserInfo;
import com.joolun.cloud.upms.common.entity.SysThirdParty;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.constant.enums.LoginTypeEnum;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import java.util.Map;

/**
 * @author
 */
@Slf4j
@AllArgsConstructor
@Service("sysThirdPartyService")
public class SysThirdPartyServiceImpl extends ServiceImpl<SysThirdPartyMapper, SysThirdParty> implements SysThirdPartyService {
	private final Map<String, LoginHandler> loginHandlerMap;
	private final CacheManager cacheManager;
	private final SysUserMapper sysUserMapper;

	/**
	 * 绑定社交账号
	 *
	 * @param type type
	 * @param code code
	 * @return
	 */
	@Override
	public Boolean bindSysThirdParty(String type, String code) {
		String identify = loginHandlerMap.get(type).identify(code);
		SysUser sysUser = sysUserMapper.selectById(SecurityUtils.getUser().getId());
		if (LoginTypeEnum.GITEE.getType().equals(type)) {
			sysUser.setGiteeLogin(identify);
		} else if (LoginTypeEnum.OSC.getType().equals(type)) {
			sysUser.setOscId(identify);
		} else if (LoginTypeEnum.WECHAT.getType().equals(type)) {
			sysUser.setWxOpenid(identify);
		} else if (LoginTypeEnum.QQ.getType().equals(type)) {
			sysUser.setQqOpenid(identify);
		}
		sysUserMapper.updateById(sysUser);
		//更新緩存
		cacheManager.getCache(CacheConstants.USER_CACHE).evict(sysUser.getUsername());
		return Boolean.TRUE;
	}

	/**
	 * 根据入参查询用户信息
	 *
	 * @param inStr TYPE@code
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(String inStr) {
		String[] inStrs = inStr.split(StringPool.AT);
		String type = inStrs[0];
		String loginStr = inStrs[1];
		return loginHandlerMap.get(type).handle(loginStr);
	}
}
