package DAO.JDBCUtils;
import org.example.Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import  java.sql.*;
import java.util.Scanner;

public class MysqlConnector {
    /**用户信息**/
    private static String url;
    private MysqlConnector(){

    }

    static{
        try{
            //加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**创建数据库连接**/
    public static  Connection createConnection() throws ClassNotFoundException, SQLException {
        //用户信息
        MysqlConnector.url="jdbc:mysql://localhost:"+ Login.getPort()+"/schoolcanteensystem?useUnicode=true&characterEncoding=utf8&useSSL=false";

        //获取连接
        Connection conn=DriverManager.getConnection(url,Login.getUser(),Login.getPassword());
//        System.out.println("连接成功");
        //返回连接
        return  conn;
    }

    /**销毁链接**/
     public static void closeConnection(Connection connection,Statement statement,ResultSet resultSet) throws SQLException {
         resultSet.close();
         statement.close();
         connection.close();
     }

    public static void closeConnection(Connection connection,Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }

}
