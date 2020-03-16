package com.joolun.cloud.weixin.common.feign;

import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.constant.ServiceNameConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.weixin.common.dto.MallUserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author
 */
@FeignClient(contextId = "feignMallUserInfoService", value = ServiceNameConstants.MALL_ADMIN_SERVICE)
public interface FeignMallUserInfoService {

	/**
	 * 新增商城用户
	 * @param mallUserInfoDTO
	 * @param from
	 * @return
	 */
	@PostMapping("/userinfo/inside")
	R saveInside(@RequestBody MallUserInfoDTO mallUserInfoDTO, @RequestHeader(SecurityConstants.FROM) String from);
}
