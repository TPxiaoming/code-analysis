package com.xiaoming.day18.service.impl;

import com.xiaoming.day18.extannotation.spring.ExtResource;
import com.xiaoming.day18.extannotation.spring.ExtService;
import com.xiaoming.day18.service.OrderService;
@ExtService
public class OrderServiceImpl implements OrderService {

    public void addOrder() {
        System.out.println("addOrder");
    }
}
