/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxMsgService;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import com.joolun.cloud.weixin.common.entity.WxMsg;
import com.joolun.cloud.weixin.common.entity.WxMsgVO;
import com.joolun.cloud.weixin.admin.config.mp.WxMpConfiguration;
import com.joolun.cloud.weixin.common.constant.WxReturnCode;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息
 *
 * @author JL
 * @date 2019-05-28 16:12:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmsg")
@Api(value = "wxmsg", tags = "wxmsg管理")
public class WxMsgController {

    private final WxMsgService wxMsgService;
	private final WxUserService wxUserService;
	private final WxAppService wxAppService;

    /**
    * 分页查询
    * @param page 分页对象
    * @param wxMsgVO 微信消息
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_index')")
    public R getWxMsgPage(Page page, WxMsgVO wxMsgVO) {
    	if(StringUtils.isNotBlank(wxMsgVO.getNotInRepType())){
			return  R.ok(wxMsgService.listWxMsgMapGroup(page,wxMsgVO));
		}
    	if(StringUtils.isNotBlank(wxMsgVO.getWxUserId())){//标记为已读
			WxMsg wxMsg = new WxMsg();
			wxMsg.setReadFlag(CommonConstants.YES);
			Wrapper queryWrapper = Wrappers.<WxMsg>lambdaQuery()
					.eq(WxMsg::getWxUserId,wxMsgVO.getWxUserId())
					.eq(WxMsg::getReadFlag,CommonConstants.NO)
					.eq(WxMsg::getAppId,wxMsgVO.getAppId());
			wxMsgService.update(wxMsg,queryWrapper);
		}
    	return R.ok(wxMsgService.page(page,Wrappers.query(wxMsgVO)));
    }

    /**
    * 通过id查询微信消息
    * @param id id
    * @return R
    */
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_get')")
    public R getById(@PathVariable("id") String id){
    	return R.ok(wxMsgService.getById(id));
    }

