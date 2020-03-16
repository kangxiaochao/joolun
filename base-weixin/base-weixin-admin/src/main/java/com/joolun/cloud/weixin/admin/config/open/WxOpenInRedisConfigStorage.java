/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.config.open;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

/**
 * @author JL
 */
public class WxOpenInRedisConfigStorage extends WxOpenInMemoryConfigStorage {
	private final static String COMPONENT_VERIFY_TICKET_KEY = "wxopen:component_verify_ticket:";
	private final static String COMPONENT_ACCESS_TOKEN_KEY = "wxopen:component_access_token:";

	private final static String AUTHORIZER_REFRESH_TOKEN_KEY = "wxopen:refresh_token:";
	private final static String AUTHORIZER_ACCESS_TOKEN_KEY = "wxopen:access_token:";
	private final static String JSAPI_TICKET_KEY = "wxopen:jsapi_ticket:";
	private final static String CARD_API_TICKET_KEY = "wxopen:card_api_ticket:";

	private final RedisTemplate<String, String> redisTemplate;

	public WxOpenInRedisConfigStorage(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	private String componentVerifyTicketKey;
	private String componentAccessTokenKey;
	private String authorizerRefreshTokenKey;
	private String authorizerAccessTokenKey;
	private String jsapiTicketKey;
	private String cardApiTicket;

	@Override
	public void setComponentAppId(String componentAppId) {
		super.setComponentAppId(componentAppId);
		componentVerifyTicketKey = COMPONENT_VERIFY_TICKET_KEY.concat(componentAppId);
		componentAccessTokenKey = COMPONENT_ACCESS_TOKEN_KEY.concat(componentAppId);
		authorizerRefreshTokenKey = AUTHORIZER_REFRESH_TOKEN_KEY.concat(componentAppId);
		authorizerAccessTokenKey = AUTHORIZER_ACCESS_TOKEN_KEY.concat(componentAppId);
		this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(componentAppId);
		this.cardApiTicket = CARD_API_TICKET_KEY.concat(componentAppId);
	}

	@Override
	public String getComponentVerifyTicket() {
		return redisTemplate.opsForValue().get(this.componentVerifyTicketKey);
	}

	@Override
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		redisTemplate.opsForValue().set(this.componentVerifyTicketKey, componentVerifyTicket);
	}

	@Override
	public String getComponentAccessToken() {
		return redisTemplate.opsForValue().get(this.componentAccessTokenKey);
	}

	@Override
	public boolean isComponentAccessTokenExpired() {
		return redisTemplate.getExpire(this.componentAccessTokenKey) < 2;
	}

	@Override
	public void expireComponentAccessToken(){
		redisTemplate.expire(this.componentAccessTokenKey, 0, TimeUnit.SECONDS);
	}

	@Override
	public void updateComponentAccessToken(String componentAccessToken, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.componentAccessTokenKey, componentAccessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	private String getKey(String prefix, String appId) {
		return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
	}

	@Override
	public String getAuthorizerRefreshToken(String appId) {
		String authorizerRefreshTokenKey = redisTemplate.opsForValue().get(this.getKey(this.authorizerRefreshTokenKey, appId));
		if(StringUtils.isBlank(authorizerRefreshTokenKey)){//redis未缓存RefreshToken，接口获取并缓存
			try {
				WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = WxOpenConfiguration.getOpenService().getWxOpenComponentService().getAuthorizerInfo(appId);
				this.setAuthorizerRefreshToken(appId, wxOpenAuthorizerInfoResult.getAuthorizationInfo().getAuthorizerRefreshToken());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		return redisTemplate.opsForValue().get(this.getKey(this.authorizerRefreshTokenKey, appId));
	}

	@Override
	public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
		redisTemplate.opsForValue().set(this.getKey(this.authorizerRefreshTokenKey, appId), authorizerRefreshToken);
	}

	@Override
	public String getAuthorizerAccessToken(String appId) {
		return redisTemplate.opsForValue().get(this.getKey(this.authorizerAccessTokenKey, appId));
	}

	@Override
	public boolean isAuthorizerAccessTokenExpired(String appId) {
		return redisTemplate.getExpire(this.getKey(this.authorizerAccessTokenKey, appId)) < 2;
	}

	@Override
	public void expireAuthorizerAccessToken(String appId) {
		redisTemplate.expire(this.getKey(this.authorizerAccessTokenKey, appId), 0, TimeUnit.SECONDS);
	}

	@Override
	public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.getKey(this.authorizerAccessTokenKey, appId), authorizerAccessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	@Override
	public String getJsapiTicket(String appId) {
		return redisTemplate.opsForValue().get(this.getKey(this.jsapiTicketKey, appId));
	}

	@Override
	public boolean isJsapiTicketExpired(String appId) {
		return redisTemplate.getExpire(this.getKey(this.jsapiTicketKey, appId)) < 2;
	}

	@Override
	public void expireJsapiTicket(String appId) {
		redisTemplate.expire(this.getKey(this.jsapiTicketKey, appId), 0, TimeUnit.SECONDS);
	}

	@Override
	public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.getKey(this.jsapiTicketKey, appId), jsapiTicket, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	@Override
	public String getCardApiTicket(String appId) {
		return redisTemplate.opsForValue().get(this.getKey(this.cardApiTicket, appId));
	}

	@Override
	public boolean isCardApiTicketExpired(String appId) {
		return redisTemplate.getExpire(this.getKey(this.cardApiTicket, appId)) < 2;
	}

	@Override
	public void expireCardApiTicket(String appId) {
		redisTemplate.expire(this.getKey(this.cardApiTicket, appId), 0, TimeUnit.SECONDS);
	}

	@Override
	public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.getKey(this.cardApiTicket, appId), cardApiTicket, expiresInSeconds - 200, TimeUnit.SECONDS);
	}
}