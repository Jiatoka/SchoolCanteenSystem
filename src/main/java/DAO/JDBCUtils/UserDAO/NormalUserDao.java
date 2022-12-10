package DAO.JDBCUtils.UserDAO;

import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;

import java.sql.SQLException;
import java.util.List;

public interface NormalUserDao {
    /**插入**/
    int insert(NormalUser normalUser) throws SQLException, ClassNotFoundException;

    /**指定主键的插入**/
    int insert(NormalUser normalUser,int id) throws SQLException, ClassNotFoundException;

    /**删除**/
    int delete(NormalUser normalUser) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(NormalUser normalUser) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<NormalUser> queryByPK(NormalUser normalUser) throws Exception;

    /**根据索引查询数据**/
    List<NormalUser> queryByIndex(NormalUser normalUser) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<NormalUser> queryAll() throws Exception;

}
