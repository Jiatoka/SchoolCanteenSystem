package DAO.JDBCUtils.Order;

import ObjectInstance.Food.Food;
import ObjectInstance.Order.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    /**插入一条订单数据**/
    int insert(Order order) throws SQLException, ClassNotFoundException;

    /**删除**/
    int delete(Order order) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(Order order) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<Order> queryByPK(Order order) throws Exception;

    /**根据索引查询数据
     * @param  identity 0:普通用户,1:商家
     * **/
    List<Order> queryByIndex(Order order,int identity) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<Order> queryAll() throws Exception;
}
