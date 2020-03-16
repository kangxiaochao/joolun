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
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import com.joolun.cloud.common.security.annotation.Inside;
import com.joolun.cloud.weixin.common.constant.WxReturnCode;
import com.joolun.cloud.weixin.common.dto.WxOpenDataDTO;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 微信用户
 *
 * @author JL
 * @date 2019-03-25 15:39:39
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxuser")
public class WxUserController {

	private final WxUserService wxUserService;

	/**
	* 分页查询
	* @param page 分页对象
	* @param wxUser 微信用户
	* @return
	*/
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_index')")
	public R getWxUserPage(Page page, WxUser wxUser,String tagId) {
		Wrapper<WxUser> queryWrapper;
		if(StringUtils.isNotBlank(tagId)){
			queryWrapper = Wrappers.lambdaQuery(wxUser)
					.and(wrapper -> wrapper
							.eq(WxUser::getTagidList,"["+tagId+"]")
							.or()
							.like(WxUser::getTagidList,","+tagId+",")
							.or()
							.likeRight(WxUser::getTagidList,"["+tagId+",")
							.or()
							.likeLeft(WxUser::getTagidList,","+tagId+"]"));
		}else{
			queryWrapper = Wrappers.lambdaQuery(wxUser);
		}
		return R.ok(wxUserService.page(page,queryWrapper));
	}


	/**
	* 通过id查询微信用户
	* @param id id
	* @return R
	*/
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_get')")
	public R getById(@PathVariable("id") String id){
	return R.ok(wxUserService.getById(id));
	}

	/**
	* 新增微信用户
	* @param wxUser 微信用户
	* @return R
	*/
	@SysLog("新增微信用户")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_add')")
	public R save(@RequestBody WxUser wxUser){
	return R.ok(wxUserService.save(wxUser));
	}

	/**
	* 修改微信用户
	* @param wxUser 微信用户
	* @return R
	*/
	@SysLog("修改微信用户")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_edit')")
	public R updateById(@RequestBody WxUser wxUser){
	return R.ok(wxUserService.updateById(wxUser));
	}

	/**
	* 通过id删除微信用户
	* @param id id
	* @return R
	*/
	@SysLog("删除微信用户")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_del')")
	public R removeById(@PathVariable String id){
    return R.ok(wxUserService.removeById(id));
  }

	@SysLog("同步微信用户")
	@PostMapping("/synchron")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_synchro')")
	public R synchron(@RequestBody WxUser wxUser){
		try {
			wxUserService.synchroWxUser(wxUser.getAppId());
			return new R<>();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("同步微信用户失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 修改微信用户备注
	 * @param wxUser
	 * @return
	 */
	@SysLog("修改微信用户备注")
	@PutMapping("/remark")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_edit_remark')")
	public R remark(@RequestBody WxUser wxUser){
		try {
			return R.ok(wxUserService.updateRemark(wxUser));
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改微信用户备注失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 打标签
	 * @param data
	 * @return
	 */
	@PutMapping("/tagid-list")
	@PreAuthorize("@ato.hasAuthority('wxmp_wxuser_tagging')")
	public R tagidList(@RequestBody JSONObject data){
		try {
			String appId = data.getStr("appId");
			String taggingType = data.getStr("taggingType");
			JSONArray tagIdsArray = data.getJSONArray("tagIds");
			JSONArray openIdsArray = data.getJSONArray("openIds");
			String[] openIds = openIdsArray.toArray(new String[0]);
			for(Object tagId : tagIdsArray){
				wxUserService.tagging(taggingType,appId,Long.valueOf(String.valueOf(tagId)),openIds);
			}
			return new R<>();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("修改微信用户备注失败", e);
			return WxReturnCode.wxErrorExceptionHandler(e);
		}
	}

	/**
	 * 通过id查询微信用户
	 * @param id id
	 * @return R
	 */
	@Inside
	@GetMapping("/inside/{id}")
	public R getByIdInside(@PathVariable("id") String id){
		return R.ok(wxUserService.getById(id));
	}

	/**
	 * 保存微信用户
	 * @param wxOpenDataDTO
	 * @return R
	 */
	@Inside
	@PostMapping("/inside")
	public R saveInside(@RequestBody WxOpenDataDTO wxOpenDataDTO){
		return R.ok(wxUserService.saveInside(wxOpenDataDTO));
	}
}
