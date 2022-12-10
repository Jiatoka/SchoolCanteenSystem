package DAO.JDBCUtils.UserDAO;

import ObjectInstance.User.Administrator;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;

import java.sql.SQLException;
import java.util.List;

public interface AdministratorDao {
    /**插入**/
    int insert(Administrator administrator) throws SQLException, ClassNotFoundException;

    /**指定主键的插入**/
    int insert(Administrator administrator,int id) throws SQLException, ClassNotFoundException;
    /**删除**/
    int delete(Administrator administrator) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(Administrator administrator) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<Administrator> queryByPK(Administrator administrator) throws Exception;

    /**根据索引查询数据**/
    List<Administrator> queryByIndex(Administrator administrator) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<Administrator> queryAll() throws Exception;

}
