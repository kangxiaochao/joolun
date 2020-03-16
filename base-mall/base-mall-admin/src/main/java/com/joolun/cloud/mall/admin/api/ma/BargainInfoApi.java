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
import com.joolun.cloud.mall.admin.service.BargainInfoService;
import com.joolun.cloud.mall.common.entity.BargainCut;
import com.joolun.cloud.mall.common.entity.BargainInfo;
import com.joolun.cloud.mall.common.entity.BargainUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 砍价
 *
 * @author JL
 * @date 2019-12-28 18:07:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/bargaininfo")
@Api(value = "bargaininfo", tags = "砍价管理Api")
public class BargainInfoApi {

    private final BargainInfoService bargainInfoService;
	private final BargainCutService bargainCutService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainInfo 砍价
     * @return
     */
    @GetMapping("/page")
    public R getPage(HttpServletRequest request, Page page, BargainInfo bargainInfo) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainInfoService.page2(page, bargainInfo));
    }

    /**
     * 砍价查询
     * @param bargainUser
     * @return R
     */
    @GetMapping
    public R get(HttpServletRequest request, BargainUser bargainUser) {
		R checkThirdSession = BaseApi.checkThirdSession(bargainUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		BargainInfo bargainInfo = bargainInfoService.getOne2(bargainUser);
		if(bargainInfo.getBargainUser() != null){
			//获取已砍金额
			bargainInfo.getBargainUser().setHavBargainAmount(bargainCutService.getTotalCutPrice(bargainInfo.getBargainUser().getId()));
			//获取当前用户的砍价信息
			BargainCut bargainCut = bargainCutService.getOne(Wrappers.<BargainCut>lambdaQuery()
					.eq(BargainCut::getBargainUserId,bargainInfo.getBargainUser().getId())
					.eq(BargainCut::getUserId,bargainUser.getUserId()));
			bargainInfo.getBargainUser().setBargainCut(bargainCut);
		}
        return R.ok(bargainInfo);
    }

}
