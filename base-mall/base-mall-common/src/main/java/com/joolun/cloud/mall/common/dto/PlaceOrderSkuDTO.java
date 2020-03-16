/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 下单参数
 *
 * @author JL
 * @date 2019-08-13 10:18:34
 */
@Data
public class PlaceOrderSkuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品Id
	 */
	private String spuId;
    /**
	 * skuId
	 */
    private String skuId;

	/**
	 * 数量
	 */
	private Integer quantity;
	/**
	 * 支付金额
	 */
	private BigDecimal paymentPrice;
	/**
	 * 运费金额
	 */
	private BigDecimal freightPrice;
	/**
	 * 支付积分
	 */
	private Integer paymentPoints;
	/**
	 * 电子券支付金额
	 */
	private BigDecimal paymentCouponPrice;
	/**
	 * 积分抵扣金额
	 */
	private BigDecimal paymentPointsPrice;
	/**
	 * 用户电子券ID
	 */
	private String couponUserId;
}
