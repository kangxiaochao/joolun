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
 * 合伙人佣金规格表
 *
 * @author code generator
 * @date 2020-03-24 10:44:30
 */
@Data
@TableName("commission")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "合伙人佣金规格表")
public class Commission extends Model<Commission> {
    private static final long serialVersionUID=1L;

    /**
     * 佣金返利id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 合伙人id
     */
    private String partnerId;
    /**
     * 是否开启代理商（1：开启，0：关闭）
     */
    private String distributionStatus;
    /**
     * 分销级别（0:默认佣金比例，1：一级代理商，2：二级代理商，3：三级代理商）
     */
    private String distributionLevel;
    /**
     * 一级代理商佣金返利
     */
    private Integer primaryAgentCommission;
    /**
     * 二级代理商佣金返利
     */
    private Integer secondaryAgentCommission;
    /**
     * 三级代理商佣金返利
     */
    private Integer tertiaryAgentCommission;

}
