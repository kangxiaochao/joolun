package com.joolun.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.entity.SysUserRole;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author
 */
public interface SysUserRoleService extends IService<SysUserRole> {

	/**
	 * 根据用户Id删除该用户的角色关系
	 */
	Boolean deleteByUserId(String userId);
}
