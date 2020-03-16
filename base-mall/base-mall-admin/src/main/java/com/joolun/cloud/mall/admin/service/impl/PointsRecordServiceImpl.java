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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.mall.common.entity.PointsRecord;
import com.joolun.cloud.mall.admin.mapper.PointsRecordMapper;
import com.joolun.cloud.mall.admin.service.PointsRecordService;
import org.springframework.stereotype.Service;

/**
 * 积分变动记录
 *
 * @author JL
 * @date 2019-12-05 19:47:22
 */
@Service
public class PointsRecordServiceImpl extends ServiceImpl<PointsRecordMapper, PointsRecord> implements PointsRecordService {

	@Override
	public IPage<PointsRecord> page1(IPage<PointsRecord> page, PointsRecord pointsRecord) {
		return baseMapper.selectPage1(page,pointsRecord);
	}
}
