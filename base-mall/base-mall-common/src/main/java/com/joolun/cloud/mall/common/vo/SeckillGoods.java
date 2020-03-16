package com.joolun.cloud.mall.common.vo;
import	java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.joolun.cloud.common.data.mybatis.typehandler.ArrayStringTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeckillGoods implements Serializable {

	private String spuId;

	private String skuId;

	private	String spuName;

	private String sellPoint;

	private String phoneSystem;

	private String lowDissipation;

	private String category;

	private String operatorName;

	private String packageName;

	private String attributionProvince;

	private String attributionCity;

	private BigDecimal seckillPrice;

	private LocalDateTime seckillStartTime;

	private LocalDateTime seckillEndTime;

//	@TableField(typeHandler = ArrayStringTypeHandler.class, jdbcType= JdbcType.VARCHAR)
	private String[] picUrls;

}
