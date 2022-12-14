package DAO.JDBCUtils.Order;

import ObjectInstance.Order.Contact;
import ObjectInstance.Order.Order;

import java.sql.SQLException;
import java.util.List;
/**修改联系表,记录顾客的联系信息**/
public interface ContactDao {
    /**插入一条订单数据**/
    int insert(Contact contact) throws SQLException, ClassNotFoundException;

    /**删除**/
    int delete(Contact contact) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(Contact contact) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<Contact> queryByPK(Contact contact) throws Exception;

    /**查询所有数据**/
    List<Contact> queryAll() throws Exception;
}
