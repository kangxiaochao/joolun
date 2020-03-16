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
import com.joolun.cloud.mall.common.entity.Notice;
import com.joolun.cloud.mall.admin.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城通知
 *
 * @author JL
 * @date 2019-11-09 19:37:56
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/notice")
@Api(value = "notice", tags = "商城通知管理")
public class NoticeController {

    private final NoticeService noticeService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param notice 商城通知
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_notice_index')")
    public R getNoticePage(Page page, Notice notice) {
        return R.ok(noticeService.page(page,Wrappers.query(notice)));
    }

    /**
    * 通过id查询商城通知
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_notice_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(noticeService.getById(id));
    }

    /**
    * 新增商城通知
    * @param notice 商城通知
    * @return R
    */
    @SysLog("新增商城通知")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_notice_add')")
    public R save(@RequestBody Notice notice){
        return R.ok(noticeService.save(notice));
    }

    /**
    * 修改商城通知
    * @param notice 商城通知
    * @return R
    */
    @SysLog("修改商城通知")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_notice_edit')")
    public R updateById(@RequestBody Notice notice){
        return R.ok(noticeService.updateById(notice));
    }

    /**
    * 通过id删除商城通知
    * @param id
    * @return R
    */
    @SysLog("删除商城通知")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_notice_del')")
    public R removeById(@PathVariable String id){
        return R.ok(noticeService.removeById(id));
    }

}
