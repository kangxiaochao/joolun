package com.joolun.cloud.mall.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单退款相关枚举
 */
public enum OrderRefundsEnum implements IEnum<String> {

	STATUS_0("0","撤销退换申请"),
	STATUS_1("1","退款申请中"),
	STATUS_11("11","同意退款"),
	STATUS_12("12","拒绝退款"),
	STATUS_2("2","退货退款申请中"),
	STATUS_21("21","退货中"),
	STATUS_22("22","拒绝退货退款"),
	STATUS_211("211","收到退货同意退款"),
	STATUS_212("212","收到退货拒绝退款");

	OrderRefundsEnum(final String value, final String desc) {
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
