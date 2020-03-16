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
 * 购物车
 *
 * @author JL
 * @date 2019-08-29 21:27:33
 */
@Data
@TableName("shopping_cart")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "购物车")
public class ShoppingCart extends Model<ShoppingCart> {
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
   * 用户编号
   */
    private String userId;
    /**
   * 商品 SPU 编号
   */
    private String spuId;
    /**
   * 商品 SKU 编号
   */
    private String skuId;
    /**
   * 商品购买数量
   */
    private Integer quantity;
	/**
	 * 加入时价格
	 */
	private BigDecimal addPrice;
	/**
	 * 加入时的spu名字
	 */
	private String spuName;
	/**
	 * 加入时的规格信息
	 */
	private String specInfo;
	/**
	 * 图片
	 */
	private String picUrl;

	@TableField(exist = false)
	private GoodsSpu goodsSpu;

	@TableField(exist = false)
	private GoodsSku goodsSku;

	@TableField(exist = false)
	private List<GoodsSkuSpecValue> specs;
}
