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
import com.joolun.cloud.mall.common.entity.BargainCut;
import com.joolun.cloud.mall.admin.service.BargainCutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 砍价帮砍记录
 *
 * @author JL
 * @date 2019-12-31 12:34:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bargaincut")
@Api(value = "bargaincut", tags = "砍价帮砍记录管理")
public class BargainCutController {

    private final BargainCutService bargainCutService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainCut 砍价帮砍记录
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_bargainuser_index')")
    public R getPage(Page page, BargainCut bargainCut) {
        return R.ok(bargainCutService.page(page, Wrappers.query(bargainCut)));
    }

    /**
     * 砍价帮砍记录查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_bargaincut_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(bargainCutService.getById(id));
    }

    /**
     * 砍价帮砍记录新增
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
    @SysLog("新增砍价帮砍记录")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_bargaincut_add')")
    public R save(@RequestBody BargainCut bargainCut) {
        return R.ok(bargainCutService.save(bargainCut));
    }

    /**
     * 砍价帮砍记录修改
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
    @SysLog("修改砍价帮砍记录")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_bargaincut_edit')")
    public R updateById(@RequestBody BargainCut bargainCut) {
        return R.ok(bargainCutService.updateById(bargainCut));
    }

    /**
     * 砍价帮砍记录删除
     * @param id
     * @return R
     */
    @SysLog("删除砍价帮砍记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_bargaincut_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(bargainCutService.removeById(id));
    }

}
