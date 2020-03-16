/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.common.constant.WxReturnCode;
import com.joolun.cloud.weixin.common.entity.WxMassMsg;
import com.joolun.cloud.weixin.admin.mapper.WxMassMsgMapper;
import com.joolun.cloud.weixin.admin.service.WxMassMsgService;
import com.joolun.cloud.weixin.admin.config.mp.WxMpConfiguration;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信消息群发
 *
 * @author JL
 * @date 2019-07-02 14:17:58
 */
@Service
public class WxMassMsgServiceImpl extends ServiceImpl<WxMassMsgMapper, WxMassMsg> implements WxMassMsgService {

	/**
	 * 消息群发方法
	 * @param entity
	 * @return
	 * @throws WxErrorException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean massMessageSend(WxMassMsg entity) throws WxErrorException {
		super.save(entity);//先入库
		//再调接口
		if(CommonConstants.YES.equals(entity.getIsToAll()) || ConfigConstant.WX_MASS_SEND_TYPE_1.equals(entity.getSendType())){
			WxMpMassTagMessage wxMpMassTagMessage = new WxMpMassTagMessage();
			wxMpMassTagMessage.setSendAll(CommonConstants.YES.equals(entity.getIsToAll()) ? true : false);
			wxMpMassTagMessage.setMsgType(WxConsts.KefuMsgType.NEWS.equals(entity.getRepType()) ? WxConsts.MassMsgType.MPNEWS :
					WxConsts.KefuMsgType.VIDEO.equals(entity.getRepType()) ? WxConsts.MassMsgType.MPVIDEO : entity.getRepType());
			wxMpMassTagMessage.setMediaId(entity.getRepMediaId());
			wxMpMassTagMessage.setContent(entity.getRepContent());
			wxMpMassTagMessage.setTagId(entity.getTagId());
//			wxMpMassTagMessage.setSendIgnoreReprint(entity.getSendIgnoreReprint());
			wxMpMassTagMessage.setClientMsgId(entity.getId());
			WxMpMassSendResult rt = WxMpConfiguration.getMpService(entity.getAppId()).getMassMessageService().massGroupMessageSend(wxMpMassTagMessage);
			doRt(rt,entity);
		}
		if(ConfigConstant.WX_MASS_SEND_TYPE_2.equals(entity.getSendType())){
			WxMpMassOpenIdsMessage wxMpMassOpenIdsMessage = new WxMpMassOpenIdsMessage();
			wxMpMassOpenIdsMessage.setMsgType(WxConsts.KefuMsgType.NEWS.equals(entity.getRepType()) ? WxConsts.MassMsgType.MPNEWS :
					WxConsts.KefuMsgType.VIDEO.equals(entity.getRepType()) ? WxConsts.MassMsgType.MPVIDEO : entity.getRepType());
			wxMpMassOpenIdsMessage.setMediaId(entity.getRepMediaId());
			wxMpMassOpenIdsMessage.setContent(entity.getRepContent());
//			wxMpMassOpenIdsMessage.setSendIgnoreReprint(entity.getSendIgnoreReprint());
			wxMpMassOpenIdsMessage.setClientMsgId(entity.getId());
			wxMpMassOpenIdsMessage.setToUsers(entity.getOpenIds());
			WxMpMassSendResult rt = WxMpConfiguration.getMpService(entity.getAppId()).getMassMessageService().massOpenIdsMessageSend(wxMpMassOpenIdsMessage);
			doRt(rt,entity);
		}
		super.updateById(entity);
		return true;
	}

	/**
	 * 处理提交结果
	 * @param rt
	 * @param entity
	 */
	public void doRt(WxMpMassSendResult rt,WxMassMsg entity){
		if(WxReturnCode.SUC_0.getCode().equals(rt.getErrorCode())){
			entity.setMsgStatus(ConfigConstant.WX_MASS_STATUS_SUB_SUCCESS);
			entity.setMsgId(rt.getMsgId());
			entity.setMsgDataId(rt.getMsgDataId());
		}else{
			entity.setMsgStatus(ConfigConstant.WX_MASS_STATUS_SUB_FAIL);
			entity.setErrorCode(rt.getErrorCode());
			entity.setErrorMsg(rt.getErrorMsg());
		}
	}
}
