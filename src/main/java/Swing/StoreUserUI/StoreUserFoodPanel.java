package Swing.StoreUserUI;

import Controller.UserPageInfo;
import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.View.StoreFoodViewDao;
import DAO.JDBCUtils.View.StoreFoodViewDaoImpl;
import Model.LoginUtil;
import ObjectInstance.Food.Food;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;
import Swing.RoundBorder;
import Swing.UIPanel.OrderPanel;
import Image.ImageUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**商家的首页,用于显示菜品信息**/
public class StoreUserFoodPanel extends JPanel {
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
    private int sum;
    /**下单**/
    private JButton addBtn;
    /**下单栏**/
    private JPanel addPanel;
    public StoreUserFoodPanel(CardLayout cardLayout, JPanel jPanel) throws Exception {
        addBtn=new JButton("新增");
        addBtn.setFont(new Font(null,Font.BOLD,30));
        foodMapTmp=new HashMap<Integer, HashMap<String,Integer>>();
        foodList=new ArrayList<HashMap<Food, Integer>>();

        //查询商家菜单信息信息
        StoreFoodViewDao storeFoodViewDao=new StoreFoodViewDaoImpl();

        //记录用户信息
        int storeId= LoginUtil.getId();
        java.util.List<StoreFoodView> storeFoodViewList=storeFoodViewDao.queryById(
                new StoreUser(storeId,"0",0,"0","0")
        );

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

        //生成新增按钮
        addBtn.setPreferredSize(new Dimension(120,60));
        addBtn.setBackground(Color.GREEN);
        addBtn.setForeground(Color.GRAY);
        addPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        addPanel.setPreferredSize(new Dimension(850,100));
        addPanel.add(addBtn);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    //提示购买成功
                    JOptionPane.showMessageDialog(
                            jPanel,
                            "新增功能未开放",
                            "错误",
                            JOptionPane.WARNING_MESSAGE
                    );
            }
        });
        //将滚动面板加入面板中
        this.setLayout(new BorderLayout(10,10));
        jScrollPane.setPreferredSize(new Dimension(1000,350));
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(addPanel,BorderLayout.SOUTH);
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
        if(ImageUtils.foodImage.containsKey(storeFoodView.getFoodId())){
            jLabel.setIcon(new ImageIcon(ImageUtils.foodImage.get(storeFoodView.getFoodId())));
        }else{
            jLabel.setIcon(new ImageIcon(ImageUtils.defaultImage));
        }
        jLabel.setOpaque(false);
        jLabel.setPreferredSize(new Dimension(150,100));
        jLabel.setFont(new Font(null,Font.PLAIN,25));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
        //生成按钮决定是否下架菜品
        JButton removeBtn=new JButton("下架");
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setBackground(Color.RED);
        removeBtn.setFont(new Font(null,Font.BOLD,30));
        removeBtn.setPreferredSize(new Dimension(100,50));
        JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        btnPanel.setPreferredSize(new Dimension(250,60));
        btnPanel.add(removeBtn);
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //提示购买成功
                JOptionPane.showMessageDialog(
                        jScrollPane,
                        "下架功能未开放",
                        "错误",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        //生成按钮决定是否修改
        JButton updateBtn=new JButton("更新");
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(Color.ORANGE);
        updateBtn.setFont(new Font(null,Font.BOLD,30));
        updateBtn.setPreferredSize(new Dimension(100,50));
        btnPanel.add(updateBtn);
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //提示购买成功
                JOptionPane.showMessageDialog(
                        jScrollPane,
                        "更新功能未开放",
                        "错误",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        //生成一个菜单栏
        jPanel.add(jLabel);
        jPanel.add(foodName);
        jPanel.add(price);
        jPanel.add(btnPanel);
        jPanel.setBorder(new RoundBorder(Color.BLUE));
        return jPanel;
    }

}
