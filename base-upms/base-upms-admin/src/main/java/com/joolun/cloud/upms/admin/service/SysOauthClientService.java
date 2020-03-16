package com.joolun.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.entity.SysOauthClient;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface SysOauthClientService extends IService<SysOauthClient> {
	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 根据客户端信息
	 *
	 * @param sysOauthClient
	 * @return
	 */
	Boolean updateClientDetailsById(SysOauthClient sysOauthClient);
}
