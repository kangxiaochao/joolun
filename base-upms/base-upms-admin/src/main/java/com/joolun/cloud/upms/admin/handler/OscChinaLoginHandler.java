package com.joolun.cloud.upms.admin.handler;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * <p>
 * 开源中国登录
 */
@Slf4j
@Component("OSC")
@AllArgsConstructor
public class OscChinaLoginHandler extends AbstractLoginHandler {
	private final SysThirdPartyMapper sysThirdPartyMapper;
	private final SysUserService sysUserService;


	/**
	 * 开源中国传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 *
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysThirdParty condition = new SysThirdParty();
		condition.setType(LoginTypeEnum.OSC.getType());
		SysThirdParty sysThirdParty = sysThirdPartyMapper.selectOne(Wrappers.lambdaQuery(condition));

		Map<String, Object> params = new HashMap<>(8);

		params.put("client_id", sysThirdParty.getAppId());
		params.put("client_secret", sysThirdParty.getAppSecret());
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", sysThirdParty.getRedirectUrl());
		params.put("code", code);
		params.put("dataType", "json");

		String result = HttpUtil.post(SecurityConstants.OSC_AUTHORIZATION_CODE_URL, params);
		log.debug("开源中国响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");

		String url = String.format(SecurityConstants.OSC_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(url);
		log.debug("开源中国获取个人信息返回报文{}", resp);

		JSONObject userInfo = JSONUtil.parseObj(resp);
		//开源中国唯一标识
		String id = userInfo.getStr("id");
		return id;
	}

	/**
	 * identify 获取用户信息
	 *
	 * @param identify 开源中国表示
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {


		SysUser user = sysUserService
				.getOne(Wrappers.<SysUser>query()
						.lambda().eq(SysUser::getOscId, identify));

		if (user == null) {
			log.info("开源中国未绑定:{}", identify);
			return null;
		}
		return sysUserService.findUserInfo(user);
	}
}
