package Swing.StoreUserUI;

import Controller.UserPageInfo;
import DAO.JDBCUtils.Order.ContactDao;
import DAO.JDBCUtils.Order.ContactDaoImpl;
import DAO.JDBCUtils.UserDAO.NormalUserDao;
import DAO.JDBCUtils.UserDAO.NormalUserDaoimpl;
import DAO.JDBCUtils.UserDAO.StoreUserDao;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import Model.LoginUtil;
import Model.OrderUtil;
import ObjectInstance.Food.Food;
import ObjectInstance.Order.Contact;
import ObjectInstance.Order.Order;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import Swing.RoundBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**实现商家接单功能**/
public class StoreOrderPanel extends JPanel {
    private JScrollPane jScrollPane;
    /**内容面板**/
    private JPanel panel;
    private int[][] color=new int[][]{{238, 203, 173},{174, 238, 238},
            {173, 255, 47}};
    int colorSize=3;
    /**记录了每样菜品的数量和价钱
     * (foodMapTmp.get(id)).get(数量)表示数量
     * (foodMapTmp.get(id)).get(价钱)表示价钱
     * **/
    private HashMap<Integer, HashMap<String,Integer>> foodMapTmp;
    /**记录每种菜品的数量**/
    private List<HashMap<Food,Integer>> foodList;
    /**用户身份**/
    private int identity;
    /**用户id**/
    private int id;
    /**用户所有订单**/
    List<Order> orderList;
    /**接受订单**/
    private  JButton receiveBtn;
    private int valid;
    public StoreOrderPanel(CardLayout cardLayout, JPanel jPanel) throws Exception {
        //用户身份
        identity= LoginUtil.getIdentity();
//        identity=1;
        id=LoginUtil.getId();
//        id=1;
        System.out.println("identity:"+identity+"userId:"+id);
        //查询订单
        orderList= OrderUtil.queryOrder(identity,id);
        //生成订单栏目
        ArrayList<JPanel> jPanelArrayList=new ArrayList<JPanel>();
        for(Order order:orderList){
            System.out.println("order_id:"+order.getOrderId());
            JPanel orderInfo=createOrderBar(order);
            if(valid==1){
                jPanelArrayList.add(orderInfo);
            }
        }

        int size=jPanelArrayList.size();

        /**********用于修改选项框布局的核心代码*************/
        //设置布局
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
        //将按钮加入面板
        for (JPanel mPanel:jPanelArrayList){
            panel.add(mPanel);
        }
        panel.setPreferredSize(new Dimension(850,size*140+20));
        /********************************************/

        //将面板加入滚动面板
        jScrollPane=new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        //将滚动面板加入面板中
        this.setLayout(new BorderLayout(10,10));
        jScrollPane.setPreferredSize(new Dimension(1000,350));
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jScrollPane,BorderLayout.CENTER);
    }

    /**添加监听器**/
    private void addListener(JButton jButton, final StoreUser user, CardLayout cardLayout, JPanel jPanel){
        // 添加按钮的点击事件监听器
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("按钮被点击");
                UserPageInfo.setStoreUser(user);
            }
        });
    }

    /**新建一条订单信息**/
    private  JPanel createOrderBar(final Order order) throws Exception {
        valid=1;
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        jPanel.setPreferredSize(new Dimension(850,120));
        //先查找订单信息
        List<HashMap<Food,Integer>> orderInfoList=OrderUtil.queryOrderFood(order.getOrderId());

        //生成用户信息
        JPanel userInfoPanel=new JPanel(new GridLayout(3,1,5,5));
        userInfoPanel.setPreferredSize(new Dimension(200,100));
        ContactDao contactDao=new ContactDaoImpl();
        List<Contact> contactList=contactDao.queryByPK(new Contact(
                order.getOrderId(),1,"0","0",
                "0"
        ));
        Contact contact;
        if(contactList.size()>0){
            contact=contactList.get(0);
        }else{
            contact=new Contact(0,0,"",
                    "","");
            valid=0;
        }

        JLabel userNameLabel=new JLabel("用户名:"+contact.getUserName());
        JLabel phoneLabel=new JLabel("联系电话:"+contact.getPhone());
        System.out.println("contact:"+contact.getPhone());
        JLabel tipsLabel=new JLabel("备注");
        userNameLabel.setFont(new Font(null,Font.PLAIN,15));
        phoneLabel.setFont(new Font(null,Font.PLAIN,15));
        tipsLabel.setFont(new Font(null,Font.PLAIN,15));
        tipsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setPreferredSize(new Dimension(180,25));
        phoneLabel.setPreferredSize(new Dimension(180,25));
        tipsLabel.setPreferredSize(new Dimension(180,25));
        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(phoneLabel);
        userInfoPanel.add(tipsLabel);

        //用户地址
        JPanel addressPanel=new JPanel(new BorderLayout(5,5));
        addressPanel.setPreferredSize(new Dimension(150,100));
        JLabel jLabel=new JLabel("用户地址");
        jLabel.setFont(new Font(null,Font.PLAIN,15));
        jLabel.setPreferredSize(new Dimension(100,25));
        JTextArea textArea=new JTextArea();
        textArea.setFont(new Font(null,Font.PLAIN,15));
        textArea.setPreferredSize(new Dimension(100,50));
        addressPanel.add(jLabel,BorderLayout.NORTH);
        addressPanel.add(textArea,BorderLayout.CENTER);
        textArea.setText(contact.getAddress());
        textArea.setLineWrap(true);
        textArea.setEnabled(false);

        //生成菜单详细信息
        JPanel foodInfoPanel=new JPanel(new GridLayout(3,1,1,1));
        foodInfoPanel.setPreferredSize(new Dimension(150,100));
        ArrayList<JLabel> foodLabelList=new ArrayList<JLabel>();
        for(HashMap<Food,Integer> hashMap:orderInfoList){
            JLabel label=new JLabel(formatFoodInfo(hashMap));
            label.setFont(new Font(null,Font.PLAIN,15));
            foodLabelList.add(label);
        }
        //生成查看更多菜单按钮
        JLabel seeMore=new JLabel("查看更多");
        seeMore.setFont(new Font(null,Font.PLAIN,12));
        seeMore.setForeground(Color.BLACK);
        seeMore.setHorizontalAlignment(SwingConstants.CENTER);
        //添加监听器
        //TODO

        //菜单详情合并,只会显示两个菜单
        int count=0;
        for(JLabel label:foodLabelList){
            foodInfoPanel.add(label);
            count+=1;
            if(count>=2){
                break;
            }
        }
        foodInfoPanel.add(seeMore);
        foodInfoPanel.setBorder(new EmptyBorder(0,10,0,0));

        //生成时间，价格，订单状态栏目
        JPanel infoPanel=new JPanel(new GridLayout(3,1,5,5));
        infoPanel.setPreferredSize(new Dimension(150,100));
        JLabel price=new JLabel("总价￥"+order.getPrice());
        price.setFont(new Font(null,Font.PLAIN,15));
        price.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel time=new JLabel("时间:"+order.getOrderTime());
        time.setFont(new Font(null,Font.PLAIN,15));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel state=null;
        if(order.getState()==0){
            state=new JLabel("等待商家接单");
            state.setForeground(Color.RED);
        }else{
            state=new JLabel("已接单");
            state.setForeground(Color.GREEN);
        }
        state.setFont(new Font(null,Font.PLAIN,20));
        state.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(price);
        infoPanel.add(time);
        infoPanel.add(state);

        //生成接单面板
        receiveBtn=new JButton("接单");
        receiveBtn.setPreferredSize(new Dimension(100,50));
        receiveBtn.setFont(new Font(null,Font.BOLD,25));
        receiveBtn.setBackground(Color.GREEN);
        receiveBtn.setForeground(Color.GRAY);
        //点击按钮就接单
        final JLabel finalState = state;
        receiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(order.getState()==1){
                    JOptionPane.showMessageDialog(
                            panel,
                            "该订单以接受,不能重复接单",
                            "错误",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                // 获取到的事件源就是按钮本身
                finalState.setText("已接单");
                finalState.setForeground(Color.GREEN);
                try {
                    OrderUtil.receiveOrder(order.getOrderId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //加入面板
        jPanel.add(userInfoPanel);
        jPanel.add(addressPanel);
        jPanel.add(foodInfoPanel);
        jPanel.add(infoPanel);
        jPanel.add(receiveBtn);
        jPanel.setBorder(new RoundBorder(Color.BLUE));
        return jPanel;
    }
    /**菜单信息格式化**/
    private String formatFoodInfo(HashMap<Food,Integer> hashMap){
        StringBuilder stringBuilder=new StringBuilder();
        int num=0;
        for(Food food:hashMap.keySet()){
            num=hashMap.get(food);
            stringBuilder.append(food.getFoodName()).append("x2");
        }
        return stringBuilder.toString();
    }
}
