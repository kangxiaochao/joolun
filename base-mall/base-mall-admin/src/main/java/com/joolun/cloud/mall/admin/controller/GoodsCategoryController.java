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
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.common.entity.GoodsCategory;
import com.joolun.cloud.mall.common.entity.GoodsCategoryTree;
import com.joolun.cloud.mall.admin.service.GoodsCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * 商品类目
 *
 * @author JL
 * @date 2019-08-12 11:46:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goodscategory")
@Api(value = "goodscategory", tags = "goodscategory管理")
public class GoodsCategoryController {

    private final GoodsCategoryService goodsCategoryService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param goodsCategory 商品类目
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_goodscategory_index')")
    public R getGoodsCategoryPage(Page page, GoodsCategory goodsCategory) {
        return R.ok(goodsCategoryService.page(page,Wrappers.query(goodsCategory)));
    }

	/**
	 *  返回树形父类集合
	 * @return
	 */
	@GetMapping("/parentTree")
	@PreAuthorize("@ato.hasAuthority('mall_goodscategory_index')")
	public List<GoodsCategoryTree> getGoodsCategoryParentTree() {
		GoodsCategory goodsCategory = new GoodsCategory();
		goodsCategory.setParentId(CommonConstants.PARENT_ID);
		List<GoodsCategoryTree> listGoodsCategoryTree = goodsCategoryService.selectTree(goodsCategory);
		GoodsCategoryTree goodsCategoryTree = new GoodsCategoryTree();
		goodsCategoryTree.setId(CommonConstants.PARENT_ID);
		goodsCategoryTree.setName("顶级分类");
		listGoodsCategoryTree.add(0,goodsCategoryTree);
		return listGoodsCategoryTree;
	}

	/**
	 *  返回树形集合
	 * @return
	 */
	@GetMapping("/tree")
	@PreAuthorize("@ato.hasAuthority('mall_goodscategory_index')")
	public R getGoodsCategoryTree() {
		return R.ok(goodsCategoryService.selectTree(null));
	}

    /**
    * 通过id查询商品类目
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodscategory_get')")
    public R getById(@PathVariable("id") String id){
        return R.ok(goodsCategoryService.getById(id));
    }

    /**
    * 新增商品类目
    * @param goodsCategory 商品类目
    * @return R
    */
    @SysLog("新增商品类目")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodscategory_add')")
    public R save(@RequestBody GoodsCategory goodsCategory){
        return R.ok(goodsCategoryService.save(goodsCategory));
    }

    /**
    * 修改商品类目
    * @param goodsCategory 商品类目
    * @return R
    */
    @SysLog("修改商品类目")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_goodscategory_edit')")
    public R updateById(@RequestBody GoodsCategory goodsCategory){
    	if(goodsCategory.getId().equals(goodsCategory.getParentId())){
			return R.failed("不能将本级设为父类");
		}
        return R.ok(goodsCategoryService.updateById(goodsCategory));
    }

    /**
    * 通过id删除商品类目
    * @param id
    * @return R
    */
    @SysLog("删除商品类目")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_goodscategory_del')")
    public R removeById(@PathVariable String id){
        return R.ok(goodsCategoryService.removeById(id));
    }

}
