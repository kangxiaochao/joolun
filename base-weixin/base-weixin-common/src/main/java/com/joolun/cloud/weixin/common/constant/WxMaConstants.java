package com.joolun.cloud.weixin.common.constant;

import com.joolun.cloud.common.core.constant.CommonConstants;

/**
 * @author
 */
public interface WxMaConstants extends CommonConstants {

	/**
	 * redis中3rd_session过期时间(单位：小时)
	 */
	long TIME_OUT_SESSION = 6;
	/**
	 * redis中3rd_session拼接前缀
	 */
	String THIRD_SESSION_BEGIN = "wx:ma:3rd_session";
}
