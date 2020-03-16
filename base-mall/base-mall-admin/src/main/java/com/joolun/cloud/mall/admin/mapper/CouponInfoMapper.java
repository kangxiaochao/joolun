/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joolun.cloud.mall.common.entity.CouponGoods;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.CouponUser;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * 电子券
 *
 * @author JL
 * @date 2019-12-14 11:30:58
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

	CouponInfo selectById2(Serializable id);

	IPage<CouponInfo> selectPage2(IPage<CouponInfo> page, @Param("query") CouponInfo couponInfo, @Param("query2") CouponGoods cuponGoods, @Param("query3") CouponUser couponUser);
}
