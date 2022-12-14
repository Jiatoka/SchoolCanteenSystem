package Swing;

import javax.swing.*;
import java.awt.*;

public class UserUI {
    private JPanel UserPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserUI");
        frame.setContentPane(new UserUI().UserPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton mainPageBtn;
    private JButton myInfoBtn;
    private JButton orderInfoBtn;

    /**食堂选择页面,商家选择页面,菜单页面的卡片**/
    private JPanel canteenPanel;
    private JPanel storePanel;
    private JPanel foodPanel;
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //创建卡片布局
        CardLayout cardLayout=new CardLayout(10,10);

        //添加布局
        contentPanel=new JPanel(cardLayout);

    }


}
