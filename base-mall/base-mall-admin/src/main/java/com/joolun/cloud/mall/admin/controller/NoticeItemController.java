/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.NoticeService;
import com.joolun.cloud.mall.common.entity.Notice;
import com.joolun.cloud.mall.common.entity.NoticeItem;
import com.joolun.cloud.mall.admin.service.NoticeItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城通知详情
 *
 * @author JL
 * @date 2019-11-09 19:39:03
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/noticeitem")
@Api(value = "noticeitem", tags = "商城通知详情管理")
public class NoticeItemController {

    private final NoticeItemService noticeItemService;

	private final NoticeService noticeService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param noticeItem 商城通知详情
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_noticeitem_index')")
    public R getNoticeItemPage(Page page, NoticeItem noticeItem) {
		Notice notice = new Notice();
		notice.setAppId(noticeItem.getAppId());
		notice.setType(noticeItem.getNoticeType());
		notice = noticeService.getOne(Wrappers.query(notice));
		if(notice == null){
			return R.ok();
		}
		noticeItem.setNoticeId(notice.getId());
        return R.ok(noticeItemService.page(page,Wrappers.query(noticeItem)));
    }

    /**
    * 通过id查询商城通知详情
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_noticeitem_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(noticeItemService.getById(id));
    }

    /**
    * 新增商城通知详情
    * @param noticeItem 商城通知详情
    * @return R
    */
    @SysLog("新增商城通知详情")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_noticeitem_add')")
    public R save(@RequestBody NoticeItem noticeItem){
        return R.ok(noticeItemService.save(noticeItem));
    }

    /**
    * 修改商城通知详情
    * @param noticeItem 商城通知详情
    * @return R
    */
    @SysLog("修改商城通知详情")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_noticeitem_edit')")
    public R updateById(@RequestBody NoticeItem noticeItem){
        return R.ok(noticeItemService.updateById(noticeItem));
    }

    /**
    * 通过id删除商城通知详情
    * @param id
    * @return R
    */
    @SysLog("删除商城通知详情")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_noticeitem_del')")
    public R removeById(@PathVariable String id){
        return R.ok(noticeItemService.removeById(id));
    }

}
