package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 系统社交登录账号表
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysThirdParty extends Model<SysThirdParty> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId
	private String id;
	/**
	 * 类型
	 */
	@NotNull(message = "类型不能为空")
	private String type;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * appid
	 */
	@NotNull(message = "账号不能为空")
	private String appId;
	/**
	 * app_secret
	 */
	@NotNull(message = "密钥不能为空")
	private String appSecret;
	/**
	 * 回调地址
	 */
	private String redirectUrl;
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
	@TableLogic
	private String delFlag;

}
