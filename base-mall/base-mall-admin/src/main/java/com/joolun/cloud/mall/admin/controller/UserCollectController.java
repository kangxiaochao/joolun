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
import com.joolun.cloud.mall.common.entity.UserCollect;
import com.joolun.cloud.mall.admin.service.UserCollectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 用户收藏
 *
 * @author JL
 * @date 2019-09-22 20:45:45
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/usercollect")
@Api(value = "usercollect", tags = "用户收藏管理")
public class UserCollectController {

    private final UserCollectService userCollectService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param userCollect 用户收藏
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_usercollect_index')")
    public R getUserCollectPage(Page page, UserCollect userCollect) {
        return R.ok(userCollectService.page(page,Wrappers.query(userCollect)));
    }

    /**
    * 通过id查询用户收藏
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_usercollect_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(userCollectService.getById(id));
    }

    /**
    * 新增用户收藏
    * @param userCollect 用户收藏
    * @return R
    */
    @SysLog("新增用户收藏")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_usercollect_add')")
    public R save(@RequestBody UserCollect userCollect){
        return R.ok(userCollectService.save(userCollect));
    }

    /**
    * 修改用户收藏
    * @param userCollect 用户收藏
    * @return R
    */
    @SysLog("修改用户收藏")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_usercollect_edit')")
    public R updateById(@RequestBody UserCollect userCollect){
        return R.ok(userCollectService.updateById(userCollect));
    }

    /**
    * 通过id删除用户收藏
    * @param id
    * @return R
    */
    @SysLog("删除用户收藏")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_usercollect_del')")
    public R removeById(@PathVariable String id){
        return R.ok(userCollectService.removeById(id));
    }

}
