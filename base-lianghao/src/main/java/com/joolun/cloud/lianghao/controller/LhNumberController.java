package com.joolun.cloud.lianghao.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.lianghao.entity.LhNumber;
import com.joolun.cloud.lianghao.service.LhNumberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 *
 *
 * @author code generator
 * @date 2019-11-21 10:46:14
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/lhnumber")
@Api(value = "lhnumber", tags = "管理")
public class LhNumberController {

	private final LhNumberService lhNumberService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param lhNumber
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_index')")
	public R getLhNumberPage(Page page, LhNumber lhNumber) {
		return R.ok(lhNumberService.page(page, Wrappers.query(lhNumber)));
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_get')")
	public R getById(@PathVariable("id") String id) {
		return R.ok(lhNumberService.getById(id));
	}

	/**
	 * 新增
	 * @param lhNumber
	 * @return R
	 */
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_add')")
	public R save(@RequestBody LhNumber lhNumber) {
		if(lhNumber.getOperatorName() != null && lhNumber.getOperatorName().contains("-")){
			String [] operator = lhNumber.getOperatorName().split("-");
			lhNumber.setOperatorId(operator[0]);
			lhNumber.setOperatorName(operator[1]);
		}

		if(lhNumber.getPackageName() != null && lhNumber.getPackageName().contains("-")){
			String [] packages = lhNumber.getPackageName().split("-");
			lhNumber.setPackageId(packages[0]);
			lhNumber.setPackageName(packages[1]);
		}
		return R.ok(lhNumberService.save(lhNumber));
	}

	/**
	 * 修改
	 * @param lhNumber
	 * @return R
	 */
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_edit')")
	public R updateById(@RequestBody LhNumber lhNumber) {
		if(lhNumber.getOperatorName() != null && lhNumber.getOperatorName().contains("-")){
			String [] operator = lhNumber.getOperatorName().split("-");
			lhNumber.setOperatorId(operator[0]);
			lhNumber.setOperatorName(operator[1]);
		}

		if(lhNumber.getPackageName() != null && lhNumber.getPackageName().contains("-")){
			String [] packages = lhNumber.getPackageName().split("-");
			lhNumber.setPackageId(packages[0]);
			lhNumber.setPackageName(packages[1]);
		}
		return R.ok(lhNumberService.updateById(lhNumber));
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return R
	 */
	@SysLog("删除")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(lhNumberService.removeById(id));
	}

}
