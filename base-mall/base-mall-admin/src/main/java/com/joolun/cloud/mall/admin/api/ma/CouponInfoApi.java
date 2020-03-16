/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.CouponInfoService;
import com.joolun.cloud.mall.common.entity.CouponGoods;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.CouponUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 电子券
 *
 * @author JL
 * @date 2019-12-14 11:30:58
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/couponinfo")
@Api(value = "couponinfo", tags = "电子券Api")
public class CouponInfoApi {

    private final CouponInfoService couponInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param couponInfo 电子券
     * @return
     */
    @GetMapping("/page")
    public R getPage(HttpServletRequest request, Page page, CouponInfo couponInfo, CouponGoods cuponGoods) {
		CouponUser couponUser = new CouponUser();
		R checkThirdSession = BaseApi.checkThirdSession(couponUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(couponInfoService.page2(page, couponInfo, cuponGoods, couponUser));
    }

    /**
     * 通过id查询电子券
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(couponInfoService.getById2(id));
    }

}
