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
import com.joolun.cloud.mall.admin.mapper.NoticeMapper;
import com.joolun.cloud.mall.admin.service.NoticeService;
import com.joolun.cloud.mall.common.entity.Notice;
import com.joolun.cloud.mall.common.entity.NoticeItem;
import com.joolun.cloud.mall.admin.mapper.NoticeItemMapper;
import com.joolun.cloud.mall.admin.service.NoticeItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城通知详情
 *
 * @author JL
 * @date 2019-11-09 19:39:03
 */
@Service
@AllArgsConstructor
public class NoticeItemServiceImpl extends ServiceImpl<NoticeItemMapper, NoticeItem> implements NoticeItemService {

	private final NoticeMapper noticeMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(NoticeItem entity) {
		Notice notice = new Notice();
		notice.setAppId(entity.getAppId());
		notice.setType(entity.getNoticeType());
		Notice notice2 = noticeMapper.selectOne(Wrappers.query(notice));
		if(notice2 == null){
			notice.setEnable(CommonConstants.YES);
			noticeMapper.insert(notice);
			entity.setNoticeId(notice.getId());
		}else{
			entity.setNoticeId(notice2.getId());
		}
		return super.save(entity);
	}
}
