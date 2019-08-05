package com.xiaoming.day21.orm.annotation;

import java.lang.annotation.*;

/**
 * 自定义参数注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExtParam {
    String value();
}
