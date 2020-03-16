/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.lianghao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.cloud.lianghao.entity.LhPackage;

import java.util.List;
import java.util.Map;

/**
 * 号卡套餐
 *
 * @author code generator
 * @date 2019-11-07 14:48:20
 */
public interface LhPackageService extends IService<LhPackage> {
	List<Map<String,String>> selectPackage();
}
