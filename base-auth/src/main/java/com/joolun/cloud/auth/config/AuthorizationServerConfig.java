package com.joolun.cloud.auth.config;

import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.security.component.BaseWebResponseExceptionTranslator;
import com.joolun.cloud.common.security.service.RedisClientDetailsService;
import com.joolun.cloud.common.security.entity.BaseUser;
import com.joolun.cloud.common.security.service.BaseUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * 认证服务器配置
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	private final DataSource dataSource;
	private final BaseUserDetailsService baseUserDetailsService;
	private final AuthenticationManager authenticationManagerBean;
	private final RedisConnectionFactory redisConnectionFactory;

	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				.allowFormAuthenticationForClients()
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				.tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancer())
				.userDetailsService(baseUserDetailsService)
				.authenticationManager(authenticationManagerBean)
				.reuseRefreshTokens(false)
				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
				.exceptionTranslator(new BaseWebResponseExceptionTranslator());
	}


	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstants.BASE_PREFIX + SecurityConstants.OAUTH_PREFIX);
		tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
			@Override
			public String extractKey(OAuth2Authentication authentication) {
				return super.extractKey(authentication) + ":" + TenantContextHolder.getTenantId();
			}
		});
		return tokenStore;
	}

	/**
	 * token增强，客户端模式不增强。
	 *
	 * @return TokenEnhancer
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			if (SecurityConstants.CLIENT_CREDENTIALS
					.equals(authentication.getOAuth2Request().getGrantType())) {
				return accessToken;
			}

			final Map<String, Object> additionalInfo = new HashMap<>(8);
			BaseUser baseUser = (BaseUser) authentication.getUserAuthentication().getPrincipal();
			additionalInfo.put(SecurityConstants.DETAILS_USER_ID, baseUser.getId());
			additionalInfo.put(SecurityConstants.DETAILS_USERNAME, baseUser.getUsername());
			additionalInfo.put(SecurityConstants.DETAILS_ORGAN_ID, baseUser.getOrganId());
			additionalInfo.put(SecurityConstants.DETAILS_TENANT_ID, baseUser.getTenantId());
			additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.BASE_LICENSE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}
