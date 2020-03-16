/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 电子券
 *
 * @author JL
 * @date 2019-12-14 11:30:58
 */
@Data
@TableName("coupon_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "电子券")
public class CouponInfo extends Model<CouponInfo> {
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
	 * 创建者ID
	 */
	private String createId;
	/**
	 * 排序字段
	 */
	private Integer sort;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型1、代金券；2：折扣券
	 */
	private String type;
	/**
	 * 订单金额满多少可使用
	 */
	private BigDecimal premiseAmount;
	/**
	 * 到期类型1、领券后生效；2：固定时间段
	 */
	private String expireType;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 减免金额（代金券特有）
	 */
	private BigDecimal reduceAmount;
	/**
	 * 折扣率（折扣券特有）
	 */
	private BigDecimal discount;
	/**
	 * 有效天数（领券后生效特有）
	 */
	private Integer validDays;
	/**
	 * 有效开始时间（固定时间段特有）
	 */
	private LocalDateTime validBeginTime;
	/**
	 * 有效结束时间（固定时间段特有）
	 */
	private LocalDateTime validEndTime;
	/**
	 * 适用类型1、全部商品；2、指定商品可用
	 */
	private String suitType;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * （1：开启；0：关闭）
	 */
	private String enable;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;

	@TableField(exist = false)
	private CouponUser couponUser;

	@TableField(exist = false)
	private List<GoodsSpu> listGoodsSpu;

}
