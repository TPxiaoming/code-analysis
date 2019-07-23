package com.xiaoming.day16.proxy;

import com.xiaoming.day16.service.UserService;
/**
 * @Author:         xiaoming
 * @CreateDate:     2019/7/22 22:02
 * @Description:    静态代理模式  代理类对象
 */
public class UserServiceProxy implements UserService{
    private UserService userService;


    public UserServiceProxy(UserService userService){
        this.userService = userService;
    }

    public void add() {
        System.out.println("静态代理开启事务");
        userService.add();
        System.out.println("静态代理关闭事务");
    }
}
