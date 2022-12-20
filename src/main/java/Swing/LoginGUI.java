package Swing;

import Controller.Lock.LoginLock;
import DAO.JDBCUtils.UserDAO.StoreUserDao;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import Image.ImageUtils;
import Model.LoginUtil;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;
import org.example.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LoginGUI {
    public JPanel LoginPanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel buttonPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel contentPanel;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel loginButtonPanel;
    private JPanel registerButtonPanel;

    /**密码和账号的要求**/
    private String regx="[0-9a-zA-Z]{3,20}";

    /**用户登入后的用户信息**/
    private UserAccount userAccount;
    private int identity=0;
    private NormalUser normalUser;
    private Administrator administrator;
    private StoreUser storeUser;
    public UserAccount getUserAccount(){
        return this.userAccount;
    }

    public int getIdentity() {
        return identity;
    }

    public LoginGUI() {

        //登入的logo
        titleLabel.setIcon(new ImageIcon(ImageUtils.getSchoolIconImage()));
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(60,147,240));
        //登入按钮
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取账号和密码
                String account=usernameField.getText().trim();
                String password=new String(passwordField.getPassword()).trim();
                System.out.println("account:"+account);
                System.out.println("password:"+password);

                //连接数据库
                try {
                    Login.login("root","a123456789","3306");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //登入数据库
                LoginUtil.initVar();
                boolean flag;
                try {
                    flag=LoginUtil.login(account,password);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                //查看是否登入成功
                if(flag){
                    //登入成功
                    try {
                        userAccount=new UserAccount(account,LoginUtil.getState(),password);
                        switch (LoginUtil.getIdentity()){
                            case 0:
                                normalUser=new NormalUser(LoginUtil.getId(),LoginUtil.getAccount(),
                                        LoginUtil.getName(),LoginUtil.getGender(),LoginUtil.getPhone());
                                break;
                            case 1:
                                //补全商店的名字和canteen_id
                                StoreUserDao storeUserDao=new StoreUserimpl();
                                List<StoreUser> storeUsers= storeUserDao.queryByIndex(new StoreUser(1,account
                                        ,1,"",""));
                                StoreUser tmpStoreUser=storeUsers.get(0);
                                storeUser=new StoreUser(tmpStoreUser.getId(),tmpStoreUser.getUserAccount(),tmpStoreUser.getCanteenId(),
                                        tmpStoreUser.getName(),tmpStoreUser.getPhone());
                                break;
                            case 2:
                                administrator=new Administrator(LoginUtil.getId(),LoginUtil.getAccount(),
                                        LoginUtil.getName(),LoginUtil.getGender(),LoginUtil.getPhone());
                                break;
                            default:
                                throw new RuntimeException("登入出错,请查看GUI的Login");

                        }
                        JOptionPane.showMessageDialog(
                                LoginPanel,
                                "登入成功,即将进入业户页面",
                                "登入成功",
                                1
                        );
                        synchronized (LoginLock.lock){
                            System.out.println("进入用户页面");
                            LoginLock.lock.notifyAll();
                        }

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    //登入失败
                    LoginUtil.initVar();
                    JOptionPane.showMessageDialog(
                            LoginPanel,
                            "登入失败,账号或密码错误",
                            "登入失败",
                            0
                    );
                }

            }
        });

        //注册按钮
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (LoginLock.lock){
                    System.out.println("注册");
                    LoginLock.lock.notifyAll();
                }
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("校园餐厅外卖系统");
        LoginGUI loginGUI=new LoginGUI();
        frame.setContentPane(loginGUI.LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setSize(600,400);
        frame.setLocation(600,200);
        frame.setResizable(false);
        frame.setVisible(true);
        System.out.println("hello world");
    }
}
