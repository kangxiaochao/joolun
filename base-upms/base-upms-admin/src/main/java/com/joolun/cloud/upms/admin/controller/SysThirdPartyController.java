package com.joolun.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.upms.admin.service.SysThirdPartyService;
import com.joolun.cloud.upms.common.entity.SysThirdParty;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.common.security.annotation.Inside;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 系统社交登录账号表
 *
 * @author
 */
@RestController
@RequestMapping("/thirdparty")
@AllArgsConstructor
@Api(value = "thirdparty", tags = "三方账号管理模块")
public class SysThirdPartyController {
	private final SysThirdPartyService sysThirdPartyService;


	/**
	 * 社交登录账户简单分页查询
	 *
	 * @param page             分页对象
	 * @param sysThirdParty 社交登录
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_third_party_index')")
	public R getSysThirdPartyPage(Page page, SysThirdParty sysThirdParty) {
		return R.ok(sysThirdPartyService.page(page, Wrappers.query(sysThirdParty)));
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_third_party_get')")
	public R getById(@PathVariable("id") String id) {
		return R.ok(sysThirdPartyService.getById(id));
	}

	/**
	 * 保存
	 *
	 * @param sysThirdParty
	 * @return R
	 */
	@SysLog("保存三方信息")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_third_party_add')")
	public R save(@Valid @RequestBody SysThirdParty sysThirdParty) {
		return R.ok(sysThirdPartyService.save(sysThirdParty));
	}

	/**
	 * 修改
	 *
	 * @param sysThirdParty
	 * @return R
	 */
	@SysLog("修改三方信息")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_third_party_edit')")
	public R updateById(@Valid @RequestBody SysThirdParty sysThirdParty) {
		sysThirdPartyService.updateById(sysThirdParty);
		return R.ok(Boolean.TRUE);
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("删除三方信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_third_party_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(sysThirdPartyService.removeById(id));
	}

	/**
	 * 通过社交账号、手机号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@Inside
	@GetMapping("/info/{inStr}")
	public R getUserInfo(@PathVariable String inStr) {
		return R.ok(sysThirdPartyService.getUserInfo(inStr));
	}

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	@PostMapping("/bind")
	public R bindSysThirdParty(String state, String code) {
		return R.ok(sysThirdPartyService.bindSysThirdParty(state, code));
	}
}
