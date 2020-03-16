package com.joolun.cloud.lianghao.api.ma;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.lianghao.entity.LhPackage;
import com.joolun.cloud.lianghao.service.LhPackageService;
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
@RequestMapping("/api/ma/lhPackage")
@Api(value = "lhpackage", tags = "码号套餐接口")
public class LhPackageApi {

	private final LhPackageService lhPackageService;

	/**
	 * 根据id获取套餐详细信息
	 * param ：id
	 * */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id){
		return R.ok(lhPackageService.getById(id));
	}

	/**
	 * 获取所有的套餐信息
	 * */
	@GetMapping("/all" )
	public R all(){
		return R.ok(lhPackageService.list());
	}

	/**
	 * 返回套餐名称为Key,套餐Id为value的Map集合
	 */
	@GetMapping("/packageMap")
	public Map<String,String> packageMap(){
		Map<String,String> map = new HashMap<>();
		List<LhPackage> packages = lhPackageService.list();
		for (int i = 0;i<packages.size(); i++){
			map.put(packages.get(i).getPackageName(),packages.get(i).getId());
		}
		return map;
	}
}
