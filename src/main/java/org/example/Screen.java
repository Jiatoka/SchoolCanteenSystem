package org.example;

import javax.swing.*;
import java.awt.*;

public class Screen {
    public static double screenWidth;
    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth=screenSize.getWidth();
    }
    public static void centerFrame(JFrame jFrame,int width,int height){
        jFrame.setBounds(((int) screenWidth - width) / 2, 0,width,height);
    }
}
