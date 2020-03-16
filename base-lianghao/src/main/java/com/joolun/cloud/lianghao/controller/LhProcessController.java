/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.lianghao.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.lianghao.entity.LhProcess;
import com.joolun.cloud.lianghao.service.LhProcessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 开卡流程描述
 *
 * @author code generator
 * @date 2019-11-30 11:49:30
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/lhprocess")
@Api(value = "lhprocess", tags = "开卡流程描述管理")
public class LhProcessController {

    private final LhProcessService lhProcessService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param lhProcess 开卡流程描述
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('generator_lhprocess_index')")
    public R getLhProcessPage(Page page, LhProcess lhProcess) {
        return R.ok(lhProcessService.page(page, Wrappers.query(lhProcess)));
    }

    /**
     * 通过id查询开卡流程描述
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_lhprocess_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(lhProcessService.getById(id));
    }

	/**
	 * 查询全部开卡方式的Id和Name
	 * @return
	 */
	@GetMapping("/selectProcess")
	public R selectProcess(){ return  R.ok(lhProcessService.selectProcess());}


	/**
     * 新增开卡流程描述
     * @param lhProcess 开卡流程描述
     * @return R
     */
    @SysLog("新增开卡流程描述")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('generator_lhprocess_add')")
    public R save(@RequestBody LhProcess lhProcess) {
        return R.ok(lhProcessService.save(lhProcess));
    }

    /**
     * 修改开卡流程描述
     * @param lhProcess 开卡流程描述
     * @return R
     */
    @SysLog("修改开卡流程描述")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('generator_lhprocess_edit')")
    public R updateById(@RequestBody LhProcess lhProcess) {
        return R.ok(lhProcessService.updateById(lhProcess));
    }

    /**
     * 通过id删除开卡流程描述
     * @param id
     * @return R
     */
    @SysLog("删除开卡流程描述")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_lhprocess_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(lhProcessService.removeById(id));
    }

}
