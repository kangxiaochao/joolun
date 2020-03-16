package com.joolun.cloud.mall.common.feign;

import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.constant.ServiceNameConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.upms.common.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author JL
 */
@FeignClient(contextId = "feignWxPayService", value = ServiceNameConstants.WX_ADMIN_SERVICE)
public interface FeignWxPayService {
	/**
	 * 调用统一下单接口，并组装生成支付所需参数对象.
	 *
	 * @param request 统一下单请求参数
	 * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
	 */
	@PostMapping("/wxpay/unifiedOrder")
	R unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 处理支付回调数据
	 * @param xmlData
	 * @return
	 */
	@PostMapping("/wxpay/notifyOrder")
	R notifyOrder(@RequestBody String xmlData, @RequestHeader(SecurityConstants.FROM) String from);

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
	@PostMapping("/wxpay/refundOrder")
	R refundOrder(@RequestBody WxPayRefundRequest request, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 退款回调
	 * @param xmlData
	 * @param from
	 * @return
	 */
	@PostMapping("/wxpay/notifyRefunds")
	R notifyRefunds(@RequestBody String xmlData, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * <pre>
	 * 查询订单（适合于需要自定义子商户号和子商户appid的情形）.
	 * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
	 * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
	 * 需要调用查询接口的情况：
	 * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
	 * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
	 * ◆ 调用被扫支付API，返回USERPAYING的状态；
	 * ◆ 调用关单或撤销接口API之前，需确认支付状态；
	 * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
	 * </pre>
	 *
	 * @param request 查询订单请求对象
	 * @return the wx pay order query result
	 * @throws WxPayException the wx pay exception
	 */
	@PostMapping("/wxpay/queryOrder")
	R queryOrder(@RequestBody WxPayOrderQueryRequest request, @RequestHeader(SecurityConstants.FROM) String from);
}
