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
import io.swagger.annotations.ApiModel;

/**
 * 商城通知详情
 *
 * @author JL
 * @date 2019-11-09 19:39:03
 */
@Data
@TableName("notice_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "商城通知详情")
public class NoticeItem extends Model<NoticeItem> {
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
   * 创建者ID
   */
    private String createId;
    /**
   * 通知ID
   */
    private String noticeId;
    /**
   * 类型1、图片；2、视频；3、文字
   */
    private String type;
    /**
   * 通知名
   */
    private String name;
    /**
   * 通知链接
   */
    private String url;
    /**
   * 跳转页面
   */
    private String page;
    /**
   * 内容
   */
    private String content;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * （1：开启；0：关闭）
	 */
	private String enable;
	/**
	 * 排序字段
	 */
	private Integer sort;

	@TableField(exist = false)
	private String appId;

	@TableField(exist = false)
	private String noticeType;
}
