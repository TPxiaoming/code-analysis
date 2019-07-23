package com.xiaoming.day16.transaction;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TransactionUtils {

    /**
     * 获取事务源
     */
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 开启事务
     */
    public TransactionStatus begin(){
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        return transaction;
    }

    /**
     * 提交事务
     */
    public void commit(TransactionStatus transaction){
        dataSourceTransactionManager.commit(transaction);
    }

    /**
     * 回滚事务
     */
    public void rollBack(TransactionStatus transaction){
       dataSourceTransactionManager.rollback(transaction);
    }

}
