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
import com.joolun.cloud.mall.common.entity.OrderItem;
import com.joolun.cloud.mall.admin.service.OrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城订单详情
 *
 * @author JL
 * @date 2019-09-10 15:31:40
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderitem")
@Api(value = "orderitem", tags = "商城订单详情管理")
public class OrderItemController {

    private final OrderItemService orderItemService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderItem 商城订单详情
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_orderitem_index')")
    public R getOrderItemPage(Page page, OrderItem orderItem) {
        return R.ok(orderItemService.page(page,Wrappers.query(orderItem)));
    }

    /**
    * 通过id查询商城订单详情
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderItemService.getById2(id));
    }

    /**
    * 新增商城订单详情
    * @param orderItem 商城订单详情
    * @return R
    */
    @SysLog("新增商城订单详情")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderitem_add')")
    public R save(@RequestBody OrderItem orderItem){
        return R.ok(orderItemService.save(orderItem));
    }

    /**
    * 修改商城订单详情
    * @param orderItem 商城订单详情
    * @return R
    */
    @SysLog("修改商城订单详情")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderitem_edit')")
    public R updateById(@RequestBody OrderItem orderItem){
        return R.ok(orderItemService.updateById(orderItem));
    }

    /**
    * 通过id删除商城订单详情
    * @param id
    * @return R
    */
    @SysLog("删除商城订单详情")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderitem_del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderItemService.removeById(id));
    }

}
