package com.xiaoming.day20;

import com.xiaoming.day20.connection.ConnectionPool;
import com.xiaoming.day20.connection.ConnectionPoolManager;

import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        ThreadConnectoin threadConnectoin = new ThreadConnectoin();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(threadConnectoin, "线程i：" + i);
            thread.start();
        }
    }
}

class ThreadConnectoin implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Connection connection = ConnectionPoolManager.getConnection();
            System.out.println(Thread.currentThread().getName() + ":" + connection);
            ConnectionPoolManager.releaseConnection(connection);
        }
    }
}
