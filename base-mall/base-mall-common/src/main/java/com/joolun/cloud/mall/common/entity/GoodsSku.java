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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 商品sku
 *
 * @author JL
 * @date 2019-08-13 10:18:34
 */
@Data
@TableName("goods_sku")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商品sku")
public class GoodsSku extends Model<GoodsSku> {
private static final long serialVersionUID = 1L;

    /**
   * PK
   */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 最后更新时间
   */
    private LocalDateTime updateTime;
    /**
   * 所属租户
   */
    private String tenantId;
    /**
   * sku编码
   */
    private String skuCode;
    /**
   * 商品Id
   */
    private String spuId;
    /**
   * 图片
   */
    private String picUrl;
    /**
   * 销售价格
   */
    private BigDecimal salesPrice;
    /**
   * 市场价
   */
    private BigDecimal marketPrice;
    /**
   * 成本价
   */
    private BigDecimal costPrice;
    /**
   * 库存
   */
    private Integer stock;
    /**
   * 重量
   */
    private BigDecimal weight;
    /**
   * 体积
   */
    private BigDecimal volume;
	/**
	 * 是否启用1、是；0否
	 */
	private String enable;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
    private String delFlag;
	/**
	 * 是否参与秒杀  1：参与  2：不参与
	 */
	private int seckillStatus;
	/**
	 * 秒杀价格
	 */
	private BigDecimal seckillPrice;
	/**
	 * 秒杀开始时间
	 */
	private LocalDateTime seckillStartTime;

	@TableField(exist = false)
	private  String seckillStartTimeStr;
	/**
	 * 秒杀结束时间
	 */
	private LocalDateTime seckillEndTime;

	@TableField(exist = false)
	private  String seckillEndTimeStr;

	@TableField(exist = false)
	private List<GoodsSkuSpecValue> specs;

	@TableField(exist = false)
	private String name;

	/**
	 * 是否参与竞拍  1：参与  2：不参与
	 */
	private int auctionStatus;
	/**
	 * 竞拍底价
	 */
	private BigDecimal auctionPrice;
	/**
	 * 竞拍每次加价
	 */
	private BigDecimal auctionAddPrice;
	/**
	 * 竞拍保证金
	 */
	private BigDecimal auctionCashDeposit;
	/**
	 * 竞拍开始时间
	 */
	private LocalDateTime auctionStartTime;

	@TableField(exist = false)
	private  String auctionStartTimeStr;
	/**
	 * 竞拍结束时间
	 */
	private LocalDateTime auctionEndTime;

	@TableField(exist = false)
	private  String auctionEndTimeStr;
}
