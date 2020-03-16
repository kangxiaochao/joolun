/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.lianghao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.lianghao.entity.LhOperator;
import com.joolun.cloud.lianghao.entity.LhPackage;
import com.joolun.cloud.lianghao.service.LhOperatorService;
import com.joolun.cloud.lianghao.service.LhPackageService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 号卡套餐
 *
 * @author code generator
 * @date 2019-11-07 14:48:20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/lhpackage")
@Api(value = "lhpackage", tags = "号卡套餐管理")
public class LhPackageController {

    private final LhPackageService lhPackageService;
	private final LhOperatorService lhOperatorService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param lhPackage 号卡套餐
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_index')")
    public R getLhPackagePage(Page page, LhPackage lhPackage) {
        return R.ok(lhPackageService.page(page,Wrappers.query(lhPackage)));
    }

    /**
    * 通过id查询号卡套餐
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(lhPackageService.getById(id));
    }

	/**
	 * 查询所有套餐
	 */
	@GetMapping("/selectPackage")
	public R selectPackage(){ return R.ok(lhPackageService.selectPackage()); }

	/**
    * 新增号卡套餐
    * @param lhPackage 号卡套餐
    * @return R
    */
    @SysLog("新增号卡套餐")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_add')")
    public R save(@RequestBody LhPackage lhPackage){
        return R.ok(lhPackageService.save(lhPackage));
    }

    /**
    * 修改号卡套餐
    * @param lhPackage 号卡套餐
    * @return R
    */
    @SysLog("修改号卡套餐")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_edit')")
    public R updateById(@RequestBody LhPackage lhPackage){
        return R.ok(lhPackageService.updateById(lhPackage));
    }

    /**
    * 通过id删除号卡套餐
    * @param id
    * @return R
    */
    @SysLog("删除号卡套餐")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_del')")
    public R removeById(@PathVariable String id){
        return R.ok(lhPackageService.removeById(id));
    }

	/**
	 * 导出订单模板
	 */
	@SysLog("Excel导入模板")
	@GetMapping("/exportTemplate")
	@PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_exportTemplate')")
	public void exportTemplate(HttpServletResponse response){
		// 构建数据
		List<LhPackage> list = new ArrayList<>();
		// 构建模板
		ExcelKit.$Export(LhPackage.class,response).downXlsx(list,true);
	}

	/**
	 * excel 导入号卡套餐数据
	 * @param file 前端传入的excel表格
	 * @return
	 * @throws Exception
	 */
	@SysLog("Excel导入号卡套餐")
	@PostMapping("/importData")
	@PreAuthorize("@ato.hasAuthority('lianghao_lhpackage_importData')")
	public R importData(@RequestParam("File") MultipartFile file) throws Exception{
		try{
			if (file.isEmpty()) {
				throw new Exception("导入数据为空");
			}
			String filename = file.getOriginalFilename();
			// 开始导入操作
			ConvertUtils.register(new DateConverter(null), Date.class);
			Stopwatch stopwatch = Stopwatch.createStarted();
			final List<LhPackage> data = Lists.newArrayList();
			final List<LhPackage> updateData = Lists.newArrayList();
			final List<LhPackage> deleteData = Lists.newArrayList();
			final List<Map<String, Object>> error = Lists.newArrayList();
			ExcelKit.$Import(LhPackage.class).readXlsx(file.getInputStream(), new ExcelReadHandler<LhPackage>() {
				@Override
				public void onSuccess(int sheet, int row, LhPackage lhPackage) {
					//可以对表格的值进行操作
					//System.out.println(lhPackage.toString());
					UUID uuid = UUID.randomUUID();
					String str = uuid.toString();
					String uuidStr = str.replace("-", "");
					lhPackage.setId(uuidStr);
					LambdaQueryWrapper<LhOperator> queryWapper = new LambdaQueryWrapper<LhOperator>();
					queryWapper.eq(LhOperator::getOperatorName,lhPackage.getOperatorId());
					List<LhOperator> list = lhOperatorService.list(queryWapper);
					lhPackage.setOperatorId(list.get(0).getId());
					data.add(lhPackage);
				}
				@Override
				public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
					// 数据校验失败时，记录到 error集合
					error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
				}
			});
			ImmutableMap<String, Object> result = null;
			if (CollectionUtils.isNotEmpty(data)) {
				// 将合法的记录批量入库
				this.lhPackageService.saveBatch(data,100);
				result = ImmutableMap.of(
						"time", stopwatch.stop().toString(),
						"data", data,
						"error", error
				);
			}
			if(result != null){
				return R.ok(true,"成功添加"+(result.size()-1)+"条数据！");
			}else{
				return R.ok(false,"添加失败！");
			}
		}catch (Exception e){
			String message = "导入Excel数据失败," + e.getMessage();
			log.error(message);
			throw new Exception(message);
		}
	}
}
