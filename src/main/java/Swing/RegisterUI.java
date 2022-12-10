package Swing;
import Controller.Lock.RegisterLock;
import DAO.JDBCUtils.UserAccountDao;
import DAO.JDBCUtils.UserAccountDaoImpl;
import DAO.JDBCUtils.UserDAO.*;
import Model.LoginUtil;
import Model.RegisterUtil;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;
import org.example.Login;

import java.io.IOException;
import java.lang.*;

import javax.swing.*;
import javax.xml.transform.OutputKeys;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterUI {
    public JPanel RegisterPanel;
    private JLabel title;
    private JPanel titlePanel;
    private JPanel infoPanel;

    /**身份
     * 0:普通用户,1:商家,2:管理员
     * **/
    private int identity=0;

    /**食堂
     * 0:荔园1食堂,1:荔园2食堂,2:荷园1食堂,3:荷园2食堂,4:燕园1食堂
     */
    private int canteenKind=0;

    /**
     * 0:女,1:男
     * **/
    private int genderKind=0;

    /**密码和账号的要求**/
    private String regx="[0-9a-zA-Z]{3,20}";

    public RegisterUI() {
        //获取性别
        gender.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //每选择一项就触发一次
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    genderKind=gender.getSelectedIndex();
                    System.out.println("选中: " + genderKind + " = " + gender.getSelectedItem());
                }
            }
        });

        //普通用户
        normalUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("选中普通用户");
                identity=0;
            }
        });

        //商家
        storeUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("选中商家");
                identity=1;
            }
        });

        //管理员
        adminUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("选中管理员");
                identity=2;
            }
        });

        //确认注册
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Login.login("root","a123456789","3306");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //获取用户信息
                String userAccount=account.getText().trim();
                String password1=new String(passwordField1.getPassword()).trim();
                String password2=new String(passwordField2.getPassword()).trim();
                String usernameFiled=username.getText().trim();
                String phoneNumber=phoneField.getText().trim();
                if(usernameFiled.length()==0||phoneNumber.length()==0){
                    JOptionPane.showMessageDialog(
                            RegisterPanel,
                            "用户名和联系方式不能为空",
                            "用户名和联系方式错误",
                            0
                    );
                    return;
                }
                //检查用户和密码是否合法
                if (Pattern.matches(regx,userAccount)&&Pattern.matches(regx,password1)){
                    //检查秘密是否重复
                    if(password1.equals(password2)){
                        //检查账号是否存在
                        UserAccountDao userAccountDao=new UserAccountDaoImpl();
                        UserAccount userAccount1= null;
                        try {
                            userAccount1 = new UserAccount(userAccount,0,password1);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        List<UserAccount> userAccountList= null;
                        try {
                            userAccountList = userAccountDao.queryByPK(userAccount1);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        if(userAccountList.size()>0){
                            JOptionPane.showMessageDialog(
                                    RegisterPanel,
                                    "账号已存在,请更换账号",
                                    "账号重复",
                                    0
                            );
                        }else{
                            //账号不存在直接在用户表中插入数据
                            try {
                                userAccountDao.insert(userAccount1);
                                insertUser(userAccount,phoneNumber,usernameFiled);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            synchronized (RegisterLock.lock){
                                System.out.println("注册成功");
                                RegisterLock.lock.notifyAll();
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(
                                RegisterPanel,
                                "请保证两次输入的密码相同",
                                "密码错误",
                                0
                        );
                    }
                }else {
                    JOptionPane.showMessageDialog(
                            RegisterPanel,
                            "账号和密码只能是0-9a-zA-Z组成的长度为3~20的字符串",
                            "账号和密码格式非法",
                            0
                    );
                }
            }
        });

        //返回登入页面
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                synchronized (RegisterLock.lock){
                    System.out.println("返回登入页面");
                    RegisterLock.lock.notifyAll();
                }
            }
        });

        //选择商家所在食堂
        canteen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //每选择一项就触发一次
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    canteenKind=canteen.getSelectedIndex();
                    System.out.println("选中: " + canteenKind + " = " + canteen.getSelectedItem());
                }
            }
        });
    }

    private void insertUser(String userAccount,String phoneNumber,String user) throws SQLException, ClassNotFoundException {
        String sex=genderKind==0?"0":"1";
        switch (identity){
            case 0:
                NormalUser normalUser=new NormalUser(1,userAccount,user,sex,phoneNumber);
                NormalUserDao normalUserDao=new NormalUserDaoimpl();
                normalUserDao.insert(normalUser);
                break;
            case 1:
                StoreUser storeUser=new StoreUser(1,userAccount,canteenKind+1,user,phoneNumber);
                StoreUserDao storeUserDao=new StoreUserimpl();
                storeUserDao.insert(storeUser);
                break;
            case 2:
                Administrator administrator=new Administrator(1,userAccount,user,sex,phoneNumber);
                AdministratorDao administratorDao=new AdministratorDaoimpl();
                administratorDao.insert(administrator);
                break;
            default:
                throw new RuntimeException("注册账号错误");
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("RegisterUI");
        frame.setContentPane(new RegisterUI().RegisterPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setSize(1200,800);
        frame.setLocation(600,200);
//        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JTextField account;
    private JLabel label1;
    private JTextField username;
    private JLabel label2;
    private JPasswordField passwordField1;
    private JLabel label3;
    private JPasswordField passwordField2;
    private JLabel label4;
    private JLabel label5;
    private JTextField phoneField;
    private JLabel genderLabel;
    private JComboBox gender;
    private JPanel genderPanel;
    private JButton confirmBtn;
    private JButton returnBtn;
    private JLabel userKind;
    private JRadioButton normalUserBtn;
    private JRadioButton storeUserBtn;
    private JRadioButton adminUserBtn;
    private JComboBox canteen;
    private JLabel canteenTitle;
}
