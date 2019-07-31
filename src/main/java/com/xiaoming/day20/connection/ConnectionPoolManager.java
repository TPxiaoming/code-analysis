package com.xiaoming.day20.connection;

import java.sql.Connection;

public class ConnectionPoolManager {

    private static DbBean dbBean = new DbBean();
    private static ConnectionPool connectionPool = new ConnectionPool(dbBean);

    /**
     * 获取连接（可重复利用机制)
     * @return
     */
    public static   Connection getConnection(){
        return connectionPool.getConnection();
    }

    /**
     * 释放连接（回收机制）
     * @return
     */
    public static void releaseConnection(Connection connection){
        connectionPool.releaseConnection(connection);
    }
}
