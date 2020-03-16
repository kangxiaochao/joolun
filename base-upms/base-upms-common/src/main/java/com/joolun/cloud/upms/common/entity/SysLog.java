package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author
 */
@Data
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 日志类型
	 */
	@NotNull(message = "日志类型不能为空")
	private String type;
	/**
	 * 日志标题
	 */
	@NotNull(message = "日志标题不能为空")
	private String title;
	/**
	 * 创建者
	 */
	private String createId;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 操作IP地址
	 */
	private String remoteAddr;
	/**
	 * 用户代理
	 */
	private String userAgent;
	/**
	 * 请求URI
	 */
	private String requestUri;
	/**
	 * 操作方式
	 */
	private String method;
	/**
	 * 操作提交的数据
	 */
	private String params;
	/**
	 * 执行时间
	 */
	private Long time;

	/**
	 * 异常信息
	 */
	private String exception;

	/**
	 * 服务ID
	 */
	private String serviceId;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;



}
