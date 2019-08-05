package com.xiaoming.day21.orm.annotation;

import java.lang.annotation.*;

/**
 * 自定义插入注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtInsert {
    String value();
}
