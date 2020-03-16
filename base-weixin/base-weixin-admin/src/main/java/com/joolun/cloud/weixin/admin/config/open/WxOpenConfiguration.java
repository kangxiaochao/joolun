/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.config.open;

import com.joolun.cloud.weixin.admin.handler.LocationHandler;
import com.joolun.cloud.weixin.admin.handler.MassMsgHandler;
import com.joolun.cloud.weixin.admin.handler.MenuHandler;
import com.joolun.cloud.weixin.admin.handler.MsgHandler;
import com.joolun.cloud.weixin.admin.handler.StoreCheckNotifyHandler;
import com.joolun.cloud.weixin.admin.handler.SubscribeHandler;
import com.joolun.cloud.weixin.admin.handler.UserActivateCardHandler;
import com.joolun.cloud.weixin.admin.handler.UserGetCardHandler;
import com.joolun.cloud.weixin.admin.handler.KfSessionHandler;
import com.joolun.cloud.weixin.admin.handler.LogHandler;
import com.joolun.cloud.weixin.admin.handler.NullHandler;
import com.joolun.cloud.weixin.admin.handler.UnSubscribeHandler;
import com.joolun.cloud.weixin.admin.handler.UserDelCardHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * 第三方平台Configuration
 * @author JL
 *
 */
@Slf4j
@Configuration
public class WxOpenConfiguration {
	private static WxOpenMessageRouter newRouter;
	private static WxOpenService wxOpenService;

	public static WxOpenService getOpenService(){
		return wxOpenService;
	}

	private static RedisTemplate redisTemplate;
	private static WxComponentConfigProperties wxComponentConfigProperties;
	private static LogHandler logHandler;
	private static NullHandler nullHandler;
	private static KfSessionHandler kfSessionHandler;
	private static StoreCheckNotifyHandler storeCheckNotifyHandler;
	private static LocationHandler locationHandler;
	private static MenuHandler menuHandler;
	private static MsgHandler msgHandler;
	private static UnSubscribeHandler unSubscribeHandler;
	private static SubscribeHandler subscribeHandler;
	private static MassMsgHandler massMsgHandler;
	private static UserGetCardHandler userGetCardHandler;
	private static UserDelCardHandler userDelCardHandler;
	private static UserActivateCardHandler userActivateCardHandler;

	public WxOpenConfiguration(LogHandler logHandler, NullHandler nullHandler
			, KfSessionHandler kfSessionHandler, StoreCheckNotifyHandler storeCheckNotifyHandler
			, LocationHandler locationHandler, MenuHandler menuHandler
			, MsgHandler msgHandler, UnSubscribeHandler unSubscribeHandler
			, SubscribeHandler subscribeHandler,  MassMsgHandler massMsgHandler
			, UserGetCardHandler userGetCardHandler,UserDelCardHandler userDelCardHandler
			, UserActivateCardHandler userActivateCardHandler
			, RedisTemplate redisTemplate,WxComponentConfigProperties wxComponentConfigProperties){
		this.logHandler = logHandler;
		this.nullHandler = nullHandler;
		this.kfSessionHandler = kfSessionHandler;
		this.storeCheckNotifyHandler = storeCheckNotifyHandler;
		this.locationHandler = locationHandler;
		this.menuHandler = menuHandler;
		this.msgHandler = msgHandler;
		this.unSubscribeHandler = unSubscribeHandler;
		this.subscribeHandler = subscribeHandler;
		this.massMsgHandler = massMsgHandler;
		this.userGetCardHandler = userGetCardHandler;
		this.userDelCardHandler = userDelCardHandler;
		this.userActivateCardHandler = userActivateCardHandler;
		this.redisTemplate = redisTemplate;
		this.wxComponentConfigProperties = wxComponentConfigProperties;
	}
	@PostConstruct
	public void init() {
		wxOpenService = new WxOpenServiceImpl();
		WxOpenInRedisConfigStorage wxOpenInRedisConfigStorage = new WxOpenInRedisConfigStorage(redisTemplate);
		wxOpenInRedisConfigStorage.setComponentAppId(wxComponentConfigProperties.getAppId());
		wxOpenInRedisConfigStorage.setComponentAppSecret(wxComponentConfigProperties.getAppSecret());
		wxOpenInRedisConfigStorage.setComponentToken(wxComponentConfigProperties.getToken());
		wxOpenInRedisConfigStorage.setComponentAesKey(wxComponentConfigProperties.getAesKey());

		wxOpenService.setWxOpenConfigStorage(wxOpenInRedisConfigStorage);
		newRouter = new WxOpenMessageRouter(wxOpenService);
		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(logHandler).next();

		// 接收客服会话管理事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
				.handler(kfSessionHandler).end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
				.handler(kfSessionHandler)
				.end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
				.handler(kfSessionHandler).end();

		// 门店审核事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.POI_CHECK_NOTIFY)
				.handler(storeCheckNotifyHandler).end();

		// 自定义菜单事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.MenuButtonType.CLICK).handler(menuHandler).end();

		// 点击菜单连接事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.MenuButtonType.VIEW).handler(menuHandler).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.SCANCODE_WAITMSG).handler(menuHandler).end();

		// 关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler)
				.end();

		// 取消关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.UNSUBSCRIBE)
				.handler(unSubscribeHandler).end();

		// 上报地理位置事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.LOCATION).handler(locationHandler)
				.end();

		// 卡券领取事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.CARD_USER_GET_CARD).handler(userGetCardHandler).end();

		// 卡券删除事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.CARD_USER_DEL_CARD).handler(userDelCardHandler).end();

		// 卡券激活事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.CARD_SUBMIT_MEMBERCARD_USER_INFO).handler(userActivateCardHandler).end();

		// 群发回调事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.MASS_SEND_JOB_FINISH).handler(massMsgHandler).end();

		// 默认
		newRouter.rule().async(false).handler(msgHandler).end();

	}
	public static WxOpenMessageRouter getWxOpenMessageRouter(){
		return newRouter;
	}
}
