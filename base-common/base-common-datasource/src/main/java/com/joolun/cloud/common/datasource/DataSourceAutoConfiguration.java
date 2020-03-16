package com.joolun.cloud.common.datasource;

import com.joolun.cloud.common.datasource.config.DruidDataSourceProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * DbAcess 自动配置类
 */
@AllArgsConstructor
@EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DataSourceAutoConfiguration {
}
