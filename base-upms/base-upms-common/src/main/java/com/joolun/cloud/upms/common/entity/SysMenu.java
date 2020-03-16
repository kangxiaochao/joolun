package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 菜单名称
	 */
	@NotNull(message = "菜单名称不能为空")
	private String name;
	/**
	 * 菜单权限标识
	 */
	private String permission;
	/**
	 * 父菜单ID
	 */
	@NotNull(message = "菜单父ID不能为空")
	private String parentId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * VUE页面
	 */
	private String component;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@NotNull(message = "菜单类型不能为空")
	private String type;
	/**
	 * 路由缓冲
	 */
	private String keepAlive;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 0--正常 1--删除
	 */
	private String delFlag;
	/**
	 * 前端URL
	 */
	private String path;

	@TableField(exist = false)
	private String roleId;
}
