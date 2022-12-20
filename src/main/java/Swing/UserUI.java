package Swing;

import Controller.UserPageInfo;
import Model.LoginUtil;
import Swing.MyInfo.MyInfoPanel;
import Swing.OrderUI.MyOrderPanel;
import Swing.StoreUserUI.StoreInfoPanel;
import Swing.StoreUserUI.StoreOrderPanel;
import Swing.StoreUserUI.StoreUserFoodPanel;
import Swing.UIPanel.CanteenPanel;
import org.example.Login;
import Image.ImageUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserUI {
    /**第一个画板
     * 0:用户 1:商家 2:管理员
     * **/
    private static int firstPanel;
    public JPanel UserPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public static void main(String[] args) throws IOException {
        Login.login("root","a123456789","3306");
        //初始化图片
        ImageUtils.initImage();
        JFrame frame = new JFrame("UserUI");
        frame.setContentPane(new UserUI().UserPanel);
//        frame.setSize(1200,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
//        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton mainPageBtn;
    private JButton myInfoBtn;
    private JButton orderInfoBtn;
    private JLabel userNameLabel;
    private JLabel identityLabel;
    private CardLayout cardLayout;
    public UserUI(){
        // 初始化标题
        userNameLabel.setText("欢迎"+ LoginUtil.getName()+"登入！");
        userNameLabel.setIcon(new ImageIcon(ImageUtils.userImage));
        userNameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        userNameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        userNameLabel.setOpaque(false);

        switch (LoginUtil.getIdentity()){
            case 0:
                identityLabel.setText("普通用户");
                break;
            case 1:
                identityLabel.setText("商家");
                break;
            case 2:
                identityLabel.setText("食堂管理员");
                break;
            default:
                System.out.println("登入错误");
        }
        // 首页监听器
        mainPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (firstPanel){
                    case 0:
                        //初始化所有的卡片
                        UserPageInfo.initVar();
                        System.out.println("点击首页");
                        //根据给定的商家进入食堂页面
                        CanteenPanel canteenPanel= null;
                        try {
                            canteenPanel = new CanteenPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(canteenPanel,"canteen");
                        cardLayout.show(contentPanel,"canteen");
                        break;
                    case 1:
                        //初始化所有的卡片
                        System.out.println("点击首页");
                        //根据给定的商家进入食堂页面
                        StoreUserFoodPanel storeUserFoodPanel= null;
                        try {
                            storeUserFoodPanel = new StoreUserFoodPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(storeUserFoodPanel,"storeUserFood");
                        cardLayout.show(contentPanel,"storeUserFood");
                        break;
                    default:
                        System.out.println("食堂管理员没有准备好");
                }
            }
        });
        //订单监听器
        orderInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (firstPanel){
                    case 0:
                        //初始化所有的卡片
                        UserPageInfo.initVar();
                        System.out.println("查看订单");
                        MyOrderPanel myOrderPanel= null;
                        try {
                            myOrderPanel = new MyOrderPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(myOrderPanel,"myOrder");
                        cardLayout.show(contentPanel,"myOrder");
                        break;
                    case 1:
                        //初始化所有的卡片
                        System.out.println("查看订单");
                        StoreOrderPanel storeOrderPanel = null;
                        try {
                            storeOrderPanel = new StoreOrderPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(storeOrderPanel,"storeOrder");
                        cardLayout.show(contentPanel,"storeOrder");
                        break;
                    default:
                        System.out.println("食堂未准备");
                }


            }
        });
        //我的信息监听器
        myInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (firstPanel){
                    case 0:
                        //初始化所有的卡片
                        UserPageInfo.initVar();
//                contentPanel.removeAll();
                        System.out.println("查看我的信息");
                        MyInfoPanel myInfoPanel= null;
                        try {
                            myInfoPanel = new MyInfoPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(myInfoPanel,"myInfo");
                        cardLayout.show(contentPanel,"myInfo");
                    break;
                    case 1:
                        //初始化所有的卡片
                        System.out.println("查看我的信息");
                        StoreInfoPanel storeInfoPanel = null;
                        try {
                            storeInfoPanel = new StoreInfoPanel(cardLayout,contentPanel);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        contentPanel.add(storeInfoPanel,"storeInfo");
                        cardLayout.show(contentPanel,"storeInfo");
                        break;
                    default:
                        System.out.println("食堂未准备");
                }

            }
        });
    }

    /**食堂选择页面,商家选择页面,菜单页面的卡片**/
    private JPanel canteenPanel;
    private JPanel storePanel;
    private JPanel foodPanel;

    /**contentPanel动态变化的页面**/
    private void createUIComponents() throws Exception {
        // TODO: place custom component creation code here
        cardLayout=new CardLayout(10,10);
        contentPanel=new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1100,600));
        /**使用Panel**/
        switch (firstPanel){
            case 0:
                CanteenPanel canteenPanel=new CanteenPanel(cardLayout,contentPanel);
                contentPanel.add(canteenPanel,"canteen");
                /**显示卡片**/
                cardLayout.show(contentPanel,"canteen");
                break;
            case 1:
                StoreUserFoodPanel storeUserFoodPanel=new StoreUserFoodPanel(cardLayout,contentPanel);
                contentPanel.add(storeUserFoodPanel,"storeUserFood");
                cardLayout.show(contentPanel,"storeUserFood");
                break;
            default:
                System.out.println("食堂管理员未准备好");
        }

    }
    public static void setPanel(int identity){
        firstPanel=identity;
    }
}
