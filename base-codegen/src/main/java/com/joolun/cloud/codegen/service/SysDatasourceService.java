package com.joolun.cloud.codegen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.codegen.entity.SysDatasource;

/**
 * 数据源表
 *
 * @author
 */
public interface SysDatasourceService extends IService<SysDatasource> {
	/**
	 * 保存数据源并且加密
	 *
	 * @param sysDatasource
	 * @return
	 */
	Boolean saveDsByEnc(SysDatasource sysDatasource);

	/**
	 * 更新数据源
	 *
	 * @param sysDatasource
	 * @return
	 */
	Boolean updateDsByEnc(SysDatasource sysDatasource);
}
