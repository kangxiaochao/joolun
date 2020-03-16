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
import com.joolun.cloud.mall.common.entity.FreightTemplat;
import com.joolun.cloud.mall.admin.service.FreightTemplatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * 运费模板
 *
 * @author JL
 * @date 2019-12-24 16:09:31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/freighttemplat")
@Api(value = "freighttemplat", tags = "运费模板管理")
public class FreightTemplatController {

    private final FreightTemplatService freightTemplatService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param freightTemplat 运费模板
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_freighttemplat_index')")
    public R getPage(Page page, FreightTemplat freightTemplat) {
        return R.ok(freightTemplatService.page(page, Wrappers.query(freightTemplat)));
    }

	/**
	 * list列表
	 * @param freightTemplat
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("@ato.hasAuthority('mall_freighttemplat_index')")
	public List<FreightTemplat> getList(FreightTemplat freightTemplat) {
		return freightTemplatService.list(Wrappers.query(freightTemplat));
	}

    /**
     * 运费模板查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_freighttemplat_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(freightTemplatService.getById(id));
    }

    /**
     * 运费模板新增
     * @param freightTemplat 运费模板
     * @return R
     */
    @SysLog("新增运费模板")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_freighttemplat_add')")
    public R save(@RequestBody FreightTemplat freightTemplat) {
        return R.ok(freightTemplatService.save(freightTemplat));
    }

    /**
     * 运费模板修改
     * @param freightTemplat 运费模板
     * @return R
     */
    @SysLog("修改运费模板")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_freighttemplat_edit')")
    public R updateById(@RequestBody FreightTemplat freightTemplat) {
        return R.ok(freightTemplatService.updateById(freightTemplat));
    }

    /**
     * 运费模板删除
     * @param id
     * @return R
     */
    @SysLog("删除运费模板")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_freighttemplat_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(freightTemplatService.removeById(id));
    }

}
