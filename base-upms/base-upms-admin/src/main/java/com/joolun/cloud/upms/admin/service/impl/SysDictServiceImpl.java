package com.joolun.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.mapper.SysDictValueMapper;
import com.joolun.cloud.upms.admin.mapper.SysDictMapper;
import com.joolun.cloud.upms.admin.service.SysDictService;
import com.joolun.cloud.upms.common.entity.SysDict;
import com.joolun.cloud.upms.common.entity.SysDictValue;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.util.R;
/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
	private final SysDictValueMapper sysDictValueMapper;

	/**
	 * 根据ID 删除字典
	 *
	 * @param id 字典ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public R removeDict(String id) {
		baseMapper.deleteById(id);
		sysDictValueMapper.delete(Wrappers.<SysDictValue>lambdaQuery()
				.eq(SysDictValue::getDictId, id));
		return R.ok();
	}

	/**
	 * 更新字典
	 *
	 * @param dict 字典
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R updateDict(SysDict dict) {
		this.updateById(dict);
		SysDictValue sysDictValue = new SysDictValue();
		sysDictValue.setType(dict.getType());
		sysDictValueMapper.update(sysDictValue,Wrappers.<SysDictValue>lambdaQuery()
				.eq(SysDictValue::getDictId, dict.getId()));
		return R.ok();
	}
}
