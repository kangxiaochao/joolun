/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.common.entity.BargainInfo;
import com.joolun.cloud.mall.admin.service.BargainInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 砍价
 *
 * @author JL
 * @date 2019-12-28 18:07:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bargaininfo")
@Api(value = "bargaininfo", tags = "砍价管理")
public class BargainInfoController {

    private final BargainInfoService bargainInfoService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainInfo 砍价
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_bargaininfo_index')")
    public R getPage(Page page, BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.page(page, Wrappers.query(bargainInfo)));
    }

	/**
	 * list列表
	 * @param bargainInfo 砍价
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("@ato.hasAuthority('mall_bargaininfo_index')")
	public R getList(BargainInfo bargainInfo) {
		return R.ok(bargainInfoService.list(Wrappers.query(bargainInfo)));
	}

    /**
     * 砍价查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_bargaininfo_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(bargainInfoService.getById(id));
    }

    /**
     * 砍价新增
     * @param bargainInfo 砍价
     * @return R
     */
    @SysLog("新增砍价")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_bargaininfo_add')")
    public R save(@RequestBody BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.save(bargainInfo));
    }

    /**
     * 砍价修改
     * @param bargainInfo 砍价
     * @return R
     */
    @SysLog("修改砍价")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_bargaininfo_edit')")
    public R updateById(@RequestBody BargainInfo bargainInfo) {
        return R.ok(bargainInfoService.updateById(bargainInfo));
    }

    /**
     * 砍价删除
     * @param id
     * @return R
     */
    @SysLog("删除砍价")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_bargaininfo_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(bargainInfoService.removeById(id));
    }

}
