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
import com.joolun.cloud.common.security.util.SecurityUtils;
import com.joolun.cloud.mall.common.entity.MaterialGroup;
import com.joolun.cloud.mall.admin.service.MaterialGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 素材分组
 *
 * @author JL
 * @date 2019-10-26 19:29:07
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/materialgroup")
@Api(value = "materialgroup", tags = "素材分组管理")
public class MaterialGroupController {

    private final MaterialGroupService materialGroupService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param materialGroup 素材分组
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('mall_material_index')")
    public R getMaterialGroupPage(Page page, MaterialGroup materialGroup) {
        return R.ok(materialGroupService.page(page,Wrappers.query(materialGroup)));
    }

    /**
    * 通过id查询素材分组
    * @param id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_material_index')")
    public R getById(@PathVariable("id") String id){
        return R.ok(materialGroupService.getById(id));
    }

    /**
    * 新增素材分组
    * @param materialGroup 素材分组
    * @return R
    */
    @SysLog("新增素材分组")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('mall_material_add')")
    public R save(@RequestBody MaterialGroup materialGroup){
		materialGroup.setCreateId(SecurityUtils.getUser().getId());
        return R.ok(materialGroupService.save(materialGroup));
    }

    /**
    * 修改素材分组
    * @param materialGroup 素材分组
    * @return R
    */
    @SysLog("修改素材分组")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('mall_material_edit')")
    public R updateById(@RequestBody MaterialGroup materialGroup){
        return R.ok(materialGroupService.updateById(materialGroup));
    }

    /**
    * 通过id删除素材分组
    * @param id
    * @return R
    */
    @SysLog("删除素材分组")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('mall_material_del')")
    public R removeById(@PathVariable String id){
        return R.ok(materialGroupService.removeById(id));
    }

}
