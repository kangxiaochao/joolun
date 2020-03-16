/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.CouponInfoService;
import com.joolun.cloud.mall.admin.service.CouponUserService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.constant.MyReturnCode;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.CouponUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 电子券用户记录
 *
 * @author JL
 * @date 2019-12-17 10:43:53
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/couponuser")
@Api(value = "couponuser", tags = "电子券用户记录Api")
public class CouponUserApi {

    private final CouponUserService couponUserService;
	private final CouponInfoService couponInfoService;
    /**
     * 分页列表
     * @param page 分页对象
     * @param couponUser 电子券用户记录
     * @return
     */
    @GetMapping("/page")
    public R getCouponUserPage(HttpServletRequest request, Page page, CouponUser couponUser) {
		R checkThirdSession = BaseApi.checkThirdSession(couponUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(couponUserService.page2(page, couponUser));
    }

    /**
     * 电子券用户记录查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(couponUserService.getById(id));
    }

    /**
     * 电子券用户记录新增
     * @param couponUser 电子券用户记录
     * @return R
     */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody CouponUser couponUser) {
		R checkThirdSession = BaseApi.checkThirdSession(couponUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		//核验用户是否领取过该券，并未使用
		int couponNum = couponUserService.count(Wrappers.<CouponUser>lambdaQuery()
				.eq(CouponUser :: getUserId, couponUser.getUserId())
				.eq(CouponUser :: getCouponId, couponUser.getCouponId())
				.eq(CouponUser :: getStatus, MallConstants.COUPON_USER_STATUS_0)
				.gt(CouponUser :: getValidEndTime, LocalDateTime.now()));
		if(couponNum > 0){
			return R.failed(MyReturnCode.ERR_80001.getCode(), MyReturnCode.ERR_80001.getMsg());
		}
		CouponInfo cuponInfo = couponInfoService.getById(couponUser.getCouponId());
		if(cuponInfo == null){
			return R.failed(MyReturnCode.ERR_80002.getCode(), MyReturnCode.ERR_80002.getMsg());
		}
		//核验库存
		if(cuponInfo.getStock() <= 0){
			return R.failed(MyReturnCode.ERR_80003.getCode(), MyReturnCode.ERR_80003.getMsg());
		}
		couponUser.setStatus(CommonConstants.NO);
		//计数有效期
		if(MallConstants.COUPON_EXPIRE_TYPE_1.equals(cuponInfo.getExpireType())){
			couponUser.setValidBeginTime(LocalDateTime.now());
			couponUser.setValidEndTime(LocalDateTime.now().plusDays(cuponInfo.getValidDays()));
		}
		if(MallConstants.COUPON_EXPIRE_TYPE_2.equals(cuponInfo.getExpireType())){
			couponUser.setValidBeginTime(cuponInfo.getValidBeginTime());
			couponUser.setValidEndTime(cuponInfo.getValidEndTime());
		}
		couponUserService.receiveCoupon(couponUser, cuponInfo);
        return R.ok(couponUser);
    }

}
