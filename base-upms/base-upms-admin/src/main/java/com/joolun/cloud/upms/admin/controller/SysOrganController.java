package com.joolun.cloud.upms.admin.controller;

import com.joolun.cloud.upms.admin.service.SysOrganService;

import com.joolun.cloud.upms.common.dto.OrganTree;
import com.joolun.cloud.upms.common.entity.SysOrgan;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author JL
 * @since 2018-01-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organ")
@Api(value = "organ", tags = "机构管理模块")
public class SysOrganController {
	private final SysOrganService sysOrganService;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysOrgan
	 */
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_organ_get')")
	public R getById(@PathVariable String id) {
		return R.ok(sysOrganService.getById(id));
	}


	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	@PreAuthorize("@ato.hasAuthority('sys_organ_index')")
	public R getTree() {
		return R.ok(sysOrganService.selectTree());
	}

	/**
	 *  返回树形父类集合
	 * @return
	 */
	@GetMapping("/parentTree")
	@PreAuthorize("@ato.hasAuthority('sys_organ_index')")
	public List<OrganTree> getSysOrganParentTree() {
		return sysOrganService.selectTree();
	}

	/**
	 * 添加
	 *
	 * @param sysOrgan 实体
	 * @return ok/false
	 */
	@SysLog("添加机构")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys_organ_add')")
	public R save(@Valid @RequestBody SysOrgan sysOrgan) {
		try {
			if(CommonConstants.PARENT_ID.equals(sysOrgan.getParentId())){
				throw new Exception("父级节点不能为0");
			}
			return R.ok(sysOrganService.saveOrgan(sysOrgan));
		} catch (DuplicateKeyException e){
			if(e.getMessage().contains("uk_tenant_id_code")){
				return R.failed("机构编码已存在");
			}else{
				return R.failed(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@SysLog("删除机构")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys_organ_del')")
	public R removeById(@PathVariable String id) {
		SysOrgan sysOrgan2 = sysOrganService.getById(id);
		if(CommonConstants.PARENT_ID.equals(sysOrgan2.getParentId())){
			return R.failed("总机构（租户机构）不能删除");
		}
		return R.ok(sysOrganService.removeOrganById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysOrgan 实体
	 * @return ok/false
	 */
	@SysLog("编辑机构")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys_organ_edit')")
	public R update(@Valid @RequestBody SysOrgan sysOrgan) {
		try {
			SysOrgan sysOrgan2 = sysOrganService.getById(sysOrgan.getId());
			if(CommonConstants.PARENT_ID.equals(sysOrgan2.getParentId())){
				sysOrgan.setParentId(CommonConstants.PARENT_ID);
			}
			return R.ok(sysOrganService.updateOrganById(sysOrgan));
		} catch (DuplicateKeyException e){
			if(e.getMessage().contains("uk_tenant_id_code")){
				return R.failed("机构编码已存在");
			}else{
				return R.failed(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}
}
