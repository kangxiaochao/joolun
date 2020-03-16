package com.joolun.cloud.upms.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.service.SysMenuService;
import com.joolun.cloud.upms.common.entity.SysMenu;
import com.joolun.cloud.upms.common.entity.SysRoleMenu;
import com.joolun.cloud.upms.common.vo.MenuVO;
import com.joolun.cloud.upms.admin.mapper.SysMenuMapper;
import com.joolun.cloud.upms.admin.mapper.SysRoleMenuMapper;
import com.joolun.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.joolun.cloud.common.core.constant.CacheConstants;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	private final SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Cacheable(value = CacheConstants.MENU_CACHE, key = "#roleId  + '_menu'", unless = "#result == null")
	public List<MenuVO> findMenuByRoleId(String roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_CACHE, allEntries = true)
	public R removeMenuById(String id) {
		// 查询父节点为当前节点的节点
		List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query()
				.lambda().eq(SysMenu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return R.failed("菜单含有下级不能删除");
		}

		sysRoleMenuMapper
				.delete(Wrappers.<SysRoleMenu>query()
						.lambda().eq(SysRoleMenu::getMenuId, id));

		//删除当前菜单及其子菜单
		return R.ok(this.removeById(id));
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_CACHE, allEntries = true)
	public Boolean updateMenuById(SysMenu sysMenu) {
		return this.updateById(sysMenu);
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public void saveMenu(SysMenu entity) {
		String roleId = entity.getRoleId();
		super.save(entity);
		if(StrUtil.isNotBlank(roleId)){
			SysRoleMenu sysRoleMenu = new SysRoleMenu();
			sysRoleMenu.setRoleId(roleId);
			sysRoleMenu.setMenuId(entity.getId());
			sysRoleMenuMapper.insert(sysRoleMenu);
		}
	}
}
