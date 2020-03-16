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
 * @author code generator
 * @date 2019-11-21 10:46:14
 */
@Data
@TableName("lh_number")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "")
public class LhNumber extends Model<LhNumber> {
	private static final long serialVersionUID = 1L;

	/**
	 * 号码ID
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 号段
	 */
	private String sectionNo;
	/**
	 * 手机号
	 */
	private String number;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 *
	 */
	private Integer status;
	/**
	 * 运营商ID
	 */
	private String operatorId;
	/**
	 * 运营商名称
	 */
	private String operatorName;
	/**
	 * 套餐ID
	 */
	private String packageId;
	/**
	 * 套餐名称
	 */
	private String packageName;

}
