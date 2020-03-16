package com.joolun.cloud.codegen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 数据源表
 *
 * @author
 */
@Data
@TableName("sys_datasource")
@EqualsAndHashCode(callSuper = true)
public class SysDatasource extends Model<SysDatasource> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * jdbcurl
	 */
	private String url;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 删除标记
	 */
	private String delFlag;
	/**
	 * 租户ID
	 */
	private String tenantId;

}
