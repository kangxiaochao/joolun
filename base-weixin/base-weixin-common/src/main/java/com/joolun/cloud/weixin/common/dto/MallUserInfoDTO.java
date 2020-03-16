package com.joolun.cloud.weixin.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MallUserInfoDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * PK
	 */
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
	 * 用户编码
	 */
	private Integer userCode;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 来源应用类型1、小程序；2、公众号
	 */
	private String appType;
	/**
	 * 来源应用id
	 */
	private String appId;
	/**
	 * 会员等级
	 */
	private Integer vipGrade;
	/**
	 * 当前积分
	 */
	private Integer pointsCurrent;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 性别（1：男，2：女，0：未知）
	 */
	private String sex;
	/**
	 * 头像
	 */
	private String headimgUrl;
}
