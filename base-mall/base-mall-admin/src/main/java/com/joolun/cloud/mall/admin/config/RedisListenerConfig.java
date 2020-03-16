package com.joolun.cloud.mall.admin.config;
import com.joolun.cloud.mall.admin.listener.RedisKeyExpirationListener;
import com.joolun.cloud.mall.admin.service.OrderInfoService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Author JL
 */
@Configuration
@AllArgsConstructor
public class RedisListenerConfig {

	private final RedisTemplate<String, String> redisTemplate;
	private final RedisConfigProperties redisConfigProperties;
	private final OrderInfoService orderInfoService;

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(new RedisKeyExpirationListener(redisTemplate, redisConfigProperties, orderInfoService), new PatternTopic("__keyevent@" + redisConfigProperties.getDatabase() + "__:expired"));
		return container;
	}
}

