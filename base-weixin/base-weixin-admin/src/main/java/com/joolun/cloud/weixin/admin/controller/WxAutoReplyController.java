/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.admin.service.WxAutoReplyService;
import com.joolun.cloud.weixin.common.entity.WxAutoReply;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 消息自动回复
 *
 * @author JL
 * @date 2019-04-18 15:40:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxautoreply")
public class WxAutoReplyController {

    private final WxAutoReplyService wxAutoReplyService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param wxAutoReply 消息自动回复
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxautoreply_index')")
    public R getWxAutoReplyPage(Page page, WxAutoReply wxAutoReply) {
    	return R.ok(wxAutoReplyService.page(page,Wrappers.query(wxAutoReply)));
    }


    /**
    * 通过id查询消息自动回复
    * @param id id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxautoreply_get')")
    public R getById(@PathVariable("id") String id){
    return R.ok(wxAutoReplyService.getById(id));
    }

    /**
    * 新增消息自动回复
    * @param wxAutoReply 消息自动回复
    * @return R
    */
    @SysLog("新增消息自动回复")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('wxmp_wxautoreply_add')")
    public R save(@RequestBody WxAutoReply wxAutoReply){
		this.jude(wxAutoReply);
    	return R.ok(wxAutoReplyService.save(wxAutoReply));
    }

    /**
    * 修改消息自动回复
    * @param wxAutoReply 消息自动回复
    * @return R
    */
    @SysLog("修改消息自动回复")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('wxmp_wxautoreply_edit')")
    public R updateById(@RequestBody WxAutoReply wxAutoReply){
		this.jude(wxAutoReply);
    	return R.ok(wxAutoReplyService.updateById(wxAutoReply));
    }

    /**
    * 通过id删除消息自动回复
    * @param id id
    * @return R
    */
    @SysLog("删除消息自动回复")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxautoreply_del')")
    public R removeById(@PathVariable String id){
    return R.ok(wxAutoReplyService.removeById(id));
    }

	/**
	 * //校验参数
	 * @param wxAutoReply
	 */
	public void jude(WxAutoReply wxAutoReply){
		if(ConfigConstant.WX_AUTO_REPLY_TYPE_2.equals(wxAutoReply.getType())){
			Wrapper<WxAutoReply> queryWrapper = Wrappers.<WxAutoReply>lambdaQuery()
					.eq(WxAutoReply::getReqType,wxAutoReply.getReqType())
					.eq(WxAutoReply::getAppId,wxAutoReply.getAppId());
			List<WxAutoReply> list = wxAutoReplyService.list(queryWrapper);
			if(StringUtils.isNotBlank(wxAutoReply.getId())){
				if(list != null && list.size() == 1){
					if(!list.get(0).getId().equals(wxAutoReply.getId())){
						throw new RuntimeException("请求消息类型重复");
					}
				}
				if(list != null && list.size()>1){
					throw new RuntimeException("请求消息类型重复");
				}
			}
		}
		if(ConfigConstant.WX_AUTO_REPLY_TYPE_3.equals(wxAutoReply.getType())){
			Wrapper<WxAutoReply> queryWrapper = Wrappers.<WxAutoReply>lambdaQuery()
					.eq(WxAutoReply::getReqKey,wxAutoReply.getReqKey())
					.eq(WxAutoReply::getRepType,wxAutoReply.getRepMate())
					.eq(WxAutoReply::getAppId,wxAutoReply.getAppId());
			List<WxAutoReply> list = wxAutoReplyService.list(queryWrapper);
			if(list != null && list.size() == 1){
				if(!list.get(0).getId().equals(wxAutoReply.getId())){
					throw new RuntimeException("关键词重复");
				}
			}
			if(list != null && list.size()>1){
				throw new RuntimeException("关键词重复");
			}
		}
	}
}
