/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.weixin.common.entity.WxMassMsg;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信消息群发
 *
 * @author JL
 * @date 2019-07-02 14:17:58
 */
public interface WxMassMsgService extends IService<WxMassMsg> {

	/**
	 * 群发微信消息
	 * @param entity
	 * @return
	 * @throws WxErrorException
	 */
	boolean massMessageSend(WxMassMsg entity) throws WxErrorException;
}
