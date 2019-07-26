package com.xiaoming.day18;

import com.xiaoming.day18.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test002 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.add();
    }
}
