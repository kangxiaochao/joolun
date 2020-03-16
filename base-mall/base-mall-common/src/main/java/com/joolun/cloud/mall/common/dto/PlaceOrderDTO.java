/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.cloud.mall.common.entity.ShoppingCart;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 下单参数
 *
 * @author JL
 * @date 2019-08-13 10:18:34
 */
@Data
public class PlaceOrderDTO extends Model<ShoppingCart> {
	private static final long serialVersionUID = 1L;

    /**
   * sku
   */
    private List<PlaceOrderSkuDTO> skus;
	/**
	 * 支付方式1、货到付款；2、在线支付
	 */
	private String paymentWay;
	/**
	 * 付款方式1、微信支付
	 */
	private String paymentType;
	/**
	 * 买家留言
	 */
	private String userMessage;
	/**
	 * 应用类型1、小程序
	 */
	private String appType;
	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户收货地址ID
	 */
	private String userAddressId;
	/**
	 * 订单类型（0、普通订单；1、砍价订单；2、拼团订单；3、秒杀订单）
	 */
	private String orderType;
	/**
	 * 关联ID（砍价记录ID，拼团记录ID，秒杀记录ID）
	 */
	private String relationId;
}
