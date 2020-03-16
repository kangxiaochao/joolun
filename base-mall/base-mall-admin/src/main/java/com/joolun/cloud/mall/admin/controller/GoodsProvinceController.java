
/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.GoodsProvinceService;
import com.joolun.cloud.mall.common.entity.GoodsProvince;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 省份表
 *
 * @author code generator
 * @date 2019-12-09 15:37:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsprovince")
@Api(value = "goodsprovince", tags = "省份表管理")
public class GoodsProvinceController {

    private final GoodsProvinceService goodsProvinceService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param goodsProvince 省份表
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('generator_goodsprovince_index')")
    public R getGoodsProvincePage(Page page, GoodsProvince goodsProvince) {
        return R.ok(goodsProvinceService.page(page, Wrappers.query(goodsProvince)));
    }

	/**
	 * 查询省份
	 */
	@GetMapping("/selectProvince")
	public R selectProvince(){ return  R.ok(goodsProvinceService.selectProvince());}

	/**
     * 通过id查询省份表
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_goodsprovince_get')")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(goodsProvinceService.getById(id));
    }

    /**
     * 新增省份表
     * @param goodsProvince 省份表
     * @return R
     */
    @SysLog("新增省份表")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('generator_goodsprovince_add')")
    public R save(@RequestBody GoodsProvince goodsProvince) {
        return R.ok(goodsProvinceService.save(goodsProvince));
    }

    /**
     * 修改省份表
     * @param goodsProvince 省份表
     * @return R
     */
    @SysLog("修改省份表")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('generator_goodsprovince_edit')")
    public R updateById(@RequestBody GoodsProvince goodsProvince) {
        return R.ok(goodsProvinceService.updateById(goodsProvince));
    }

    /**
     * 通过id删除省份表
     * @param id
     * @return R
     */
    @SysLog("删除省份表")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_goodsprovince_del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(goodsProvinceService.removeById(id));
    }

}
