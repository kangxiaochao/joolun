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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxAutoReplyService;
import com.joolun.cloud.weixin.admin.service.WxMsgService;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import com.joolun.cloud.weixin.common.entity.WxAutoReply;
import com.joolun.cloud.weixin.common.entity.WxMsg;
import com.joolun.cloud.weixin.common.constant.WebSocketConstant;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.builder.outxml.ImageBuilder;
import me.chanjar.weixin.mp.builder.outxml.MusicBuilder;
import me.chanjar.weixin.mp.builder.outxml.NewsBuilder;
import me.chanjar.weixin.mp.builder.outxml.VideoBuilder;
import me.chanjar.weixin.mp.builder.outxml.VoiceBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author JL
 */
@Slf4j
@Component
@AllArgsConstructor
public class MsgHandler extends AbstractHandler {

	private final WxAutoReplyService wxAutoReplyService;
	private final WxAppService wxAppService;
	private final WxUserService wxUserService;
	private final WxMsgService wxMsgService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	@Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
    	//组装回复消息
        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			WxMpXmlOutMessage rs;
            //TODO 可以选择将消息保存到本地
			WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
			TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
			WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), wxMessage.getFromUser());
			if(WxConsts.KefuMsgType.TEXT.equals(wxMessage.getMsgType())){//1、先处理是否有文本关键字回复
				//先全匹配
				List<WxAutoReply> listWxAutoReply = wxAutoReplyService.list(Wrappers
						.<WxAutoReply>query().lambda()
						.eq(WxAutoReply::getAppId, wxApp.getId())
						.eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_3)
						.eq(WxAutoReply::getRepMate, ConfigConstant.WX_REP_MATE_1)
						.eq(WxAutoReply::getReqKey, wxMessage.getContent()));
				if(listWxAutoReply!=null && listWxAutoReply.size()>0){
					rs = MsgHandler.getWxMpXmlOutMessage(wxMessage,listWxAutoReply,wxApp,wxUser,wxMsgService,simpMessagingTemplate);
					if(rs != null){
						return  rs;
					}
				}
				//再半匹配
				listWxAutoReply = wxAutoReplyService.list(Wrappers
						.<WxAutoReply>query().lambda()
						.eq(WxAutoReply::getTenantId, wxApp.getTenantId())
						.eq(WxAutoReply::getAppId, wxApp.getId())
						.eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_3)
						.eq(WxAutoReply::getRepMate, ConfigConstant.WX_REP_MATE_2)
						.like(WxAutoReply::getReqKey, wxMessage.getContent()));
				if(listWxAutoReply!=null && listWxAutoReply.size()>0) {
					rs = MsgHandler.getWxMpXmlOutMessage(wxMessage, listWxAutoReply, wxApp, wxUser,wxMsgService,simpMessagingTemplate);
					if (rs != null) {
						return rs;
					}
				}
			}
			//2、再处理消息回复
			List<WxAutoReply> listWxAutoReply = wxAutoReplyService.list(Wrappers
					.<WxAutoReply>query().lambda()
					.eq(WxAutoReply::getTenantId, wxApp.getTenantId())
					.eq(WxAutoReply::getAppId, wxApp.getId())
					.eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_2)
					.eq(WxAutoReply::getReqType, wxMessage.getMsgType()));
			rs = MsgHandler.getWxMpXmlOutMessage(wxMessage,listWxAutoReply,wxApp,wxUser,wxMsgService,simpMessagingTemplate);
			return rs;
        }
        return null;

    }

	/**
	 * 组装回复消息，并记录消息
	 * @param wxMessage
	 * @param listWxAutoReply
	 * @return
	 */
    public static WxMpXmlOutMessage getWxMpXmlOutMessage(WxMpXmlMessage wxMessage, List<WxAutoReply> listWxAutoReply, WxApp wxApp, WxUser wxUser,
														 WxMsgService wxMsgService,SimpMessagingTemplate simpMessagingTemplate){
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
		wxMsg.setRepMediaId(wxMessage.getMediaId());
		if(WxConsts.XmlMsgType.TEXT.equals(wxMessage.getMsgType())){
			wxMsg.setRepContent(wxMessage.getContent());
		}
		if(WxConsts.XmlMsgType.VOICE.equals(wxMessage.getMsgType())){
			wxMsg.setRepName(wxMessage.getMediaId() + "." + wxMessage.getFormat());
			wxMsg.setRepContent(wxMessage.getRecognition());
		}
		if(WxConsts.XmlMsgType.IMAGE.equals(wxMessage.getMsgType())){
			wxMsg.setRepUrl(wxMessage.getPicUrl());
		}
		if(WxConsts.XmlMsgType.LINK.equals(wxMessage.getMsgType())){
			wxMsg.setRepName(wxMessage.getTitle());
			wxMsg.setRepDesc(wxMessage.getDescription());
			wxMsg.setRepUrl(wxMessage.getUrl());
		}
		if(WxConsts.MediaFileType.FILE.equals(wxMessage.getMsgType())){
			wxMsg.setRepName(wxMessage.getTitle());
			wxMsg.setRepDesc(wxMessage.getDescription());
		}
		if(WxConsts.XmlMsgType.VIDEO.equals(wxMessage.getMsgType())){
			wxMsg.setRepThumbMediaId(wxMessage.getThumbMediaId());
		}
		if(WxConsts.XmlMsgType.LOCATION.equals(wxMessage.getMsgType())){
			wxMsg.setRepLocationX(wxMessage.getLocationX());
			wxMsg.setRepLocationY(wxMessage.getLocationY());
			wxMsg.setRepScale(wxMessage.getScale());
			wxMsg.setRepContent(wxMessage.getLabel());
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
		if(listWxAutoReply!=null && listWxAutoReply.size()>0){
			WxAutoReply wxAutoReply = listWxAutoReply.get(0);
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
			wxMsg.setRepType(wxAutoReply.getRepType());

			if(WxConsts.KefuMsgType.TEXT.equals(wxAutoReply.getRepType())){//文本
				wxMsg.setRepContent(wxAutoReply.getRepContent());
				wxMpXmlOutMessage = new TextBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content(wxAutoReply.getRepContent()).build();
			}
			if(WxConsts.KefuMsgType.IMAGE.equals(wxAutoReply.getRepType())){//图片
				wxMsg.setRepName(wxAutoReply.getRepName());
				wxMsg.setRepUrl(wxAutoReply.getRepUrl());
				wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
				wxMpXmlOutMessage = new ImageBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxAutoReply.getRepMediaId()).build();
			}
			if(WxConsts.KefuMsgType.VOICE.equals(wxAutoReply.getRepType())){
				wxMsg.setRepName(wxAutoReply.getRepName());
				wxMsg.setRepUrl(wxAutoReply.getRepUrl());
				wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
				wxMpXmlOutMessage = new VoiceBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxAutoReply.getRepMediaId()).build();
			}
			if(WxConsts.KefuMsgType.VIDEO.equals(wxAutoReply.getRepType())){
				wxMsg.setRepName(wxAutoReply.getRepName());
				wxMsg.setRepDesc(wxAutoReply.getRepDesc());
				wxMsg.setRepUrl(wxAutoReply.getRepUrl());
				wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
				wxMpXmlOutMessage = new VideoBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).mediaId(wxAutoReply.getRepMediaId())
						.title(wxAutoReply.getRepName()).description(wxAutoReply.getRepDesc()).build();
			}
			if(WxConsts.KefuMsgType.MUSIC.equals(wxAutoReply.getRepType())){
				wxMsg.setRepName(wxAutoReply.getRepName());
				wxMsg.setRepDesc(wxAutoReply.getRepDesc());
				wxMsg.setRepUrl(wxAutoReply.getRepUrl());
				wxMsg.setRepHqUrl(wxAutoReply.getRepHqUrl());
				wxMsg.setRepThumbMediaId(wxAutoReply.getRepThumbMediaId());
				wxMsg.setRepThumbUrl(wxAutoReply.getRepThumbUrl());
				wxMpXmlOutMessage = new MusicBuilder().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
									.thumbMediaId(wxAutoReply.getRepThumbMediaId())
						.title(wxAutoReply.getRepName()).description(wxAutoReply.getRepDesc())
						.musicUrl(wxAutoReply.getRepUrl()).hqMusicUrl(wxAutoReply.getRepHqUrl()).build();
			}
			if(WxConsts.KefuMsgType.NEWS.equals(wxAutoReply.getRepType())){
				List<WxMpXmlOutNewsMessage.Item> list = new ArrayList<>();
				List<JSONObject> listJSONObject = wxAutoReply.getContent().getJSONArray("articles").toList(JSONObject.class);
				WxMpXmlOutNewsMessage.Item t;
				for(JSONObject jSONObject : listJSONObject){
					t = new WxMpXmlOutNewsMessage.Item();
					t.setTitle(jSONObject.getStr("title"));
					t.setDescription(jSONObject.getStr("digest"));
					t.setPicUrl(jSONObject.getStr("thumbUrl"));
					t.setUrl(jSONObject.getStr("url"));
					list.add(t);
				}
				wxMsg.setRepName(wxAutoReply.getRepName());
				wxMsg.setRepDesc(wxAutoReply.getRepDesc());
				wxMsg.setRepUrl(wxAutoReply.getRepUrl());
				wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
				wxMsg.setContent(wxAutoReply.getContent());
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
