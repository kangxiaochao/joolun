package com.joolun.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.cloud.upms.common.entity.SysMenu;
import com.joolun.cloud.upms.common.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	List<MenuVO> listMenusByRoleId(String roleId);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> listPermissionsByRoleIds(String roleIds);
}
