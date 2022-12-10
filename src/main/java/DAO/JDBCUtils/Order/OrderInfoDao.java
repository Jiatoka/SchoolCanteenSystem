package DAO.JDBCUtils.Order;
import ObjectInstance.Order.OrderInfo;
import java.sql.SQLException;
import java.util.List;
/**订单详细信息**/
public interface OrderInfoDao {
    int insert(OrderInfo orderInfo) throws SQLException, ClassNotFoundException;

    /**删除**/
    int delete(OrderInfo orderInfo) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(OrderInfo orderInfo) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<OrderInfo> queryByPK(OrderInfo orderInfo) throws Exception;

    /**根据索引(order_id)查询数据**/
    List<OrderInfo> queryByIndex(OrderInfo orderInfo) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<OrderInfo> queryAll() throws Exception;
}
