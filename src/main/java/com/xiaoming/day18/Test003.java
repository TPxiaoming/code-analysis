package com.xiaoming.day18;

import com.xiaoming.day18.service.UserService;
import com.xiaoming.day18.xml.spring.MyClassPathXmlApplicationContext;

public class Test003 {
    public static void main(String[] args) throws Exception {
        MyClassPathXmlApplicationContext myClassPathXmlApplicationContext = new MyClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) myClassPathXmlApplicationContext.getBean("userService");
        userService.add();
    }
}
