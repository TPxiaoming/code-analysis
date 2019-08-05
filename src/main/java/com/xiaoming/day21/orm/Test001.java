package com.xiaoming.day21.orm;

import com.xiaoming.day21.entity.User;
import com.xiaoming.day21.mapper.UserMapper;
import com.xiaoming.day21.sql.SqlSession;

/**
 * mybatis 攻破
 *
 * 1.mapper 接口的方法需要和 SQL 语句进行绑定
 *      思考：接口不能被实例化？那么如何实现调用呢？
 *          1.使用字节码技术创建虚拟子类     2.使用匿名内部类方式     3.使用动态代理方式
 */
public class Test001 {
    public static void main(String[] args) {
        UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
        User result = userMapper.selectUser("xiaoming", 18);
        System.out.println(result);
    }
}
