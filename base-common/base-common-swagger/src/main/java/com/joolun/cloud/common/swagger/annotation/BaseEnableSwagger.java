package com.joolun.cloud.common.swagger.annotation;

import com.joolun.cloud.common.swagger.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author JL
 * 开启 swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfiguration.class})
public @interface BaseEnableSwagger {
}
