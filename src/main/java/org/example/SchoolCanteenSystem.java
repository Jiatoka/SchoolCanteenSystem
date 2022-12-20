package org.example;

import Controller.LoginAndRegisterController;
import DAO.JDBCUtils.MysqlConnector;
import Image.ImageUtils;
import Model.LoginUtil;
import Swing.LoginGUI;
import Swing.RegisterUI;
import Swing.UserUI;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

/**
 * Hello world!
 * @author Liang
 */
public class SchoolCanteenSystem
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

        //初始化Jframe和JPanel
        Login.login("root","a123456789","3306");
        ImageUtils.initImage();
        //初始化布局
        JFrame jFrame=new JFrame("校园外卖点餐系统");
        LoginGUI loginGUI=new LoginGUI();
        RegisterUI registerUI=new RegisterUI();

        //初始化登入和注册页面控制器
        LoginAndRegisterController loginAndRegisterController=new LoginAndRegisterController();

        //登入和注册页面
        loginAndRegisterController.loginAndRegisterController(jFrame,loginGUI,registerUI);
        UserUI.setPanel(LoginUtil.getIdentity());
        //进入用户页面
        jFrame.setSize(1300,800);
        UserUI userUI=new UserUI();
        jFrame.setContentPane(userUI.UserPanel);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        //结束
        System.out.println("退出系统");
        Thread.sleep((long)Math.pow(2,30));
    }
}
