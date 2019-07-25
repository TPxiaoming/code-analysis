package com.xiaoming.day17.aop;

import com.xiaoming.day16.transaction.TransactionUtils;
import com.xiaoming.day17.annotation.ExtTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

@Aspect
@Component
public class AopExtTransaction {

    /**
     * TransactionUtils 不要实现为单例的，如果为单例的话可能会发生线程安全问题
     * 一个事务的实例针对一个方法
     */
    @Autowired
    private TransactionUtils transactionUtils;

    @Around("execution(* com.xiaoming.day17.service.*.*.*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        //1.获取该方法上是否加上注解
        ExtTransaction annotation  =  getMethodExtTransaction(pjp);

        TransactionStatus status = begin(annotation);

        //3.调用目标代理对象方法
        pjp.proceed();

        //4.判断该方法上是否加上注解
        commit(status);
    }

    private void commit(TransactionStatus status){
        if (status != null){
            //5.如果存在注解，提交事务
            transactionUtils.commit(status);
        }
    }

    private TransactionStatus begin(ExtTransaction annotation){
        if (annotation == null){
            return null;
        }
        //2.如果存在事务注解，开启事务
        return transactionUtils.begin();
    }

    /**
     * 获取方法上是否存在事务注解
     * @param pjp
     * @return
     * @throws NoSuchMethodException
     */
    public ExtTransaction getMethodExtTransaction(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        // 获取方法名称
        String methodName = pjp.getSignature().getName();
        // 获取目标对象
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        ExtTransaction annotation = objMethod.getAnnotation(ExtTransaction.class);
        return annotation;
    }


    /**
     * 使用异常通知回滚事务
     */
    @AfterThrowing("execution(* com.xiaoming.day17.service.*.*.*(..))")
    public void afterThrowing() {
        System.out.println("回滚事务");
        //获取当前事务    直接回滚
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        //判断下异常方法是否有事务注解
        transactionUtils.rollBack();
    }
}
