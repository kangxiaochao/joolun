/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 微信账号配置
 *
 * @author JL
 * @date 2019-03-23 21:26:35
 */
@Data
@TableName("wx_app")
@EqualsAndHashCode(callSuper = true)
public class WxApp extends Model<WxApp> {
private static final long serialVersionUID = 1L;

    /**
   * 应用ID
   */
    @TableId(type = IdType.INPUT)
	@NotNull(message = "应用ID不能为空")
    private String id;
    /**
   * 创建者
   */
    private String createId;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 更新者
   */
    private String updateId;
    /**
   * 更新时间
   */
    private LocalDateTime updateTime;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
    private String delFlag;
    /**
   * 所属租户
   */
    private String tenantId;
	/**
	 * 机构ID
	 */
	private String organId;
    /**
   * 微信原始标识
   */
    private String weixinSign;
    /**
   * 应用类型(1:小程序，2:公众号)
   */
	@NotNull(message = "应用类型不能为空")
    private String appType;
    /**
   * 应用密钥
   */
    private String secret;
    /**
   * token
   */
    private String token;
    /**
   * EncodingAESKey
   */
    private String aesKey;
    /**
   * 微信号名称
   */
    private String name;
    /**
   * 公众号类型（0：订阅号；1：由历史老帐号升级后的订阅号；2：服务号）
   */
	@NotNull(message = "公众号类型不能为空")
    private String weixinType;
    /**
   * 公众号微信号
   */
    private String weixinHao;
	/**
	 * logo
	 */
	private String logo;
	/**
	 * 二维码
	 */
	private String qrCode;
    /**
   * 微社区URL
   */
    private String community;
	/**
	 * 认证类型
	 * 类型	说明
	 * -1	未认证
	 * 0	微信认证
	 * 1	新浪微博认证
	 * 2	腾讯微博认证
	 * 3	已资质认证通过但还未通过名称认证
	 * 4	已资质认证通过、还未通过名称认证，但通过了新浪微博认证
	 * 5	已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
	 */
	@NotNull(message = "认证类型不能为空")
	private String verifyType;
	/**
	 * 主体名称
	 */
	private String principalName;
    /**
	* 是否第三方平台应用（0：是；1：否）
	*/
	private String isComponent;
    /**
   * 备注信息
   */
    private String remarks;
    /**
   * 绑定的会员卡ID
   */
    private String vipCardId;
	/**
	 * 微信支付商户号
	 */
	private String mchId;
	/**
	 * 微信支付商户密钥
	 */
	private String mchKey;
	/**
	 * p12证书的位置，可以指定绝对路径，也可以指定类路径（以classpath:开头）
	 */
	private String keyPath;
}
