package com.quchwe.gd.cms.downLoadFile.download;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool
{
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    
    private Vector<Connection> pool;
    
    /* 公有属性 */
    private String url = "jdbc:mysql://172.17.98.23:1234/lbss_spjd";
    
    private String username = "lbss";
    
    private String password = "lbss2015@)!%";
    
    private String driverClassName = "com.mysql.jdbc.Driver";
    
    private int poolSize = 10;
    
    Connection conn = null;
    
    /* 构造方法，做一些初始化工作 */
    public ConnectionPool()
    {
        pool = new Vector<Connection>(poolSize);
        
        for (int i = 0; i < poolSize; i++)
        {
            try
            {
                Class.forName(driverClassName);
                conn = DriverManager.getConnection(url, username, password);
                pool.add(conn);
            }
            catch (ClassNotFoundException e)
            {
                logger.error("ClassNotFoundException error :{}", e);
            }
            catch (SQLException e)
            {
                logger.error("SQLException error :{}", e);
            }
        }
    }
    
    /* 返回连接到连接池 */
    public synchronized void release()
    {
        pool.add(conn);
    }
    
    /* 返回连接池中的一个数据库连接 */
    public synchronized Connection getConnection()
    {
        if (pool.size() > 0)
        {
            Connection conn = pool.get(0);
            pool.remove(conn);
            return conn;
        }
        else
        {
            return null;
        }
    }
    
}
