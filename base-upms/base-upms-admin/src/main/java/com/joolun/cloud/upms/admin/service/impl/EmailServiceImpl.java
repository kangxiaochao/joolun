package com.joolun.cloud.upms.admin.service.impl;

import com.joolun.cloud.upms.admin.config.EmailConfigProperties;
import com.joolun.cloud.upms.admin.service.EmailService;
import com.joolun.cloud.common.core.util.R;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author
 * <p>
 * 邮箱登录相关业务实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final TaskExecutor taskExecutor;
	private final EmailConfigProperties emailConfigProperties;


	/**
	 * 发送邮件
	 * @param
	 * @return code
	 */
	@Override
	public R<Boolean> sendEmail(String to, String title, String content) {
		String text = content;
		String from = emailConfigProperties.getSiteName();
		taskExecutor.execute(() -> {
			try {
				OhMyEmail.subject(title)
						.from(from)
						.to(to)
						.html(text)
						.send();
				log.info("email: {} send success", to);
			} catch (SendMailException e) {
				log.error("邮件发送出错：{}", to);
			}
		});
		return R.ok();
	}
}
