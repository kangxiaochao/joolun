package com.joolun.cloud.upms.admin.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.upms.common.entity.SysTenant;


/**
 * <p>
 * 租户管理 服务类
 * </p>
 *
 * @author
 */
public interface SysTenantService extends IService<SysTenant> {

	IPage<SysTenant> page1(IPage<SysTenant> page, Wrapper<SysTenant> queryWrapper);
}
