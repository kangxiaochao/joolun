/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.mall.common.enums.OrderItemEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 商城订单详情
 *
 * @author JL
 * @date 2019-09-10 15:31:40
 */
@Data
@TableName("order_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商城订单详情")
public class OrderItem extends Model<OrderItem> {
	private static final long serialVersionUID = 1L;

	/**
	 * PK
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 所属租户
	 */
	private String tenantId;
	/**
	 * 逻辑删除标记（0：显示；1：隐藏）
	 */
	private String delFlag;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 最后更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 商品Id
	 */
	private String spuId;
	/**
	 * 商品名
	 */
	private String spuName;
	/**
	 * 规格信息
	 */
	private String specInfo;
	/**
	 * skuId
	 */
	private String skuId;
	/**
	 * 图片
	 */
	private String picUrl;
	/**
	 * 商品数量
	 */
	private Integer quantity;
	/**
	 * 购买单价
	 */
	private BigDecimal salesPrice;
	/**
	 * 运费金额
	 */
	private BigDecimal freightPrice;
	/**
	 * 支付金额（购买单价*商品数量+运费金额-积分抵扣金额-电子券抵扣金额）
	 */
	private BigDecimal paymentPrice;
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
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态1：退款中；2、拒绝退款；3、同意退款
	 */
	private String status;
	/**
	 * 是否退款0:否 1：是
	 */
	private String isRefund;
	/**
	 * 状态1：申请退款；2、拒绝退款；3、同意退款
	 */
	@TableField(exist = false)
	private String statusDesc;

	public String getStatusDesc() {
		if (this.status == null) {
			return null;
		}
		return OrderItemEnum.valueOf(OrderItemEnum.STATUS_PREFIX + "_" + this.status).getDesc();
	}

	@TableField(exist = false)
	private List<OrderRefunds> listOrderRefunds;

}
