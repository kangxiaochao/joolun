package com.joolun.cloud.upms.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.upms.admin.mapper.SysUserMapper;
import com.joolun.cloud.upms.admin.service.MobileService;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * <p>
 * 手机登录相关业务实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class MobileServiceImpl implements MobileService {
	private final RedisTemplate redisTemplate;
	private final SysUserMapper userMapper;


	/**
	 * 发送手机验证码
	 * TODO: 调用短信网关发送验证码,测试返回前端
	 *
	 * @param mobile mobile
	 * @return code
	 */
	@Override
	public R<Boolean> sendSmsCode(String mobile) {
		List<SysUser> userList = userMapper.selectList(Wrappers
			.<SysUser>query().lambda()
			.eq(SysUser::getPhone, mobile));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}", mobile);
			return R.failed(Boolean.FALSE, "手机号未注册");
		}
		String key = CacheConstants.VER_CODE_DEFAULT + CommonConstants.SMS + ":" + mobile;
		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
			return R.failed(Boolean.FALSE, "验证码发送过频繁");
		}

		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
		log.debug("手机号生成验证码成功:{},{}", mobile, code);
		redisTemplate.opsForValue().set(key, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
		return R.ok(Boolean.TRUE, code);
	}
}
