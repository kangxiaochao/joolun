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
 * 商品sku规格值
 *
 * @author JL
 * @date 2019-08-13 10:19:09
 */
@Data
@TableName("goods_sku_spec_value")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商品sku规格值")
public class GoodsSkuSpecValue extends Model<GoodsSkuSpecValue> {
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
	 * spu_id
	 */
	private String spuId;
    /**
   * sku_id
   */
    private String skuId;
    /**
   * 规格值id
   */
    private String specValueId;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 更新时间
   */
    private LocalDateTime updateTime;
	/**
	 * 规格id
	 */
	@TableField(exist = false)
	private String specId;
	/**
	 * 规格值名
	 */
	@TableField(exist = false)
	private String specValueName;
  
}
