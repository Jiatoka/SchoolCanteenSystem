package org.example;

import DAO.JDBCUtils.MysqlConnector;
import com.mysql.cj.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 登入数据库
 * **/
public class Login {
    private static String user;
    private static String password;
    private static String port;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Login.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Login.password = password;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Login.port = port;
    }

    public static void login() throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\n请输入账号:");
        Login.user=reader.readLine();
        System.out.print("\n请输入密码:");
        Login.password= reader.readLine();
        System.out.print("\n请输入端口号:");
        Login.port=reader.readLine();
        reader.close();
        System.out.println(Login.user+"\n"+Login.password+"\n"+Login.port);
    }

    public static void login(String user,String password,String port) throws IOException {
        Login.user=user;
        Login.password= password;
        Login.port=port;
        System.out.println(Login.user+"\n"+Login.password+"\n"+Login.port);
    }
}
