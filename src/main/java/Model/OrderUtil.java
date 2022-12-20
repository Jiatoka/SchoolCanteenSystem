package Model;

import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.Order.*;
import ObjectInstance.Food.Food;
import ObjectInstance.Order.Contact;
import ObjectInstance.Order.Order;
import ObjectInstance.Order.OrderInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**下单和接单工具**/
public class OrderUtil {
    /**
     * 下单,将订单信息插入到order和order_info表
     * @param storeId  商家号
     * @param foodList 预定的食物
     * @param userId   用户号
     * @param price    价钱
     * @return orderId 返回订单号
     * **/
    public static int placeOrder(int storeId, int userId, int price,String tips, List<HashMap<Food,Integer>> foodList) throws SQLException, ClassNotFoundException {
        //生成订单记录
        Order order=new Order(1,storeId,userId,new Date(),price,tips,0);
        OrderDao orderDao=new OrderDaoImpl();
        int orderId=orderDao.insert(order);

        //生成订单详细记录
        OrderInfoDao orderInfoDao=new OrderInfoDaoImpl();
        for(HashMap<Food,Integer> hashMap:foodList){
            for (Food food:hashMap.keySet()){
                int num=hashMap.get(food);
                OrderInfo orderInfo=new OrderInfo(1,orderId,
                        food.getFoodId(),num,num*food.getPrice());
                orderInfoDao.insert(orderInfo);
            }
        }
        return orderId;
    }

    /**
     * 插入顾客的联系方式,外卖地址
     * @param address
     * @param orderId
     * @param phone
     * @param userId
     * @param userName
     **/

    public static void insertContact(int orderId,int userId,String userName,String phone,String address) throws SQLException, ClassNotFoundException {
        Contact contact=new Contact(orderId,userId,userName,phone,address);
        ContactDao contactDao=new ContactDaoImpl();
        contactDao.insert(contact);
    }

    /**商家接单
     * @param orderId 订单号
     * **/
    public static  boolean receiveOrder(int orderId) throws SQLException, ClassNotFoundException {
        OrderDao orderDao=new OrderDaoImpl();
        Order order=new Order(orderId,0,0,new Date(),0,"",1);
        orderDao.update(order);
        return true;
    }

    /**
     * 查询自己的订单
     * @param identity 0:普通用户 1:商家
     * @param id 用户的id
     * @return 用户所有订单
     * **/
    public static List<Order> queryOrder(int identity,int id) throws SQLException, ClassNotFoundException {
        OrderDao orderDao=new OrderDaoImpl();
        Order order=new Order(1,id,id,new Date(),0,"",0);
        List<Order> orderList=orderDao.queryByIndex(order,identity);
        return orderList;
    }

    /**
     * 查询订单的详细记录
     * @param orderId 订单号
     * **/
    public static List<HashMap<Food,Integer>> queryOrderFood(int orderId) throws Exception {
        List<HashMap<Food,Integer>> foodList=new ArrayList<HashMap<Food, Integer>>();
        //寻找订单详细记录
        OrderInfoDao orderInfoDao=new OrderInfoDaoImpl();
        OrderInfo orderInfo=new OrderInfo(0,orderId,0,0,0);
        List<OrderInfo> orderInfoList=orderInfoDao.queryByIndex(orderInfo);

        //寻找对应的菜品
        FoodDao foodDao=new FoodDaoImpl();
        for (OrderInfo obj:orderInfoList){
            HashMap<Food,Integer> hashMap = new HashMap<Food, Integer>();
            Food food=foodDao.queryByPK(new Food(obj.getFoodId(),
                    1,"","",0)).get(0);
            hashMap.put(food,obj.getOrderInfoNumber());
            foodList.add(hashMap);
        }

        return foodList;
    }

    /**
     * 查询订单的联系方式
     * @param orderId 订单编号
     * @return contact 返回顾客的联系方式
     * **/
    public static Contact queryContact(int orderId) throws Exception {
        ContactDao contactDao=new ContactDaoImpl();
        Contact contact=new Contact(orderId, 0,"","","");
        List<Contact> contactList=contactDao.queryByPK(contact);
        if(contactList.size()==0){
            throw new RuntimeException("不存在指定编号的订单,请检查联系方式");
        }
        return  contactList.get(0);
    }

}
