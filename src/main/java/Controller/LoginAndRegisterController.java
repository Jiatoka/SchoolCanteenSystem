package Controller;

import Controller.Lock.LoginLock;
import Controller.Lock.RegisterLock;
import Model.LoginUtil;
import Swing.LoginGUI;
import Swing.RegisterUI;
import org.example.Screen;

import javax.swing.*;
/**
 * 登入和注册页面切换的控制器
 *
 * 登入页面中只有点击注册或是登入成功才会切换页面
 * 主页页面中只有成功注册或是返回才会切换页面
 * **/
public class LoginAndRegisterController {
    /**控制登入页面和注册页面**/
    public  void loginAndRegisterController(JFrame jFrame, LoginGUI loginGUI, RegisterUI registerUI) throws InterruptedException {
        while(true){
            loginFrame(jFrame);
            jFrame.setContentPane(loginGUI.LoginPanel);
            jFrame.setVisible(true);
            synchronized (LoginLock.lock){
                System.out.println("正在登入");
                LoginLock.lock.wait();
                System.out.println("登入结束");
                jFrame.remove(loginGUI.LoginPanel);
                if(LoginUtil.getState()==1){
                    break;
                }
            }

            registerFrame(jFrame);
            jFrame.setContentPane(registerUI.RegisterPanel);
            jFrame.setVisible(true);
            synchronized (RegisterLock.lock){
                System.out.println("正在注册");
                RegisterLock.lock.wait();
                System.out.println("注册完毕");
                jFrame.remove(registerUI.RegisterPanel);
            }
            loginGUI=new LoginGUI();
        }
    }
    /**登入页面布局**/
    private   void loginFrame(JFrame frame){
        frame.setSize(600,400);
        Screen.centerFrame(frame,600,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**注册页面布局**/
    private   void registerFrame(JFrame frame){
        frame.setSize(1200,800);
        Screen.centerFrame(frame,1200,800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
