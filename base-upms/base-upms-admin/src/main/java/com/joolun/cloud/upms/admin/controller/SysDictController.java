package com.joolun.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.cloud.upms.admin.service.SysDictValueService;
import com.joolun.cloud.upms.admin.service.SysDictService;
import com.joolun.cloud.upms.common.entity.SysDict;
import com.joolun.cloud.upms.common.entity.SysDictValue;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.joolun.cloud.common.core.constant.CacheConstants;
import javax.validation.Valid;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author JL
 * @since 2017-11-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "dict", tags = "字典管理模块")
public class SysDictController {
	private final SysDictService sysDictService;
	private final SysDictValueService sysDictValueService;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_dict_get')")
	public R getById(@PathVariable String id) {
		return R.ok(sysDictService.getById(id));
	}

	/**
	 * 分页查询字典信息
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys_dict_index')")
	public R<IPage> getDictPage(Page page, SysDict sysDict) {
		return R.ok(sysDictService.page(page, Wrappers.query(sysDict)));
	}

	/**
	 * 通过字典类型查找字典
	 *
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/type/{type}")
	@Cacheable(value = CacheConstants.DICT_CACHE, key = "#type", unless = "#result == null")
	public R getDictByType(@PathVariable String type) {
		return R.ok(sysDictValueService.list(Wrappers
				.<SysDictValue>query().lambda()
				.eq(SysDictValue::getType, type)));
	}

	/**
	 * 添加字典
	 *
	 * @param sysDict 字典信息
	 * @return ok、false
	 */
	@SysLog("添加字典")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_dict_add')")
	public R save(@Valid @RequestBody SysDict sysDict) {
		return R.ok(sysDictService.save(sysDict));
	}

	/**
	 * 删除字典，并且清除字典缓存
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除字典")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_dict_del')")
	public R removeById(@PathVariable String id) {
		return sysDictService.removeDict(id);
	}

	/**
	 * 修改字典
	 *
	 * @param sysDict 字典信息
	 * @return ok/false
	 */
	@PutMapping
	@SysLog("修改字典")
	@PreAuthorize("@ato.hasAuthority('sys_dict_edit')")
	public R updateById(@Valid @RequestBody SysDict sysDict) {
		return sysDictService.updateDict(sysDict);
	}

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param sysDictValue 字典项
	 * @return
	 */
	@GetMapping("/item/page")
	public R getSysDictItemPage(Page page, SysDictValue sysDictValue) {
		return R.ok(sysDictValueService.page(page, Wrappers.query(sysDictValue)));
	}


	/**
	 * 通过id查询字典项
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/item/{id}")
	public R getDictItemById(@PathVariable("id") String id) {
		return R.ok(sysDictValueService.getById(id));
	}

	/**
	 * 新增字典项
	 *
	 * @param sysDictValue 字典项
	 * @return R
	 */
	@SysLog("新增字典项")
	@PostMapping("/item")
	@CacheEvict(value = CacheConstants.DICT_CACHE, allEntries = true)
	public R save(@RequestBody SysDictValue sysDictValue) {
		return R.ok(sysDictValueService.save(sysDictValue));
	}

	/**
	 * 修改字典项
	 *
	 * @param sysDictValue 字典项
	 * @return R
	 */
	@SysLog("修改字典项")
	@PutMapping("/item")
	public R updateById(@RequestBody SysDictValue sysDictValue) {
		return sysDictValueService.updateDictItem(sysDictValue);
	}

	/**
	 * 通过id删除字典项
	 *
	 * @param id id
	 * @return R
	 */
	@SysLog("删除字典项")
	@DeleteMapping("/item/{id}")
	public R removeDictItemById(@PathVariable String id) {
		return sysDictValueService.removeDictItem(id);
	}
}
