/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.weixin.admin.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.weixin.admin.config.ma.WxMaConfiguration;
import com.joolun.cloud.weixin.common.constant.ConfigConstant;
import com.joolun.cloud.weixin.admin.handler.SubscribeHandler;
import com.joolun.cloud.weixin.admin.service.WxAppService;
import com.joolun.cloud.weixin.admin.service.WxUserService;
import com.joolun.cloud.weixin.admin.mapper.WxUserMapper;
import com.joolun.cloud.weixin.admin.config.mp.WxMpConfiguration;
import com.joolun.cloud.weixin.common.constant.WxMaConstants;
import com.joolun.cloud.weixin.common.dto.MallUserInfoDTO;
import com.joolun.cloud.weixin.common.dto.WxOpenDataDTO;
import com.joolun.cloud.weixin.common.entity.ThirdSession;
import com.joolun.cloud.weixin.common.entity.WxApp;
import com.joolun.cloud.weixin.common.entity.WxUser;
import com.joolun.cloud.weixin.common.feign.FeignMallUserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 微信用户
 *
 * @author JL
 * @date 2019-03-25 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

	private final WxAppService wxAppService;
	private final FeignMallUserInfoService feignMallUserInfoService;
	private final RedisTemplate redisTemplate;

	@Override
	public WxUser getByOpenId(String appId, String openId){
		return this.baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
				.eq(WxUser::getAppId, appId)
				.eq(WxUser::getOpenId,openId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateRemark(WxUser entity) throws WxErrorException {
		String id = entity.getId();
		String remark = entity.getRemark();
		String appId = entity.getAppId();
		String openId = entity.getOpenId();
		entity = new WxUser();
		entity.setId(id);
		entity.setRemark(remark);
		super.updateById(entity);
		WxMpUserService wxMpUserService = WxMpConfiguration.getMpService(appId).getUserService();
		wxMpUserService.userUpdateRemark(openId,remark);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void tagging(String taggingType,String appId,Long tagId,String[] openIds) throws WxErrorException {
		WxMpUserTagService wxMpUserTagService = WxMpConfiguration.getMpService(appId).getUserTagService();
		WxUser wxUser;
		if("tagging".equals(taggingType)){
			for(String openId : openIds){
				wxUser = this.getByOpenId(appId, openId);
				Long[] tagidList = wxUser.getTagidList();
				List<Long> list = Arrays.asList(tagidList);
				list = new ArrayList<>(list);
				if(!list.contains(tagId)){
					list.add(tagId);
					tagidList = list.toArray(new Long[list.size()]);
					wxUser.setTagidList(tagidList);
					this.updateById(wxUser);
				}
			}
			wxMpUserTagService.batchTagging(tagId,openIds);
		}
		if("unTagging".equals(taggingType)){
			for(String openId : openIds){
				wxUser = this.getByOpenId(appId, openId);
				Long[] tagidList = wxUser.getTagidList();
				List<Long> list = Arrays.asList(tagidList);
				list = new ArrayList<>(list);
				if(list.contains(tagId)){
					list.remove(tagId);
					tagidList = list.toArray(new Long[list.size()]);
					wxUser.setTagidList(tagidList);
					this.updateById(wxUser);
				}
			}
			wxMpUserTagService.batchUntagging(tagId,openIds);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void synchroWxUser(String appId) throws WxErrorException {
		//先将已关注的用户取关
		WxUser wxUser = new WxUser();
		wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);
		this.baseMapper.update(wxUser, Wrappers.<WxUser>lambdaQuery()
				.eq(WxUser::getAppId, appId)
				.eq(WxUser::getSubscribe, ConfigConstant.SUBSCRIBE_TYPE_YES));
		WxMpUserService wxMpUserService = WxMpConfiguration.getMpService(appId).getUserService();
		WxApp wxApp = wxAppService.getById(appId);
		this.recursionGet(wxApp,wxMpUserService,null);
	}

	/**
	 * 递归获取
	 * @param nextOpenid
	 */
	void recursionGet(WxApp wxApp,WxMpUserService wxMpUserService,String nextOpenid) throws WxErrorException {
		WxMpUserList userList = wxMpUserService.userList(nextOpenid);
		List<WxUser> listWxUser = new ArrayList<>();
		List<WxMpUser> listWxMpUser = getWxMpUserList(wxMpUserService,userList.getOpenids());
		listWxMpUser.forEach(wxMpUser -> {
			WxUser wxUser = this.getByOpenId(wxApp.getId(),wxMpUser.getOpenId());
			if(wxUser == null){//用户未存在
				wxUser = new WxUser();
				wxUser.setSubscribeNum(1);
			}
			SubscribeHandler.setWxUserValue(wxApp,wxUser,wxMpUser);
			listWxUser.add(wxUser);
		});
		this.saveOrUpdateBatch(listWxUser);
		if(userList.getCount() >= 10000){
			this.recursionGet(wxApp,wxMpUserService,userList.getNextOpenid());
		}
	}

	/**
	 * 分批次获取微信粉丝信息 每批100条
	 * @param wxMpUserService
	 * @param openidsList
	 * @return
	 * @throws WxErrorException
	 * @author
	 */
	private List<WxMpUser> getWxMpUserList(WxMpUserService wxMpUserService, List<String> openidsList) throws WxErrorException {
		// 粉丝openid数量
		int count = openidsList.size();
		if (count <= 0) {
			return new ArrayList<>();
		}
		List<WxMpUser> list = Lists.newArrayList();
		List<WxMpUser> followersInfoList;
		int a = count % 100 > 0 ? count / 100 + 1 : count / 100;
		for (int i = 0; i < a; i++) {
			if (i + 1 < a) {
				log.debug("i:{},from:{},to:{}", i, i * 100, (i + 1) * 100);
				followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, ((i + 1) * 100)));
				if (null != followersInfoList && !followersInfoList.isEmpty()) {
					list.addAll(followersInfoList);
				}
			}
			else {
				log.debug("i:{},from:{},to:{}", i, i * 100, count - i * 100);
				followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, count));
				if (null != followersInfoList && !followersInfoList.isEmpty()) {
					list.addAll(followersInfoList);
				}
			}
		}
		log.debug("本批次获取微信粉丝数：",list.size());
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public WxUser saveInside(WxOpenDataDTO wxOpenDataDTO) {
		WxMaUserService wxMaUserService = WxMaConfiguration.getMaService(wxOpenDataDTO.getAppId()).getUserService();
		WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(wxOpenDataDTO.getSessionKey(), wxOpenDataDTO.getEncryptedData(), wxOpenDataDTO.getIv());
		WxUser wxUser = new WxUser();
		BeanUtil.copyProperties(wxMaUserInfo,wxUser);
		wxUser.setId(wxOpenDataDTO.getUserId());
		wxUser.setAppId(wxOpenDataDTO.getAppId());
		wxUser.setMallUserId(wxOpenDataDTO.getMallUserId());
		wxUser.setSex(wxMaUserInfo.getGender());
		wxUser.setHeadimgUrl(wxMaUserInfo.getAvatarUrl());
		baseMapper.updateById(wxUser);
		return baseMapper.selectById(wxUser.getId());
	}

	/**
	 * 小程序登录
	 * @param wxApp
	 * @param jsCode
	 * @return
	 * @throws WxErrorException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public WxUser loginMa(WxApp wxApp, String jsCode) throws WxErrorException {
		WxMaJscode2SessionResult jscode2session = WxMaConfiguration.getMaService(wxApp.getId()).jsCode2SessionInfo(jsCode);
		WxUser wxUser = this.getByOpenId(wxApp.getId(),jscode2session.getOpenid());
		if(wxUser==null) {
			//新增商城用户
			MallUserInfoDTO mallUserInfoDTO = new MallUserInfoDTO();
			mallUserInfoDTO.setAppType(wxApp.getAppType());
			mallUserInfoDTO.setAppId(wxApp.getId());
			R r = feignMallUserInfoService.saveInside(mallUserInfoDTO, SecurityConstants.FROM_IN);
			if(!r.isOk()){
				throw new RuntimeException(r.getMsg());
			}
			Map map = (Map<String, Object>) r.getData();
			String mallUserId = String.valueOf(map.get("id"));
			//新增微信用户
			wxUser = new WxUser();
			wxUser.setAppId(wxApp.getId());
			wxUser.setAppType(wxApp.getAppType());
			wxUser.setOpenId(jscode2session.getOpenid());
			wxUser.setSessionKey(jscode2session.getSessionKey());
			wxUser.setUnionId(jscode2session.getUnionid());
			wxUser.setMallUserId(mallUserId);
			this.save(wxUser);
		}else {
			//更新SessionKey
			wxUser.setAppId(wxApp.getId());
			wxUser.setAppType(wxApp.getAppType());
			wxUser.setOpenId(jscode2session.getOpenid());
			wxUser.setSessionKey(jscode2session.getSessionKey());
			wxUser.setUnionId(jscode2session.getUnionid());
			this.updateById(wxUser);
		}

		String thirdSessionKey = UUID.randomUUID().toString();
		ThirdSession thirdSession = new ThirdSession();
		thirdSession.setAppId(wxApp.getId());
		thirdSession.setSessionKey(wxUser.getSessionKey());
		thirdSession.setWxUserId(wxUser.getId());
		thirdSession.setOpenId(wxUser.getOpenId());
		thirdSession.setMallUserId(wxUser.getMallUserId());
		//将3rd_session和用户信息存入redis，并设置过期时间
		String key = WxMaConstants.THIRD_SESSION_BEGIN + ":" + thirdSessionKey;
		redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(thirdSession) , WxMaConstants.TIME_OUT_SESSION, TimeUnit.HOURS);
		wxUser.setSessionKey(thirdSessionKey);
		return wxUser;
	}
}
