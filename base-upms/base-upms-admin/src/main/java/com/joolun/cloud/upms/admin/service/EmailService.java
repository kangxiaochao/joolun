package com.joolun.cloud.upms.admin.service;

import com.joolun.cloud.common.core.util.R;

/**
 * @author
 */
public interface EmailService {
	/**
	 * 发送邮件
	 *
	 * @param to
	 * @param title
	 * @param content
	 * @return R
	 */
	R<Boolean> sendEmail(String to, String title, String content);
}
