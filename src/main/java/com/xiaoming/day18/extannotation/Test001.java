package com.xiaoming.day18.extannotation;

import com.xiaoming.day18.service.UserService;
import com.xiaoming.day18.extannotation.spring.ExtClassPathXmlApplicationContext;

public class Test001 {
    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext context = new ExtClassPathXmlApplicationContext("com.xiaoming.day18.service.impl");
        UserService userService = (UserService) context.getBean("userServiceImpl");
        userService.add();
    }
}
