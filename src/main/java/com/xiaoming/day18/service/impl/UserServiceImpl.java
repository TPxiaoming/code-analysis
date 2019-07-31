package com.xiaoming.day18.service.impl;

import com.xiaoming.day16.dao.UserDao;
import com.xiaoming.day18.extannotation.spring.ExtResource;
import com.xiaoming.day18.extannotation.spring.ExtService;
import com.xiaoming.day18.service.OrderService;
import com.xiaoming.day18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@ExtService
public class UserServiceImpl implements UserService {

    @ExtResource
    private OrderService orderServiceImpl;

    public void add() {
//        orderServiceImpl.addOrder();
        System.out.println("往数据库添加数据.....");
    }
}
