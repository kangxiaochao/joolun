package com.joolun.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.joolun.cloud.upms.common.entity.SysTenant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 租户管理 Mapper 接口
 * </p>
 *
 * @author JL
 * @since 2019-01-20
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {

	@SqlParser(filter=true)
	@Select("select * from sys_organ ${ew.customSqlSegment}")
	IPage<SysTenant> selectPage2(IPage<SysTenant> page, @Param(Constants.WRAPPER) Wrapper<SysTenant> queryWrapper);

}
