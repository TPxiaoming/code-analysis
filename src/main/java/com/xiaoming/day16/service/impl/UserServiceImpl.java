package com.xiaoming.day16.service.impl;

import com.xiaoming.day16.dao.UserDao;
import com.xiaoming.day16.service.UserService;
import com.xiaoming.day16.transaction.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

//    @Autowired
//    private TransactionUtils transactionUtils;


    public void add() {
        //注意事项：在使用 spring 事务的时候，service 不要 try，最好将异常排除给外层 aop 异常通知接受回滚
        userDao.add("test001", 20);
        System.out.println("往数据库添加数据.....");
        int i = 2 / 0;
        userDao.add("test002", 22);

        /*TransactionStatus status = null ;
        try {
            status = transactionUtils.begin();
            userDao.add("test001", 20);
            System.out.println("往数据库添加数据.....");
//            int i = 2 / 0;
            userDao.add("test002", 22);
            if (status != null){
                //提交事务
                transactionUtils.commit(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            if (status != null){
                transactionUtils.rollBack(status);
            }

        }*/


    }
}
