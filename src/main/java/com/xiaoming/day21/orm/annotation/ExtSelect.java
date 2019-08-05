package com.xiaoming.day21.orm.annotation;

import java.lang.annotation.*;

/**
 * 自定义查询注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtSelect {
    String value();
}
