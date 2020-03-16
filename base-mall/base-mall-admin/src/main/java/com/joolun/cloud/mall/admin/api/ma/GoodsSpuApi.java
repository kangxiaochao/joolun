/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.mapper.GoodsSkuMapper;
import com.joolun.cloud.mall.admin.mapper.UserCollectMapper;
import com.joolun.cloud.mall.admin.service.GoodsSpuService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.entity.GoodsSku;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.mapper.UserCollectMapper;
import com.joolun.cloud.mall.admin.service.CouponInfoService;
import com.joolun.cloud.mall.admin.service.GoodsSpuService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.constant.MyReturnCode;
import com.joolun.cloud.mall.common.entity.CouponGoods;
import com.joolun.cloud.mall.common.entity.CouponInfo;
import com.joolun.cloud.mall.common.entity.GoodsSpu;
import com.joolun.cloud.mall.common.entity.UserCollect;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品api
 *
 * @author JL
 * @date 2019-08-12 16:25:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/goodsspu")
@Api(value = "goods", tags = "商品接口")
public class GoodsSpuApi<main> {

	private final GoodsSpuService goodsSpuService;
	private final UserCollectMapper userCollectMapper;
	private final GoodsSkuMapper goodsSkuMapper;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param goodsSpu spu商品
	 * @return
	 */
//    @GetMapping("/page")
	public R getGoodsSpuPage(HttpServletRequest request, Page page, GoodsSpu goodsSpu) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		String name = goodsSpu.getName();
		goodsSpu.setName(null);

