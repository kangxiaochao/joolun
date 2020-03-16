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
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.OrderLogisticsService;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import com.joolun.cloud.mall.common.entity.OrderInfo;
import com.joolun.cloud.mall.admin.service.OrderInfoService;
import com.joolun.cloud.mall.common.entity.OrderLogistics;
import com.joolun.cloud.mall.common.feign.FeignWxAppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.Map;

/**
 * 商城订单
 *
 * @author JL
 * @date 2019-09-10 15:21:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orderinfo")
@Api(value = "orderinfo", tags = "商城订单管理")
public class OrderInfoController {

    private final OrderInfoService orderInfoService;
	private final UserInfoService userInfoService;
	private final FeignWxAppService feignWxAppService;
	private final OrderLogisticsService orderLogisticsService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param orderInfo 商城订单
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_index')")
    public R getOrderInfoPage(Page page, OrderInfo orderInfo) {
        return R.ok(orderInfoService.page1(page, Wrappers.query(orderInfo)));
    }

    /**
    * 通过id查询商城订单
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_get')")
    public R getById(@PathVariable("id") String id){
		OrderInfo orderInfo = orderInfoService.getById(id);
		R r2 = feignWxAppService.getById(orderInfo.getAppId(), SecurityConstants.FROM_IN);
		orderInfo.setUserInfo(userInfoService.getById(orderInfo.getUserId()));
		orderInfo.setApp((Map<Object, Object>)r2.getData());
		OrderLogistics orderLogistics = orderLogisticsService.getById(orderInfo.getLogisticsId());
		orderInfo.setOrderLogistics(orderLogistics);
        return R.ok(orderInfo);
    }

    /**
    * 新增商城订单
    * @param orderInfo 商城订单
    * @return R
    */
    @SysLog("新增商城订单")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_add')")
    public R save(@RequestBody OrderInfo orderInfo){
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
    * 修改商城订单
    * @param orderInfo 商城订单
    * @return R
    */
    @SysLog("修改商城订单")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_edit')")
    public R updateById(@RequestBody OrderInfo orderInfo){
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
    * 通过id删除商城订单
    * @param id
    * @return R
    */
    @SysLog("删除商城订单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_orderinfo_del')")
    public R removeById(@PathVariable String id){
        return R.ok(orderInfoService.removeById(id));
    }

}
