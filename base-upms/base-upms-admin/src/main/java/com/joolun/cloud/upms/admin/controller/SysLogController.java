package com.joolun.cloud.upms.admin.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.upms.admin.service.SysLogService;
import com.joolun.cloud.upms.common.entity.SysLog;
import com.joolun.cloud.upms.common.vo.PreLogVO;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.security.annotation.Inside;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Api(value = "log", tags = "日志管理模块")
public class SysLogController {
	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 *
	 * @param page   分页对象
	 * @param sysLog 系统日志
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_log_index')")
	public R getLogPage(Page page, SysLog sysLog) {
		return R.ok(sysLogService.page(page, Wrappers.query(sysLog)));
	}

	/**
	 * 删除日志
	 *
	 * @param id ID
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_log_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(sysLogService.removeById(id));
	}

	/**
	 * 插入日志
	 *
	 * @param sysLog 日志实体
	 * @return
	 */
	@Inside
	@PostMapping("/save")
	public R save(@Valid @RequestBody SysLog sysLog) {
		return R.ok(sysLogService.save(sysLog));
	}

	/**
	 * 批量插入前端异常日志
	 *
	 * @param preLogVOList 日志实体
	 * @return ok/false
	 */
	@PostMapping("/logs")
	public R saveBatchLogs(@RequestBody List<PreLogVO> preLogVOList) {
		return R.ok(sysLogService.saveBatchLogs(preLogVOList));
	}
}
