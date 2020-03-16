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
import com.joolun.cloud.mall.common.entity.CouponGoods;
import com.joolun.cloud.mall.admin.service.CouponGoodsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 电子券商品关联
 *
 * @author JL
 * @date 2019-12-16 17:42:19
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/coupongoods")
@Api(value = "coupongoods", tags = "电子券商品关联管理")
public class CouponGoodsController {

    private final CouponGoodsService couponGoodsService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param couponGoods 电子券商品关联
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_coupongoods_index')")
    public R getCouponGoodsPage(Page page, CouponGoods couponGoods) {
        return R.ok(couponGoodsService.page(page, Wrappers.query(couponGoods)));
    }

    /**
     * 电子券商品关联查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_coupongoods_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(couponGoodsService.getById(id));
    }

    /**
     * 电子券商品关联新增
     * @param couponGoods 电子券商品关联
     * @return R
     */
    @SysLog("新增电子券商品关联")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_coupongoods_add')")
    public R save(@RequestBody CouponGoods couponGoods) {
        return R.ok(couponGoodsService.save(couponGoods));
    }

    /**
     * 电子券商品关联修改
     * @param couponGoods 电子券商品关联
     * @return R
     */
    @SysLog("修改电子券商品关联")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_coupongoods_edit')")
    public R updateById(@RequestBody CouponGoods couponGoods) {
        return R.ok(couponGoodsService.updateById(couponGoods));
    }

    /**
     * 电子券商品关联删除
     * @param id
     * @return R
     */
    @SysLog("删除电子券商品关联")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_coupongoods_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(couponGoodsService.removeById(id));
    }

}
