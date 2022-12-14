package Swing;

import javax.swing.*;
import java.awt.*;

public class GUITEST extends JFrame {

/*Date: 2017年1月21日  Time: 下午4:59:40
@firstmiki ---blog.ppt1234.com*/

        //1. 定义组件
        JButton jButton, jButton2,jButton3,jButton4,jButton5;

        public GUITEST() {
            JPanel panel=new JPanel(new GridLayout(1,2));
            JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel panel2=new JPanel(new FlowLayout(FlowLayout.LEFT));

            //2. 创建组件
            jButton = new JButton("中间");
            jButton2 = new JButton("北间");
            jButton3 = new JButton("西间");
            jButton4 = new JButton("东间");
            jButton5 = new JButton("南间");

            //3. 添加各个组件
            panel1.add(jButton);
            panel2.add(jButton2);

//            jButton.setSize(80,80);
//            jButton2.setSize(80,80);

            panel.add(panel1);
            panel.add(panel2);

            this.add(panel);
//            this.add(jButton3, BorderLayout.WEST);   //布局的西边
//            this.add(jButton4, BorderLayout.EAST);   //布局的东边
//            this.add(jButton5, BorderLayout.SOUTH);  //布局的南边

            //4. 设置窗体属性
            this.setTitle("演示边界布局管理器");
            this.setSize(500, 500);
            this.setLocation(200, 200);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        public static void main(String[] args) {
            GUITEST testBorderLayout = new GUITEST();

        }

}
