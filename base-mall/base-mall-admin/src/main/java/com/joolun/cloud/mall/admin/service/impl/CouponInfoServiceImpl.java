/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.mall.admin.service.CouponGoodsService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.entity.*;
import com.joolun.cloud.mall.admin.mapper.CouponInfoMapper;
import com.joolun.cloud.mall.admin.service.CouponInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子券
 *
 * @author JL
 * @date 2019-12-14 11:30:58
 */
@Service
@AllArgsConstructor
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

	private final CouponGoodsService couponGoodsService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(CouponInfo entity) {
		super.save(entity);
		if(MallConstants.COUPON_SUIT_TYPE_2.equals(entity.getSuitType())){//是否指定商品
			doCouponGoods(entity);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById1(CouponInfo entity) {
		super.updateById(entity);
		//先删除关联商品
		couponGoodsService.remove(Wrappers.<CouponGoods>lambdaQuery()
				.eq(CouponGoods::getCouponId,entity.getId()));
		if(MallConstants.COUPON_SUIT_TYPE_2.equals(entity.getSuitType())){//是否指定商品
			doCouponGoods(entity);
		}
		return Boolean.TRUE;
	}

	/**
	 * 批量添加关联商品
	 * @param entity
	 */
	protected void doCouponGoods(CouponInfo entity){
		List<GoodsSpu> listGoodsSpu = entity.getListGoodsSpu();
		List<CouponGoods> listCouponGoods = new ArrayList<>();
		if(listGoodsSpu != null && listGoodsSpu.size() > 0){
			listGoodsSpu.forEach(goodsSpu -> {
				CouponGoods couponGoods = new CouponGoods();
				couponGoods.setCouponId(entity.getId());
				couponGoods.setSpuId(goodsSpu.getId());
				listCouponGoods.add(couponGoods);
			});
			//添加关联商品
			couponGoodsService.saveBatch(listCouponGoods);
		}
	}

	@Override
	public CouponInfo getById2(Serializable id) {
		return baseMapper.selectById2(id);
	}

	@Override
	public IPage<CouponInfo> page2(IPage<CouponInfo> page, CouponInfo couponInfo, CouponGoods cuponGoods, CouponUser couponUser) {
		return baseMapper.selectPage2(page, couponInfo, cuponGoods, couponUser);
	}
}
