/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.api.ma;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.mall.admin.service.BargainUserService;
import com.joolun.cloud.mall.common.constant.MyReturnCode;
import com.joolun.cloud.mall.common.entity.BargainUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 砍价记录
 *
 * @author JL
 * @date 2019-12-30 11:53:14
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/ma/bargainuser")
@Api(value = "bargainuser", tags = "砍价记录管理Api")
public class BargainUserApi {

    private final BargainUserService bargainUserService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param bargainUser 砍价记录
     * @return
     */
    @GetMapping("/page")
    public R getPage(HttpServletRequest request, Page page, BargainUser bargainUser) {
		R checkThirdSession = BaseApi.checkThirdSession(bargainUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainUserService.page2(page, bargainUser));
    }

    /**
     * 砍价记录查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(HttpServletRequest request, @PathVariable("id") String id) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainUserService.getById(id));
    }

    /**
     * 砍价记录新增，发起砍价
     * @param bargainUser 砍价记录
     * @return R
     */
    @PostMapping
    public R save(HttpServletRequest request, @RequestBody BargainUser bargainUser) {
		R checkThirdSession = BaseApi.checkThirdSession(bargainUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
		if(StrUtil.isBlank(bargainUser.getBargainId())){
			R.failed(MyReturnCode.ERR_80005.getCode(), MyReturnCode.ERR_80005.getMsg());
		}
        return R.ok(bargainUserService.saveBargain(bargainUser));
    }

    /**
     * 砍价记录修改
     * @param bargainUser 砍价记录
     * @return R
     */
    @PutMapping
    public R updateById(HttpServletRequest request, @RequestBody BargainUser bargainUser) {
		R checkThirdSession = BaseApi.checkThirdSession(bargainUser, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainUserService.updateById(bargainUser));
    }

    /**
     * 砍价记录删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R removeById(HttpServletRequest request, @PathVariable String id) {
		R checkThirdSession = BaseApi.checkThirdSession(null, request);
		if(!checkThirdSession.isOk()) {//检验失败，直接返回失败信息
			return checkThirdSession;
		}
        return R.ok(bargainUserService.removeById(id));
    }

}
