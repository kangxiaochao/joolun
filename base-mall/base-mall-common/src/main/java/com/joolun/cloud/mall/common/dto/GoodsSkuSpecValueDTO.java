/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品sku规格值
 *
 * @author JL
 * @date 2019-08-13 10:19:09
 */
@Data
public class GoodsSkuSpecValueDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
   * PK
   */
    private String id;
	/**
	 * spu_id
	 */
	private String spuId;
    /**
   * sku_id
   */
    private String skuId;
	/**
	 * 规格id
	 */
	private String specId;
    /**
   * 规格值id
   */
    private String specValueId;
	/**
	 * 规格值名
	 */
	private String specValueName;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 更新时间
   */
    private LocalDateTime updateTime;
  
}
