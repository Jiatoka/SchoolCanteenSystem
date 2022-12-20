package Swing.MyInfo;

import Controller.UserPageInfo;
import Model.LoginUtil;
import Model.OrderUtil;
import ObjectInstance.User.StoreUser;
import Swing.UIPanel.CanteenPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.jar.JarEntry;

/**个人信息面板**/
public class MyInfoPanel extends JPanel {
    private JPanel contentPanel;
    private JPanel btnPanel;
    private JPanel userNamePanel;
    private JPanel phonePanel;
    private JPanel accountPanel;
    private JPanel passwdPanel;
    private JPanel identityPanel;
    private JPanel genderPanel;
    private JLabel tipsLabel;
    private JButton jButton;
    /**用于记录文本框
     * 0:username 1:phone 2:
     * **/
    private ArrayList<JTextField> textFieldArrayList;
    /**获取用户类别**/
    private  int identity;
    /**用户的账号**/
    private  String account;
    /**用户的名字**/
    private  String userName;
    /**联系方式**/
    private  String phoneNumber;
    /**性别
     * 0:女  1:男
     * **/
    private  int gender;
    /**密码**/
    private JPasswordField passwd;
    /**男性按钮**/
    private JRadioButton radioButton1;
    /**女性按钮**/
    private JRadioButton radioButton0;
    /**保存按钮**/
    private JButton saveBtn;
    private JPanel saveBtnPanel;
    /**标题**/
    private JLabel title;
    public MyInfoPanel(final CardLayout cardLayout, final JPanel jPanel){
        title=new JLabel("用户信息");
        title.setFont(new Font(null,Font.BOLD,25));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(800,50));
        //获取用户信息
        identity= LoginUtil.getIdentity();
        account=LoginUtil.getAccount();
        userName=LoginUtil.getName();
        phoneNumber=LoginUtil.getPhone();
        String genderKind=LoginUtil.getGender();
        if("0".equals(genderKind)){
            gender=0;
        }else{
            gender=1;
        }


        textFieldArrayList=new ArrayList<JTextField>();

        //新建一个用户名字输入框
        userNamePanel=createContenPanel("用户名",userName,0);
        //联系电话输入框
        phonePanel=createContenPanel("手机号",phoneNumber,0);
        //账号
        accountPanel=createContenPanel("账号",account,0);
        //密码
        passwdPanel=createPasswdPanel("密码",0);
        //类型
        switch (identity){
            case 0:
                identityPanel=createContenPanel("用户类型","普通用户",0);
                break;
            case 1:
                identityPanel=createContenPanel("用户类型","商家",0);
                break;
            case 2:
                identityPanel=createContenPanel("用户类型","食堂管理员",0);
                break;
        }
        //性别
        radioButton0=new JRadioButton("女");
        radioButton1=new JRadioButton("男");
        radioButton0.setFont(new Font(null,Font.PLAIN,20));
        radioButton1.setFont(new Font(null,Font.PLAIN,20));
        ButtonGroup btnGroup=new ButtonGroup();
        btnGroup.add(radioButton1);
        btnGroup.add(radioButton0);
        if(gender==0){
            radioButton0.setSelected(true);
        }else {
            radioButton1.setSelected(true);
        }
        genderPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        JLabel genderLabel=new JLabel("性别");
        genderLabel.setFont(new Font(null,Font.PLAIN,20));
        genderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        genderLabel.setPreferredSize(new Dimension(150,30));
        genderLabel.setVerticalAlignment(SwingConstants.CENTER);
        genderPanel.add(genderLabel);
        genderPanel.add(radioButton0);
        genderPanel.add(radioButton1);
        genderPanel.setPreferredSize(new Dimension(800,35));
        //女性
        radioButton0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("选中为女性");
                gender=0;
            }
        });
        //男性
        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("选中为男性");
                gender=1;
            }
        });
        //保存按钮
        saveBtn=new JButton("保存");
        saveBtn.setFont(new Font(null,Font.BOLD,25));
        saveBtn.setForeground(Color.GRAY);
        saveBtn.setBackground(Color.GREEN);
        saveBtn.setPreferredSize(new Dimension(100,35));
        saveBtnPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        saveBtnPanel.add(saveBtn);
        saveBtnPanel.setPreferredSize(new Dimension(800,50));

        //男性
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        jPanel,
                        "更新功能暂未开放",
                        "错误",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        //填写面板
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        this.add(title);
        this.add(userNamePanel);
        this.add(accountPanel);
        this.add(passwdPanel);
        this.add(identityPanel);
        this.add(phonePanel);
        this.add(genderPanel);
        this.add(saveBtnPanel);
    }
    //新建一个联系地址的输入panel
    private JPanel createContenPanel(String title,String content,int i){
        //新建一个Panel
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        jPanel.setPreferredSize(new Dimension(800,50));
        //新建一个label
        JLabel titleLabel=new JLabel(title);
        titleLabel.setFont(new Font(null,Font.PLAIN,20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setPreferredSize(new Dimension(150,30));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        //新建一个输入框
        JTextField input=new JTextField();
        input.setPreferredSize(new Dimension(550,30));
        input.setFont(new Font(null,Font.PLAIN,20));
        input.setHorizontalAlignment(SwingConstants.LEFT);
        input.setText(content);
        if(i==0){
            input.setEnabled(false);
        }

        jPanel.add(titleLabel);
        jPanel.add(input);
        textFieldArrayList.add(input);
        return jPanel;
    }

    //新建一个联系地址的输入panel
    private JPanel createPasswdPanel(String title,int i){
        //新建一个Panel
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        jPanel.setPreferredSize(new Dimension(800,50));
        //新建一个label
        JLabel titleLabel=new JLabel(title);
        titleLabel.setFont(new Font(null,Font.PLAIN,20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setPreferredSize(new Dimension(150,30));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        //新建一个输入框
        passwd=new JPasswordField();
        passwd.setPreferredSize(new Dimension(550,30));
        passwd.setFont(new Font(null,Font.PLAIN,20));
        passwd.setHorizontalAlignment(SwingConstants.LEFT);
        if(i==0){
            passwd.setEnabled(false);
        }

        jPanel.add(titleLabel);
        jPanel.add(passwd);
        return jPanel;
    }

}
