package com.joolun.cloud.upms.admin.handler;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.upms.admin.service.SysUserService;
import com.joolun.cloud.upms.common.dto.UserInfo;
import com.joolun.cloud.upms.common.entity.SysThirdParty;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.upms.admin.mapper.SysThirdPartyMapper;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author
 * <p>
 * 码云登录
 */
@Slf4j
@Component("GITEE")
@AllArgsConstructor
public class GiteeLoginHandler extends AbstractLoginHandler {
	private final SysThirdPartyMapper sysThirdPartyMapper;
	private final SysUserService sysUserService;


	/**
	 * 码云登录传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 *
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysThirdParty condition = new SysThirdParty();
		condition.setType(LoginTypeEnum.GITEE.getType());
		SysThirdParty sysThirdParty = sysThirdPartyMapper.selectOne(Wrappers.lambdaQuery(condition));

		String url = String.format(SecurityConstants.GITEE_AUTHORIZATION_CODE_URL, code
				, sysThirdParty.getAppId(), URLUtil.encode(sysThirdParty.getRedirectUrl()), sysThirdParty.getAppSecret());
		String result = HttpUtil.post(url, new HashMap<>(0));
		log.debug("码云响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");
		String userUrl = String.format(SecurityConstants.GITEE_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(userUrl);
		log.debug("码云获取个人信息返回报文{}", resp);

		JSONObject userInfo = JSONUtil.parseObj(resp);
		//码云唯一标识
		String login = userInfo.getStr("login");
		return login;
	}

	/**
	 * identify 获取用户信息
	 *
	 * @param identify identify
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {


		SysUser user = sysUserService
				.getOne(Wrappers.<SysUser>query()
						.lambda().eq(SysUser::getGiteeLogin, identify));

		if (user == null) {
			log.info("码云未绑定:{}", identify);
			return null;
		}
		return sysUserService.findUserInfo(user);
	}
}
