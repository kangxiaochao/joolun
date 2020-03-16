/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.joolun.cloud.weixin.common.constant.WebSocketConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author JL
 * <p>
 * WebSocket配置类
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private RemoteTokenServices tokenService;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		log.info("WebSocket服务器注册");
		registry.addEndpoint("/ws")
				.setAllowedOrigins("*")
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		log.info("WebSocket服务器启动");
		registry.setUserDestinationPrefix(WebSocketConstant.USER_DESTINATION_PREFIX);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				// 判断是否首次连接请求
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String tokens = accessor.getFirstNativeHeader("Authorization");
					log.info("webSocket token is {}", tokens);
					if (StrUtil.isBlank(tokens)) {
						return null;
					}
					// 验证令牌信息
					OAuth2Authentication auth2Authentication = tokenService.loadAuthentication(tokens.split(" ")[1]);
					if (ObjectUtil.isNotNull(auth2Authentication)) {
						SecurityContextHolder.getContext().setAuthentication(auth2Authentication);
						accessor.setUser(() -> auth2Authentication.getName());
						return message;
					} else {
						return null;
					}
				}
				//不是首次连接，已经成功登陆
				return message;
			}
		});
	}
}