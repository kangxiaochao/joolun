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
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.admin.service.CouponInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * 电子券
 *
 * @author JL
 * @date 2019-12-14 11:30:58
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/couponinfo")
@Api(value = "couponinfo", tags = "电子券管理")
public class CouponInfoController {

    private final CouponInfoService couponInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param couponInfo 电子券
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_couponinfo_index')")
    public R getPage(Page page, CouponInfo couponInfo) {
        return R.ok(couponInfoService.page(page, Wrappers.query(couponInfo)));
    }

	/**
	 * list查询
	 * @param couponInfo
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("@ato.hasAuthority('mall_couponinfo_index')")
	public List<CouponInfo> getList(CouponInfo couponInfo) {
		return couponInfoService.list(Wrappers.query(couponInfo).lambda()
				.select(CouponInfo::getId,
						CouponInfo::getName));
	}

    /**
     * 通过id查询电子券
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_couponinfo_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponInfoService.getById2(id));
    }

    /**
     * 新增电子券
     * @param couponInfo 电子券
     * @return R
     */
    @SysLog("新增电子券")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_couponinfo_add')")
    public R save(@RequestBody CouponInfo couponInfo) {
        return R.ok(couponInfoService.save(couponInfo));
    }

    /**
     * 修改电子券
     * @param couponInfo 电子券
     * @return R
     */
    @SysLog("修改电子券")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_couponinfo_edit')")
    public R updateById(@RequestBody CouponInfo couponInfo) {
        return R.ok(couponInfoService.updateById1(couponInfo));
    }

    /**
     * 通过id删除电子券
     * @param id
     * @return R
     */
    @SysLog("删除电子券")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_couponinfo_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(couponInfoService.removeById(id));
    }

}
