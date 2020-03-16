/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.security.annotation.Inside;
import com.joolun.cloud.weixin.admin.config.pay.WxPayConfiguration;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.common.entity.WxApp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信支付
 *
 * @author JL
 * @date 2019-03-23 21:26:35
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxpay")
public class WxPayController {

	private final WxAppService wxAppService;

	/**
	 * 调用统一下单接口，并组装生成支付所需参数对象.
	 *
	 * @param request 统一下单请求参数
	 * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
	 */
	@Inside
	@PostMapping("/unifiedOrder")
	public R unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.createOrder(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg());
		}
	}

	/**
	 * 处理支付回调数据
	 * @param xmlData
	 * @return
	 */
	@Inside
	@PostMapping("/notifyOrder")
	public R notifyOrder(@RequestBody String xmlData) {
		String appId = WxPayOrderNotifyResult.fromXML(xmlData).getAppid();
		WxPayService wxPayService = WxPayConfiguration.getPayService(appId);
		try {
			WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
			WxApp wxApp = wxAppService.findByAppId(appId);
			return R.ok(notifyResult,wxApp.getTenantId());
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg());
		}
	}

	/**
	 * <pre>
	 * 微信支付-申请退款.
	 * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	 * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
	 * </pre>
	 *
	 * @param request 请求对象
	 * @return 退款操作结果 wx pay refund result
	 * @throws WxPayException the wx pay exception
	 */
	@Inside
	@PostMapping("/refundOrder")
	public R refundOrder(@RequestBody WxPayRefundRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.refund(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg());
		}
	}

	/**
	 * 处理退款回调数据
	 * @param xmlData
	 * @return
	 */
	@Inside
	@PostMapping("/notifyRefunds")
	public R notifyRefunds(@RequestBody String xmlData) {
		String appId = WxPayOrderNotifyResult.fromXML(xmlData).getAppid();
		WxPayService wxPayService = WxPayConfiguration.getPayService(appId);
		try {
			WxPayRefundNotifyResult notifyResult = wxPayService.parseRefundNotifyResult(xmlData);
			WxApp wxApp = wxAppService.findByAppId(appId);
			return R.ok(notifyResult,wxApp.getTenantId());
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg());
		}
	}

	/**
	 * 查询订单
	 * @param request
	 * @return
	 */
	@Inside
	@PostMapping("/queryOrder")
	public R refundOrder(@RequestBody WxPayOrderQueryRequest request) {
		WxPayService wxPayService = WxPayConfiguration.getPayService(request.getAppid());
		try {
			return R.ok(wxPayService.queryOrder(request));
		} catch (WxPayException e) {
			e.printStackTrace();
			return R.failed(e.getReturnMsg());
		}
	}
}
