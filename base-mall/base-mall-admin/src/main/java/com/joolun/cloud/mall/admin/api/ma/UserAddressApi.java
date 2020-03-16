/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.UserAddressService;
import com.joolun.cloud.mall.common.entity.UserAddress;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户收货地址
 *
 * @author JL
 * @date 2019-09-11 14:28:59
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/useraddress")
@Api(value = "useraddress", tags = "用户收货地址管理")
public class UserAddressApi {

    private final UserAddressService userAddressService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param userAddress 用户收货地址
    * @return
    */
    @GetMapping("/page")
    public R getUserAddressPage(HttpServletRequest request, Page page, UserAddress userAddress) {
		R checkThirdSession = BaseApi.checkThirdSession(userAddress, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(userAddressService.page(page,Wrappers.query(userAddress)));
    }

    /**
    * 新增、修改用户收货地址
    * @param userAddress 用户收货地址
    * @return R
    */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody UserAddress userAddress){
		R checkThirdSession = BaseApi.checkThirdSession(userAddress, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(userAddressService.saveOrUpdate(userAddress));
    }

    /**
    * 通过id删除用户收货地址
    * @param id
    * @return R
    */
    @DeleteMapping("/{id}")
    public R removeById(HttpServletRequest request, @PathVariable String id){
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(userAddressService.removeById(id));
    }

}
