/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.GoodsCategoryService;
import com.joolun.cloud.mall.admin.service.GoodsSkuService;
import com.joolun.cloud.mall.admin.service.GoodsSpuService;
import com.joolun.cloud.mall.common.entity.GoodsCategory;
import com.joolun.cloud.mall.common.entity.GoodsSku;
import com.joolun.cloud.mall.common.entity.GoodsSpu;
import com.joolun.cloud.mall.common.feign.FeignLianghaoService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


/**
 * spu商品
 *
 * @author JL
 * @date 2019-08-12 16:25:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsspu")
@Api(value = "goodsspu", tags = "spu商品管理")
public class GoodsSpuController {

	private final GoodsSpuService goodsSpuService;
	private final GoodsCategoryService goodsCategoryService;
	private final GoodsSkuService goodsSkuService;
	private final FeignLianghaoService feignLianghaoService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param goodsSpu spu商品
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_index')")
	public R getGoodsSpuPage(Page page, GoodsSpu goodsSpu) {
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
//								GoodsSpu::getPicUrls,
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
								GoodsSpu::getProcessName,
								GoodsSpu::getPointsGiveSwitch,
								GoodsSpu::getPointsGiveNum,
								GoodsSpu::getPointsDeductSwitch,
								GoodsSpu::getPointsDeductScale,
								GoodsSpu::getPointsDeductAmount,
								GoodsSpu::getFreightTemplatId)
						.like(GoodsSpu::getName, StrUtil.isNotBlank(name) ? name : "")
				)
		);
	}

	/**
	 * 通过id查询spu商品
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_get')")
	public R getById(@PathVariable("id") String id) {
		return R.ok(goodsSpuService.getById2(id));
	}

	/**
	 * 新增spu商品
	 *
	 * @param goodsSpu spu商品
	 * @return R
	 */
	@SysLog("新增spu商品")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_add')")
	public R save(@RequestBody GoodsSpu goodsSpu) {
		return R.ok(goodsSpuService.save1(goodsSpu));
	}

	/**
	 * 修改spu商品
	 *
	 * @param goodsSpu spu商品
	 * @return R
	 */
	@SysLog("修改spu商品")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_edit')")
	public R updateById(@RequestBody GoodsSpu goodsSpu) {
		return R.ok(goodsSpuService.updateById1(goodsSpu));
	}

	/**
	 * 商品上下架操作
	 *
	 * @param shelf
	 * @param ids
	 * @return R
	 */
	@SysLog("商品上下架操作")
	@PutMapping("/shelf")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_edit')")
	public R updateById(@RequestParam(value = "shelf") String shelf, @RequestParam(value = "ids") String ids) {
		GoodsSpu goodsSpu = new GoodsSpu();
		goodsSpu.setShelf(shelf);
		return R.ok(goodsSpuService.update(goodsSpu, Wrappers.<GoodsSpu>lambdaQuery()
				.in(GoodsSpu::getId, Convert.toList(ids))));
	}

	/**
	 * 通过id删除spu商品
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("删除spu商品")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(goodsSpuService.removeById(id));
	}

	/**
	 * 导出商品模板
	 */
	@SysLog("Excel导入模板")
	@GetMapping("/exportTemplate")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_exportTemplate')")
	public void exportTemplate(HttpServletResponse response) {
		// 构建数据
		List<GoodsSpu> list = new ArrayList<>();
		// 构建模板
		ExcelKit.$Export(GoodsSpu.class, response).downXlsx(list, true);
	}

	@SysLog("Excel导入商品")
	@PostMapping("/importData")
	@PreAuthorize("@ato.hasAuthority('mall_goodsspu_importData')")
	public R importData(@RequestParam("File") MultipartFile file) throws Exception {
		try {
			if (file.isEmpty()) {
				throw new Exception("导入数据为空");
			}
			String filename = file.getOriginalFilename();
			// 开始导入操作
			ConvertUtils.register(new DateConverter(null), Date.class);
			Stopwatch stopwatch = Stopwatch.createStarted();
			final List<GoodsSpu> goodsSpuData = Lists.newArrayList();
			final List<GoodsSku> goodsSkugData = Lists.newArrayList();
			final List<GoodsSpu> updateData = Lists.newArrayList();
			final List<GoodsSpu> deleteData = Lists.newArrayList();
			final List<Map<String, Object>> error = Lists.newArrayList();
			final Map<String, String> operatorMap = feignLianghaoService.operatorMap();
			final Map<String, String> packageMap = feignLianghaoService.packageMap();
			final Map<String, String> processMap = feignLianghaoService.processMap();
			ExcelKit.$Import(GoodsSpu.class).readXlsx(file.getInputStream(), new ExcelReadHandler<GoodsSpu>() {
				@Override
				public void onSuccess(int sheet, int row, GoodsSpu goodsSpu) {
					UUID uuid = UUID.randomUUID();
					String str = uuid.toString();
					String goodsSpuId = str.replace("-", "");
					goodsSpu.setId(goodsSpuId);                                    //随机主键ID
					//获取一级分类ID与二级分类ID
					LambdaQueryWrapper<GoodsCategory> queryWapper = new LambdaQueryWrapper<GoodsCategory>();
					queryWapper.eq(GoodsCategory::getName, goodsSpu.getNameType().toUpperCase());    //类型转大写
					List<GoodsCategory> list = goodsCategoryService.list(queryWapper);
					goodsSpu.setCategoryFirst(list.get(0).getParentId());       //一级代理商ID
					goodsSpu.setCategorySecond(list.get(0).getId());            //二级代理商ID
					//上传默认的图片为空
					String[] picUrls = {""};
					goodsSpu.setPicUrls(picUrls);                               //上传默认图片
					goodsSpu.setShelf("0");                                     //是否上架
					goodsSpu.setSort(0);                                        //排序字段
					goodsSpu.setSaleNum(0);                                     //销量初始为0
					goodsSpu.setPriceDown(goodsSpu.getSalesPrice());            //最高价
					goodsSpu.setPriceUp(goodsSpu.getSalesPrice());              //最低价
					goodsSpu.setPhoneSystem(goodsSpu.getPhoneSystem());         //制式
					goodsSpu.setLowDissipation(goodsSpu.getLowDissipation());   //低消
					goodsSpu.setDescription(null);                              //备注
					//根据Excel传入的运营商名称，获取出对应的运营商ID
					if (operatorMap.get(goodsSpu.getOperatorName()) != null) {
						goodsSpu.setOperatorId(operatorMap.get(goodsSpu.getOperatorName()));
					}
					//根据Excel传入的套餐名称，获取出对应的套餐ID
					if (packageMap.get(goodsSpu.getPackageName()) != null) {
						goodsSpu.setPackageId(packageMap.get(goodsSpu.getPackageName()));
					}
					//根据Excel传入的开卡方式名称，获取出对应的开卡方式ID
					if (processMap.get(goodsSpu.getProcessName()) != null) {
						goodsSpu.setProcessId(processMap.get(goodsSpu.getProcessName()));
					}
					goodsSpu.setCreateTime(LocalDateTime.now());                //创建时间
					goodsSpu.setUpdateTime(LocalDateTime.now());                //最后更新时间
					goodsSpu.setSpecType("0");                                  //统一规格
					goodsSpu.setPointsGiveSwitch("0");                          //是否开启积分赠送 0:关闭 1：开启
					goodsSpu.setPointsDeductSwitch("0");						//是否开启积分抵扣开关 0：关闭  1:开启
					goodsSpu.setFreightTemplatId("e2daad9554fc3206bab0ab4788840b42");	//运费模板，默认全国包邮
					goodsSpu.setDelFlag("0");                                   //逻辑删除 0保留 1删除
					goodsSpuData.add(goodsSpu);


					//将数据存入goods_sku表
					GoodsSku goodsSku = new GoodsSku();
					goodsSku.setId(UUID.randomUUID().toString().replace("-", ""));                                    //SKUid
					goodsSku.setCreateTime(LocalDateTime.now());                //创建时间
					goodsSku.setUpdateTime(LocalDateTime.now());                //最后修改时间
					goodsSku.setSpuId(goodsSpuId);                              //spuid
					goodsSku.setSalesPrice(goodsSpu.getSalesPrice());           //销售价
					goodsSku.setMarketPrice(goodsSpu.getMarketPrice());         //市场价
					goodsSku.setCostPrice(goodsSpu.getCostPrice());             //成本价
					goodsSku.setStock(1);                                       //库存默认为1个
					goodsSku.setWeight(new BigDecimal(1));                  //重量
					goodsSku.setVolume(new BigDecimal(2));                  //体积
					goodsSku.setEnable("1");                                    //是否启用  1：启用  0不启用
					goodsSku.setDelFlag("0");                                  	//是否显示	0：显示 1：隐藏
					goodsSku.setSeckillStatus(0);                               //是否参与秒杀 1：参与  0：不参与
					goodsSku.setAuctionStatus(0);								//是否参与竞拍 1：参与  0：不参与
					goodsSkugData.add(goodsSku);
				}

				@Override
				public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
					// 数据校验失败时，记录到 error集合
					error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
				}
			});
			ImmutableMap<String, Object> result = null;
			if (CollectionUtils.isNotEmpty(goodsSpuData) && CollectionUtils.isNotEmpty(goodsSkugData)) {
				// 将合法的记录批量入库
				this.goodsSpuService.saveBatch(goodsSpuData, 100);
				this.goodsSkuService.saveBatch(goodsSkugData, 100);
				result = ImmutableMap.of(
						"time", stopwatch.stop().toString(),
						"data", goodsSpuData,
						"error", error
				);
			}
			if (result != null) {
				return R.ok(true, "成功添加" + (result.size() - 1) + "条数据！");
			} else {
				return R.ok(false, "添加失败！");
			}
		} catch (Exception e) {
			String message = "导入Excel数据失败," + e.getMessage();
			log.error(message);
			throw new Exception(message);
		}
	}
}
