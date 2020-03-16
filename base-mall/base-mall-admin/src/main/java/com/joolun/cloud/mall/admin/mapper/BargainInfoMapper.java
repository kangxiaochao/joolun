/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joolun.cloud.mall.common.entity.BargainInfo;
import com.joolun.cloud.mall.common.entity.BargainUser;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 砍价
 *
 * @author JL
 * @date 2019-12-28 18:07:51
 */
public interface BargainInfoMapper extends BaseMapper<BargainInfo> {
	BargainInfo selectById2(Serializable id);

	IPage<BargainInfo> selectPage2(IPage<BargainInfo> page, @Param("query") BargainInfo bargainInfo);

	BargainInfo selectOne2(@Param("query2") BargainUser bargainUser);
}
