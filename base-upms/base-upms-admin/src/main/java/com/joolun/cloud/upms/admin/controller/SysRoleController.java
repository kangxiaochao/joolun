package com.joolun.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.upms.admin.service.SysRoleMenuService;
import com.joolun.cloud.upms.admin.service.SysRoleService;
import com.joolun.cloud.upms.common.entity.SysRole;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.upms.common.entity.SysRoleMenu;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "role", tags = "角色管理模块")
public class SysRoleController {
	private final SysRoleService sysRoleService;
	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 通过ID查询角色信息
	 *
	 * @param id ID
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_role_get')")
	public R getById(@PathVariable String id) {
		return R.ok(sysRoleService.getById(id));
	}

	/**
	 * 添加角色
	 *
	 * @param sysRole 角色信息
	 * @return ok、false
	 */
	@SysLog("添加角色")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_role_add')")
	public R save(@Valid @RequestBody SysRole sysRole) {
		return R.ok(sysRoleService.save(sysRole));
	}

	/**
	 * 修改角色
	 *
	 * @param sysRole 角色信息
	 * @return ok/false
	 */
	@SysLog("修改角色")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_role_edit')")
	public R update(@Valid @RequestBody SysRole sysRole) {
		if(!this.judeAdmin(sysRole.getId())){
			return R.failed("管理员角色不能操作");
		}
		return R.ok(sysRoleService.updateById(sysRole));
	}

	/**
	 * 删除角色
	 *
	 * @param id
	 * @return
	 */
	@SysLog("删除角色")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_role_del')")
	public R removeById(@PathVariable String id) {
		if(!this.judeAdmin(id)){
			return R.failed("管理员角色不能操作");
		}
		return R.ok(sysRoleService.removeRoleById(id));
	}

	/**
	 * 获取角色列表
	 *
	 * @return 角色列表
	 */
	@GetMapping("/list")
	@PreAuthorize("@ato.hasAuthority('sys_role_index')")
	public R listRoles() {
		return R.ok(sysRoleService.list(Wrappers.emptyWrapper()));
	}

	/**
	 * 分页查询角色信息
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	public R getRolePage(Page page) {
		return R.ok(sysRoleService.page(page, Wrappers.emptyWrapper()));
	}

	/**
	 * 更新角色菜单
	 *
	 * @param sysRoleMenu roleId  角色ID
	 * @param sysRoleMenu menuId 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return ok、false
	 */
	@SysLog("更新角色菜单")
	@PutMapping("/menu")
	@PreAuthorize("@ato.hasAuthority('sys_role_perm')")
	public R saveRoleMenus(@RequestBody SysRoleMenu sysRoleMenu) {
		String roleId = sysRoleMenu.getRoleId();
		String menuIds = sysRoleMenu.getMenuId();
		if(StrUtil.isBlank(roleId) || StrUtil.isBlank(menuIds)){
			return R.ok();
		}
		if(!this.judeAdmin(roleId)){
			return R.failed("管理员角色不能操作");
		}
		SysRole sysRole = sysRoleService.getById(roleId);
		return R.ok(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
	}

	/**
	 * 校验是否是管理员角色
	 * @param roleId
	 * @return
	 */
	boolean judeAdmin(String roleId){
		SysRole sysRole = sysRoleService.getById(roleId);
		if(CommonConstants.ROLE_CODE_ADMIN.equals(sysRole.getRoleCode())){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 更新租户管理员角色菜单
	 *
	 * @param sysRoleMenu roleId  角色ID
	 * @param sysRoleMenu menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return ok、false
	 */
	@SysLog("更新租户管理员角色菜单")
	@PutMapping("/menu/tenant")
	@PreAuthorize("@ato.hasAuthority('sys_tenant_edit')")
	public R saveRoleMenusTenant(@RequestBody SysRoleMenu sysRoleMenu) {
		String tenantId = sysRoleMenu.getTenantId();
		String roleId = sysRoleMenu.getRoleId();
		String menuIds = sysRoleMenu.getMenuId();
		if(StrUtil.isBlank(tenantId) || StrUtil.isBlank(menuIds) || StrUtil.isBlank(menuIds)){
			return R.ok();
		}
		TenantContextHolder.setTenantId(tenantId);
		SysRole sysRole = sysRoleService.getById(roleId);
		return R.ok(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
	}
}
