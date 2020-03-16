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
        import java.math.BigDecimal;
    import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;

/**
 * 运费模板
 *
 * @author JL
 * @date 2019-12-24 16:09:31
 */
@Data
@TableName("freight_templat")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "运费模板")
public class FreightTemplat extends Model<FreightTemplat> {
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
     * 排序字段
     */
    private Integer sort;
    /**
     * 名称
     */
    private String name;
    /**
     * 模板类型1、买家承担运费；2、卖家包邮
     */
    private String type;
    /**
     * 计费方式1、按件数；2、按重量；3、按体积
     */
    private String chargeType;
    /**
     * 首件、首体积、首重量
     */
    private BigDecimal firstNum;
    /**
     * 首运费
     */
    private BigDecimal firstFreight;
    /**
     * 续件、续体积、续重量
     */
    private BigDecimal continueNum;
    /**
     * 续运费
     */
    private BigDecimal continueFreight;

}
