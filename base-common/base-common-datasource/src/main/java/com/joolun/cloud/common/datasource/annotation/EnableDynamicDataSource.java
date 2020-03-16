package com.joolun.cloud.common.datasource.annotation;

import com.joolun.cloud.common.datasource.config.DynamicDataSourceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启动态数据源
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DynamicDataSourceConfig.class})
public @interface EnableDynamicDataSource {
}
