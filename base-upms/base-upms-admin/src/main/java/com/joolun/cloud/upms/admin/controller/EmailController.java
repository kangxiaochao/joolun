package com.joolun.cloud.upms.admin.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.upms.admin.mapper.SysUserMapper;
import com.joolun.cloud.upms.admin.service.SysUserService;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.upms.admin.service.EmailService;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.security.annotation.Inside;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author JL
 * @date 2019/07/14
 * 邮箱管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/email")
@Api(value = "email", tags = "邮箱管理")
public class EmailController {
	private final EmailService emailService;
	private final SysUserMapper sysUserMapper;
	private final RedisTemplate redisTemplate;

	/**
	 * 发送邮箱验证码
	 * @param email
	 * @return
	 */
	@Inside(value = false)
	@GetMapping("/{email}")
	public R sendEmailCode(@PathVariable String email,@RequestParam("type") String type) {
		String title = "";
		String content = "";
		switch (type) {
			case CommonConstants.EMAIL_SEND_TYPE_REGISTER ://注册
				SysUser sysUser = new SysUser();
				sysUser.setEmail(email);
				sysUser = sysUserMapper.getByNoTenant(sysUser);
				if(sysUser != null){
					return R.failed("该邮箱已被注册");
				}
				String key = CacheConstants.VER_CODE_REGISTER + CommonConstants.EMAIL + ":" + email;
				String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));//生成验证码
				redisTemplate.opsForValue().set(key, code, 2, TimeUnit.HOURS);//验证码有效期2小时
				title = "JooLun快速开发平台用户注册";
				content = "你正在注册JooLun快速开发平台用户，邮箱验证码："+code+"；验证码有效期为2小时，请尽快完成注册";
		}
		return emailService.sendEmail(email,title,content);
	}
}
