/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.LocalDateTimeUtil;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.mall.admin.config.MallConfigProperties;
import com.joolun.cloud.mall.admin.service.OrderInfoService;
import com.joolun.cloud.mall.admin.service.OrderLogisticsService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.constant.MyReturnCode;
import com.joolun.cloud.mall.common.dto.PlaceOrderDTO;
import com.joolun.cloud.mall.common.entity.OrderInfo;
import com.joolun.cloud.mall.common.enums.OrderInfoEnum;
import com.joolun.cloud.mall.common.feign.FeignWxPayService;
import com.joolun.cloud.weixin.common.entity.WxUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城订单
 *
 * @author JL
 * @date 2019-09-10 15:21:22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/orderinfo")
@Api(value = "orderinfo", tags = "商城订单管理")
public class OrderInfoApi {

    private final OrderInfoService orderInfoService;
	private final FeignWxPayService feignWxPayService;
	private final MallConfigProperties mallConfigProperties;
	private final OrderLogisticsService orderLogisticsService;


    /**
    * 分页查询
    * @param page 分页对象
    * @param orderInfo 商城订单
    * @return
    */
    @GetMapping("/page")
    public R getOrderInfoPage(HttpServletRequest request, Page page, OrderInfo orderInfo) {
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(orderInfo, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(orderInfoService.page2(page,orderInfo));
	}

    /**
    * 通过id查询商城订单
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(orderInfoService.getById2(id));
    }

    /**
    * 新增商城订单
    * @param placeOrderDTO 商城订单
    * @return R
    */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody PlaceOrderDTO placeOrderDTO){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(placeOrderDTO, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		if(StrUtil.isBlank(placeOrderDTO.getPaymentType())){
			R.failed(MyReturnCode.ERR_70002.getCode(), MyReturnCode.ERR_70002.getMsg());
		}
		placeOrderDTO.setAppType(MallConstants.APP_TYPE_1);
		placeOrderDTO.setPaymentWay(MallConstants.PAYMENT_WAY_2);
		OrderInfo orderInfo = orderInfoService.orderSub(placeOrderDTO);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70003.getCode(), MyReturnCode.ERR_70003.getMsg());
		}
		return R.ok(orderInfo);
	}

    /**
    * 通过id删除商城订单
    * @param id
    * @return R
    */
    @DeleteMapping("/{id}")
    public R removeById(HttpServletRequest request, @PathVariable String id){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!OrderInfoEnum.STATUS_5.getValue().equals(orderInfo.getStatus())){
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		return R.ok(orderInfoService.removeById(id));
    }

	/**
	 * 取消商城订单
	 * @param id 商城订单
	 * @return R
	 */
	@PutMapping("/cancel/{id}")
	public R orderCancel(HttpServletRequest request, @PathVariable String id){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!CommonConstants.NO.equals(orderInfo.getIsPay())){//只有未支付订单能取消
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		orderInfoService.orderCancel(orderInfo);
		return R.ok();
	}

	/**
	 * 商城订单确认收货
	 * @param id 商城订单
	 * @return R
	 */
	@PutMapping("/receive/{id}")
	public R orderReceive(HttpServletRequest request, @PathVariable String id){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		OrderInfo orderInfo = orderInfoService.getById(id);
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!OrderInfoEnum.STATUS_2.getValue().equals(orderInfo.getStatus())){//只有待收货订单能确认收货
			return R.failed(MyReturnCode.ERR_70001.getCode(), MyReturnCode.ERR_70001.getMsg());
		}
		orderInfoService.orderReceive(orderInfo);
		return R.ok();
	}

	/**
	 * 调用统一下单接口，并组装生成支付所需参数对象.
	 *
	 * @param request 统一下单请求参数
	 * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
	 */
	@PostMapping("/unifiedOrder")
	public R unifiedOrder(HttpServletRequest request, @RequestBody OrderInfo orderInfo){
		//检验用户session登录
		WxUser wxUser = new WxUser();
		R checkThirdSession = BaseApi.checkThirdSession(wxUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		orderInfo = orderInfoService.getById(orderInfo.getId());
		if(orderInfo == null){
			return R.failed(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
		}
		if(!CommonConstants.NO.equals(orderInfo.getIsPay())){//只有未支付的详单能发起支付
			return R.failed(MyReturnCode.ERR_70004.getCode(), MyReturnCode.ERR_70004.getMsg());
		}
		if(orderInfo.getPaymentPrice().compareTo(BigDecimal.ZERO)==0){//0元购买不调支付
			orderInfo.setPaymentTime(LocalDateTime.now());
			orderInfoService.notifyOrder(orderInfo);
			return R.ok();
		}
		String appId = BaseApi.getAppId(request);
		WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
		wxPayUnifiedOrderRequest.setAppid(appId);
		wxPayUnifiedOrderRequest.setBody("靓号订单"+orderInfo.getOrderNo());
		wxPayUnifiedOrderRequest.setOutTradeNo(orderInfo.getId());
		wxPayUnifiedOrderRequest.setTotalFee(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
		wxPayUnifiedOrderRequest.setTradeType("JSAPI");
		wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost()+"/mall/api/ma/orderinfo/notify-order");
		wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
		wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
		return feignWxPayService.unifiedOrder(wxPayUnifiedOrderRequest, SecurityConstants.FROM_IN);
	}

	/**
	 * 支付回调
	 * @param xmlData
	 * @return
	 * @throws WxPayException
	 */
	@PostMapping("/notify-order")
	public String notifyOrder(@RequestBody String xmlData) {
		log.info("支付回调:"+xmlData);
		R r = feignWxPayService.notifyOrder(xmlData, SecurityConstants.FROM_IN);
		if(r.isOk()){
			TenantContextHolder.setTenantId(r.getMsg());
			WxPayOrderNotifyResult notifyResult = BeanUtil.mapToBean((Map<Object, Object>) r.getData(),WxPayOrderNotifyResult.class,true);
			OrderInfo orderInfo = orderInfoService.getById(notifyResult.getOutTradeNo());
			if(orderInfo != null){
				if(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue() == notifyResult.getTotalFee()){
					String timeEnd = notifyResult.getTimeEnd();
					LocalDateTime paymentTime = LocalDateTimeUtil.parse(timeEnd);
					orderInfo.setPaymentTime(paymentTime);
					orderInfo.setTransactionId(notifyResult.getTransactionId());
					orderInfoService.notifyOrder(orderInfo);
					return WxPayNotifyResponse.success("成功");
				}else{
					return WxPayNotifyResponse.fail("付款金额与订单金额不等");
				}
			}else{
				return WxPayNotifyResponse.fail("无此订单");
			}
		}else{
			return WxPayNotifyResponse.fail(r.getMsg());
		}
	}

	/**
	 * 物流信息回调
	 * @param request
	 * @return
	 */
	@PostMapping("/notify-logisticsr")
	public String notifyLogisticsr(HttpServletRequest request, HttpServletResponse response) {
		String param = request.getParameter("param");
		String logisticsId = request.getParameter("logisticsId");
		String tenantId = request.getParameter("tenantId");
		TenantContextHolder.setTenantId(tenantId);
		log.info("物流信息回调:"+param);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",false);
		map.put("returnCode","500");
		map.put("message","保存失败");
		try {
			JSONObject jsonObject = JSONUtil.parseObj(param);
			orderInfoService.notifyLogisticsr(logisticsId, jsonObject);
			map.put("result",true);
			map.put("returnCode","200");
			map.put("message","保存成功");
			//这里必须返回，否则认为失败，过30分钟又会重复推送。
			response.getWriter().print(JSONUtil.parseObj(map));
		} catch (Exception e) {
			map.put("message","保存失败" + e.getMessage());
			//保存失败，服务端等30分钟会重复推送。
			try {
				response.getWriter().print(JSONUtil.parseObj(map));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过物流id查询订单物流
	 * @param logisticsId
	 * @return R
	 */
	@GetMapping("/orderlogistics/{logisticsId}")
	public R getOrderLogistics(HttpServletRequest request, @PathVariable("logisticsId") String logisticsId){
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(orderLogisticsService.getById(logisticsId));
	}
}
