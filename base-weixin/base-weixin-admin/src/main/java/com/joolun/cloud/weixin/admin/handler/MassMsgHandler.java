/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxMassMsg;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxMassMsgService;
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
 * 群发结果回调
 * @author JL
 */
@Slf4j
@Component
@AllArgsConstructor
public class MassMsgHandler extends AbstractHandler {

	private final WxAppService wxAppService;
	private final WxMassMsgService wxMassMsgService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		// TODO 组装回复消息
		WxApp wxApp = wxAppService.findByWeixinSign(wxMessage.getToUser());
		TenantContextHolder.setTenantId(wxApp.getTenantId());//加入租户ID
		String msgId = String.valueOf(wxMessage.getMsgId());
		WxMassMsg wxMassMsg = wxMassMsgService.getOne(Wrappers.<WxMassMsg>query().lambda()
				.eq(WxMassMsg::getAppId,wxApp.getId())
				.eq(WxMassMsg::getMsgId,msgId));
		String errCode = wxMessage.getStatus();
		wxMassMsg.setMsgStatus(WxConsts.MassMsgStatus.SEND_SUCCESS.equals(errCode) ? ConfigConstant.WX_MASS_STATUS_SEND_SUCCESS : ConfigConstant.WX_MASS_STATUS_SEND_FAIL);
		wxMassMsg.setErrorCode(errCode);
		wxMassMsg.setErrorMsg(WxConsts.MassMsgStatus.STATUS_DESC.get(errCode));
		wxMassMsg.setTotalCount(wxMessage.getTotalCount());
		wxMassMsg.setSentCount(wxMessage.getSentCount());
		wxMassMsg.setErrorCount(wxMessage.getErrorCount());
		wxMassMsg.setFilterCount(wxMessage.getFilterCount());
		wxMassMsgService.updateById(wxMassMsg);
		return null;
	}

}