    /**
    * 新增微信消息
    * @param wxMsg 微信消息
    * @return R
    */
    @SysLog("新增微信消息")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_add')")
    public R save(@RequestBody WxMsg wxMsg){
		try {
			WxApp wxApp = wxAppService.getById(wxMsg.getAppId());
			WxUser wxUser = wxUserService.getById(wxMsg.getWxUserId());
			//入库
			wxMsg.setAppName(wxApp.getName());
			wxMsg.setAppLogo(wxApp.getLogo());
			wxMsg.setNickName(wxUser.getNickName());
			wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
			wxMsg.setCreateTime(LocalDateTime.now());
			wxMsg.setType(ConfigConstant.WX_MSG_TYPE_2);
			WxMpKefuMessage wxMpKefuMessage = null;
			if(WxConsts.KefuMsgType.TEXT.equals(wxMsg.getRepType())){
				wxMsg.setRepContent(wxMsg.getRepContent());
				wxMpKefuMessage = WxMpKefuMessage.TEXT().build();
				wxMpKefuMessage.setContent(wxMsg.getRepContent());
			}
			if(WxConsts.KefuMsgType.IMAGE.equals(wxMsg.getRepType())){//图片
				wxMsg.setRepName(wxMsg.getRepName());
				wxMsg.setRepUrl(wxMsg.getRepUrl());
				wxMsg.setRepMediaId(wxMsg.getRepMediaId());
				wxMpKefuMessage = WxMpKefuMessage.IMAGE().build();
				wxMpKefuMessage.setMediaId(wxMsg.getRepMediaId());
			}
			if(WxConsts.KefuMsgType.VOICE.equals(wxMsg.getRepType())){
				wxMsg.setRepName(wxMsg.getRepName());
				wxMsg.setRepUrl(wxMsg.getRepUrl());
				wxMsg.setRepMediaId(wxMsg.getRepMediaId());
				wxMpKefuMessage = WxMpKefuMessage.VOICE().build();
				wxMpKefuMessage.setMediaId(wxMsg.getRepMediaId());
			}
			if(WxConsts.KefuMsgType.VIDEO.equals(wxMsg.getRepType())){
				wxMsg.setRepName(wxMsg.getRepName());
				wxMsg.setRepDesc(wxMsg.getRepDesc());
				wxMsg.setRepUrl(wxMsg.getRepUrl());
				wxMsg.setRepMediaId(wxMsg.getRepMediaId());
				wxMpKefuMessage = WxMpKefuMessage.VIDEO().build();
				wxMpKefuMessage.setMediaId(wxMsg.getRepMediaId());
				wxMpKefuMessage.setTitle(wxMsg.getRepName());
				wxMpKefuMessage.setDescription(wxMsg.getRepDesc());
			}
			if(WxConsts.KefuMsgType.MUSIC.equals(wxMsg.getRepType())){
				wxMsg.setRepName(wxMsg.getRepName());
				wxMsg.setRepDesc(wxMsg.getRepDesc());
				wxMsg.setRepUrl(wxMsg.getRepUrl());
				wxMsg.setRepHqUrl(wxMsg.getRepHqUrl());
				wxMpKefuMessage = WxMpKefuMessage.MUSIC().build();
				wxMpKefuMessage.setTitle(wxMsg.getRepName());
				wxMpKefuMessage.setDescription(wxMsg.getRepDesc());
				wxMpKefuMessage.setMusicUrl(wxMsg.getRepUrl());
				wxMpKefuMessage.setHqMusicUrl(wxMsg.getRepHqUrl());
				wxMpKefuMessage.setThumbMediaId(wxMsg.getRepThumbMediaId());
			}
			if(WxConsts.KefuMsgType.NEWS.equals(wxMsg.getRepType())){
				List<WxMpKefuMessage.WxArticle> list = new ArrayList<>();
				JSONArray jSONArray = wxMsg.getContent().getJSONArray("articles");
				WxMpKefuMessage.WxArticle t;
				for(Object object : jSONArray){
					JSONObject jSONObject = JSONUtil.parseObj(JSONUtil.toJsonStr(object));
					t = new WxMpKefuMessage.WxArticle();
					t.setTitle(jSONObject.getStr("title"));
					t.setDescription(jSONObject.getStr("digest"));
					t.setPicUrl(jSONObject.getStr("thumbUrl"));
					t.setUrl(jSONObject.getStr("url"));
					list.add(t);
				}
				wxMsg.setRepName(wxMsg.getRepName());
				wxMsg.setRepDesc(wxMsg.getRepDesc());
				wxMsg.setRepUrl(wxMsg.getRepUrl());
				wxMsg.setRepMediaId(wxMsg.getRepMediaId());
				wxMsg.setContent(wxMsg.getContent());
				wxMpKefuMessage = WxMpKefuMessage.NEWS().build();
				wxMpKefuMessage.setArticles(list);
			}
			if(wxMpKefuMessage != null){
				WxMpKefuService wxMpKefuService = WxMpConfiguration.getMpService(wxMsg.getAppId()).getKefuService();
				wxMpKefuMessage.setToUser(wxUser.getOpenId());
				wxMpKefuService.sendKefuMessage(wxMpKefuMessage);
				wxMsgService.save(wxMsg);
				return R.ok(wxMsg);
			}else{
				return R.failed("非法消息类型");
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("新增微信消息失败"+e.getMessage());
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
    }

    /**
    * 修改微信消息
    * @param wxMsg 微信消息
    * @return R
    */
    @SysLog("修改微信消息")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_edit')")
    public R updateById(@RequestBody WxMsg wxMsg){
    	return R.ok(wxMsgService.updateById(wxMsg));
    }

    /**
    * 通过id删除微信消息
    * @param id id
    * @return R
    */
    @SysLog("删除微信消息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('wxmp_wxmsg_del')")
    public R removeById(@PathVariable String id){
    	return R.ok(wxMsgService.removeById(id));
    }

}
