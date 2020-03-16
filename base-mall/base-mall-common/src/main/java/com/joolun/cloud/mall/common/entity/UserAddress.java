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
 * 用户收货地址
 *
 * @author JL
 * @date 2019-09-11 14:28:59
 */
@Data
@TableName("user_address")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户收货地址")
public class UserAddress extends Model<UserAddress> {
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
   * 收货人名字
   */
    private String userName;
    /**
   * 邮编
   */
    private String postalCode;
    /**
   * 省名
   */
    private String provinceName;
    /**
   * 市名
   */
    private String cityName;
    /**
   * 区名
   */
    private String countyName;
    /**
   * 详情地址
   */
    private String detailInfo;
    /**
   * 电话号码
   */
    private String telNum;
    /**
   * 是否默认 1是0否
   */
    private String isDefault;
  
}
