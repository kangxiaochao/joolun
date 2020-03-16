package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author
 */
@Data
public class SysUser extends Model<SysUser> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 邮箱
	 */
	private String email;

	private String password;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	private String salt;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;

	/**
	 * 锁定标记
	 */
	private String lockFlag;

	/**
	 * 简介
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 机构ID
	 */
	private String organId;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 码云唯一标识
	 */
	private String giteeLogin;

	/**
	 * 开源中国唯一标识
	 */
	private String oscId;
}
