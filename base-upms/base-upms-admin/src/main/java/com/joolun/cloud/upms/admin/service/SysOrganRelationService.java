package com.joolun.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.entity.SysOrgan;
import com.joolun.cloud.upms.common.entity.SysOrganRelation;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface SysOrganRelationService extends IService<SysOrganRelation> {

	/**
	 * 新建机构关系
	 *
	 * @param sysOrgan 机构
	 */
	void insertOrganRelation(SysOrgan sysOrgan);

	/**
	 * 通过ID删除机构关系
	 *
	 * @param id
	 */
	void deleteAllOrganRealtion(String id);

	/**
	 * 更新机构关系
	 *
	 * @param relation
	 */
	void updateOrganRealtion(SysOrganRelation relation);
}
