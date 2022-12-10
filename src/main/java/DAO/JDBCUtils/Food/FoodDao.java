package DAO.JDBCUtils.Food;

import ObjectInstance.Food.Food;
import ObjectInstance.User.Administrator;

import java.sql.SQLException;
import java.util.List;

public interface FoodDao {
    int insert(Food food,int kind) throws SQLException, ClassNotFoundException;

    /**指定主键的插入**/
    int insert(Food food,int id,int kind) throws SQLException, ClassNotFoundException;
    /**删除**/
    int delete(Food food) throws SQLException, ClassNotFoundException;

    /**更新**/
    int update(Food food) throws SQLException, ClassNotFoundException;

    /**根据主键查询数据**/
    List<Food> queryByPK(Food food) throws Exception;

    /**根据索引(food_name)查询数据**/
    List<Food> queryByIndex(Food food) throws SQLException, ClassNotFoundException;

    /**查询所有数据**/
    List<Food> queryAll() throws Exception;
}
