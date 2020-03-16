package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.service.SysOauthClientService;
import com.joolun.cloud.upms.common.entity.SysOauthClient;
import com.joolun.cloud.upms.admin.mapper.SysOauthClientMapper;
import com.joolun.cloud.common.core.constant.CacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements SysOauthClientService {

	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_CACHE, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 根据客户端信息
	 *
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_CACHE, key = "#clientDetails.id")
	public Boolean updateClientDetailsById(SysOauthClient clientDetails) {
		return this.updateById(clientDetails);
	}
}
