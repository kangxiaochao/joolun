package com.joolun.cloud.lianghao.api.ma;

import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.lianghao.entity.LhProcess;
import com.joolun.cloud.lianghao.service.LhProcessService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/lhProcess")
@Api(value = "lhProcess", tags = "号码开卡方式接口")
public class LhProcessApi {

	private final LhProcessService lhProcessService;

	/**
	 * 根据开卡方式ID获取开卡方式详情
	 * param id 开卡方式ID
	 * */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id){
		return R.ok(lhProcessService.getById(id));
	}

	/**
	 * 返回套餐名称为Key,套餐Id为value的Map集合
	 */
	@GetMapping("/processMap")
	public Map<String,String> processMap(){
		Map<String,String> map = new HashMap<>();
		List<LhProcess> processList = lhProcessService.list();
		for (int i = 0;i<processList.size(); i++){
			map.put(processList.get(i).getName(),processList.get(i).getId());
		}
		return map;
	}

}
