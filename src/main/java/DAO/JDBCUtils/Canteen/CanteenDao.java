package DAO.JDBCUtils.Canteen;

import ObjectInstance.Canteen.Canteen;
import ObjectInstance.User.StoreUser;

import java.sql.SQLException;
import java.util.List;

public interface CanteenDao {
    int insert(Canteen canteen) throws SQLException, ClassNotFoundException;

    /**指定主键的插入**/
    int insert(Canteen canteen,int id) throws SQLException, ClassNotFoundException;
    /**删除**/
    int delete(Canteen canteen) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(Canteen canteen) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<Canteen> queryByPK(Canteen canteen) throws Exception;

    /**查询所有数据**/
    List<Canteen> queryAll() throws Exception;

}
