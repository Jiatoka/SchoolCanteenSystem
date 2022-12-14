package DAO.JDBCUtils.View;

import ObjectInstance.Order.Order;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;

import java.sql.SQLException;
import java.util.List;

public interface StoreFoodViewDao {
    /**根据商家的id查询商家的菜单**/
    List<StoreFoodView> queryById(StoreUser storeUser) throws Exception;

    /**根据商家的名字查询商家的菜单
     * **/
    List<StoreFoodView> queryByName(StoreUser storeUser) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<StoreFoodView> queryAll() throws Exception;
}
