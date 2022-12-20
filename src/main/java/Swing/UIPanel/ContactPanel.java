package Swing.UIPanel;

import Controller.UserPageInfo;
import Model.LoginUtil;
import Model.OrderUtil;
import ObjectInstance.Order.Contact;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**填写联系方式面板,最终下单**/
public class ContactPanel extends JPanel {
    private JPanel contentPanel;
    private JPanel btnPanel;
    private JPanel username;
    private JPanel phone;
    private JPanel address;
    private JPanel textAreaPanel;
    private JTextArea textArea;
    private JLabel tipsLabel;
    private JButton jButton;
    /**用于记录文本框
     * 0:username 1:phone 2:address
     * **/
    private ArrayList<JTextField> textFieldArrayList;
    public ContactPanel(final CardLayout cardLayout, final JPanel jPanel){
        textFieldArrayList=new ArrayList<JTextField>();
        //新建一个收件人输入框
        username=createContenPanel("收件人");
        //联系电话输入框
        phone=createContenPanel("手机号");
        //地址输入框
        address=createContenPanel("用户地址");
        //填写联系的面板
        contentPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        contentPanel.setPreferredSize(new Dimension(800,200));
        contentPanel.add(username);
        contentPanel.add(phone);
        contentPanel.add(address);

        //备注
        tipsLabel=new JLabel("备注:");
        tipsLabel.setFont(new Font(null,Font.PLAIN,20));
        textArea=new JTextArea();
        textArea.setFont(new Font(null,Font.PLAIN,20));
        textArea.setLineWrap(true);
        textAreaPanel=new JPanel(null);
        textAreaPanel.setPreferredSize(new Dimension(800,200));
        textAreaPanel.add(tipsLabel);
        textAreaPanel.add(textArea);
        tipsLabel.setBounds(10,10,150,30);
        textArea.setBounds(170,10,300,150);

        //下单按钮
        jButton=new JButton("下单");
        jButton.setFont(new Font(null,Font.BOLD,25));
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击下单按钮后立刻下单并更新到数据库
                String nameStr=textFieldArrayList.get(0).getText().trim();
                String phoneStr=textFieldArrayList.get(1).getText().trim();
                String addressStr=textFieldArrayList.get(2).getText().trim();
                if(nameStr.length()==0||phoneStr.length()==0||addressStr.length()==0){
                    JOptionPane.showMessageDialog(
                            jPanel,
                            "请填写完整的联系方式",
                            "错误",
                            JOptionPane.WARNING_MESSAGE
                    );
                }else{
                    //联系方式填写完毕更新订单
                    StoreUser storeUser= UserPageInfo.getStoreUser();
                    int userId= LoginUtil.getId();
                    //插入订单
                    int orderId;
                    try {
                        orderId=OrderUtil.placeOrder(storeUser.getId(),userId,
                                UserPageInfo.getSum(),textArea.getText(),UserPageInfo.getFoodList());
                        System.out.println("插入订单");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    //插入联系方式
                    try {
                        OrderUtil.insertContact(orderId,userId,nameStr,
                                phoneStr,addressStr);
                        System.out.println("插入联系方式");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    //提示购买成功
                    JOptionPane.showMessageDialog(
                            jPanel,
                            "下单成功,请等待商家接单",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    //删除所有面板并返回首页
                    System.out.println("下单成功,返回首页");
                    jPanel.removeAll();
                    CanteenPanel canteenPanel= null;
                    try {
                        canteenPanel = new CanteenPanel(cardLayout,jPanel);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    jPanel.add(canteenPanel,"canteen");
                    cardLayout.show(jPanel,"canteen");
                }
            }
        });

        //填写按钮面板
        btnPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        btnPanel.add(jButton);
        btnPanel.setPreferredSize(new Dimension(800,50));

        //填写面板
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        this.add(contentPanel);
        this.add(textAreaPanel);
        this.add(btnPanel);
    }
    //新建一个联系地址的输入panel
    private JPanel createContenPanel(String title){
        //新建一个Panel
        JPanel jPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        jPanel.setPreferredSize(new Dimension(800,50));
        //新建一个label
        JLabel titleLabel=new JLabel(title);
        titleLabel.setFont(new Font(null,Font.PLAIN,20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setPreferredSize(new Dimension(150,30));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        //新建一个输入框
        JTextField input=new JTextField();
        input.setPreferredSize(new Dimension(550,30));
        input.setFont(new Font(null,Font.PLAIN,20));
        input.setHorizontalAlignment(SwingConstants.LEFT);

        jPanel.add(titleLabel);
        jPanel.add(input);
        textFieldArrayList.add(input);
        return jPanel;
    }

}
