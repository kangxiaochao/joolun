package com.joolun.cloud.common.datasource.support;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 根据当前线程来选择具体的数据源
 */
@UtilityClass
public class DynamicDataSourceContextHolder {
	private final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 提供给AOP去设置当前的线程的数据源的信息
	 *
	 * @param dataSourceType
	 */
	public void setDataSourceType(String dataSourceType) {
		CONTEXT_HOLDER.set(dataSourceType);
	}

	/**
	 * 提供给AbstractRoutingDataSource的实现类，通过key选择数据源
	 *
	 * @return
	 */
	public String getDataSourceType() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 使用默认的数据源
	 *
	 */
	public void clearDataSourceType() {
		CONTEXT_HOLDER.remove();
	}
}