package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOrgan extends Model<SysOrgan> {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 机构名称
	 */
	@NotNull(message = "机构名称不能为空")
	private String name;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空")
	private Integer sort;
	/**
	 * 机构编码
	 */
	@NotNull(message = "机构编码不能为空")
	private String code;
	/**
	 * 机构类型
	 */
	@NotNull(message = "机构类型不能为空")
	private String type;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

	private String parentId;

	/**
	 * 是否删除  -1：已删除  0：正常
	 */
//	@TableLogic
	private String delFlag;

}
