package com.xiaoming.day16.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Author:         xiaoming
 * @CreateDate:     2019/7/23 21:37
 * @Description:    编程事务，需要手动 begin 手动回滚 手动提交
 */
@Component
@Scope("prototype") //每个事务都是新的实例，就是多例的 目的：解决线程安全问题
public class TransactionUtils {

    /**
     * 全局接收事务状态
     */
    private TransactionStatus status;

    /**
     * 获取事务源
     */
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 开启事务
     */
    public TransactionStatus begin(){
        System.out.println("开启事务");
        status = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return status;
    }

    /**
     * 提交事务
     */
    public void commit(TransactionStatus transaction){
        System.out.println("提交事务");
        dataSourceTransactionManager.commit(transaction);
    }

    /**
     * 回滚事务
     */
    public void rollBack(){
       dataSourceTransactionManager.rollback(status);
    }

}
