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
 * 号卡套餐
 *
 * @author code generator
 * @date 2019-11-07 14:48:20
 */
@Data
@TableName("lh_package")
@Excel("lh_package")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "号卡套餐")
public class LhPackage extends Model<LhPackage> {
  private static final long serialVersionUID = 1L;

    /**
   * 套餐id
   */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
   * 套餐名
   */
	@TableField("package_name")
	@ExcelField(value = "套餐名")
    private String packageName;
    /**
   * 运营商ID
   */
	@TableField("operator_id")
	@ExcelField(value = "运营商名称",comment = "运营商名称应与运营商管理中的名称相同，否则会添加失败")
    private String operatorId;
    /**
   * 号卡类型
   */
	@TableField("card_type")
	@ExcelField(value = "号卡类型")
    private String cardType;
    /**
   * 预存话费
   */
	@TableField("prestore")
	@ExcelField(value = "预存话费")
    private Integer prestore;
    /**
   * 最低消费
   */
	@TableField("min_charge")
	@ExcelField(value = "最低消费")
    private Integer minCharge;
    /**
   * 月租
   */
	@TableField("monthly_rent")
	@ExcelField(value = "月租")
    private String monthlyRent;
    /**
   * 必选包
   */
	@TableField("will_choose_package")
	@ExcelField(value = "必选包")
    private String willChoosePackage;
    /**
   * 国内语音
   */
	@TableField("domestic_voice")
	@ExcelField(value = "国内语音")
    private String domesticVoice;
    /**
   * 流量
   */
	@TableField("flow")
	@ExcelField(value = "流量")
    private String flow;
    /**
   * 短信
   */
	@TableField("note")
	@ExcelField(value = "短信")
    private String note;
    /**
   * 来电显示
   */
	@TableField("caller")
	@ExcelField(value = "来电显示")
    private String caller;
    /**
   * 流量日租包
   */
	@TableField("mini_daily_flow")
	@ExcelField(value = "流量日租包")
    private String miniDailyFlow;
    /**
   * 套餐描述
   */
	@TableField("package_des")
	@ExcelField(value = "套餐描述")
    private String packageDes;
    /**
   * 备注
   */
	@TableField("remark")
	@ExcelField(value = "备注")
    private String remark;
  
}
