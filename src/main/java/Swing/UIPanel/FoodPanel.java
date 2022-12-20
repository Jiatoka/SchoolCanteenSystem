package Swing.UIPanel;

import Controller.UserPageInfo;
import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.UserDAO.StoreUserDao;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import DAO.JDBCUtils.View.StoreFoodViewDao;
import DAO.JDBCUtils.View.StoreFoodViewDaoImpl;
import Model.LoginUtil;
import ObjectInstance.Canteen.Canteen;
import ObjectInstance.Food.Food;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;
import Swing.RoundBorder;
import org.json.JSONArray;
import Image.ImageUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class FoodPanel extends JPanel {
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
    private HashMap<Integer,HashMap<String,Integer>> foodMapTmp;
    /**记录每种菜品的数量**/
    private List<HashMap<Food,Integer>> foodList;
    /**总价**/
    private JLabel priceSum;
    private int sum;
    /**下单**/
    private JButton orderBtn;
    /**下单栏**/
    private JPanel orderPanel;
    public FoodPanel(CardLayout cardLayout, JPanel jPanel) throws Exception {
        sum=0;
        priceSum=new JLabel("总价:0");
        priceSum.setFont(new Font(null,Font.PLAIN,30));
//        priceSum.setPreferredSize(new Dimension(200,100));
        orderBtn=new JButton("下单");
        orderBtn.setFont(new Font(null,Font.PLAIN,30));
//        orderBtn.setPreferredSize(new Dimension(150,100));
        foodMapTmp=new HashMap<Integer, HashMap<String,Integer>>();
        foodList=new ArrayList<HashMap<Food, Integer>>();

        //查询商家菜单信息信息
        StoreFoodViewDao storeFoodViewDao=new StoreFoodViewDaoImpl();

        //记录商家
        StoreUser storeUser= UserPageInfo.getStoreUser();
        int storeId=storeUser.getId();
        java.util.List<StoreFoodView> storeFoodViewList=storeFoodViewDao.queryById(storeUser);

        //生成菜单选项栏
        ArrayList<JPanel> jPanelArrayList=new ArrayList<JPanel>();
        for(StoreFoodView storeFoodView:storeFoodViewList){
            JPanel foodInfo=createFoodBar(storeFoodView);
            jPanelArrayList.add(foodInfo);
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

        //生成付款菜单栏
        orderPanel=createOrderPanel(jPanel,cardLayout);

        //将滚动面板加入面板中
        this.setLayout(new BorderLayout(10,10));
        jScrollPane.setPreferredSize(new Dimension(1000,350));
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(orderPanel,BorderLayout.SOUTH);
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

    /**新建一条菜单信息,用于点菜**/
    private  JPanel createFoodBar(StoreFoodView storeFoodView){
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        jPanel.setPreferredSize(new Dimension(850,120));

        //生成菜品
        final Food food=new Food(storeFoodView.getFoodId(),storeFoodView.getStoreId(),
                storeFoodView.getFoodName(), storeFoodView.getFoodDescription(),
                storeFoodView.getPrice());

        //生成餐饮图片
        JLabel jLabel=new JLabel();
        jLabel.setPreferredSize(new Dimension(150,100));
        jLabel.setFont(new Font(null,Font.PLAIN,25));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setOpaque(false);
        if(ImageUtils.foodImage.containsKey(storeFoodView.getFoodId())){
            jLabel.setIcon(new ImageIcon(ImageUtils.foodImage.get(storeFoodView.getFoodId())));
        }else{
            jLabel.setIcon(new ImageIcon(ImageUtils.defaultImage));
        }

        //生成菜品名字
        JLabel foodName=new JLabel(food.getFoodName());
        foodName.setFont(new Font(null,Font.PLAIN,25));
        foodName.setPreferredSize(new Dimension(200,50));
        foodName.setHorizontalAlignment(SwingConstants.CENTER);
        //生成价格
        JLabel price=new JLabel("￥"+food.getPrice());
        price.setFont(new Font(null,Font.PLAIN,25));
        price.setPreferredSize(new Dimension(80,50));
        price.setHorizontalAlignment(SwingConstants.CENTER);

        //生成减号
        JButton subBtn=new JButton(new ImageIcon(ImageUtils.getSubImage()));
        subBtn.setBorderPainted(false);
        subBtn.setContentAreaFilled(false);
        //生成加号
        JButton addBtn=new JButton(new ImageIcon(ImageUtils.getAddImage()));
        addBtn.setBorderPainted(false);
        addBtn.setContentAreaFilled(false);
        //生成数量文本框
        final JTextField textField=new JTextField(2);
        textField.setFont(new Font(null,Font.PLAIN,25));
        textField.setText("0");
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setPreferredSize(new Dimension(60,30));
        textField.setEnabled(false);
        final HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
        //生成监听器
        subBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("减1");
                //改变文本框内容
                int num=Integer.parseInt(textField.getText());
                if(num>0){
                    num-=1;
                    textField.setText(""+num);
                    hashMap.put("number",num);
                    hashMap.put("price",num*food.getPrice());
                    foodMapTmp.put(food.getFoodId(),hashMap);
                    sum-= food.getPrice();
                    priceSum.setText("总价:"+sum);
                }

            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("加1");
                //改变文本框内容
                int num=Integer.parseInt(textField.getText());
                if(num<99){
                    num+=1;
                    textField.setText(""+num);
                    hashMap.put("number",num);
                    hashMap.put("price",num*food.getPrice());
                    foodMapTmp.put(food.getFoodId(),hashMap);
                    sum+= food.getPrice();
                    priceSum.setText("总价:"+sum);
                }
            }
        });

        //生成一个摆放数量的面板
        JPanel numPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        numPanel.add(subBtn);
        numPanel.add(textField);
        numPanel.add(addBtn);
        //生成一个菜单栏
        jPanel.add(jLabel);
        jPanel.add(foodName);
        jPanel.add(price);
        jPanel.add(numPanel);
        jPanel.setBorder(new RoundBorder(Color.BLUE));
        return jPanel;
    }

    /**创建下单栏**/
    private JPanel createOrderPanel(final JPanel parentPanel, final CardLayout cardLayout){
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
        //生成监听器
        orderBtn.addActionListener(new ActionListener() {
            FoodDao foodDao=new FoodDaoImpl();
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("下单");
                foodList.clear();
                //生成菜单列表
                for(Integer id: foodMapTmp.keySet()){
                    if(foodMapTmp.get(id).get("number")!=0){
                        List<Food> foods=null;
                        //查询菜品
                        try {
                            foods=foodDao.queryByPK(new Food(id,0,"","",0));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        Food food=foods.get(0);
                        HashMap<Food,Integer> hashMap=new HashMap<Food, Integer>();
                        hashMap.put(food,foodMapTmp.get(id).get("number"));
                        foodList.add(hashMap);
                    }
                }

                //如果没点任何菜品则需要进行提醒
                if(foodList.size()==0){
                    JOptionPane.showMessageDialog(
                            panel,
                            "错误",
                            "菜品不能为空",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return ;
                }

                //提示是否确定要下单
                int result = JOptionPane.showConfirmDialog(
                        panel,
                        "请确认下单",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                if(result==JOptionPane.YES_OPTION){
                    System.out.println("确认下单,即将进入下单页面...");
                    UserPageInfo.setFoodList(foodList);
                    UserPageInfo.setSum(sum);
                    OrderPanel orderPanel1=new OrderPanel(cardLayout,parentPanel);
                    parentPanel.add(orderPanel1,"order");
                    cardLayout.show(parentPanel,"order");
                }
            }
        });
        //加入菜单栏
        jPanel.add(priceSum);
        jPanel.add(orderBtn);
        return jPanel;
    }
}
