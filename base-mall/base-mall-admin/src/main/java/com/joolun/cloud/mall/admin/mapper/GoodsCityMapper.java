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
import com.joolun.cloud.mall.common.entity.GoodsCity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 城市表
 *
 * @author code generator
 * @date 2019-12-09 15:37:23
 */
@Component
public interface GoodsCityMapper extends BaseMapper<GoodsCity> {
	List<Map<String,String>> selectCity(String provinceName);
}
