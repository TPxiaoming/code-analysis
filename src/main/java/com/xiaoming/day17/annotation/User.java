package com.xiaoming.day17.annotation;

import java.lang.reflect.Method;

public class User {
    @AddAnnotation(userId = 18, userName = "xiaoming", arrays = {"1"})
    public void add(){

    }

    public void del(){

    }

    public static void main(String[] args) throws ClassNotFoundException {
        //怎么样获取到方法上注解信息 反射机制
        Class<?> forName = Class.forName("com.xiaoming.day17.annotation.User");
        //获取到当前类（不包含继承）所有的方法
        Method[] methods = forName.getDeclaredMethods();
        for (Method method : methods) {
            //获取该方法上是否存在注解
            AddAnnotation annotation = method.getDeclaredAnnotation(AddAnnotation.class);
            if (annotation == null){
                //该方法上没有注解
                System.out.println(method.getName() + "该方法上没有加注解");
                continue;
            }
            //在该方法上查找该注解

            System.out.println( annotation.userId());

            System.out.println(annotation.userName());

            System.out.println(annotation.arrays());
        }
    }
}
