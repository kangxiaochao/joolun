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
import com.joolun.cloud.mall.common.entity.OrderLogistics;
import com.joolun.cloud.mall.admin.service.OrderLogisticsService;
import com.joolun.cloud.mall.common.enums.OrderLogisticsEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 订单物流
 *
 * @author JL
 * @date 2019-09-16 09:53:17
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderlogistics")
@Api(value = "orderlogistics", tags = "订单物流管理")
public class OrderLogisticsController {

    private final OrderLogisticsService orderLogisticsService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderLogistics 订单物流
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogistics_index')")
    public R getOrderLogisticsPage(Page page, OrderLogistics orderLogistics) {
        return R.ok(orderLogisticsService.page(page,Wrappers.query(orderLogistics)));
    }

    /**
    * 通过id查询订单物流
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogistics_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(orderLogisticsService.getById(id));
    }

    /**
    * 新增订单物流
    * @param orderLogistics 订单物流
    * @return R
    */
    @SysLog("新增订单物流")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderlogistics_add')")
    public R save(@RequestBody OrderLogistics orderLogistics){
        return R.ok(orderLogisticsService.save(orderLogistics));
    }

    /**
    * 修改订单物流
    * @param orderLogistics 订单物流
    * @return R
    */
    @SysLog("修改订单物流")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderlogistics_edit')")
    public R updateById(@RequestBody OrderLogistics orderLogistics){
        return R.ok(orderLogisticsService.updateById(orderLogistics));
    }

    /**
    * 通过id删除订单物流
    * @param id
    * @return R
    */
    @SysLog("删除订单物流")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderlogistics_del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderLogisticsService.removeById(id));
    }

	/**
	 * 获取相关枚举数据的字典
	 * @param type
	 * @return
	 */
	@GetMapping("/dict/{type}")
	public R getDictByType(@PathVariable String type) {
		return R.ok(OrderLogisticsEnum.queryAll(type));
	}
}
