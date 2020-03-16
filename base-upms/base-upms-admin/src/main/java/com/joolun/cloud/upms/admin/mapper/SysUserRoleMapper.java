package com.joolun.cloud.upms.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.cloud.upms.common.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 */
	Boolean deleteByUserId(@Param("userId") String userId);
}
