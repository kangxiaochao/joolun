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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;

/**
 * 积分变动记录
 *
 * @author JL
 * @date 2019-12-05 19:47:22
 */
@Data
@TableName("points_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "积分变动记录")
public class PointsRecord extends Model<PointsRecord> {
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
	 * 用户ID
	 */
	private String userId;
	/**
	 * 数量
	 */
	private Integer amount;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 商品ID
	 */
	private String spuId;
	/**
	 * 订单详情ID
	 */
	private String orderItemId;
	/**
	 * 记录类型1、系统初始化；2、商品赠送；3、退款赠送减回；4、商品抵扣；5、退款抵扣加回
	 */
	private String recordType;
	@TableField(exist = false)
	private UserInfo userInfo;
}
