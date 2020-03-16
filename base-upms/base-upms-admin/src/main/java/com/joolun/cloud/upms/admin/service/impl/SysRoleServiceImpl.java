package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.mapper.SysRoleMapper;
import com.joolun.cloud.upms.admin.mapper.SysRoleMenuMapper;
import com.joolun.cloud.upms.admin.service.SysRoleService;
import com.joolun.cloud.upms.common.entity.SysRole;
import com.joolun.cloud.upms.common.entity.SysRoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.joolun.cloud.common.core.constant.CacheConstants;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	private SysRoleMenuMapper sysRoleMenuMapper;

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List findRoleIdsByUserId(String userId) {
		return baseMapper.listRoleIdsByUserId(userId);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeRoleById(String id) {
		sysRoleMenuMapper.delete(Wrappers
			.<SysRoleMenu>update().lambda()
			.eq(SysRoleMenu::getRoleId, id));
		return this.removeById(id);
	}
}
