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
import com.joolun.cloud.mall.common.entity.GoodsSpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu商品
 *
 * @author JL
 * @date 2019-08-12 16:25:10
 */
public interface GoodsSpuMapper extends BaseMapper<GoodsSpu> {

	List<GoodsSpu> selectGoodsSpuPage(GoodsSpu goodsSpu);

	IPage<GoodsSpu> selectPage1(IPage<GoodsSpu> page, @Param("query") GoodsSpu goodsSpu);

	GoodsSpu selectById2(String id);

	GoodsSpu selectById4(String id);

	GoodsSpu selectOneByShoppingCart(String id);

	/**
	 * 查询电子券的关联商品
	 * @param couponId
	 * @return
	 */
	List<GoodsSpu> selectListByCouponId(String couponId);

	IPage<GoodsSpu> selectPage2(IPage<GoodsSpu> page, @Param("query") GoodsSpu goodsSpu, @Param("query2") CouponGoods couponGoods, @Param("query3") CouponInfo couponInfo);
}
