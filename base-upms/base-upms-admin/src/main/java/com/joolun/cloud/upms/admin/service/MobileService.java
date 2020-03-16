package com.joolun.cloud.upms.admin.service;

import com.joolun.cloud.common.core.util.R;

/**
 * @author
 */
public interface MobileService {
	/**
	 * 发送手机验证码
	 *
	 * @param mobile mobile
	 * @return code
	 */
	R<Boolean> sendSmsCode(String mobile);
}
