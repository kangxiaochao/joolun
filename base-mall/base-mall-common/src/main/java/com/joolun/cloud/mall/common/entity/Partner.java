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
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * 合伙人表
 *
 * @author code generator
 * @date 2020-03-23 09:17:24
 */
@Data
@TableName("partner")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "合伙人表")
public class Partner extends Model<Partner> {
    private static final long serialVersionUID=1L;

    /**
     * 公众号代理商id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 代理商公众号名称
     */
    private String partnerName;
    /**
     * 代理商名称
     */
    private String agentName;
    /**
     * 公众号的appid
     */
    private String appId;
    /**
     * 公众号代理商对应的用户编码
     */
    private Integer userCode;
    /**
     * 代理商手机号
     */
    private String phone;
    /**
     * 商品利润的佣金分配比例
     */
    private Integer commissionRate;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private String creator;
	@TableField(exist = false)
    private List<Commission> commission;
}
