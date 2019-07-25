package com.xiaoming.day16.springAop;

import com.xiaoming.day16.transaction.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
@Aspect
public class AopTransaction {

    /**
     * TransactionUtils 不要实现为单例的，如果为单例的话可能会发生线程安全问题
     */
    @Autowired
    private TransactionUtils transactionUtils;

    @Around("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //调用方法之前执行
        System.out.println("开启事务");
        TransactionStatus status = transactionUtils.begin();
        proceedingJoinPoint.proceed(); //代理调用方法 注意点：如果调用方法抛出异常 不会执行后面代码
        //调用方法之后执行
        System.out.println("提交事务");
        transactionUtils.commit(status);
    }


    @AfterThrowing("execution(* com.xiaoming.day16.service.UserService.add(..))")
    public void afterThrowing() {
        System.out.println("回滚事务");
        //获取当前事务    直接回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
