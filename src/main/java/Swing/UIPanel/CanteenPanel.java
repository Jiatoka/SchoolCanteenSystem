package Swing.UIPanel;

import Controller.UserPageInfo;
import DAO.JDBCUtils.Canteen.CanteenDao;
import DAO.JDBCUtils.Canteen.CanteenDaoImpl;
import ObjectInstance.Canteen.Canteen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * 选择食堂页面
 * **/
public class CanteenPanel extends JPanel {
    /**滚动面板**/
    private JScrollPane jScrollPane;
    /**内容面板**/
    private JPanel panel;
    private int[][] color=new int[][]{{238, 203, 173},{174, 238, 238},
            {173, 255, 47}};
    int colorSize=3;
    public CanteenPanel(CardLayout cardLayout,JPanel jPanel) throws Exception {

        //查询食堂信息
        CanteenDao canteenDao=new CanteenDaoImpl();
        List<Canteen> canteenList=canteenDao.queryAll();
        //新建button
        ArrayList<JButton> jButtons=new ArrayList<JButton>();
        int colorIndex=0;
        for(Canteen canteen:canteenList){
            colorIndex=canteen.getId()%colorSize;
            JButton jButton=new JButton(canteen.getName());
            jButton.setBackground(new Color(color[colorIndex][0],
                    color[colorIndex][1],color[colorIndex][2]));
            jButton.setFont(new Font(null, Font.BOLD,50));
            //添加监听器
            addListener(jButton,canteen,cardLayout,jPanel);
            //添加按钮
            jButtons.add(jButton);
        }
        int size=jButtons.size();
        /**********用于修改选项框布局的核心代码*************/
        //设置布局
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        //将按钮加入面板
        for (JButton jButton:jButtons){
            jButton.setPreferredSize(new Dimension(800,100));
            panel.add(jButton);
        }
        int nums = panel.getComponentCount();  //组件数量
        panel.setPreferredSize(new Dimension(800,nums*110+10));
        /********************************************/
        //将面板加入滚动面板
        jScrollPane=new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //将滚动面板加入面板中
        this.setLayout(new GridLayout());
        this.add(jScrollPane);
    }

    /**添加监听器**/
    private void addListener(JButton jButton, final Canteen canteen, final CardLayout cardLayout, final JPanel jPanel){
        // 添加按钮的点击事件监听器
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取到的事件源就是按钮本身
                System.out.println("按钮被点击");
                UserPageInfo.setCanteen(canteen);
                try {
                    StorePanel storePanel=new StorePanel(cardLayout,jPanel);
                    jPanel.add(storePanel,"store");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                //根据给定的食堂进入商家页面
                cardLayout.show(jPanel,"store");
            }
        });
    }
}
