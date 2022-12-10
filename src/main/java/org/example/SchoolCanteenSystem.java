package org.example;

import DAO.JDBCUtils.MysqlConnector;
import Swing.LoginGUI;
import Swing.RegisterUI;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

/**
 * Hello world!
 * @author Liang
 */
public class SchoolCanteenSystem
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException, IOException {

        //初始化Jframe和JPanel
        JFrame frame = new JFrame("LoginUI");
        LoginGUI loginGUI=new LoginGUI();
        RegisterUI registerUI=new RegisterUI();
        frame.setContentPane(loginGUI.LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,800);
        frame.setLocation(600,200);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
