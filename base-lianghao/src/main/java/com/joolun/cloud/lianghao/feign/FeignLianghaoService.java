package com.joolun.cloud.lianghao.feign;

import com.joolun.cloud.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author
 */
@FeignClient(contextId = "feignLianghaoService", value = ServiceNameConstants.LIANG_HAO_SERVICE)
public interface FeignLianghaoService {

	@GetMapping("/api/ma/lhOperator/operatorMap")
	Map<String,String> operatorMap();

	@GetMapping("/api/ma/lhPackage/packageMap")
	Map<String,String> packageMap();

	@GetMapping("/api/ma/lhProcess/processMap")
	Map<String,String> processMap();
}
