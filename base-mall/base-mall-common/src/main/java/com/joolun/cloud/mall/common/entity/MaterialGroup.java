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
 * 素材分组
 *
 * @author JL
 * @date 2019-10-26 19:29:07
 */
@Data
@TableName("material_group")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "素材分组")
public class MaterialGroup extends Model<MaterialGroup> {
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
   * 分组名
   */
    private String name;
  
}
