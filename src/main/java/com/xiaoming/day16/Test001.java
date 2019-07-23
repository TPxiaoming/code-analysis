package com.xiaoming.day16;

import com.xiaoming.day16.proxy.UserServiceProxy;
import com.xiaoming.day16.service.UserService;
import com.xiaoming.day16.service.impl.UserServiceImpl;

public class Test001 {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        new UserServiceProxy(userService).add();
    }
}
