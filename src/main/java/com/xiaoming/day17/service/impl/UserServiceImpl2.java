package com.xiaoming.day17.service.impl;

import com.xiaoming.day16.dao.UserDao;
import com.xiaoming.day17.annotation.ExtTransaction;
import com.xiaoming.day17.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl2 implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 声明，@Transactional 或者xml 方式
     * 方法开始执行前，开启提交事务
     */
    @ExtTransaction
    public void add() {
        //注意事项：在使用 spring 事务的时候，service 不要 try，最好将异常排除给外层 aop 异常通知接受回滚

        userDao.add("test001", 20);
        System.out.println("往数据库添加数据.....");
        int i = 2 / 0;
        userDao.add("test002", 22);

    }
    //方法执行完毕后，才会提交事务
}
