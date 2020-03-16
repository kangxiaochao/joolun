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
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.CouponUserService;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import com.joolun.cloud.mall.common.entity.CouponUser;
import com.joolun.cloud.mall.common.entity.ShoppingCart;
import com.joolun.cloud.mall.common.entity.UserInfo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 商城用户
 *
 * @author JL
 * @date 2019-12-04 11:09:55
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/userinfo")
@Api(value = "userinfo", tags = "商城用户管理")
public class UserInfoApi {

    private final UserInfoService userInfoService;
	private final CouponUserService couponUserService;

    /**
     * 查询商城用户
     * @return R
     */
    @GetMapping
    public R getById(HttpServletRequest request) {
		UserInfo userInfo = new UserInfo();
		R checkThirdSession = BaseApi.checkThirdSession(userInfo, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		int couponNum = couponUserService.count(Wrappers.<CouponUser>lambdaQuery()
				.eq(CouponUser :: getUserId, userInfo.getId())
				.eq(CouponUser :: getStatus, CommonConstants.NO)
				.gt(CouponUser :: getValidEndTime, LocalDateTime.now()));
		userInfo = userInfoService.getById(userInfo.getId());
		userInfo.setCouponNum(couponNum);
        return R.ok(userInfo);
    }
}
