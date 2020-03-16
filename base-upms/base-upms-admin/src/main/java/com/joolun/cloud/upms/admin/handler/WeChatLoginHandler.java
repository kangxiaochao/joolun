package com.joolun.cloud.upms.admin.handler;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.upms.admin.mapper.SysThirdPartyMapper;
import com.joolun.cloud.upms.admin.service.SysUserService;
import com.joolun.cloud.upms.common.dto.UserInfo;
import com.joolun.cloud.upms.common.entity.SysThirdParty;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author JL
 * @date
 */
@Slf4j
@Component("WX")
@AllArgsConstructor
public class WeChatLoginHandler extends AbstractLoginHandler {
	private final SysUserService sysUserService;
	private final SysThirdPartyMapper sysThirdPartyMapper;

	/**
	 * 微信登录传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 *
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysThirdParty condition = new SysThirdParty();
		condition.setType(LoginTypeEnum.WECHAT.getType());
		SysThirdParty sysThirdParty = sysThirdPartyMapper.selectOne(Wrappers.lambdaQuery(condition));

		String url = String.format(SecurityConstants.WX_AUTHORIZATION_CODE_URL
			, sysThirdParty.getAppId(), sysThirdParty.getAppSecret(), code);
		String result = HttpUtil.get(url);
		log.debug("微信响应报文:{}", result);

		Object obj = JSONUtil.parseObj(result).get("openid");
		return obj.toString();
	}

	/**
	 * openId 获取用户信息
	 *
	 * @param openId
	 * @return
	 */
	@Override
	public UserInfo info(String openId) {
		SysUser user = sysUserService
			.getOne(Wrappers.<SysUser>query()
				.lambda().eq(SysUser::getWxOpenid, openId));

		if (user == null) {
			log.info("微信未绑定:{}", openId);
			return null;
		}
		return sysUserService.findUserInfo(user);
	}
}
