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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.OrderInfoService;
import com.joolun.cloud.mall.admin.service.OrderRefundsService;
import com.joolun.cloud.mall.common.entity.OrderRefunds;
import com.joolun.cloud.mall.common.feign.FeignWxPayService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 退款详情
 *
 * @author JL
 * @date 2019-11-14 16:35:25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/orderrefunds")
@Api(value = "orderrefunds", tags = "退款详情管理")
public class OrderRefundsApi {

    private final OrderRefundsService orderRefundsService;
	private final OrderInfoService orderInfoService;
	private final FeignWxPayService feignWxPayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param orderRefunds 退款详情
     * @return
     */
    @GetMapping("/page")
    public R getOrderRefundsPage(HttpServletRequest request, Page page, OrderRefunds orderRefunds) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(orderRefundsService.page(page, Wrappers.query(orderRefunds)));
    }

    /**
     * 通过id查询退款详情
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(orderRefundsService.getById(id));
    }

    /**
     * 新增退款详情(发起退款)
     * @param orderRefunds 退款详情
     * @return R
     */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody OrderRefunds orderRefunds) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(orderRefundsService.saveRefunds(orderRefunds));
    }

    /**
     * 修改退款详情
     * @param orderRefunds 退款详情
     * @return R
     */
    @PutMapping
	public R updateById(HttpServletRequest request, @RequestBody OrderRefunds orderRefunds) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(orderRefundsService.updateById(orderRefunds));
	}

    /**
     * 通过id删除退款详情
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id) {
        return R.ok(orderRefundsService.removeById(id));
    }


	/**
	 * 退款回调
	 * @param xmlData
	 * @return
	 * @throws WxPayException
	 */
	@PostMapping("/notify-refunds")
	public String notifyRefunds(@RequestBody String xmlData) {
		log.info("退款回调:"+xmlData);
		R r = feignWxPayService.notifyRefunds(xmlData, SecurityConstants.FROM_IN);
		if(r.isOk()){
			TenantContextHolder.setTenantId(r.getMsg());
			WxPayRefundNotifyResult notifyResult = BeanUtil.mapToBean((Map<Object, Object>) r.getData(),WxPayRefundNotifyResult.class,true);
			OrderRefunds orderRefunds = orderRefundsService.getById(notifyResult.getReqInfo().getOutRefundNo());
			if(orderRefunds != null){
				orderRefundsService.notifyRefunds(orderRefunds);
				return WxPayNotifyResponse.success("成功");
			}else{
				return WxPayNotifyResponse.fail("无此订单详情");
			}
		}else{
			return WxPayNotifyResponse.fail(r.getMsg());
		}
	}
}
