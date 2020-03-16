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
import com.joolun.cloud.mall.admin.service.GoodsCityService;
import com.joolun.cloud.mall.common.entity.GoodsCity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 城市表
 *
 * @author code generator
 * @date 2019-12-09 15:37:23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodscity")
@Api(value = "goodscity", tags = "城市表管理")
public class GoodsCityController {

    private final GoodsCityService goodsCityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param goodsCity 城市表
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('generator_goodscity_index')")
    public R getGoodsCityPage(Page page, GoodsCity goodsCity) {
        return R.ok(goodsCityService.page(page, Wrappers.query(goodsCity)));
    }

	/**
	 * 根据获取的省份，查询出对应的市区
	 */
	@GetMapping("/selectCity/{provinceName}")
	public R selectCity(@PathVariable("provinceName") String provinceName){ return R.ok(goodsCityService.selectCity(provinceName)); }

	/**
     * 通过id查询城市表
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_goodscity_get')")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(goodsCityService.getById(id));
    }

    /**
     * 新增城市表
     * @param goodsCity 城市表
     * @return R
     */
    @SysLog("新增城市表")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('generator_goodscity_add')")
    public R save(@RequestBody GoodsCity goodsCity) {
        return R.ok(goodsCityService.save(goodsCity));
    }

    /**
     * 修改城市表
     * @param goodsCity 城市表
     * @return R
     */
    @SysLog("修改城市表")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('generator_goodscity_edit')")
    public R updateById(@RequestBody GoodsCity goodsCity) {
        return R.ok(goodsCityService.updateById(goodsCity));
    }

    /**
     * 通过id删除城市表
     * @param id
     * @return R
     */
    @SysLog("删除城市表")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('generator_goodscity_del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(goodsCityService.removeById(id));
    }

}
