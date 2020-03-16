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
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.mall.common.entity.UserAddress;
import com.joolun.cloud.mall.admin.mapper.UserAddressMapper;
import com.joolun.cloud.mall.admin.service.UserAddressService;
import org.springframework.stereotype.Service;

/**
 * 用户收货地址
 *
 * @author JL
 * @date 2019-09-11 14:28:59
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

	@Override
	public boolean save(UserAddress entity) {
		setIsDefault(entity);
		return super.save(entity);
	}

	@Override
	public boolean updateById(UserAddress entity) {
		setIsDefault(entity);
		return super.updateById(entity);
	}

	/**
	 * 修改默认地址
	 * @param entity
	 */
	void setIsDefault(UserAddress entity){
		if(CommonConstants.YES.equals(entity.getIsDefault())){
			UserAddress userAddress = new UserAddress();
			userAddress.setIsDefault(CommonConstants.NO);
			super.update(userAddress, Wrappers.<UserAddress>lambdaQuery()
					.eq(UserAddress::getUserId,entity.getUserId())
					.eq(UserAddress::getIsDefault,CommonConstants.YES));
		}
	}
}
