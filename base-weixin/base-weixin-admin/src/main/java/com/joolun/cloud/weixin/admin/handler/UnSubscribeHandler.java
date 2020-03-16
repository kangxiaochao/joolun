/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.handler;

import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxMsgService;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户取消关注
 * @author JL
 */
@Slf4j
@Component
@AllArgsConstructor
public class UnSubscribeHandler extends AbstractHandler {

	private final WxAppService wxAppService;
	private final WxUserService wxUserService;
	private final WxMsgService wxMsgService;
	private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        log.info("取消关注用户 OPENID: " + openId);
        // TODO 更新本地数据库为取消关注状态
		WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
		TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
		WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), openId);
		if(wxUser!=null){
			wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);
			wxUser.setCancelSubscribeTime(LocalDateTime.now());
			wxUserService.updateById(wxUser);
			//消息记录
			MsgHandler.getWxMpXmlOutMessage(wxMessage,null,wxApp,wxUser,wxMsgService,simpMessagingTemplate);
		}
        return null;
    }

}
