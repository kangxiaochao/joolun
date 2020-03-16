package com.joolun.cloud.codegen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.codegen.entity.GenConfig;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface SysGeneratorService {
	/**
	 * 生成代码
	 *
	 * @param tableNames 表名称
	 * @return
	 */
	byte[] generatorCode(GenConfig tableNames);

	/**
	 * 分页查询表
	 *
	 * @param tableName 表名
	 * @param id        数据源ID
	 * @return
	 */
	IPage<List<Map<String, Object>>> getPage(Page page, String tableName, String id);
}
