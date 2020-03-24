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
import com.joolun.cloud.mall.admin.service.PartnerService;
import com.joolun.cloud.mall.common.entity.Partner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.time.LocalDateTime;

/**
 * 合伙人表
 *
 * @author code generator
 * @date 2020-03-23 09:17:24
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/partner")
@Api(value = "partner", tags = "合伙人表管理")
public class PartnerController {

    private final PartnerService partnerService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param partner 合伙人表
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_partner_index')")
    public R getPage(Page page, Partner partner) {
        return R.ok(partnerService.page(page, Wrappers.query(partner)));
    }

    /**
     * 合伙人表查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_partner_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(partnerService.getById(id));
    }

    /**
     * 合伙人表新增
     * @param partner 合伙人表
     * @return R
     */
    @SysLog("新增合伙人表")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_partner_add')")
    public R save(@RequestBody Partner partner) {
    	partner.setCreateTime(LocalDateTime.now());
        return R.ok(partnerService.save(partner));
    }

    /**
     * 合伙人表修改
     * @param partner 合伙人表
     * @return R
     */
    @SysLog("修改合伙人表")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_partner_edit')")
    public R updateById(@RequestBody Partner partner) {
		partner.setUpdateTime(LocalDateTime.now());
    	return R.ok(partnerService.updateById(partner));
    }

    /**
     * 合伙人表删除
     * @param id
     * @return R
     */
    @SysLog("删除合伙人表")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_partner_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(partnerService.removeById(id));
    }



    //  id  合伙人名称

	/**
	 * 通过id查询
	 * @param id
	 * @return R
	 */
	/*@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('generator_lhnumber_get')")
	public R getById(@PathVariable("id") String id) {
		return R.ok(lhNumberService.getById(id));
	}
*/
}
