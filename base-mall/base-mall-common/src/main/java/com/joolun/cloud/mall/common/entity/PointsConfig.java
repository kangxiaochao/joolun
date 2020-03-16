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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;

/**
 * 积分设置
 *
 * @author JL
 * @date 2019-12-06 16:15:01
 */
@Data
@TableName("points_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "积分设置")
public class PointsConfig extends Model<PointsConfig> {
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
	 * 用户初始积分数
	 */
	private Integer initPosts;
	/**
	 * 会员初始积分数
	 */
	private Integer initVipPosts;
	/**
	 * 订单金额满多少可使用抵扣
	 */
	private BigDecimal premiseAmount;
	/**
	 * 默认抵扣比例
	 */
	private Integer defaultDeductScale;
	/**
	 * 默认1积分数可抵多少元
	 */
	private BigDecimal defaultDeductAmount;

}
