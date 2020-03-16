package com.joolun.cloud.common.log.event;

import com.joolun.cloud.upms.common.entity.SysLog;
import com.joolun.cloud.upms.common.feign.FeignLogService;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
	private final FeignLogService feignLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = event.getSysLog();
		feignLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
	}
}
