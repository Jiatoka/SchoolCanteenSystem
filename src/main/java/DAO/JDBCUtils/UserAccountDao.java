package DAO.JDBCUtils;

import ObjectInstance.UserAccount;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Liang
 */
public interface UserAccountDao {
    /**插入**/
    int insert(UserAccount userAccount) throws SQLException, ClassNotFoundException;

    /**删除**/
    int delete(UserAccount userAccount) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(UserAccount userAccount) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<UserAccount> queryByPK(UserAccount userAccount) throws Exception;
    /**查询所有数据**/
    List<UserAccount> queryAll() throws Exception;

}