		return R.ok(goodsSpuService.page(page, Wrappers.query(goodsSpu).lambda()
						.select(GoodsSpu::getId,
								GoodsSpu::getName,
								GoodsSpu::getTenantId,
								GoodsSpu::getSpuCode,
								GoodsSpu::getSellPoint,
								GoodsSpu::getCategoryFirst,
								GoodsSpu::getCategorySecond,
								GoodsSpu::getPicUrls,
								GoodsSpu::getShelf,
								GoodsSpu::getSort,
								GoodsSpu::getPhoneSystem,
								GoodsSpu::getLowDissipation,
								GoodsSpu::getPriceDown,
								GoodsSpu::getPriceUp,
								GoodsSpu::getSaleNum,
								GoodsSpu::getCreateTime,
								GoodsSpu::getUpdateTime,
								GoodsSpu::getSpecType,
								GoodsSpu::getOperatorId,
								GoodsSpu::getOperatorName,
								GoodsSpu::getPackageId,
								GoodsSpu::getPackageName,
								GoodsSpu::getAttributionProvince,
								GoodsSpu::getAttributionCity,
								GoodsSpu::getProcessId,
								GoodsSpu::getProcessName)
						.like(GoodsSpu::getName, StrUtil.isNotBlank(name) ? name : "")
						.eq(GoodsSpu::getShelf, 1)
				)
		);
	}

	/**
	 * 自定义分页查询
	 */
	@GetMapping("/page")
	public R selectGoodsSpuPage(HttpServletRequest request, Page page, GoodsSpu goodsSpu) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		if (!StrUtil.isNotBlank(goodsSpu.getName())) {
			goodsSpu.setName("");
		}
		return R.ok(goodsSpuService.selectGoodsSpuPage(page, goodsSpu));
	}

	public static void main(String  [] args){
		System.out.println(6%9);
	}

	/**
	 * 通过spuid获取skuid的方法
	 *
	 * @param request
	 * @param spuId
	 * @return
	 */
	@GetMapping("/skus/{spuId}")
	public R selectSkusBySpuId(HttpServletRequest request, @PathVariable("spuId") String spuId) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		GoodsSku goodsSku = new GoodsSku();
		return R.ok(goodsSkuMapper.selectOne(Wrappers.query(goodsSku).lambda().select(
				GoodsSku::getId,
				GoodsSku::getWeight,
				GoodsSku::getVolume).eq(GoodsSku::getSpuId, spuId)));
	}

	/**
	 * 筛选页用分页查询
	 *
	 * @param page     分页对象
	 * @param jsonData json参数
	 * @return
	 */
	@GetMapping("/query/page")
	public R getGoodsSpuQueryPage(HttpServletRequest request, Page page, String jsonData) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		JSONObject json = JSONObject.parseObject(jsonData);
		String operator = json.getString("operator");
		String category = json.getString("category");
		String region = json.getString("region");
		String priceStart = json.getString("priceStart");
		String priceEnd = json.getString("priceEnd");
		String lowDissipationStart = json.getString("lowDissipationStart");
		String lowDissipationEnd = json.getString("lowDissipationEnd");
		String phoneSystem = json.getString("phoneSystem");
		String packageName = json.getString("packageName");

		String num_1 = StringUtils.isNotEmpty(json.getString("num_01")) ? json.getString("num_01") : ".";
		String num_2 = StringUtils.isNotEmpty(json.getString("num_02")) ? json.getString("num_02") : ".";
		String num_3 = StringUtils.isNotEmpty(json.getString("num_03")) ? json.getString("num_03") : ".";
		String num_4 = StringUtils.isNotEmpty(json.getString("num_04")) ? json.getString("num_04") : ".";
		String num_5 = StringUtils.isNotEmpty(json.getString("num_05")) ? json.getString("num_05") : ".";
		String num_6 = StringUtils.isNotEmpty(json.getString("num_06")) ? json.getString("num_06") : ".";
		String num_7 = StringUtils.isNotEmpty(json.getString("num_07")) ? json.getString("num_07") : ".";
		String num_8 = StringUtils.isNotEmpty(json.getString("num_08")) ? json.getString("num_08") : ".";
		String num_9 = StringUtils.isNotEmpty(json.getString("num_09")) ? json.getString("num_09") : ".";
		String num_10 = StringUtils.isNotEmpty(json.getString("num_10")) ? json.getString("num_10") : ".";
		String num_11 = StringUtils.isNotEmpty(json.getString("num_11")) ? json.getString("num_11") : ".";
		String regexStr = num_1 + num_2 + num_3 + num_4 + num_5 + num_6 + num_7 + num_8 + num_9 + num_10 + num_11;
		LambdaQueryWrapper<GoodsSpu> wrapper = new LambdaQueryWrapper<GoodsSpu>();
		wrapper.select(GoodsSpu::getId,
				GoodsSpu::getName,
				GoodsSpu::getTenantId,
				GoodsSpu::getSpuCode,
				GoodsSpu::getSellPoint,
				GoodsSpu::getCategoryFirst,
				GoodsSpu::getCategorySecond,
				GoodsSpu::getPicUrls,
				GoodsSpu::getShelf,
				GoodsSpu::getSort,
				GoodsSpu::getPhoneSystem,
				GoodsSpu::getLowDissipation,
				GoodsSpu::getPriceDown,
				GoodsSpu::getPriceUp,
				GoodsSpu::getSaleNum,
				GoodsSpu::getCreateTime,
				GoodsSpu::getUpdateTime,
				GoodsSpu::getSpecType,
				GoodsSpu::getOperatorId,
				GoodsSpu::getOperatorName,
				GoodsSpu::getPackageId,
				GoodsSpu::getPackageName,
				GoodsSpu::getAttributionProvince,
				GoodsSpu::getAttributionCity,
				GoodsSpu::getProcessId,
				GoodsSpu::getProcessName).eq(GoodsSpu::getShelf, 1);
		wrapper.inSql(GoodsSpu::getId, "select id from goods_spu where name REGEXP '" + regexStr + "'");
		wrapper.inSql(GoodsSpu::getId, "SELECT DISTINCT spu_id from goods_sku where seckill_status = 0 and stock > 0");
		if (json.containsKey("category")) {
			if (!"".equals(json.getString("category"))) {
				wrapper.inSql(GoodsSpu::getCategorySecond, "select id from goods_category where name = '" + category + "'");
			}
		}
		if (json.containsKey("operator")) {
			if (!"".equals(json.getString("operator"))) {
				wrapper.like(GoodsSpu::getOperatorName, operator);
			}
		}
		if(json.containsKey("packageName")){
			wrapper.eq(GoodsSpu::getPackageName,packageName);//套餐
		}
		if (json.containsKey("phoneSystem")) {
			wrapper.eq(GoodsSpu::getPhoneSystem, phoneSystem);//制式
		}
		if (!lowDissipationStart.equals("") && lowDissipationStart != null) {
			wrapper.ge(GoodsSpu::getLowDissipation, lowDissipationStart);//号码低消大于等于范围小值
		}
		if (!lowDissipationEnd.equals("") && lowDissipationEnd != null) {
			wrapper.le(GoodsSpu::getLowDissipation, lowDissipationEnd);//号码低消小于等于范围大值
		}
		if (!priceStart.equals("") && priceStart != null) {
			wrapper.ge(GoodsSpu::getPriceDown, priceStart);//最低价大于等于范围小值
		}
		if (!priceEnd.equals("") && priceStart != null) {
			wrapper.le(GoodsSpu::getPriceUp, priceEnd);//最高价小于等于范围大值
		}
		if (region.contains(",")) {
			//["北京市","北京市"]
			String[] locationArr = region.split(",");
			for (int i = 0; i < locationArr.length; i++) {
				locationArr[i] = locationArr[i].replaceAll("[^\\u4e00-\\u9fa5]", "");
			}
			if (!locationArr[0].equals("") && locationArr[0] != null) {
				//删除小程序传入地址中的省市区
				wrapper.like(GoodsSpu::getAttributionProvince, locationArr[0].substring(0, locationArr[0].length() - 1));
			}
			if (!locationArr[1].equals("") && locationArr[1] != null) {
				//删除小程序传入地址中的省市区
				wrapper.like(GoodsSpu::getAttributionCity, locationArr[1].substring(0, locationArr[1].length() - 1));
			}
		}
		return R.ok(goodsSpuService.page(page, wrapper));
	}

	/**
	 * 通过id查询spu商品
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R getById(HttpServletRequest request, @PathVariable("id") String id) {
		UserCollect userCollect = new UserCollect();
		R checkThirdSession = BaseApi.checkThirdSession(userCollect, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		//查询用户是否收藏
		GoodsSpu goodsSpu = goodsSpuService.getById2(id);
		userCollect.setType(MallConstants.COLLECT_TYPE_1);
		userCollect.setRelationId(id);
		goodsSpu.setCollectId(userCollectMapper.selectCollectId(userCollect));
		return R.ok(goodsSpu);
	}


	/**
	 * 获取秒杀商品列表
	 *
	 * @return R
	 */
	@GetMapping("/forSeckill")
	public R getGoodsForSeckill(HttpServletRequest request) {
		UserCollect userCollect = new UserCollect();
		R checkThirdSession = BaseApi.checkThirdSession(userCollect, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(goodsSkuMapper.selectSpuForSeckill());
	}

	/**
	 * 获取竞拍商品列表
	 *
	 * @return R
	 */
	@GetMapping("/forAuction")
	public R getGoodsForAuction(HttpServletRequest request) {
		UserCollect userCollect = new UserCollect();
		R checkThirdSession = BaseApi.checkThirdSession(userCollect, request);
		if (!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(goodsSkuMapper.selectSpuForSeckill());
	}
}
