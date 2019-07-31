package com.xiaoming.day20.connection;

import java.sql.Connection;

/**
 * 数据库连接池
 */
public interface IConnectionPool {

    /**
     * 获取连接（可重复利用机制)
     * @return
     */
    public  Connection getConnection();

    /**
     * 释放连接（回收机制）
     * @return
     */
    public void releaseConnection(Connection connection);
}
