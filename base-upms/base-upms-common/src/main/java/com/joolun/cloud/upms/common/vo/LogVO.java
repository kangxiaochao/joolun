package com.joolun.cloud.upms.common.vo;

import com.joolun.cloud.upms.common.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class LogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private SysLog sysLog;
	private String username;
}
