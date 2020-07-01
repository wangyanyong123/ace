package com.github.wxiaoqi.security.plan.annotation;

import java.lang.annotation.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "master";
}