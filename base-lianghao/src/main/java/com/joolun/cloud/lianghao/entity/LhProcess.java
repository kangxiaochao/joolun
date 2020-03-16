/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.lianghao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 开卡流程描述
 *
 * @author code generator
 * @date 2019-11-30 11:49:30
 */
@Data
@TableName("lh_process")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "开卡流程描述")
public class LhProcess extends Model<LhProcess> {
    private static final long serialVersionUID=1L;

/**
 * 
 */
        @TableId(type = IdType.ASSIGN_UUID)
        private String id;
/**
 * 开卡流程名称
 */
        private String name;
/**
 * 运营商
 */
        private String operator;
/**
 * 开卡流程描述
 */
        private String description;

}
