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
import com.joolun.cloud.mall.common.entity.GoodsSpec;
import com.joolun.cloud.mall.admin.service.GoodsSpecService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 规格
 *
 * @author JL
 * @date 2019-08-13 16:10:54
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsspec")
@Api(value = "goodsspec", tags = "规格管理")
public class GoodsSpecController {

    private final GoodsSpecService goodsSpecService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsSpec 规格
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_goodsspec_index')")
    public R getGoodsSpecPage(Page page, GoodsSpec goodsSpec) {
        return R.ok(goodsSpecService.page(page,Wrappers.query(goodsSpec)));
    }

	@GetMapping("/list")
	public R getGoodsSpecList(GoodsSpec goodsSpec) {
		return R.ok(goodsSpecService.list(Wrappers.query(goodsSpec)));
	}

    /**
    * 通过id查询规格
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodsspec_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsSpecService.getById(id));
    }

    /**
    * 新增规格
    * @param goodsSpec 规格
    * @return R
    */
    @SysLog("新增规格")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodsspu_index')")
    public R save(@RequestBody GoodsSpec goodsSpec){
		goodsSpecService.save(goodsSpec);
        return R.ok(goodsSpec);
    }

    /**
    * 修改规格
    * @param goodsSpec 规格
    * @return R
    */
    @SysLog("修改规格")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodsspec_edit')")
    public R updateById(@RequestBody GoodsSpec goodsSpec){
        return R.ok(goodsSpecService.updateById(goodsSpec));
    }

    /**
    * 通过id删除规格
    * @param id
    * @return R
    */
    @SysLog("删除规格")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodsspec_del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsSpecService.removeById(id));
    }

}
