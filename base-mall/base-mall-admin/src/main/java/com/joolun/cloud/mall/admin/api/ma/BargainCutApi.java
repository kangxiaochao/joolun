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
import com.joolun.cloud.mall.admin.service.BargainCutService;
import com.joolun.cloud.mall.common.entity.BargainCut;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 砍价帮砍记录
 *
 * @author JL
 * @date 2019-12-31 12:34:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/bargaincut")
@Api(value = "bargaincut", tags = "砍价帮砍记录Api")
public class BargainCutApi {

    private final BargainCutService bargainCutService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainCut 砍价帮砍记录
     * @return
     */
    @GetMapping("/page")
    public R getPage(HttpServletRequest request, Page page, BargainCut bargainCut) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainCutService.page(page, Wrappers.query(bargainCut)));
    }

    /**
     * 砍价帮砍记录新增
     * @param bargainCut 砍价帮砍记录
     * @return R
     */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody BargainCut bargainCut) {
		R checkThirdSession = BaseApi.checkThirdSession(bargainCut, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		bargainCutService.save2(bargainCut);
        return R.ok(bargainCut);
    }


}
