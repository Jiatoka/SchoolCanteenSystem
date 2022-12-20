package Swing.UIPanel;

import Controller.UserPageInfo;
import DAO.JDBCUtils.View.StoreFoodViewDao;
import DAO.JDBCUtils.View.StoreFoodViewDaoImpl;
import ObjectInstance.Food.Food;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**订单页面*/
public class OrderPanel extends JPanel {
    private JScrollPane jScrollPane;
    private JTable jTable;
    public OrderPanel(final CardLayout cardLayout, final JPanel jPanel){

        //jTable内容居中
        DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        //获取订单信息
        List<HashMap<Food,Integer>> foodList=UserPageInfo.getFoodList();
        //填写表格
        String[] header=new String[]{
                "名字",
                "数量",
                "价格"
        };
        int size=foodList.size();
        Object[][] rowData=new Object[size+1][3];
        int index=0;
        for(HashMap<Food,Integer> hashMap:foodList){
            for(Food food:hashMap.keySet()){
                rowData[index][0]=food.getFoodName();
                rowData[index][1]=hashMap.get(food);
                rowData[index][2]=food.getPrice()*hashMap.get(food);
                index+=1;
            }
        }
        //加入总价
        int sum=UserPageInfo.getSum();
        rowData[size][0]="总价";
        rowData[size][1]="";
        rowData[size][2]=sum;
        jTable=new JTable(rowData,header);
        jTable.setForeground(Color.BLACK);                   // 字体颜色
        jTable.setFont(new Font(null, Font.PLAIN, 20));      // 字体样式
        jTable.setShowGrid(false);
        jTable.setEnabled(false);
        jTable.setDefaultRenderer(Object.class,defaultTableCellRenderer); //文字居中

        jTable.getTableHeader().setFont(new Font(null, Font.BOLD, 20));  // 设置表头名称字体样式
        jTable.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        jTable.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        jTable.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列
//        jTable.getTableHeader().setDefaultRenderer(defaultTableCellRenderer); //文字居中


        jTable.setRowHeight(30);
        jTable.setPreferredScrollableViewportSize(new Dimension(800,350));

        //订单多时使用滚动面板
        jScrollPane=new JScrollPane(jTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //当订单太少时为了美观就不用下拉菜单了
//        JPanel panelLess=new JPanel(new BorderLayout());
//        panelLess.setPreferredSize(new Dimension(800,400));
//        panelLess.add(jTable.getTableHeader(),BorderLayout.NORTH);
//        panelLess.add(jTable,BorderLayout.CENTER);

        //将滚动面板加入面板中
        this.setLayout(new BorderLayout(10,10));
        this.add(jScrollPane,BorderLayout.CENTER);

        //加入标题
        JLabel title=new JLabel("订单信息");
        title.setFont(new Font(null,Font.BOLD,30));
        title.setSize(800,50);
        this.add(title,BorderLayout.NORTH);

        //加入按钮
        JPanel btnPanel=new JPanel();
        JButton confirmBtn=new JButton("确定");
        confirmBtn.setFont(new Font(null,Font.BOLD,35));
        JButton retBtn=new JButton("返回");
        retBtn.setFont(new Font(null,Font.BOLD,35));
        // 添加按钮的点击事件监听器
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("进入填写联系方式页面");
                ContactPanel contactPanel=new ContactPanel(cardLayout,jPanel);
                jPanel.add(contactPanel,"contact");
                cardLayout.show(jPanel,"contact");
            }
        });
        // 添加按钮的点击事件监听器
        retBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 返回到点菜页面,点菜页面的对象是第一次点菜的哪个实例对象
                System.out.println("返回菜品页面");
                cardLayout.show(jPanel,"food");
            }
        });
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        btnPanel.add(retBtn);
        btnPanel.add(confirmBtn);
        this.add(btnPanel,BorderLayout.SOUTH);
    }

}
