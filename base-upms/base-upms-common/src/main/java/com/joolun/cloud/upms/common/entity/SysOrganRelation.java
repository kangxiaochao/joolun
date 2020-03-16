package com.joolun.cloud.upms.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 机构关系表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOrganRelation extends Model<SysOrganRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	private String ancestor;
	/**
	 * 后代节点
	 */
	private String descendant;

}
