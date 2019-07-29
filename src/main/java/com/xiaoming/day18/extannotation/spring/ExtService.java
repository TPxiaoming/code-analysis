package com.xiaoming.day18.extannotation.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author: xiaoming
 * @Date: 11:10 2019/7/29
 * 自定义注解 service 注入bean 容器
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtService {
}
