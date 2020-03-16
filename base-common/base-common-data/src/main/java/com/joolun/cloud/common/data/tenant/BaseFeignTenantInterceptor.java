package com.joolun.cloud.common.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.joolun.cloud.common.core.constant.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 */
@Slf4j
public class BaseFeignTenantInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (StrUtil.isBlank(TenantContextHolder.getTenantId())) {
			return;
		}
		requestTemplate.header(CommonConstants.TENANT_ID, TenantContextHolder.getTenantId());
	}
}
