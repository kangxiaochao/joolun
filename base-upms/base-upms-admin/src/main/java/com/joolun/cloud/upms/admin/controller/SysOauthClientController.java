package com.joolun.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.upms.admin.service.SysOauthClientService;
import com.joolun.cloud.upms.common.entity.SysOauthClient;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 终端控制器
 *
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "client", tags = "客户端管理模块")
public class SysOauthClientController {
	private final SysOauthClientService sysOauthClientService;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysOauthClient
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_client_get')")
	public R getById(@PathVariable String id) {
		return R.ok(sysOauthClientService.getById(id));
	}


	/**
	 * 简单分页查询
	 *
	 * @param page                  分页对象
	 * @param sysOauthClient 系统终端
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_client_index')")
	public R getOauthClientDetailsPage(Page page, SysOauthClient sysOauthClient) {
		return R.ok(sysOauthClientService.page(page, Wrappers.query(sysOauthClient)));
	}

	/**
	 * 添加
	 *
	 * @param sysOauthClient 实体
	 * @return ok/false
	 */
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClient sysOauthClient) {
		return R.ok(sysOauthClientService.save(sysOauthClient));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@SysLog("删除终端")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_client_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(sysOauthClientService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysOauthClient 实体
	 * @return ok/false
	 */
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClient sysOauthClient) {
		return R.ok(sysOauthClientService.updateClientDetailsById(sysOauthClient));
	}
}
