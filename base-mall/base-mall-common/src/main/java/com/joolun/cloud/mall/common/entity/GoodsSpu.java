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
import com.joolun.cloud.common.data.mybatis.typehandler.ArrayStringTypeHandler;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import com.joolun.cloud.mall.common.dto.SpuSpecDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import org.apache.ibatis.type.JdbcType;

/**
 * spu商品
 *
 * @author JL
 * @date 2019-08-12 16:25:10
 */
@Data
@TableName("goods_spu")
@Excel("goods_spu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "spu商品")
public class GoodsSpu extends Model<GoodsSpu> {
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
   * spu编码
   */
    private String spuCode;
    /**
   * spu名字
   */
	@TableField("name")
	@ExcelField(value = "手机号")
    private String name;
    /**
	 * 一级分类ID
	 */
    private String categoryFirst;
	/**
	 * 二级分类ID
	 */
	private String categorySecond;
	/**
	 *名称类型（根据名称类型在“goods_category”表中查询出一级与二级的分类ID）
	 */
	@TableField(exist = false)
	@ExcelField(value = "号卡类型",comment ="(列：AAAA、ABAB、ABCD),添加前请核对平台是否有当前类型")
	private String nameType;

	/**
	 * 归属省份
	 * */
	@TableField("attribution_province")
	@ExcelField(value = "归属省份",comment ="(列：山东、北京、上海)，省份中不包含省或市")
	private String attributionProvince;

	/**
	 * 归属城市
	 * */
	@TableField("attribution_city")
	@ExcelField(value = "归属城市",comment ="(列：济南、菏泽、青岛)，城市中不包含市或区")
	private String attributionCity;
	/**
	 * 制式
	 */
	@TableField("phone_system")
	@ExcelField(value = "制式",comment ="(移动、联通、电信),三选一")
	private String phoneSystem;
	/**
	 * 低消
	 */
	@TableField("low_dissipation")
	@ExcelField(value = "低消")
	private String lowDissipation;
	/**
	 * 运营商ID
	 * */
	private String operatorId;

	/**
	 * 运营商名称
	 * */
	@TableField("operator_name")
	@ExcelField(value = "运营商",comment ="请确保该运营商已存在于平台中且添加名称与平台中对应运营商名称相同，否则运营商将关联失败")
	private String operatorName;

	/**
	 * 套餐ID
	 * */
	private String packageId;

	/**
	 * 套餐名称
	 * */
	@TableField("package_name")
	@ExcelField(value = "套餐",comment ="请确保该套餐已存在于平台中且添加名称与平台中对应套餐名称相同，否则将关联失败")
	private String packageName;

	/**
	 * 开卡方式ID
	 * */
	private String processId;

	/**
	 * 开卡方式
	 * */
	@TableField("process_name")
	@ExcelField(value = "开卡方式",comment ="请确保该开卡方式已存在于平台中且添加名称与平台中对应开卡方式名称相同，否则将关联失败")
	private String processName;


	/**
	 * 卖点
	 */
	@TableField("sell_point")
	@ExcelField(value = "买点")
	private String sellPoint;

    /**
   * 商品主图
   */
//	@TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType= JdbcType.VARCHAR)
    private String[] picUrls;

	/**
	 * 是否上架（0否 1是）
	 * */
	private String shelf;

	 /**
   * 排序字段
   */
    private Integer sort;
	/**
	 * 最低价
	 */
//	@TableField("price_down")
//	@ExcelField(value = "最低价")
	private BigDecimal priceDown;
	/**
	 * 最高价
	 */
//	@TableField("price_up")
//	@ExcelField(value = "最高价")
	private BigDecimal priceUp;

	@TableField(exist = false)
	@ExcelField(value = "销售价")
	private BigDecimal salesPrice;
	@TableField(exist = false)
	@ExcelField(value = "市场价")
	private BigDecimal marketPrice;
	@TableField(exist = false)
	@ExcelField(value = "成本价")
	private BigDecimal costPrice;

	@TableField(exist = false)
	private String sortField;

	@TableField(exist = false)
	private long total;

	@TableField(exist = false)
	private long size;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 销量
	 */
	private Integer saleNum;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 最后更新时间
   */
    private LocalDateTime updateTime;
	/**
	 * 0统一规格；1多规格
	 */
	private String specType;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
    private String delFlag;
	/**
	 *积分赠送开关（1开 0关）
	 */
	private String pointsGiveSwitch;
	/**
	 * 积分赠送数量
	 */
	private Integer pointsGiveNum;
	/**
	 * 积分抵扣开关（1开 0关）
	 */
	private String pointsDeductSwitch;
	/**
	 * 积分抵扣商品金额比例（0~100）
	 */
	private Integer pointsDeductScale;
	/**
	 * 1积分数可抵多少元
	 */
	private BigDecimal pointsDeductAmount;
	/**
	 * 运费模板ID
	 */
	private String freightTemplatId;

	@TableField(exist = false)
	private List<GoodsSku> skus;

	@TableField(exist = false)
	private String collectId;
	/**
	 * 可领电子券
	 */
	@TableField(exist = false)
	private List<CouponInfo> listCouponInfo;

	@TableField(exist = false)
	private List<SpuSpecDTO> spuSpec;

	@TableField(exist = false)
	private FreightTemplat freightTemplat;
}
