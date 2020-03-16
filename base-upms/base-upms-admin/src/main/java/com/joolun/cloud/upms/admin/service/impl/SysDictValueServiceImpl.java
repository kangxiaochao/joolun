package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.mapper.SysDictValueMapper;
import com.joolun.cloud.upms.admin.service.SysDictValueService;
import com.joolun.cloud.upms.common.entity.SysDictValue;
import org.springframework.stereotype.Service;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
/**
 * 字典项
 *
 * @author
 */
@Service
@AllArgsConstructor
public class SysDictValueServiceImpl extends ServiceImpl<SysDictValueMapper, SysDictValue> implements SysDictValueService {

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_CACHE, allEntries = true)
	public R removeDictItem(String id) {
		return R.ok(this.removeById(id));
	}

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_CACHE, allEntries = true)
	public R updateDictItem(SysDictValue item) {
		return R.ok(this.updateById(item));
	}
}
