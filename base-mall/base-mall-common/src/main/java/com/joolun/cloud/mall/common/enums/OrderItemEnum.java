package com.joolun.cloud.mall.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单详情相关枚举
 */
public enum OrderItemEnum implements IEnum<String> {
	STATUS_0("0","正常"),
	STATUS_1("1","退款中"),
	STATUS_2("2","退货退款中"),
	STATUS_3("3","完成退款"),
	STATUS_4("4","完成退货退款");

	OrderItemEnum(final String value, final String desc) {
		this.value = value;
		this.desc = desc;
	}
	public static String STATUS_PREFIX = "STATUS";
	private String value;
	private String desc;

	@Override
	public String getValue() {
		return this.value;
	}

	@JsonValue
	public String getDesc(){
		return this.desc;
	}
}
