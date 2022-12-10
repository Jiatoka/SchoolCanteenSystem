package DAO.JDBCUtils.UserDAO;

import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;

import java.sql.SQLException;
import java.util.List;

public interface StoreUserDao {
    /**插入**/
    int insert(StoreUser storeUser) throws SQLException, ClassNotFoundException;

    /**指定主键的插入**/
    int insert(StoreUser storeUser,int id) throws SQLException, ClassNotFoundException;
    /**删除**/
    int delete(StoreUser storeUser) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(StoreUser storeUser) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<StoreUser> queryByPK(StoreUser storeUser) throws Exception;

    /**根据索引查询数据**/
    List<StoreUser> queryByIndex(StoreUser storeUser) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<StoreUser> queryAll() throws Exception;
}
