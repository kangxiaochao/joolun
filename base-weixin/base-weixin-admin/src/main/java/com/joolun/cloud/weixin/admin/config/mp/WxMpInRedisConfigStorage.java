/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.config.mp;

import java.util.concurrent.TimeUnit;

import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.data.redis.core.RedisTemplate;
import me.chanjar.weixin.mp.enums.TicketType;

/**
 * 基于Redis的微信配置provider.
 * 
 * @author jl
 */
@SuppressWarnings("serial")
public class WxMpInRedisConfigStorage extends WxMpDefaultConfigImpl {

	public static final String ACCESS_TOKEN_KEY = "wx:admin:access_token:";

	public final static String JSAPI_TICKET_KEY = "wx:admin:jsapi_ticket:";

	public final static String CARDAPI_TICKET_KEY = "wx:admin:cardapi_ticket:";

	private final RedisTemplate<String, String> redisTemplate;

	public WxMpInRedisConfigStorage(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private String accessTokenKey;

	private String jsapiTicketKey;

	private String cardapiTicketKey;

	/**
	 * 每个公众号生成独有的存储key.
	 */
	@Override
	public void setAppId(String appId) {
		super.setAppId(appId);
		this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
		this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
		this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appId);
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	private String getTicketRedisKey(TicketType type) {
		return String.format("wx:admin:ticket:key:%s:%s", this.appId, type.getCode());
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getAccessToken() {
		return redisTemplate.opsForValue().get(this.accessTokenKey);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public boolean isAccessTokenExpired() {
		return redisTemplate.getExpire(accessTokenKey) < 2;
	}

	/**
	 *
	 * @param accessToken
	 * @param expiresInSeconds
	 */
	@Override
	public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.accessTokenKey, accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	/**
	 *
	 */
	@Override
	public void expireAccessToken() {
		redisTemplate.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	@Override
	public String getTicket(TicketType type) {
		return redisTemplate.opsForValue().get(this.getTicketRedisKey(type));
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	@Override
	public boolean isTicketExpired(TicketType type) {
		return redisTemplate.getExpire(this.getTicketRedisKey(type)) < 2;
	}

	/**
	 *
	 * @param type
	 * @param jsapiTicket
	 * @param expiresInSeconds
	 */
	@Override
	public synchronized void updateTicket(TicketType type, String jsapiTicket, int expiresInSeconds) {
		redisTemplate.opsForValue().set(this.getTicketRedisKey(type), jsapiTicket, expiresInSeconds - 200, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @param type
	 */
	@Override
	public void expireTicket(TicketType type) {
		redisTemplate.expire(this.getTicketRedisKey(type), 0, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getJsapiTicket() {
		return redisTemplate.opsForValue().get(this.jsapiTicketKey);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getCardApiTicket() {
		return redisTemplate.opsForValue().get(cardapiTicketKey);
	}
}
