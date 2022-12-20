package Swing.OrderUI;

import Controller.UserPageInfo;
import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.Order.OrderDao;
import DAO.JDBCUtils.Order.OrderDaoImpl;
import DAO.JDBCUtils.UserDAO.StoreUserDao;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import DAO.JDBCUtils.View.StoreFoodViewDao;
import DAO.JDBCUtils.View.StoreFoodViewDaoImpl;
import Model.LoginUtil;
import Model.OrderUtil;
import ObjectInstance.Food.Food;
import ObjectInstance.Order.Order;
import ObjectInstance.Order.OrderInfo;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;
import Swing.UIPanel.OrderPanel;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**查看用户订单记录**/
public class MyOrderPanel extends JPanel {
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
    public MyOrderPanel(CardLayout cardLayout, JPanel jPanel) throws Exception {
        //用户身份
        identity= LoginUtil.getIdentity();
        id=LoginUtil.getId();
        System.out.println("identity:"+identity+"userId:"+id);
        //查询订单
        orderList= OrderUtil.queryOrder(identity,id);
        //生成订单栏目
        ArrayList<JPanel> jPanelArrayList=new ArrayList<JPanel>();
        for(Order order:orderList){
            JPanel orderInfo=createOrderBar(order);
            jPanelArrayList.add(orderInfo);
        }

        int size=jPanelArrayList.size();

        /**********用于修改选项框布局的核心代码*************/
        //设置布局
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        //将按钮加入面板
        for (JPanel mPanel:jPanelArrayList){
            panel.add(mPanel);
        }
        panel.setPreferredSize(new Dimension(800,size*110+10));
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
    private void addListener(JButton jButton, final StoreUser user, CardLayout cardLayout,JPanel jPanel){
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
    private  JPanel createOrderBar(Order order) throws Exception {
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        jPanel.setPreferredSize(new Dimension(800,100));
        //先查找订单信息
        List<HashMap<Food,Integer>> orderInfoList=OrderUtil.queryOrderFood(order.getOrderId());

        //生成餐店图片
        JLabel jLabel=new JLabel("暂无图片");
        jLabel.setPreferredSize(new Dimension(150,100));
        jLabel.setFont(new Font(null,Font.PLAIN,20));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //生成商店名字
        StoreUserDao storeUserDao=new StoreUserimpl();
        List<StoreUser> storeUsers=storeUserDao.queryByPK(new StoreUser(order.getStoreId(),
                "0",1,"0","0"));
        StoreUser storeUser=storeUsers.get(0);
        String userName=storeUser.getName();
        JLabel userNameLabel=new JLabel(userName);
        userNameLabel.setFont(new Font(null,Font.PLAIN,20));
        userNameLabel.setPreferredSize(new Dimension(160,50));

        //生成菜单详细信息
        JPanel foodInfoPanel=new JPanel(new GridLayout(3,1,1,1));
        foodInfoPanel.setPreferredSize(new Dimension(150,100));
        ArrayList<JLabel> foodLabelList=new ArrayList<JLabel>();
        for(HashMap<Food,Integer> hashMap:orderInfoList){
            JLabel label=new JLabel(formatFoodInfo(hashMap));
            label.setFont(new Font(null,Font.PLAIN,20));
            foodLabelList.add(label);
        }
        //生成查看更多菜单按钮
        JLabel seeMore=new JLabel("查看更多");
        seeMore.setFont(new Font(null,Font.PLAIN,10));
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

        //加入面板
        jPanel.add(jLabel);
        jPanel.add(userNameLabel);
        jPanel.add(foodInfoPanel);
        jPanel.add(infoPanel);
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
