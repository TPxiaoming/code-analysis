package com.xiaoming.day20.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool implements IConnectionPool {

    /**
     * 空闲线程集合
     */
    private List<Connection> freeConnection = new Vector<>();

    /**
     * 活动线程集合
     */
    private List<Connection> activeConnection = new Vector<>();

    private DbBean dbBean;

    private AtomicInteger countConne = new AtomicInteger();

    public ConnectionPool(DbBean dbBean)  {
        this.dbBean = dbBean;
        init();
    }

    /**
     * 初始化线程池（初始化空闲线程）
     */
    private void init() {
        if (dbBean == null){
            return;
        }
        //1.获取初始化连接数
        for (int i = 0; i < dbBean.getInitConnections(); i++) {
            //2.创建Connection连接
            Connection connection = createConnection();
            freeConnection.add(connection);
        }

        //3.存放在freeConnection集合
    }

    private Connection createConnection(){
        try {
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(), dbBean.getPassword());
            countConne.incrementAndGet();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public synchronized Connection getConnection() {
        //当前创建的连接>最大连接数
        Connection connection = null;
        if (countConne.get() < dbBean.getMaxConnections()){
            //小于最大活动连接数
            //1.判断空闲线程是否有数据
            if (freeConnection.size() > 0 ){
                //空闲线程有存在连接
                //拿到再删除
                connection = freeConnection.remove(0);
            }else{
                //创建新连接
                connection = createConnection();
            }
            boolean available = isAvailable(connection);
            if (available){
                //存放在活动连接池
                activeConnection.add(connection);
            }else{
                countConne.decrementAndGet();
                connection = getConnection();
            }
        }else{
            //大于最大活动连接数 等待重复获得连接的频率后 重试
            try {
                wait(dbBean.getConnTimeOut());
                connection = getConnection();
            } catch (InterruptedException e) {
                return null;
            }
        }
        return connection;
    }

    private boolean isAvailable(Connection connection){
        try {
            if (connection == null || connection.isClosed()){
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public synchronized void releaseConnection(Connection connection)  {
        //判断线程是否可用
        if (isAvailable(connection)){
            //判断空闲线程是否大于活动线程
            if (freeConnection.size() < dbBean.getMaxConnections()){
                //空闲线程没有满
                freeConnection.add(connection);
            } else{
                //空闲线程已满
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            activeConnection.remove(connection);
            countConne.decrementAndGet();
            notifyAll();
        }
    }
}
