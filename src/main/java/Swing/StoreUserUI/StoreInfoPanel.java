package Swing.StoreUserUI;

import DAO.JDBCUtils.UserDAO.StoreUserDao;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import Model.LoginUtil;
import ObjectInstance.User.StoreUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class StoreInfoPanel extends JPanel {
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
    /**食堂**/
    private JComboBox<String> comboBox;
    private int canteenId=0;
    private JPanel canteenPanel;
    public StoreInfoPanel(final CardLayout cardLayout, final JPanel jPanel) throws Exception {
        canteenPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));

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
        //食堂
        JLabel jLabel=new JLabel("食堂");
        jLabel.setPreferredSize(new Dimension(150,30));
        jLabel.setFont(new Font(null,Font.PLAIN,20));
        String[] canteenList=new String[]{"荔园一食堂","荔园二食堂","荷园一食堂","荷园二食堂",
        "燕园一食堂"};
        comboBox=new JComboBox<String>(canteenList);
        comboBox.setFont(new Font(null,Font.PLAIN,20));
        // 添加条目选中状态改变的监听器
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + comboBox.getSelectedIndex() + " = " + comboBox.getSelectedItem());
                    canteenId=1+comboBox.getSelectedIndex();
                }
            }
        });
        StoreUserDao storeUserDao=new StoreUserimpl();
        StoreUser storeUser=storeUserDao.queryByPK(new StoreUser(LoginUtil.getId(),
                "0",0,"0",
                "0")).get(0);
        comboBox.setSelectedIndex(storeUser.getCanteenId()-1);
//        comboBox.setEnabled(false);
        //专属面板存放饭堂选择
        canteenPanel.add(jLabel);
        canteenPanel.add(comboBox);
        canteenPanel.setPreferredSize(new Dimension(800,50));

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
        this.add(canteenPanel);
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
