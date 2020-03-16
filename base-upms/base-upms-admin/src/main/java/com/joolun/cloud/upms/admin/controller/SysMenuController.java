package com.joolun.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.upms.admin.service.SysMenuService;
import com.joolun.cloud.upms.admin.service.SysRoleService;
import com.joolun.cloud.upms.admin.service.SysTenantService;
import com.joolun.cloud.upms.common.dto.MenuTree;
import com.joolun.cloud.upms.common.entity.SysMenu;
import com.joolun.cloud.upms.common.entity.SysRole;
import com.joolun.cloud.upms.common.vo.MenuVO;
import com.joolun.cloud.upms.common.util.TreeUtil;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JL
 * @date 2019/07/11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "menu", tags = "菜单管理模块")
public class SysMenuController {
	private final SysMenuService sysMenuService;
	private final SysRoleService sysRoleService;
	private final SysTenantService sysTenantService;

	/**
	 * 返回当前用户的树形菜单集合
	 *
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	public R getUserMenu() {
		// 获取符合条件的菜单
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
				.forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
				.filter(menuVo -> CommonConstants.MENU.equals(menuVo.getType()))
				.map(MenuTree::new)
				.sorted(Comparator.comparingInt(MenuTree::getSort))
				.collect(Collectors.toList());
		return R.ok(TreeUtil.build(menuTreeList, CommonConstants.MENU_TREE_ROOT_ID));
	}

	/**
	 * 返回树形菜单集合（当前用户拥有的）
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	@PreAuthorize("@ato.hasAuthority('sys_menu_index')")
	public R getTree() {
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
				.forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
				.map(MenuTree::new)
				.collect(Collectors.toList());
		return R.ok(TreeUtil.build(menuTreeList, CommonConstants.MENU_TREE_ROOT_ID));
	}

	/**
	 * 返回角色的菜单集合
	 *
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	public R getRoleTree(@PathVariable String roleId) {
		return R.ok(sysMenuService.findMenuByRoleId(roleId)
				.stream()
				.map(MenuVO::getId)
				.collect(Collectors.toList()));
	}

	/**
	 * 通过ID查询菜单的详细信息
	 *
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable String id) {
		return R.ok(sysMenuService.getById(id));
	}

	/**
	 * 新增菜单
	 *
	 * @param sysMenu 菜单信息
	 * @return ok/false
	 */
	@SysLog("新增菜单")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_menu_add')")
	public R save(@Valid @RequestBody SysMenu sysMenu) {
		sysMenuService.saveMenu(sysMenu);
		return R.ok();
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 * @return ok/false
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_menu_del')")
	public R removeById(@PathVariable String id) {
		return sysMenuService.removeMenuById(id);
	}

	/**
	 * 更新菜单
	 *
	 * @param sysMenu
	 * @return
	 */
	@SysLog("更新菜单")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_menu_edit')")
	public R update(@Valid @RequestBody SysMenu sysMenu) {
		return R.ok(sysMenuService.updateMenuById(sysMenu));
	}

	/**
	 * 返回租户管理员角色的菜单集合
	 *
	 * @param tenantId 租户ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/tenant/{tenantId}")
	@PreAuthorize("@ato.hasAuthority('sys_tenant_edit')")
	public R getRoleTreeTenant(@PathVariable String tenantId) {
		TenantContextHolder.setTenantId(tenantId);
		//找出指定租户的管理员角色
		SysRole sysRole = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode,CommonConstants.ROLE_CODE_ADMIN));
		List listMenuVO = sysMenuService.findMenuByRoleId(sysRole.getId())
				.stream()
				.map(MenuVO::getId)
				.collect(Collectors.toList());
		Map<String,Object> map = new HashMap<>();
		map.put("sysRole",sysRole);
		map.put("listMenuVO",listMenuVO);
		//菜单集合
		return R.ok(map);
	}
}
