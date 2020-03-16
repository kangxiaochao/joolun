/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.mall.admin.mapper.BargainCutMapper;
import com.joolun.cloud.mall.admin.mapper.BargainInfoMapper;
import com.joolun.cloud.mall.admin.service.BargainCutService;
import com.joolun.cloud.mall.admin.service.BargainInfoService;
import com.joolun.cloud.mall.admin.service.UserInfoService;
import com.joolun.cloud.mall.common.entity.BargainCut;
import com.joolun.cloud.mall.common.entity.BargainInfo;
import com.joolun.cloud.mall.common.entity.BargainUser;
import com.joolun.cloud.mall.admin.mapper.BargainUserMapper;
import com.joolun.cloud.mall.admin.service.BargainUserService;
import com.joolun.cloud.mall.common.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 砍价记录
 *
 * @author JL
 * @date 2019-12-30 11:53:14
 */
@Service
@AllArgsConstructor
public class BargainUserServiceImpl extends ServiceImpl<BargainUserMapper, BargainUser> implements BargainUserService {

	private final BargainInfoMapper bargainInfoMapper;
	private final BargainCutService bargainCutService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveBargain(BargainUser bargainUser) {
		//获取砍价详情
		BargainInfo bargainInfo = bargainInfoMapper.selectOne(Wrappers.<BargainInfo>lambdaQuery()
				.eq(BargainInfo::getId,bargainUser.getBargainId())
				.eq(BargainInfo::getEnable, CommonConstants.YES));
		if(bargainInfo != null && LocalDateTime.now().isAfter(bargainInfo.getValidBeginTime())
				&& LocalDateTime.now().isBefore(bargainInfo.getValidEndTime())){
			bargainUser.setSpuId(bargainInfo.getSpuId());
			bargainUser.setSkuId(bargainInfo.getSkuId());
			bargainUser.setValidBeginTime(bargainInfo.getValidBeginTime());
			bargainUser.setValidEndTime(bargainInfo.getValidEndTime());
			bargainUser.setBargainPrice(bargainInfo.getBargainPrice());
			bargainUser.setFloorBuy(bargainInfo.getFloorBuy());
			baseMapper.insert(bargainUser);
			if(CommonConstants.YES.equals(bargainInfo.getSelfCut())){//是否自砍一刀
				BargainCut bargainCut = new BargainCut();
				bargainCut.setUserId(bargainUser.getUserId());
				bargainCut.setBargainUserId(bargainUser.getId());
				bargainCutService.save2(bargainCut);//自砍一刀
			}
			bargainInfo.setLaunchNum(bargainInfo.getLaunchNum()+1);
			bargainInfoMapper.updateById(bargainInfo);
		}
		return Boolean.TRUE;
	}

	@Override
	public IPage<BargainUser> page1(IPage<BargainUser> page, BargainUser bargainUser) {
		return baseMapper.selectPage1(page, bargainUser);
	}

	@Override
	public IPage<BargainUser> page2(IPage<BargainUser> page, BargainUser bargainUser) {
		return baseMapper.selectPage2(page, bargainUser);
	}

}
