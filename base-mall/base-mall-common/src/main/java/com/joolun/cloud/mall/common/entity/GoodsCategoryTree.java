/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.common.entity;

import com.joolun.cloud.upms.common.dto.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 商品类目
 *
 * @author JL
 * @date 2019-08-12 11:46:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsCategoryTree  extends TreeNode {

    /**
   * 所属租户
   */
    private String tenantId;
    /**
   * 父分类编号
   */
    private String parentId;
    /**
   * 名称
   */
    private String name;
    /**
   * 描述
   */
    private String description;
    /**
   * 图片
   */
    private String picUrl;
    /**
   * 排序
   */
    private Integer sort;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 最后更新时间
   */
    private LocalDateTime updateTime;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
    private String delFlag;
  
}
