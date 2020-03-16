package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.mapper.SysUserRoleMapper;
import com.joolun.cloud.upms.admin.service.SysUserRoleService;
import com.joolun.cloud.upms.common.entity.SysUserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 */
	@Override
	public Boolean deleteByUserId(String userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
