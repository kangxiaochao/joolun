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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.mall.common.entity.CouponGoods;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.GoodsSpu;
/**
 * spu商品
 *
 * @author JL
 * @date 2019-08-12 16:25:10
 */
public interface GoodsSpuService extends IService<GoodsSpu> {


	Page<GoodsSpu> selectGoodsSpuPage(Page page, GoodsSpu goodsSpu);

	IPage<GoodsSpu> page1(IPage<GoodsSpu> page, GoodsSpu goodsSpu);

	boolean save1(GoodsSpu goodsSpu);

	boolean updateById1(GoodsSpu goodsSpu);

	GoodsSpu getById2(String id);

	IPage<GoodsSpu> page2(IPage<GoodsSpu> page, GoodsSpu goodsSpu, CouponGoods couponGoods, CouponInfo couponInfo);
}
