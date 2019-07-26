package com.xiaoming.day18.service.impl;

import com.xiaoming.day16.dao.UserDao;
import com.xiaoming.day18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class UserServiceImpl implements UserService {

    public void add() {
        System.out.println("往数据库添加数据.....");
    }
}
