/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.common.entity.CouponUser;
import com.joolun.cloud.mall.admin.service.CouponUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 电子券用户记录
 *
 * @author JL
 * @date 2019-12-17 10:43:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/couponuser")
@Api(value = "couponuser", tags = "电子券用户记录管理")
public class CouponUserController {

    private final CouponUserService couponUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param couponUser 电子券用户记录
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_couponuser_index')")
    public R getCouponUserPage(Page page, CouponUser couponUser) {
        return R.ok(couponUserService.page1(page, couponUser));
    }

    /**
     * 电子券用户记录查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_couponuser_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponUserService.getById(id));
    }

    /**
     * 电子券用户记录新增
     * @param couponUser 电子券用户记录
     * @return R
     */
    @SysLog("新增电子券用户记录")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_couponuser_add')")
    public R save(@RequestBody CouponUser couponUser) {
        return R.ok(couponUserService.save(couponUser));
    }

    /**
     * 电子券用户记录修改
     * @param couponUser 电子券用户记录
     * @return R
     */
    @SysLog("修改电子券用户记录")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_couponuser_edit')")
    public R updateById(@RequestBody CouponUser couponUser) {
        return R.ok(couponUserService.updateById(couponUser));
    }

    /**
     * 电子券用户记录删除
     * @param id
     * @return R
     */
    @SysLog("删除电子券用户记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_couponuser_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(couponUserService.removeById(id));
    }

}
