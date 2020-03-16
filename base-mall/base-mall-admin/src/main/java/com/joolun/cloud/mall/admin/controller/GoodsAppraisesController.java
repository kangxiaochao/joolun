/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.common.entity.GoodsAppraises;
import com.joolun.cloud.mall.admin.service.GoodsAppraisesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商品评价
 *
 * @author JL
 * @date 2019-09-23 15:51:01
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodsappraises")
@Api(value = "goodsappraises", tags = "商品评价管理")
public class GoodsAppraisesController {

    private final GoodsAppraisesService goodsAppraisesService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsAppraises 商品评价
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_goodsappraises_index')")
    public R getGoodsAppraisesPage(Page page, GoodsAppraises goodsAppraises) {
        return R.ok(goodsAppraisesService.page1(page,goodsAppraises));
    }

    /**
    * 通过id查询商品评价
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodsappraises_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsAppraisesService.getById(id));
    }

    /**
    * 新增商品评价
    * @param goodsAppraises 商品评价
    * @return R
    */
    @SysLog("新增商品评价")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodsappraises_add')")
    public R save(@RequestBody GoodsAppraises goodsAppraises){
        return R.ok(goodsAppraisesService.save(goodsAppraises));
    }

    /**
    * 修改商品评价
    * @param goodsAppraises 商品评价
    * @return R
    */
    @SysLog("修改商品评价")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodsappraises_edit')")
    public R updateById(@RequestBody GoodsAppraises goodsAppraises){
        return R.ok(goodsAppraisesService.updateById(goodsAppraises));
    }

    /**
    * 通过id删除商品评价
    * @param id
    * @return R
    */
    @SysLog("删除商品评价")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodsappraises_del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsAppraisesService.removeById(id));
    }

}
