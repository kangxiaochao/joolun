package com.joolun.cloud.mall.common.feign;

import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.constant.ServiceNameConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.common.dto.WxOpenDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author
 */
@FeignClient(contextId = "feignWxAppService", value = ServiceNameConstants.WX_ADMIN_SERVICE)
public interface FeignWxAppService {

	/**
	 * 获取微信应用
	 * @param id
	 * @return
	 */
	@GetMapping("/wxapp/inside/{id}")
	R getById(@PathVariable("id") String id, @RequestHeader(SecurityConstants.FROM) String from);

}
