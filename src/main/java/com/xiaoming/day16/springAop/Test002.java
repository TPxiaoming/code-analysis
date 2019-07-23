package com.xiaoming.day16.springAop;

import com.xiaoming.day16.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test002 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userServiceImpl");
        userService.add();
    }
}
