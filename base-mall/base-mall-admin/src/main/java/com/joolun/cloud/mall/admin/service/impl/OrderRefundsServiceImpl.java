/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.config.MallConfigProperties;
import com.joolun.cloud.mall.admin.mapper.OrderRefundsMapper;
import com.joolun.cloud.mall.admin.service.*;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.entity.*;
import com.joolun.cloud.mall.common.enums.OrderInfoEnum;
import com.joolun.cloud.mall.common.enums.OrderItemEnum;
import com.joolun.cloud.mall.common.enums.OrderRefundsEnum;
import com.joolun.cloud.mall.common.feign.FeignWxPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 退款详情
 *
 * @author JL
 * @date 2019-11-14 16:35:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderRefundsServiceImpl extends ServiceImpl<OrderRefundsMapper, OrderRefunds> implements OrderRefundsService {

	private final OrderItemService orderItemService;
	private final OrderInfoService orderInfoService;
	private final FeignWxPayService feignWxPayService;
	private final MallConfigProperties mallConfigProperties;
	private final GoodsSkuService goodsSkuService;
	private final PointsRecordService pointsRecordService;
	private final UserInfoService userInfoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveRefunds(OrderRefunds entity) {
		OrderItem orderItem = orderItemService.getById(entity.getOrderItemId());
		if(StrUtil.isNotBlank(entity.getStatus())
				&& orderItem != null
				&& CommonConstants.NO.equals(orderItem.getIsRefund())
		 		&& OrderItemEnum.STATUS_0.getValue().equals(orderItem.getStatus())){//只有未退款的订单才能发起退款
			//修改订单详情状态为退款中
			orderItem.setStatus(entity.getStatus());
			orderItem.setIsRefund(CommonConstants.NO);
			orderItemService.updateById(orderItem);
			//新增退款记录
			entity.setOrderId(orderItem.getOrderId());
			entity.setOrderItemId(orderItem.getId());
			entity.setRefundAmount(orderItem.getSalesPrice());
			baseMapper.insert(entity);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean doOrderRefunds(OrderRefunds entity) {
		OrderRefunds orderRefunds = baseMapper.selectById(entity.getId());
		if(orderRefunds != null){
			//发起微信退款申请
			if(OrderRefundsEnum.STATUS_11.getValue().equals(entity.getStatus()) || OrderRefundsEnum.STATUS_211.getValue().equals(entity.getStatus())){
				//查询该订单详情是否有赠送积分
				PointsRecord pointsRecord = new PointsRecord();
				pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_2);
				pointsRecord.setOrderItemId(orderRefunds.getOrderItemId());
				pointsRecord = pointsRecordService.getOne(Wrappers.query(pointsRecord));
				if(pointsRecord !=null && StrUtil.isNotBlank(pointsRecord.getId())){
					UserInfo userInfo = userInfoService.getById(pointsRecord.getUserId());
					//校验用户当前积分是否够积分回滚
					if(userInfo.getPointsCurrent() < pointsRecord.getAmount()){
						throw new RuntimeException("该用户当前积分不够抵扣订单赠送的积分");
					}
				}
				OrderItem orderItem = orderItemService.getById2(orderRefunds.getOrderItemId());
				OrderInfo orderInfo = orderInfoService.getById(orderItem.getOrderId());
				//校验数据，只有已支付的订单、未退款的订单详情才能退款
				if(CommonConstants.YES.equals(orderInfo.getIsPay()) && CommonConstants.NO.equals(orderItem.getIsRefund())){
					WxPayRefundRequest request = new WxPayRefundRequest();
					request.setAppid(orderInfo.getAppId());
					request.setTransactionId(orderInfo.getTransactionId());
					request.setOutRefundNo(orderRefunds.getId());
					request.setTotalFee(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
//					request.setRefundFee((orderItem.getSalesPrice().multiply(new BigDecimal(orderItem.getQuantity()))).multiply(new BigDecimal(100)).intValue());
					request.setRefundFee(orderItem.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
					request.setNotifyUrl(mallConfigProperties.getNotifyHost()+"/mall/api/ma/orderrefunds/notify-refunds");
					R r = feignWxPayService.refundOrder(request, SecurityConstants.FROM_IN);
					if(r.isOk()){
						WxPayRefundResult wxPayRefundResult = BeanUtil.mapToBean((Map<Object, Object>) r.getData(),WxPayRefundResult.class,true);
						entity.setRefundTradeNo(wxPayRefundResult.getRefundId());
					}else{
						throw new RuntimeException(r.getMsg());
					}
				}
			}
			//更新退款
			baseMapper.updateById(entity);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void notifyRefunds(OrderRefunds orderRefunds) {
		//修改订单详情退款状态为已退款
		OrderItem orderItem = orderItemService.getById(orderRefunds.getOrderItemId());
		orderItem.setIsRefund(CommonConstants.YES);
		String status = "";
		if(OrderRefundsEnum.STATUS_11.getValue().equals(orderRefunds.getStatus())){
			status = OrderItemEnum.STATUS_3.getValue();
		}
		if(OrderRefundsEnum.STATUS_211.getValue().equals(orderRefunds.getStatus())){
			status = OrderItemEnum.STATUS_4.getValue();
		}
		orderItem.setStatus(status);
		orderItemService.updateById(orderItem);
		baseMapper.updateById(orderRefunds);

		//回滚库存
		GoodsSku goodsSku = goodsSkuService.getById(orderItem.getSkuId());
		if(goodsSku != null){
			goodsSku.setStock(goodsSku.getStock() + orderItem.getQuantity());
			goodsSkuService.updateById(goodsSku);
		}
		//回滚赠送积分
		PointsRecord pointsRecord = new PointsRecord();
		pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_2);
		pointsRecord.setOrderItemId(orderItem.getId());
		pointsRecord = pointsRecordService.getOne(Wrappers.query(pointsRecord));
		//查询该订单详情是否有赠送积分
		if(pointsRecord !=null && StrUtil.isNotBlank(pointsRecord.getId())){
			//减回赠送的积分
			pointsRecord.setId(null);
			pointsRecord.setTenantId(null);
			pointsRecord.setCreateTime(null);
			pointsRecord.setUpdateTime(null);
			pointsRecord.setDescription("【退款】 " + pointsRecord.getDescription());
			pointsRecord.setAmount(-pointsRecord.getAmount());
			pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_3);
			//新增积分记录
			pointsRecordService.save(pointsRecord);
			//减去赠送积分
			OrderInfo orderInfo = orderInfoService.getById(orderItem.getOrderId());
			UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
			userInfo.setPointsCurrent(userInfo.getPointsCurrent() + pointsRecord.getAmount());
			userInfoService.updateById(userInfo);
		}

		//回滚抵扣积分
		PointsRecord pointsRecord2 = new PointsRecord();
		pointsRecord2.setRecordType(MallConstants.POINTS_RECORD_TYPE_4);
		pointsRecord2.setOrderItemId(orderItem.getId());
		pointsRecord2 = pointsRecordService.getOne(Wrappers.query(pointsRecord2));
		//查询该订单详情是否有抵扣积分
		if(pointsRecord2 !=null && StrUtil.isNotBlank(pointsRecord2.getId())){
			//减回赠送的积分
			pointsRecord2.setId(null);
			pointsRecord2.setTenantId(null);
			pointsRecord2.setCreateTime(null);
			pointsRecord2.setUpdateTime(null);
			pointsRecord2.setDescription("【退款】 " + pointsRecord2.getDescription());
			pointsRecord2.setAmount(-pointsRecord2.getAmount());
			pointsRecord2.setRecordType(MallConstants.POINTS_RECORD_TYPE_6);
			//新增积分记录
			pointsRecordService.save(pointsRecord2);
			//减去赠送积分
			OrderInfo orderInfo = orderInfoService.getById(orderItem.getOrderId());
			UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
			userInfo.setPointsCurrent(userInfo.getPointsCurrent() + pointsRecord2.getAmount());
			userInfoService.updateById(userInfo);
		}

		List<OrderItem> listOrderItem = orderItemService.list(Wrappers.<OrderItem>query().lambda()
				.eq(OrderItem::getOrderId,orderItem.getOrderId()));
		List<OrderItem> listOrderItem2 = listOrderItem.stream()
				.filter(obj -> !obj.getId().equals(orderRefunds.getOrderItemId()) && CommonConstants.NO.equals(obj.getIsRefund())).collect(Collectors.toList());
		//如果订单下面所有订单详情都退款了，则取消订单
		if(listOrderItem2.size() <= 0){
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(orderItem.getOrderId());
			orderInfo.setStatus(OrderInfoEnum.STATUS_5.getValue());
			orderInfoService.updateById(orderInfo);
		}
	}

	@Override
	public IPage<OrderRefunds> page1(IPage<OrderRefunds> page, OrderRefunds orderRefunds) {
		return baseMapper.selectPage1(page,orderRefunds);
	}
}
