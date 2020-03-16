package com.joolun.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.dto.OrganTree;
import com.joolun.cloud.upms.common.entity.SysOrgan;

import java.util.List;

/**
 * <p>
 * 机构管理 服务类
 * </p>
 *
 * @author
 */
public interface SysOrganService extends IService<SysOrgan> {

	/**
	 * 查询机构树菜单
	 *
	 * @return 树
	 */
	List<OrganTree> selectTree();

	/**
	 * 添加信息机构
	 *
	 * @param sysOrgan
	 * @return
	 */
	Boolean saveOrgan(SysOrgan sysOrgan);

	/**
	 * 删除机构
	 *
	 * @param id 机构 ID
	 * @return 成功、失败
	 */
	Boolean removeOrganById(String id);

	/**
	 * 更新机构
	 *
	 * @param sysOrgan 机构信息
	 * @return 成功、失败
	 */
	Boolean updateOrganById(SysOrgan sysOrgan);

}
