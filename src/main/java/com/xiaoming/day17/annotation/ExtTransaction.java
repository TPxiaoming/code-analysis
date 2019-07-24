package com.xiaoming.day17.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: xiaoming
 * @Date: 16:18 2019/7/24
 * 事务注解
 * 设置传播行为
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtTransaction {


}
