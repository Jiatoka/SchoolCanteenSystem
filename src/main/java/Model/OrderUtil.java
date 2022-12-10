package Model;

import DAO.JDBCUtils.Food.FoodDao;
import DAO.JDBCUtils.Food.FoodDaoImpl;
import DAO.JDBCUtils.Order.OrderDao;
import DAO.JDBCUtils.Order.OrderDaoImpl;
import DAO.JDBCUtils.Order.OrderInfoDao;
import DAO.JDBCUtils.Order.OrderInfoDaoImpl;
import ObjectInstance.Food.Food;
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
     * **/
    public static boolean placeOrder(int storeId, int userId, int price,String tips, List<HashMap<Food,Integer>> foodList) throws SQLException, ClassNotFoundException {
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
        return true;
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
     * **/
    public static List<Order>queryOrder(int identity,int id) throws SQLException, ClassNotFoundException {
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

}
