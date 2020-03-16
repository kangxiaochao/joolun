package com.joolun.cloud.upms.admin.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.upms.admin.service.SysTenantService;
import com.joolun.cloud.upms.common.entity.SysTenant;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * <p>
 * 租户管理
 * </p>
 *
 * @author JL
 * @since 2018-01-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "tenant", tags = "租户管理模块")
public class SysTenantController {
	private final SysTenantService sysTenantService;

	/**
	 * 分页查询
	 *
	 * @param page    参数集
	 * @param sysTenant 查询参数列表
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_tenant_index')")
	public R getUserPage(Page page, SysTenant sysTenant) {
		return R.ok(sysTenantService.page1(page, Wrappers.query(sysTenant).lambda().
				eq(SysTenant::getParentId,CommonConstants.PARENT_ID)));
	}


	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return Systenant
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_tenant_get')")
	public R getById(@PathVariable String id) {
		TenantContextHolder.setTenantId(id);
		return R.ok(sysTenantService.getById(id));
	}

	/**
	 * 添加
	 *
	 * @param sysTenant 实体
	 * @return ok/false
	 */
	@SysLog("添加租户")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_tenant_add')")
	public R save(@Valid @RequestBody SysTenant sysTenant) {
		String id = IdUtil.simpleUUID();
		TenantContextHolder.setTenantId(id);
		sysTenant.setId(id);
		sysTenant.setParentId(CommonConstants.PARENT_ID);
		return R.ok(sysTenantService.save(sysTenant));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@SysLog("删除租户")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_tenant_del')")
	public R removeById(@PathVariable String id) {
		TenantContextHolder.setTenantId(id);
		return R.ok(sysTenantService.removeById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysTenant 实体
	 * @return ok/false
	 */
	@SysLog("编辑租户")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_tenant_edit')")
	public R update(@Valid @RequestBody SysTenant sysTenant) {
		TenantContextHolder.setTenantId(sysTenant.getId());
		sysTenant.setParentId(CommonConstants.PARENT_ID);
		return R.ok(sysTenantService.updateById(sysTenant));
	}
}
