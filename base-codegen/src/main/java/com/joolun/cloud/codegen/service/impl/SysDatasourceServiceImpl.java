package com.joolun.cloud.codegen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.codegen.entity.SysDatasource;
import com.joolun.cloud.common.datasource.config.DynamicDataSourceConfig;
import com.joolun.cloud.codegen.mapper.SysDatasourceMapper;
import com.joolun.cloud.codegen.service.SysDatasourceService;
import lombok.AllArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

/**
 * 数据源表
 */
@Service
@AllArgsConstructor
public class SysDatasourceServiceImpl extends ServiceImpl<SysDatasourceMapper, SysDatasource> implements SysDatasourceService {
	private final DynamicDataSourceConfig dynamicDataSourceConfig;
	private final StringEncryptor stringEncryptor;

	/**
	 * 保存数据源并且加密
	 *
	 * @param sysDatasource
	 * @return
	 */
	@Override
	public Boolean saveDsByEnc(SysDatasource sysDatasource) {
		sysDatasource.setPassword(stringEncryptor.encrypt(sysDatasource.getPassword()));
		this.baseMapper.insert(sysDatasource);
		return dynamicDataSourceConfig.reload();
	}

	/**
	 * 更新数据源
	 *
	 * @param sysDatasource
	 * @return
	 */
	@Override
	public Boolean updateDsByEnc(SysDatasource sysDatasource) {
		if (StrUtil.isNotBlank(sysDatasource.getPassword())) {
			sysDatasource.setPassword(stringEncryptor.encrypt(sysDatasource.getPassword()));
		}
		this.baseMapper.updateById(sysDatasource);
		return dynamicDataSourceConfig.reload();
	}
}
