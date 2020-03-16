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
import com.joolun.cloud.mall.common.entity.OrderLogisticsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流详情
 *
 * @author JL
 * @date 2019-09-21 12:39:00
 */
public interface OrderLogisticsDetailMapper extends BaseMapper<OrderLogisticsDetail> {

	List<OrderLogisticsDetail> selectList2(@Param("query") OrderLogisticsDetail queryWrapper);
}
