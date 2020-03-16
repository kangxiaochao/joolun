package com.joolun.cloud.upms.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.cloud.upms.admin.mapper.SysUserMapper;
import com.joolun.cloud.upms.admin.service.SysOrganRelationService;
import com.joolun.cloud.upms.admin.service.SysOrganService;
import com.joolun.cloud.upms.admin.service.SysMenuService;
import com.joolun.cloud.upms.admin.service.SysRoleMenuService;
import com.joolun.cloud.upms.admin.service.SysRoleService;
import com.joolun.cloud.upms.admin.service.SysUserRoleService;
import com.joolun.cloud.upms.admin.service.SysUserService;
import com.joolun.cloud.upms.common.dto.UserDTO;
import com.joolun.cloud.upms.common.dto.UserInfo;
import com.joolun.cloud.upms.common.dto.UserRegister;
import com.joolun.cloud.upms.common.entity.SysOrgan;
import com.joolun.cloud.upms.common.entity.SysOrganRelation;
import com.joolun.cloud.upms.common.entity.SysRole;
import com.joolun.cloud.upms.common.entity.SysRoleMenu;
import com.joolun.cloud.upms.common.entity.SysUser;
import com.joolun.cloud.upms.common.entity.SysUserRole;
import com.joolun.cloud.upms.common.vo.MenuVO;
import com.joolun.cloud.upms.common.vo.UserVO;
import com.joolun.cloud.common.core.constant.CacheConstants;
import com.joolun.cloud.common.core.constant.CommonConstants;
import com.joolun.cloud.common.core.util.R;
import com.joolun.cloud.common.data.tenant.TenantContextHolder;
import com.joolun.cloud.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysMenuService sysMenuService;
	private final SysRoleService sysRoleService;
	private final SysOrganService sysOrganService;
	private final SysUserRoleService sysUserRoleService;
	private final SysOrganRelationService sysOrganRelationService;
	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return ok/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(sysUser);
		List<SysUserRole> userRoleList = userDto.getRoleIds()
				.stream().map(roleId -> {
					SysUserRole userRole = new SysUserRole();
					userRole.setUserId(sysUser.getId());
					userRole.setRoleId(roleId);
					return userRole;
				}).collect(Collectors.toList());
		return sysUserRoleService.saveBatch(userRoleList);
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param sysUser 用户
	 * @return
	 */
	@Override
	public UserInfo findUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		//设置角色列表  （ID）
		List<String> roleIds = sysRoleService.findRoleIdsByUserId(sysUser.getId());
		userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));

		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		roleIds.forEach(roleId -> {
			List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
					.stream()
					.filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
					.map(MenuVO::getPermission)
					.collect(Collectors.toList());
			permissions.addAll(permissionList);
		});
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO selectUserVoById(String id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#sysUser.username")
	public Boolean deleteUserById(SysUser sysUser) {
		sysUserRoleService.deleteByUserId(sysUser.getId());
		this.removeById(sysUser.getId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#userDto.username")
	public R<Boolean> updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
				&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
				return R.ok(Boolean.FALSE, "原密码错误，修改失败");
			}
		}
		sysUser.setPhone(userDto.getPhone());
		sysUser.setId(userVO.getId());
		sysUser.setAvatar(userDto.getAvatar());
		sysUser.setEmail(userDto.getEmail());
		return R.ok(this.updateById(sysUser));
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_CACHE, key = "#userDto.username")
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());
		sysUser.setPassword(null);
		this.updateById(sysUser);

		sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
				.eq(SysUserRole::getUserId, userDto.getId()));
		userDto.getRoleIds().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * 查询上级机构的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsers(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getUsername, username));

		SysOrgan sysOrgan = sysOrganService.getById(sysUser.getOrganId());
		if (sysOrgan == null) {
			return null;
		}

		String parentId = sysOrgan.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda()
				.eq(SysUser::getOrganId, parentId));
	}

	/**
	 * 获取当前用户的子机构信息
	 *
	 * @return 子机构列表
	 */
	private List<String> getChildOrgans() {
		String organId = SecurityUtils.getUser().getOrganId();
		//获取当前机构的子机构
		return sysOrganRelationService
				.list(Wrappers.<SysOrganRelation>query().lambda()
						.eq(SysOrganRelation::getAncestor, organId))
				.stream()
				.map(SysOrganRelation::getDescendant)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R register(UserRegister userRegister) {
		String tenantId = IdUtil.simpleUUID();
		TenantContextHolder.setTenantId(tenantId);
		//新建机构
		SysOrgan sysOrgan = new SysOrgan();
		sysOrgan.setId(tenantId);
		sysOrgan.setName(userRegister.getOrganname());
		sysOrgan.setParentId(CommonConstants.PARENT_ID);
		sysOrgan.setType(CommonConstants.ORGAN_TYPE_1);
		sysOrgan.setCode("10000");
		sysOrganService.save(sysOrgan);
		//新建机构关联
		sysOrganRelationService.insertOrganRelation(sysOrgan);
		//新建用户
		SysUser sysUser = new SysUser();
		sysUser.setOrganId(sysOrgan.getId());
		sysUser.setUsername(userRegister.getUsername());
		sysUser.setEmail(userRegister.getEmail());
		sysUser.setPassword(ENCODER.encode(userRegister.getPassword()));
		baseMapper.insert(sysUser);
		//新建角色
		SysRole sysRole = new SysRole();
		sysRole.setRoleName("管理员");
		sysRole.setRoleCode(CommonConstants.ROLE_CODE_ADMIN);
		sysRole.setRoleDesc(userRegister.getOrganname()+"管理员");
		sysRole.setDsType(CommonConstants.DS_TYPE_0);
		sysRoleService.save(sysRole);
		//新建用户角色
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRoleId(sysRole.getId());
		sysUserRole.setUserId(sysUser.getId());
		sysUserRoleService.save(sysUserRole);
		//新建角色菜单，将role_id为2的角色所拥有的权限赋给新注册用户的角色
		List<SysRoleMenu> listSysRoleMenu = sysRoleMenuService
				.list(Wrappers.<SysRoleMenu>query().lambda()
				.eq(SysRoleMenu::getRoleId,2))
				.stream().map(sysRoleMenu -> {
					sysRoleMenu.setRoleId(sysRole.getId());
					return sysRoleMenu;
				}).collect(Collectors.toList());
		sysRoleMenuService.saveBatch(listSysRoleMenu);
		return R.ok();
	}

}
