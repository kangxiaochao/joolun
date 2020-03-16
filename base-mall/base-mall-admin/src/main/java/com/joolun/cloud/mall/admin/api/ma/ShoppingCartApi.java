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
import com.joolun.cloud.mall.admin.service.ShoppingCartService;
import com.joolun.cloud.mall.common.entity.ShoppingCart;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车
 *
 * @author JL
 * @date 2019-08-29 21:27:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/shoppingcart")
@Api(value = "shoppingcart", tags = "购物车接口")
public class ShoppingCartApi{

    private final ShoppingCartService shoppingCartService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param shoppingCart 购物车
	 * @return
	 */
    @GetMapping("/page")
    public R getShoppingCartPage(HttpServletRequest request, Page page, ShoppingCart shoppingCart) {
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(shoppingCart, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(shoppingCartService.page2(page, shoppingCart));
    }

	/**
	 * 数量
	 * @param request
	 * @return
	 */
	@GetMapping("/count")
	public R getShoppingCartCount(HttpServletRequest request,ShoppingCart shoppingCart) {
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(shoppingCart, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(shoppingCartService.count(Wrappers.query(shoppingCart)));
	}

	/**
	 * 加入购物车
	 * @param request
	 * @param shoppingCart
	 * @return
	 */
	@PostMapping
	public R save(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(shoppingCart, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(shoppingCartService.save(shoppingCart));
	}

	/**
	 * 修改购物车商品
	 * @param request
	 * @param shoppingCart
	 * @return
	 */
	@PutMapping
	public R edit(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(shoppingCart, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(shoppingCartService.updateById(shoppingCart));
	}

	/**
	 * 删除购物车商品数量
	 * @param request
	 * @param ids
	 * @return
	 */
	@PostMapping("/del")
	public R del(HttpServletRequest request, @RequestBody List<String> ids){
		//检验用户session登录
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		return R.ok(shoppingCartService.removeByIds(ids));
	}
}
