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
import com.joolun.cloud.mall.common.constant.MallConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 电子券用户记录
 *
 * @author JL
 * @date 2019-12-17 10:43:53
 */
@Data
@TableName("coupon_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "电子券用户记录")
public class CouponUser extends Model<CouponUser> {
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
	 * 电子券ID
	 */
	private String couponId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 电子券码
	 */
	private Integer couponCode;
	/**
	 * 状态0、未使用；1、已使用
	 */
	private String status;
	/**
	 * 使用时间
	 */
	private LocalDateTime usedTime;
	/**
	 * 有效开始时间（固定时间段特有）
	 */
	private LocalDateTime validBeginTime;
	/**
	 * 有效结束时间（固定时间段特有）
	 */
	private LocalDateTime validEndTime;
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
	 * 减免金额（代金券特有）
	 */
	private BigDecimal reduceAmount;
	/**
	 * 折扣率（折扣券特有）
	 */
	private BigDecimal discount;
	/**
	 * 适用类型1、全部商品；2、指定商品可用
	 */
	private String suitType;

	@TableField(exist = false)
	private CouponInfo couponInfo;

	@TableField(exist = false)
	private UserInfo userInfo;

	@TableField(exist = false)
	private GoodsSpu goodsSpu;

	@TableField(exist = false)
	private List<String> spuIds;

	public String getStatus() {
		if(MallConstants.COUPON_USER_STATUS_0.equals(status) && this.validEndTime != null){
			if(this.validEndTime.isAfter(LocalDateTime.now())){
				return status;
			}else{
				return MallConstants.COUPON_USER_STATUS_2;
			}
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
