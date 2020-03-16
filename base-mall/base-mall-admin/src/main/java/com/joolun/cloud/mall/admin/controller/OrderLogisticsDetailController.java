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
import com.joolun.cloud.mall.common.entity.OrderLogisticsDetail;
import com.joolun.cloud.mall.admin.service.OrderLogisticsDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 物流详情
 *
 * @author JL
 * @date 2019-09-21 12:39:00
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderlogisticsdetail")
@Api(value = "orderlogisticsdetail", tags = "物流详情管理")
public class OrderLogisticsDetailController {

    private final OrderLogisticsDetailService orderLogisticsDetailService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderLogisticsDetail 物流详情
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogisticsdetail_index')")
    public R getOrderLogisticsDetailPage(Page page, OrderLogisticsDetail orderLogisticsDetail) {
        return R.ok(orderLogisticsDetailService.page(page,Wrappers.query(orderLogisticsDetail)));
    }

    /**
    * 通过id查询物流详情
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogisticsdetail_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderLogisticsDetailService.getById(id));
    }

    /**
    * 新增物流详情
    * @param orderLogisticsDetail 物流详情
    * @return R
    */
    @SysLog("新增物流详情")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderlogisticsdetail_add')")
    public R save(@RequestBody OrderLogisticsDetail orderLogisticsDetail){
        return R.ok(orderLogisticsDetailService.save(orderLogisticsDetail));
    }

    /**
    * 修改物流详情
    * @param orderLogisticsDetail 物流详情
    * @return R
    */
    @SysLog("修改物流详情")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderlogisticsdetail_edit')")
    public R updateById(@RequestBody OrderLogisticsDetail orderLogisticsDetail){
        return R.ok(orderLogisticsDetailService.updateById(orderLogisticsDetail));
    }

    /**
    * 通过id删除物流详情
    * @param id
    * @return R
    */
    @SysLog("删除物流详情")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogisticsdetail_del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderLogisticsDetailService.removeById(id));
    }

}
