/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxMenuService;
import com.joolun.cloud.weixin.admin.service.WxMsgService;
import com.joolun.cloud.weixin.common.entity.WxMenu;
import com.joolun.cloud.weixin.common.entity.WxMsg;
import com.joolun.cloud.weixin.common.constant.WebSocketConstant;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.builder.outxml.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义菜单处理
 * @author JL
 */
@Slf4j
@Component
@AllArgsConstructor
public class MenuHandler extends AbstractHandler {

	private final WxMenuService wxMenuService;
	private final WxUserService wxUserService;
	private final WxAppService wxAppService;
	private final WxMsgService wxMsgService;
	private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
		//消息记录
		WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
		TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
		WxMenu wxMenu = null;
		if(WxConsts.EventType.CLICK.equals(wxMessage.getEvent())
				|| WxConsts.EventType.SCANCODE_WAITMSG.equals(wxMessage.getEvent())){
			wxMenu = wxMenuService.getById(wxMessage.getEventKey());
			if(wxMenu == null){//菜单过期
				return new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("非常抱歉，该菜单已删除！").build();
			}
		}else{
			wxMenu = new WxMenu();
		}
		WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), wxMessage.getFromUser());
		if(wxUser==null){//库中无此用户
			WxMpUser userWxInfo = weixinService.getUserService()
					.userInfo(wxMessage.getFromUser(), null);
			wxUser = new WxUser();
			wxUser.setSubscribeNum(1);
			SubscribeHandler.setWxUserValue(wxApp,wxUser,userWxInfo);
//			wxUser.setTenantId(wxApp.getTenantId());
			wxUserService.save(wxUser);
		}
		//组装菜单回复消息
		return getWxMpXmlOutMessage(wxMessage, wxMenu, wxApp, wxUser);
    }

	/**
	 * 组装菜单回复消息
	 * @param wxMessage
	 * @param wxMenu
	 * @return
	 */
	public WxMpXmlOutMessage getWxMpXmlOutMessage(WxMpXmlMessage wxMessage, WxMenu wxMenu, WxApp wxApp, WxUser wxUser){
		WxMpXmlOutMessage wxMpXmlOutMessage = null;
		//记录接收消息
		WxMsg wxMsg = new WxMsg();
//		wxMsg.setTenantId(wxApp.getTenantId());
		wxMsg.setAppId(wxApp.getId());
		wxMsg.setWxUserId(wxUser.getId());
		wxMsg.setAppName(wxApp.getName());
		wxMsg.setAppLogo(wxApp.getLogo());
		wxMsg.setNickName(wxUser.getNickName());
		wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
		wxMsg.setType(ConfigConstant.WX_MSG_TYPE_1);
		wxMsg.setRepEvent(wxMessage.getEvent());
		wxMsg.setRepType(wxMessage.getMsgType());
		wxMsg.setRepName(wxMenu.getName());
		if(WxConsts.EventType.VIEW.equals(wxMessage.getEvent())){
			wxMsg.setRepUrl(wxMessage.getEventKey());
		}
		if(WxConsts.EventType.SCANCODE_WAITMSG.equals(wxMessage.getEvent())){
			wxMsg.setRepContent(wxMessage.getScanCodeInfo().getScanResult());
		}
		wxMsg.setReadFlag(CommonConstants.NO);
		LocalDateTime now = LocalDateTime.now();
		wxMsg.setCreateTime(now);
		wxMsgService.save(wxMsg);
		//推送websocket
		String destination = WebSocketConstant.USER_DESTINATION_PREFIX + WebSocketConstant.WX_MSG + wxMsg.getWxUserId();
		try {
			simpMessagingTemplate.convertAndSend(destination , JSONUtil.toJsonStr(wxMsg));
		}catch (Exception e){}
		if(WxConsts.MenuButtonType.CLICK.equals(wxMenu.getType())
				|| WxConsts.MenuButtonType.SCANCODE_WAITMSG.equals(wxMenu.getType())){
			//记录回复消息
			wxMsg = new WxMsg();
//			wxMsg.setTenantId(wxApp.getTenantId());
			wxMsg.setAppId(wxApp.getId());
			wxMsg.setWxUserId(wxUser.getId());
			wxMsg.setAppName(wxApp.getName());
			wxMsg.setAppLogo(wxApp.getLogo());
			wxMsg.setNickName(wxUser.getNickName());
			wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
			wxMsg.setCreateTime(now.plusSeconds(1));
			wxMsg.setType(ConfigConstant.WX_MSG_TYPE_2);
			wxMsg.setRepType(wxMenu.getRepType());
			if(WxConsts.KefuMsgType.TEXT.equals(wxMenu.getRepType())){
				wxMsg.setRepContent(wxMenu.getRepContent());
				wxMpXmlOutMessage = new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content(wxMenu.getRepContent()).build();
			}
			if(WxConsts.KefuMsgType.IMAGE.equals(wxMenu.getRepType())){
				wxMsg.setRepName(wxMenu.getRepName());
				wxMsg.setRepUrl(wxMenu.getRepUrl());
				wxMsg.setRepMediaId(wxMenu.getRepMediaId());
				wxMpXmlOutMessage = new ImageBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxMenu.getRepMediaId()).build();
			}
			if(WxConsts.KefuMsgType.VOICE.equals(wxMenu.getRepType())){
				wxMsg.setRepName(wxMenu.getRepName());
				wxMsg.setRepUrl(wxMenu.getRepUrl());
				wxMsg.setRepMediaId(wxMenu.getRepMediaId());
				wxMpXmlOutMessage = new VoiceBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxMenu.getRepMediaId()).build();
			}
			if(WxConsts.KefuMsgType.VIDEO.equals(wxMenu.getRepType())){
				wxMsg.setRepName(wxMenu.getRepName());
				wxMsg.setRepDesc(wxMenu.getRepDesc());
				wxMsg.setRepUrl(wxMenu.getRepUrl());
				wxMsg.setRepMediaId(wxMenu.getRepMediaId());
				wxMpXmlOutMessage = new VideoBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxMenu.getRepMediaId())
						.title(wxMenu.getRepName()).description(wxMenu.getRepDesc()).build();
			}
			if(WxConsts.KefuMsgType.MUSIC.equals(wxMenu.getRepType())){
				wxMsg.setRepName(wxMenu.getRepName());
				wxMsg.setRepDesc(wxMenu.getRepDesc());
				wxMsg.setRepUrl(wxMenu.getRepUrl());
				wxMsg.setRepHqUrl(wxMenu.getRepHqUrl());
				wxMsg.setRepThumbMediaId(wxMenu.getRepThumbMediaId());
				wxMsg.setRepThumbUrl(wxMenu.getRepThumbUrl());
				wxMpXmlOutMessage = new MusicBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
									.thumbMediaId(wxMenu.getRepThumbMediaId())
						.title(wxMenu.getRepName()).description(wxMenu.getRepDesc())
						.musicUrl(wxMenu.getRepUrl()).hqMusicUrl(wxMenu.getRepHqUrl()).build();
			}
			if(WxConsts.KefuMsgType.NEWS.equals(wxMenu.getRepType())){
				List<WxMpXmlOutNewsMessage.Item> list = new ArrayList<>();
				List<JSONObject> listJSONObject = JSONUtil.toList(wxMenu.getContent().getJSONArray("articles"),JSONObject.class);
				WxMpXmlOutNewsMessage.Item t;
				for(JSONObject jSONObject : listJSONObject){
					t = new WxMpXmlOutNewsMessage.Item();
					t.setTitle(jSONObject.getStr("title"));
					t.setDescription(jSONObject.getStr("digest"));
					t.setPicUrl(jSONObject.getStr("thumbUrl"));
					t.setUrl(jSONObject.getStr("url"));
					list.add(t);
				}
				wxMsg.setRepName(wxMenu.getRepName());
				wxMsg.setRepDesc(wxMenu.getRepDesc());
				wxMsg.setRepUrl(wxMenu.getRepUrl());
				wxMsg.setRepMediaId(wxMenu.getRepMediaId());
				wxMsg.setContent(wxMenu.getContent());
				wxMpXmlOutMessage = new NewsBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).articles(list).build();
			}
			wxMsgService.save(wxMsg);
			try {
				simpMessagingTemplate.convertAndSend(destination , JSONUtil.toJsonStr(wxMsg));
			}catch (Exception e){}
		}
		return wxMpXmlOutMessage;
	}
}
