/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.common.core.constant.SecurityConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.mall.admin.service.PointsConfigService;
import com.joolun.cloud.mall.admin.service.PointsRecordService;
import com.joolun.cloud.mall.common.constant.MallConstants;
import com.joolun.cloud.mall.common.dto.WxOpenDataDTO;
import com.joolun.cloud.mall.common.entity.PointsConfig;
import com.joolun.cloud.mall.common.entity.PointsRecord;
import com.joolun.cloud.mall.common.entity.UserInfo;
import com.joolun.cloud.mall.admin.mapper.UserInfoMapper;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import com.joolun.cloud.mall.common.feign.FeignWxUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 商城用户
 *
 * @author JL
 * @date 2019-12-04 11:09:55
 */
@Service
@AllArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

	private final FeignWxUserService feignWxUserService;
	private final PointsConfigService pointsConfigService;
	private final PointsRecordService pointsRecordService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R saveByWxUser(WxOpenDataDTO wxOpenDataDTO) {
		//修改微信用户信息
		R r = feignWxUserService.save(wxOpenDataDTO, SecurityConstants.FROM_IN);
		if(!r.isOk()){
			throw new RuntimeException(r.getMsg());
		}
		//修改商城用户信息
		Map map = (Map<String, Object>) r.getData();
		if(map.get("mallUserId") != null){
			UserInfo userInfo = baseMapper.selectById(String.valueOf(map.get("mallUserId")));
			userInfo.setNickName(String.valueOf(map.get("nickName")));
			userInfo.setHeadimgUrl(String.valueOf(map.get("headimgUrl")));
			userInfo.setSex(String.valueOf(map.get("sex")));
			userInfo.setCity(String.valueOf(map.get("city")));
			userInfo.setCountry(String.valueOf(map.get("country")));
			userInfo.setProvince(String.valueOf(map.get("province")));
			if(MallConstants.USER_GRADE_0.equals(userInfo.getUserGrade())){
				userInfo.setUserGrade(MallConstants.USER_GRADE_1);//1：普通会员
				//获取会员初始积分
				PointsConfig pointsConfig = pointsConfigService.getOne(Wrappers.emptyWrapper());
				int initVipPosts = pointsConfig != null ? pointsConfig.getInitVipPosts() : 0;
				userInfo.setPointsCurrent(userInfo.getPointsCurrent() + initVipPosts);
				if(initVipPosts > 0){
					//新增积分变动记录
					PointsRecord pointsRecord = new PointsRecord();
					pointsRecord.setUserId(userInfo.getId());
					pointsRecord.setAmount(initVipPosts);
					pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_1);
					pointsRecord.setDescription("会员初始积分");
					pointsRecordService.save(pointsRecord);
				}
			}
			baseMapper.updateById(userInfo);
		}
		return r;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveInside(UserInfo userInfo) {
		//获取用户初始积分
		PointsConfig pointsConfig = pointsConfigService.getOne(Wrappers.emptyWrapper());
		int initPosts = pointsConfig != null ? pointsConfig.getInitPosts() : 0;
		//新增商城用户
		userInfo.setUserGrade(MallConstants.USER_GRADE_0);
		userInfo.setPointsCurrent(initPosts);
		baseMapper.insert(userInfo);
		if(initPosts > 0){
			//新增积分变动记录
			PointsRecord pointsRecord = new PointsRecord();
			pointsRecord.setUserId(userInfo.getId());
			pointsRecord.setAmount(initPosts);
			pointsRecord.setRecordType(MallConstants.POINTS_RECORD_TYPE_0);
			pointsRecord.setDescription("用户初始积分");
			pointsRecordService.save(pointsRecord);
		}
		return Boolean.TRUE;
	}
}
