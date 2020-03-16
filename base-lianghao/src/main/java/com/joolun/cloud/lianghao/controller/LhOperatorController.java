/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.lianghao.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.lianghao.entity.LhOperator;
import com.joolun.cloud.lianghao.service.LhOperatorService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 运营商
 *
 * @author xxz
 * @date 2019-11-07 15:34:41
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/lhoperator")
@Api(value = "lhoperator", tags = "运营商管理")
public class LhOperatorController {

    private final LhOperatorService lhOperatorService;


    /**
    * 分页查询
    * @param page 分页对象
    * @param lhOperator 运营商
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_index')")
    public R getLhOperatorPage(Page page, LhOperator lhOperator) {
        return R.ok(lhOperatorService.page(page,Wrappers.query(lhOperator)));
    }

    /**
    * 通过id查询运营商
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(lhOperatorService.getById(id));
    }

	/**
	 * 查询所有运营商名称
	 */
	@GetMapping("/selectOperator")
	public R selectOperator(){ return R.ok(lhOperatorService.selectOperator());}


	/**
    * 新增运营商
    * @param lhOperator 运营商
    * @return R
    */
    @SysLog("新增运营商")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_add')")
    public R save(@RequestBody LhOperator lhOperator){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		lhOperator.setId(uuidStr);
    	return R.ok(lhOperatorService.save(lhOperator));
    }

    /**
    * 修改运营商
    * @param lhOperator 运营商
    * @return R
    */
    @SysLog("修改运营商")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_edit')")
    public R updateById(@RequestBody LhOperator lhOperator){
        return R.ok(lhOperatorService.updateById(lhOperator));
    }

    /**
    * 通过id删除运营商
    * @param id
    * @return R
    */
    @SysLog("删除运营商")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_del')")
    public R removeById(@PathVariable String id){
        return R.ok(lhOperatorService.removeById(id));
    }

	/**
	 * 获取所有的运营商
	 * @return R
	 * */
    //@SysLog("获取所有的运营商")
	@GetMapping("/all")
    public R selectProviders(){
    	return R.ok(lhOperatorService.list());
	}

	/**
	 * 导出订单模板
	 */
	@SysLog("Excel导入模板")
	@GetMapping("/exportTemplate")
	@PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_exportTemplate')")
	public void exportTemplate(HttpServletResponse response){
		// 构建数据
		List<LhOperator> list = new ArrayList<>();
		// 构建模板
		ExcelKit.$Export(LhOperator.class,response).downXlsx(list,true);
	}

	/**
	 * excel 导入号卡套餐数据
	 * @param file 前端传入的excel表格
	 * @return
	 * @throws Exception
	 */
	@SysLog("Excel导入运营商数据")
	@PostMapping("/importData")
	@PreAuthorize("@ato.hasAuthority('lianghao_lhoperator_importData')")
	public R importData(@RequestParam("File") MultipartFile file) throws Exception{
		try{
			if (file.isEmpty()) {
				throw new Exception("导入数据为空");
			}
			String filename = file.getOriginalFilename();
			// 开始导入操作
			ConvertUtils.register(new DateConverter(null), Date.class);
			Stopwatch stopwatch = Stopwatch.createStarted();
			final List<LhOperator> data = Lists.newArrayList();
			final List<LhOperator> updateData = Lists.newArrayList();
			final List<LhOperator> deleteData = Lists.newArrayList();
			final List<Map<String, Object>> error = Lists.newArrayList();
			ExcelKit.$Import(LhOperator.class).readXlsx(file.getInputStream(), new ExcelReadHandler<LhOperator>() {
				@Override
				public void onSuccess(int sheet, int row, LhOperator lhOperator) {
					//可以对表格的值进行操作
					//System.out.println(lhPackage.toString());
					UUID uuid = UUID.randomUUID();
					String str = uuid.toString();
					String uuidStr = str.replace("-", "");
					lhOperator.setId(uuidStr);
					data.add(lhOperator);
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
				this.lhOperatorService.saveBatch(data,100);
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
