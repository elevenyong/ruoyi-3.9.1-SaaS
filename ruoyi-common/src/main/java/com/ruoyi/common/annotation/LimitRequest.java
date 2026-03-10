package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * 限制接口请求次数
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    String type() default "";
}
