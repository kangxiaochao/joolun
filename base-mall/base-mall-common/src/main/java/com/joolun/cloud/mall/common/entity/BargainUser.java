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
import com.joolun.cloud.mall.common.constant.MallConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * 砍价记录
 *
 * @author JL
 * @date 2019-12-30 11:53:14
 */
@Data
@TableName("bargain_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "砍价记录")
public class BargainUser extends Model<BargainUser> {
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
	 * 砍价ID
	 */
	private String bargainId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 商品Id
	 */
	private String spuId;
	/**
	 * skuId
	 */
	private String skuId;
	/**
	 * 开始时间
	 */
	private LocalDateTime validBeginTime;
	/**
	 * 结束时间
	 */
	private LocalDateTime validEndTime;
	/**
	 * 砍价底价
	 */
	private BigDecimal bargainPrice;
	/**
	 * 必须底价购买（1：是；0：否）
	 */
	private String floorBuy;
	/**
	 * 状态（0：砍价中；1：完成砍价）
	 */
	private String status;
	/**
	 * 是否购买（1：是；0：否）
	 */
	private String isBuy;
	/**
	 * 订单Id
	 */
	private String orderId;
	/**
	 * 已砍金额
	 */
	@TableField(exist = false)
	private BigDecimal havBargainAmount;

	@TableField(exist = false)
	private BargainCut bargainCut;

	@TableField(exist = false)
	private BargainInfo bargainInfo;

	@TableField(exist = false)
	private UserInfo userInfo;

	public String getStatus() {
		if (MallConstants.BARGAIN_USER_STATUS_0.equals(status) && this.validEndTime != null) {
			if (this.validEndTime.isAfter(LocalDateTime.now())) {
				return status;
			} else {
				return MallConstants.BARGAIN_USER_STATUS_2;
			}
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
