/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.mall.common.entity.GoodsSku;
import com.joolun.cloud.mall.admin.mapper.GoodsSkuMapper;
import com.joolun.cloud.mall.admin.service.GoodsSkuService;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 商品sku
 *
 * @author JL
 * @date 2019-08-13 10:18:34
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

	@Override
	public List<GoodsSku> listGoodsSkuBySpuId(String spuId) {
		return baseMapper.listGoodsSkuBySpuId(spuId);
	}
}
