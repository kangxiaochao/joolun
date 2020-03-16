/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import com.joolun.cloud.weixin.admin.config.mp.WxMpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author JL
 */
@Slf4j
@RestController
@RequestMapping("/portal/{appid}")
public class WxPortalController {

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
            timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMpService wxService = WxMpConfiguration.getMpService(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        final WxMpService wxService = WxMpConfiguration.getMpService(appid);
		log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
		String out = null;
		if(wxService!=null){
			if (!wxService.checkSignature(timestamp, nonce, signature)) {
				throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
			}
			if (encType == null) {
				// 明文传输的消息
				WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
				WxMpXmlOutMessage outMessage = this.route(inMessage, appid);
				if (outMessage == null) {
					return "";
				}

				out = outMessage.toXml();
			} else if ("aes".equalsIgnoreCase(encType)) {
				// aes加密的消息
				WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
						timestamp, nonce, msgSignature);
				log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
				WxMpXmlOutMessage outMessage = this.route(inMessage, appid);
				if (outMessage == null) {
					return "";
				}

				out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
			}
		}
		log.debug("\n组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message, String appid) {
        try {
            return WxMpConfiguration.getWxMpMessageRouter(appid).route(message);
        } catch (Exception e) {
			e.printStackTrace();
			log.error("路由消息时出现异常！", e);
        }

        return null;
    }

}
