/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 * 注意：
 * 本软件为www.joolun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package com.joolun.cloud.gateway.entity;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;
import java.util.List;

/**
 * 路由定义模型
 *
 * @author JL
 * @date 2019-03-23 21:26:35
 */
@Data
public class GatewayRouteList {
private static final long serialVersionUID = 1L;

	List<RouteDefinition> routes;
  
}
