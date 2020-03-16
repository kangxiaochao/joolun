/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.weixin.common.constant.WxReturnCode;
import com.joolun.cloud.weixin.admin.service.WxMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 自定义菜单
 *
 * @author JL
 * @date 2019-03-27 16:52:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmenu")
public class WxMenuController {

  private final WxMenuService wxMenuService;

  /**
   * 通过appId查询自定义菜单
   * @param appId
   * @return R
   */
  @GetMapping("/list")
  @PreAuthorize("@ato.hasAuthority('wxmp_wxmenu_get')")
  public R getWxMenuButton(String appId){
    return R.ok(wxMenuService.getWxMenuButton(appId));
  }
  /**
   * 保存并发布菜单
   * @param
   * @return R
   */
  @SysLog("保存并发布菜单")
  @PostMapping
  @PreAuthorize("@ato.hasAuthority('wxmp_wxmenu_add')")
  public R saveAndRelease(@RequestBody String data){
	  JSONObject jSONObject = JSONUtil.parseObj(data);
	  String strWxMenu = jSONObject.getStr("strWxMenu");
	  String appId = jSONObject.getStr("appId");
	  try {
		  wxMenuService.saveAndRelease(appId,strWxMenu);
		  return R.ok(Boolean.TRUE);
	  } catch (WxErrorException e) {
		  e.printStackTrace();
		  log.error("发布自定义菜单失败appID:"+appId+":"+e.getMessage());
		  return WxReturnCode.wxErrorExceptionHandler(e);
	  }
  }
}
