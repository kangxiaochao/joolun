/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.mall.common.entity.GoodsCategory;
import com.joolun.cloud.mall.common.entity.GoodsCategoryTree;

import java.util.List;

/**
 * 商品类目
 *
 * @author JL
 * @date 2019-08-12 11:46:28
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

	/**
	 * 查询类目树
	 *
	 * @return 树
	 */
	List<GoodsCategoryTree> selectTree(GoodsCategory goodsCategory);
}
