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
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * spu规格
 *
 * @author JL
 * @date 2019-08-13 16:56:46
 */
@Data
@TableName("goods_spu_spec")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "spu规格")
public class GoodsSpuSpec extends Model<GoodsSpuSpec> {
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
   * spec_id
   */
    private String specId;
	/**
	 * 规格名
	 */
	private String specName;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 更新时间
   */
    private LocalDateTime updateTime;
  
}
