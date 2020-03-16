/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.CouponUser;

/**
 * 电子券用户记录
 *
 * @author JL
 * @date 2019-12-17 10:43:53
 */
public interface CouponUserService extends IService<CouponUser> {

	IPage<CouponUser> page1(IPage<CouponUser> page, CouponUser couponUser);

	IPage<CouponUser> page2(IPage<CouponUser> page, CouponUser couponUser);

	/**
	 * 领券
	 * @param couponUser
	 */
	Boolean receiveCoupon(CouponUser couponUser, CouponInfo cuponInfo);
}
