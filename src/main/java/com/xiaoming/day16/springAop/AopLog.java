/*
package com.xiaoming.day16.springAop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

*/
/**
 * @Author:         xiaoming
 * @CreateDate:     2019/7/22 22:25
 * @Description:    切面类
 *//*


@Component  //注入到 spring 容器中
@Aspect
public class AopLog {

    //aop 编程里有几个通知 ：前置通知、后置通知、运行通知、异常通知、环绕通知

    @Before("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void before(){
        System.out.println("前置通知 在方法之前执行...");
    }

    @After("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void after(){
        System.out.println("后置通知 在方法之后执行...");
    }


    @AfterReturning("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void returning() {
        System.out.println("运行通知");
    }


    @AfterThrowing("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    @Around("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //调用方法之前执行
        System.out.println("环绕通知调用方法之前执行");
        proceedingJoinPoint.proceed(); //代理调用方法 注意点：如果调用方法抛出异常 不会执行后面代码
        //调用方法之后执行
        System.out.println("环绕通知调用方法之后执行");
    }
}
*/
