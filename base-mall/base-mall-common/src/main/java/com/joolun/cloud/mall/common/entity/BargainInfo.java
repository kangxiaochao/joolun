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
 * 砍价
 *
 * @author JL
 * @date 2019-12-28 18:07:51
 */
@Data
@TableName("bargain_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "砍价")
public class BargainInfo extends Model<BargainInfo> {
    private static final long serialVersionUID=1L;

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
     * （1：开启；0：关闭）
     */
    private String enable;
    /**
     * 商品Id
     */
    private String spuId;
    /**
     * skuId
     */
    private String skuId;
	/**
	 * 砍价名称
	 */
	private String name;
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
     * 自砍一刀（1：开启；0：关闭）
     */
    private String selfCut;
    /**
     * 必须底价购买（1：是；0：否）
     */
    private String floorBuy;
    /**
     * 发起人数
     */
    private Integer launchNum;
    /**
     * 单次可砍最高金额
     */
    private BigDecimal cutMax;
    /**
     * 单次可砍最低金额
     */
    private BigDecimal cutMin;
    /**
     * 砍价规则
     */
    private String cutRule;
    /**
     * 分享标题 
     */
    private String shareTitle;
	/**
	 * 图片
	 */
	private String picUrl;

	@TableField(exist = false)
    private GoodsSpu goodsSpu;

	@TableField(exist = false)
	private GoodsSku goodsSku;

	@TableField(exist = false)
	private BargainUser bargainUser;

	@TableField(exist = false)
	private String status;

	public String getStatus() {
		if(this.validEndTime != null){
			if(LocalDateTime.now().isBefore(this.validBeginTime)){
				return MallConstants.BARGAIN_INFO_STATUS_0;
			}else if(LocalDateTime.now().isAfter(this.validEndTime)){
				return MallConstants.BARGAIN_INFO_STATUS_2;
			}else{
				return MallConstants.BARGAIN_INFO_STATUS_1;
			}
		}
		return null;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
