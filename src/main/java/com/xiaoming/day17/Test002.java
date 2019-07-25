package com.xiaoming.day17;

import com.xiaoming.day17.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test002 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userServiceImpl2");
        userService.add();
    }
}
