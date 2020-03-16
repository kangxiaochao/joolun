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
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;
import java.util.Map;


/**
 * @author JL
 */
@Slf4j
@Component
@AllArgsConstructor
public class LocationHandler extends AbstractHandler {

	private final WxUserService wxUserService;
	private final WxAppService wxAppService;

	@Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        if (wxMessage.getEvent().equals(WxConsts.EventType.LOCATION)) {
            try {
				WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
				TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
				WxUser wxUser = wxUserService.getByOpenId(wxApp.getId(), wxMessage.getFromUser());
				wxUser.setLatitude(wxMessage.getLatitude());
				wxUser.setLongitude(wxMessage.getLongitude());
				wxUser.setPrecision(wxMessage.getPrecision());
				wxUserService.updateById(wxUser);
                return null;
            } catch (Exception e) {
            	e.printStackTrace();
                log.error("位置消息接收处理失败", e);
                return null;
            }
        }
        return null;
    }

}
