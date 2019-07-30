package com.xiaoming.day19.controller;

import com.xiaoming.day19.ext.springmvc.extannotation.ExtController;
import com.xiaoming.day19.ext.springmvc.extannotation.ExtRequestMapping;

@ExtController
@ExtRequestMapping("/ext")
public class ExtIndexController {

    @ExtRequestMapping("/test")
    public String test(){
        System.out.println("我是手写 springmvc 框架");
        return "index";
    }
}
