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
import com.joolun.cloud.common.security.annotation.Inside;
import com.joolun.cloud.mall.common.entity.UserInfo;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 商城用户
 *
 * @author JL
 * @date 2019-12-04 11:09:55
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userinfo")
@Api(value = "userinfo", tags = "商城用户管理")
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param userInfo 商城用户
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_userinfo_index')")
    public R getUserinfoPage(Page page, UserInfo userInfo) {
        return R.ok(userInfoService.page(page, Wrappers.query(userInfo)));
    }

    /**
     * 通过id查询商城用户
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_userinfo_get')")
    public R getById(@PathVariable("id") String id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 新增商城用户
     * @param userInfo 商城用户
     * @return R
     */
    @SysLog("新增商城用户")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_userinfo_add')")
    public R save(@RequestBody UserInfo userInfo) {
        return R.ok(userInfoService.save(userInfo));
    }

    /**
     * 修改商城用户
     * @param userInfo 商城用户
     * @return R
     */
    @SysLog("修改商城用户")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_userinfo_edit')")
    public R updateById(@RequestBody UserInfo userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    /**
     * 通过id删除商城用户
     * @param id
     * @return R
     */
    @SysLog("删除商城用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_userinfo_del')")
    public R removeById(@PathVariable String id) {
        return R.ok(userInfoService.removeById(id));
    }

	/**
	 * 新增商城用户（供服务间调用）
	 * @param userInfo 商城用户
	 * @return R
	 */
	@Inside
	@PostMapping("/inside")
	public R saveInside(@RequestBody UserInfo userInfo) {
		userInfoService.saveInside(userInfo);
		return R.ok(userInfo);
	}
}
