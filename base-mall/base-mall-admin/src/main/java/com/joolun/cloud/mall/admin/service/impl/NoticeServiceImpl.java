/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.mall.common.entity.Notice;
import com.joolun.cloud.mall.admin.mapper.NoticeMapper;
import com.joolun.cloud.mall.admin.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 商城通知
 *
 * @author JL
 * @date 2019-11-09 19:37:56
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {


	@Override
	public Notice getOne(Wrapper<Notice> queryWrapper) {
		return baseMapper.getOne2(queryWrapper.getEntity());
	}
}
