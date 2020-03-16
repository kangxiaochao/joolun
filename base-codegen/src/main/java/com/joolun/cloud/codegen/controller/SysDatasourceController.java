package com.joolun.cloud.codegen.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.codegen.entity.SysDatasource;
import com.joolun.cloud.codegen.service.SysDatasourceService;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 数据源管理
 *
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dsconf")
public class SysDatasourceController {
	private final SysDatasourceService sysDatasourceService;

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param sysDatasource 数据源表
	 * @return
	 */
	@GetMapping("/page")
	public R getSysDatasourceConfPage(Page page, SysDatasource sysDatasource) {
		return R.ok(sysDatasourceService.page(page, Wrappers.query(sysDatasource)));
	}

	/**
	 * 查询全部数据源
	 *
	 * @return
	 */
	@GetMapping("/list")
	public R list() {
		return R.ok(sysDatasourceService.list());
	}


	/**
	 * 通过id查询数据源表
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") String id) {
		return R.ok(sysDatasourceService.getById(id));
	}

	/**
	 * 新增数据源表
	 *
	 * @param sysDatasource 数据源表
	 * @return R
	 */
	@SysLog("新增数据源表")
	@PostMapping
	public R save(@RequestBody SysDatasource sysDatasource) {
		return R.ok(sysDatasourceService.saveDsByEnc(sysDatasource));
	}

	/**
	 * 修改数据源表
	 *
	 * @param sysDatasource 数据源表
	 * @return R
	 */
	@SysLog("修改数据源表")
	@PutMapping
	public R updateById(@RequestBody SysDatasource sysDatasource) {
		return R.ok(sysDatasourceService.updateDsByEnc(sysDatasource));
	}

	/**
	 * 通过id删除数据源表
	 *
	 * @param id id
	 * @return R
	 */
	@SysLog("删除数据源表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable String id) {
		return R.ok(sysDatasourceService.removeById(id));
	}

}
