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
import com.joolun.cloud.mall.common.entity.ShoppingCart;
import com.joolun.cloud.mall.admin.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 购物车
 *
 * @author JL
 * @date 2019-08-29 21:27:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/shoppingcart")
@Api(value = "shoppingcart", tags = "购物车管理")
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param shoppingCart 购物车
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('admin_shoppingcart_index')")
	public R getShoppingCartPage(Page page, ShoppingCart shoppingCart) {
		return R.ok(shoppingCartService.page(page,Wrappers.query(shoppingCart)));
	}

	/**
	 * 通过id查询购物车
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('admin_shoppingcart_get')")
	public R getById(@PathVariable("id") String id){
		return R.ok(shoppingCartService.getById(id));
	}

	/**
	 * 新增购物车
	 * @param shoppingCart 购物车
	 * @return R
	 */
	@SysLog("新增购物车")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('admin_shoppingcart_add')")
	public R save(@RequestBody ShoppingCart shoppingCart){
		return R.ok(shoppingCartService.save(shoppingCart));
	}

	/**
	 * 修改购物车
	 * @param shoppingCart 购物车
	 * @return R
	 */
	@SysLog("修改购物车")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('admin_shoppingcart_edit')")
	public R updateById(@RequestBody ShoppingCart shoppingCart){
		return R.ok(shoppingCartService.updateById(shoppingCart));
	}

	/**
	 * 通过id删除购物车
	 * @param id
	 * @return R
	 */
	@SysLog("删除购物车")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('admin_shoppingcart_del')")
	public R removeById(@PathVariable String id){
		return R.ok(shoppingCartService.removeById(id));
	}

}
