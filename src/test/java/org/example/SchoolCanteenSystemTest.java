package org.example;

import Controller.LoginAndRegisterController;
import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.Order.ContactDao;
import DAO.JDBCUtils.Order.ContactDaoImpl;
import DAO.JDBCUtils.UserAccountDao;
import DAO.JDBCUtils.UserAccountDaoImpl;
import DAO.JDBCUtils.UserDAO.NormalUserDao;
import DAO.JDBCUtils.UserDAO.NormalUserDaoimpl;
import DAO.JDBCUtils.View.StoreFoodViewDao;
import DAO.JDBCUtils.View.StoreFoodViewDaoImpl;
import Model.LoginUtil;
import Model.OrderUtil;
import Model.RegisterUtil;
import ObjectInstance.Food.Food;
import ObjectInstance.Order.Order;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;
import ObjectInstance.View.StoreFoodView;
import Swing.LoginGUI;
import Swing.MyInfo.MyInfoPanel;
import Swing.OrderUI.MyOrderPanel;
import Swing.RegisterUI;
import Swing.StoreUserUI.StoreOrderPanel;
import Swing.UIPanel.CanteenPanel;
import Image.ImageUtils;
import Swing.UIPanel.StorePanel;
import Swing.UserUI;
import com.mysql.cj.log.Log;
import com.sun.org.apache.xpath.internal.operations.Or;
import jdk.jfr.StackTrace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.cert.TrustAnchor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class SchoolCanteenSystemTest {
    @Test
    public void testSchoolSystem() throws SQLException, IOException, ClassNotFoundException {
        //登入
        Login.login("root","a123456789","3306");

        Connection connection= MysqlConnector.createConnection();
        String sql="insert into user_account values('200111215','0','a123456789')";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        if(preparedStatement.executeUpdate()>0){
            System.out.println("Insert Success");
        }
        String sql2="select  * from user_account";
        ResultSet rs=preparedStatement.executeQuery(sql2);

        while(rs.next()){
            System.out.println("user_account:"+rs.getString(1)+
                    "\nstate:"+rs.getInt("state")+
                    "\nuser_password:"+rs.getString("user_password"));
            System.out.println("-----------------------------------------------");
        }
        MysqlConnector.closeConnection(connection,preparedStatement,rs);
    }

    @Test
    public void testUserAccount() throws Exception {
        Login.login("root","a123456789","3306");

        /**test count**/
        UserAccountDao userAccountDao=new UserAccountDaoImpl();
        System.out.println(UserAccountDaoImpl.getCount());

        /**insert**/
        userAccountDao.insert(new UserAccount("200111230",1,"123456789"));

        /**update**/
        userAccountDao.update(new UserAccount("200111230",0,"123"));

        /**select**/
        List<UserAccount> resList1=userAccountDao.queryByPK(new UserAccount("200111230",0,"123456789"));

        /**delete**/
        userAccountDao.delete(new UserAccount("200111230",1,"123456789"));

        /**select all**/
        List<UserAccount> resList2= userAccountDao.queryAll();

        JSONArray jsonArray1=new JSONArray(resList1);
        System.out.println(jsonArray1.toString());
        JSONArray jsonArray2=new JSONArray(resList2);
        System.out.println(jsonArray2.toString());
    }

    @Test
    public  void testUser() throws Exception {
        Login.login("root","a123456789","3306");

        /**test count**/
        NormalUserDao normalUserDao=new NormalUserDaoimpl();
        System.out.println(NormalUserDaoimpl.getCount());
        System.out.println(NormalUserDaoimpl.getMaxId());
        NormalUser normalUser=new NormalUser(1,
                "200111215","LiHua","","13570784729");
        /**insert**/
        normalUserDao.insert(normalUser);
        normalUser.setGender("0");
        normalUser.setName("XiaoHong");
        /**update**/
        normalUserDao.update(normalUser);

        /**select**/
        List<NormalUser> resList1=normalUserDao.queryByPK(normalUser);

        /**delete**/
        normalUserDao.delete(normalUser);

        /**select all**/
        List<NormalUser> resList2= normalUserDao.queryAll();

        JSONArray jsonArray1=new JSONArray(resList1);
        System.out.println(jsonArray1.toString());
        JSONArray jsonArray2=new JSONArray(resList2);
        System.out.println(jsonArray2.toString());
    }

    @Test
    public void testRegisterAndLogin() throws Exception {
        Login.login("root","a123456789","3306");
        LoginUtil.login("200111211","123");
        System.out.println(LoginUtil.getIdentity());
        System.out.println(LoginUtil.getName());
        System.out.println(LoginUtil.getAccount());
        System.out.println(LoginUtil.getPhone());
        System.out.println(LoginUtil.getState());
        LoginUtil.initVar();
        RegisterUtil.register(new UserAccount("200111201",0,"123456789")
        ,new NormalUser(1,"200111201","LiangJiajun","0","13380874079"));
        LoginUtil.login("200111201","123456789");
        System.out.println(LoginUtil.getIdentity());
        LoginUtil.initVar();

        RegisterUtil.register(new UserAccount("5201314",0,"123456789")
                ,new StoreUser(1,"5201314",1,"IKun","5201314"));
        LoginUtil.login("5201314","123456789");
        System.out.println(LoginUtil.getIdentity());
        LoginUtil.initVar();

        RegisterUtil.register(new UserAccount("5201314",0,"123456789")
                ,new Administrator(1,"5201314","IKun","0","5201314"));
        LoginUtil.login("5201314","123456");
        System.out.println(LoginUtil.getIdentity());
        LoginUtil.initVar();
    }
    @Test
    public void test() throws InterruptedException {
//        String a="   123    5    ";
//        System.out.println(a.trim());
//        System.out.println((" 123    5 ".trim()).equals(a.trim()));
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 创建文本区域组件
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);                         // 自动换行
        textArea.setFont(new Font(null, Font.PLAIN, 18));   // 设置字体

        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane = new JScrollPane(
                textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        jf.setContentPane(scrollPane);
        jf.setVisible(true);
        Thread.sleep(10000);
    }

    @Test
    public  void  testFood() throws IOException, SQLException, ClassNotFoundException {
        Login.login("root","a123456789","3306");
        String[] foodName=new String[]{"可乐","雪碧","橙汁","啤酒"
        ,"提拉米苏","泡芙","葡式蛋挞","草莓布丁",
        "北京烤鸭","白切只因(鸡)","盐焗只因(鸡)","烧只因(鸡)"};
        int[] kind=new int[]{1,1,1,1,2,2,2,2,3,3,3,3};
        int[] price=new int[]{3,3,3,10,25,20,5,15,299,250,250,250};

        //初始化Dao
        FoodDao foodDao=new FoodDaoImpl();
        for(int i=0;i< foodName.length;i++){
            Food food=new Food(1,((int)(Math.random()*2))+1,foodName[i],"",
                    price[i]);
            foodDao.insert(food,kind[i]);
        }

    }

    @Test
    public void testOrder() throws Exception {
        Login.login("root","a123456789","3306");
        FoodDao foodDao=new FoodDaoImpl();
        List<HashMap<Food,Integer>> foodList=new ArrayList<HashMap<Food, Integer>>();
        List<Food> foodList1= foodDao.queryAll();
        HashMap<Food,Integer> hashMap=new HashMap<Food, Integer>();

        hashMap.put(foodList1.get(2),1);
        foodList.add((HashMap<Food, Integer>) hashMap.clone());
        hashMap.clear();

        hashMap.put(foodList1.get(8),1);
        foodList.add((HashMap<Food, Integer>) hashMap.clone());
        hashMap.clear();

        hashMap.put(foodList1.get(9),1);
        foodList.add((HashMap<Food, Integer>) hashMap.clone());
        hashMap.clear();

        //订单
        OrderUtil.placeOrder(1,1,552,"只因",foodList);

        //查看订单
        List<Order> orderList=OrderUtil.queryOrder(0,1);
        System.out.println(new JSONArray(orderList).toString());

        //查看详细订单
        List<HashMap<Food,Integer>> foodList2=OrderUtil.queryOrderFood(1);
        for (HashMap<Food,Integer> obj:foodList2){
            for(Food food:obj.keySet()){
                System.out.println(food.getFoodName()+":"+obj.get(food));
            }
        }

        //接单
        OrderUtil.receiveOrder(1);

        //查看订单
        List<Order> orderList1=OrderUtil.queryOrder(1,1);
        System.out.println(new JSONArray(orderList1).toString());
    }
    @Test
    public void testContact() throws IOException, SQLException, ClassNotFoundException {
        Login.login("root","a123456789","3306");
        OrderUtil.insertContact(1,1,"LiangJiajun","13380874078","哈尔滨工业大学(深圳)荔园6栋804B");
    }

    @Test
    public void testView() throws Exception {
        Login.login("root","a123456789","3306");
        StoreFoodViewDao storeFoodViewDao=new StoreFoodViewDaoImpl();
        List<StoreFoodView> storeFoodViewList= storeFoodViewDao.queryById(new StoreUser(1,"",
                0,"",""));
        JSONArray jsonArray=new JSONArray(storeFoodViewList);
        System.out.println(jsonArray.toString());
    }
    @Test
    public void testUI() throws InterruptedException, IOException {
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
        Object Lock=null;
        Thread.sleep((long)Math.pow(2,30));
    }
    @Test
    public void testUserUIPanel() throws Exception {
        Login.login("root","a123456789","3306");
        ImageUtils.initImage();
        JFrame jFrame=new JFrame();
        jFrame.setLocationRelativeTo(null);
//        jFrame.setResizable(false);

        jFrame.setSize(1000,600);
        CardLayout cardLayout=new CardLayout(10,10);
        JPanel panel=new JPanel(cardLayout);

        /**使用Panel**/
        CanteenPanel canteenPanel=new CanteenPanel(cardLayout,panel);
        panel.add(canteenPanel,"canteen");
        MyOrderPanel myOrderPanel=new MyOrderPanel(cardLayout,panel);
        panel.add(myOrderPanel,"myOrder");
        MyInfoPanel myInfoPanel=new MyInfoPanel(cardLayout,panel);
        panel.add(myInfoPanel,"myInfo");
        /**使用Panel**/
        cardLayout.show(panel,"myInfo");
        jFrame.setContentPane(panel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread.sleep(100000);
    }

    @Test
    public  void testStore() throws Exception {
        Login.login("root","a123456789","3306");
        ImageUtils.initImage();
        JFrame jFrame=new JFrame();
        jFrame.setLocationRelativeTo(null);
//        jFrame.setResizable(false);

        jFrame.setSize(1000,600);
        CardLayout cardLayout=new CardLayout(10,10);
        JPanel panel=new JPanel(cardLayout);

        /**使用Panel**/
        CanteenPanel canteenPanel=new CanteenPanel(cardLayout,panel);
        panel.add(canteenPanel,"canteen");
        MyOrderPanel myOrderPanel=new MyOrderPanel(cardLayout,panel);
        panel.add(myOrderPanel,"myOrder");
        MyInfoPanel myInfoPanel=new MyInfoPanel(cardLayout,panel);
        panel.add(myInfoPanel,"myInfo");
        StoreOrderPanel storeOrderPanel=new StoreOrderPanel(cardLayout,panel);
        panel.add(storeOrderPanel,"storeOrderPanel");
        /**使用Panel**/
        cardLayout.show(panel,"storeOrderPanel");
        jFrame.setContentPane(panel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread.sleep(100000);
    }
}
