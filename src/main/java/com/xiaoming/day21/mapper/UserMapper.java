package com.xiaoming.day21.mapper;

import com.xiaoming.day21.entity.User;
import com.xiaoming.day21.orm.annotation.ExtInsert;
import com.xiaoming.day21.orm.annotation.ExtParam;
import com.xiaoming.day21.orm.annotation.ExtSelect;

public interface UserMapper {

    @ExtInsert("insert into user(userName,userAge) values(#{userName},#{userAge})")
    public int insertUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);

    @ExtSelect("select * from user where userName = #{userName} and userAge = #{userAge}")
    public User selectUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);
}
