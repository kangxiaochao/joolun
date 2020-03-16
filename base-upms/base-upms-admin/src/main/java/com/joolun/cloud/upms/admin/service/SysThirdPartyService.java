package com.joolun.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.dto.UserInfo;
import com.joolun.cloud.upms.common.entity.SysThirdParty;

/**
 * 系统社交登录账号表
 *
 * @author
 */
public interface SysThirdPartyService extends IService<SysThirdParty> {

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	Boolean bindSysThirdParty(String state, String code);

	/**
	 * 根据入参查询用户信息
	 *
	 * @param inStr
	 * @return
	 */
	UserInfo getUserInfo(String inStr);
}

