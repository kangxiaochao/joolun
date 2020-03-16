package com.joolun.cloud.lianghao.api.ma;
import	java.util.HashMap;

import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.lianghao.entity.LhOperator;
import com.joolun.cloud.lianghao.service.LhOperatorService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/lhOperator")
@Api(value = "lhOperator", tags = "码号运营商接口")
public class LhOperatorApi {

	private final LhOperatorService lhOperatorService;

	/**
	 * 获取所有的运营商
	 * @return R
	 * */
	//@SysLog("获取所有的运营商")
	@GetMapping("/all")
	public R all(){
		return R.ok(lhOperatorService.list());
	}

	/**
	 * 返回所有运营商名称为Key,运营商id为value的map集合
	 */
	@GetMapping("/operatorMap")
	public Map<String,String> operatorMap(){
		Map<String,String> map = new HashMap<> ();
		List<LhOperator> list = lhOperatorService.list();
		for (int i = 0 ; i < list.size(); i++){
			map.put(list.get(i).getOperatorName(),list.get(i).getId());
		}
		return map;
	}
}
