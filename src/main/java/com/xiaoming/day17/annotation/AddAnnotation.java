package com.xiaoming.day17.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: xiaoming
 * @Date: 15:37 2019/7/24
 * @interface 定义注解
 * @Target(value = ElementType.METHOD)设置注解权限
 * @Retention(RetentionPolicy.RUNTIME) 表示需要在什么级别保存该注释信息，用于描述注解的生命周期（即：被描述的注解在什么范围内有效）
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddAnnotation {
    int userId() default 0;

    String userName() default "默认名称";

    String[] arrays();
}
