package com.xiaoming.day21.sql;

import com.xiaoming.day21.aop.MyInvocationHandlerMybatis;

import java.lang.reflect.Proxy;

/**
 * 获取SqlSession对象
 */
public class SqlSession {
    public static  <T> T getMapper(Class clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyInvocationHandlerMybatis(clazz));
    }
}
