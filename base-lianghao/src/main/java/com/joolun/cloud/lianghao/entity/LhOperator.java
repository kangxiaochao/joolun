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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 运营商
 *
 * @author xxz
 * @date 2019-11-07 15:34:41
 */
@Data
@TableName("lh_operator")
@Excel("lh_operator")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "运营商")
public class LhOperator extends Model<LhOperator> {
  private static final long serialVersionUID = 1L;

    /**
   * 运营商id
   */
	@TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    /**
   * 运营商名称
   */
	@TableField("operator_name")
	@ExcelField(value = "运营商名称")
    private String operatorName;
    /**
   * 运营商简称
   */
	@TableField("short_name")
	@ExcelField(value = "运营商简称")
    private String shortName;
    /**
   * 联系电话
   */
	@TableField("telephone")
	@ExcelField(value = "联系电话")
    private String telephone;
    /**
   * 类型(虚商或三网)
   */
	@TableField("type")
	@ExcelField(value = "类型(虚商或三网)")
    private String type;
    /**
   * 开卡方式
   */
	@TableField("open_card_way")
	@ExcelField(value = "开卡方式")
    private String openCardWay;
    /**
   * 开卡描述
   */
	@TableField("open_card_description")
	@ExcelField(value = "开卡描述")
    private String openCardDescription;
    /**
   * 备注
   */
	@TableField("remark")
	@ExcelField(value = "备注")
    private String remark;
  
}
