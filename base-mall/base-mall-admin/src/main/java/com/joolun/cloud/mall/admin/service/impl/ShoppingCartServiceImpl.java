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
import com.joolun.cloud.mall.common.entity.ShoppingCart;
import com.joolun.cloud.mall.admin.mapper.ShoppingCartMapper;
import com.joolun.cloud.mall.admin.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 购物车
 *
 * @author JL
 * @date 2019-08-29 21:27:33
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(ShoppingCart entity) {
		ShoppingCart shoppingCart = baseMapper.selectOne(Wrappers.<ShoppingCart>lambdaQuery()
				.eq(ShoppingCart::getUserId,entity.getUserId())
				.eq(ShoppingCart::getSpuId,entity.getSpuId())
				.eq(ShoppingCart::getSkuId,entity.getSkuId()));
		if(shoppingCart == null){
			return super.save(entity);
		}else{
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(ShoppingCart entity) {
		ShoppingCart shoppingCart = baseMapper.selectOne(Wrappers.<ShoppingCart>lambdaQuery()
				.eq(ShoppingCart::getUserId,entity.getUserId())
				.eq(ShoppingCart::getSpuId,entity.getSpuId())
				.eq(ShoppingCart::getSkuId,entity.getSkuId()));
		if(shoppingCart != null && !entity.getId().equals(shoppingCart.getId())){
			entity.setQuantity(entity.getQuantity() + shoppingCart.getQuantity());
			baseMapper.deleteById(shoppingCart);
			return super.updateById(entity);
		}else{
			return super.updateById(entity);
		}
	}

	@Override
	public IPage<ShoppingCart> page2(IPage<ShoppingCart> page, ShoppingCart shoppingCart) {
		return baseMapper.selectPage2(page, shoppingCart);
	}
}
