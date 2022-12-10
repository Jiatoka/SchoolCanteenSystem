package DAO.JDBCUtils.Food;

import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.UserDAO.AdministratorDaoimpl;
import ObjectInstance.Food.Food;
import ObjectInstance.User.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**访问food表**/
public class FoodDaoImpl implements FoodDao {
    private static int count=0;
    public static int maxId=0;
    public static int getCount() {
        return count;
    }
    public static int getMaxId(){
        return maxId;
    }

    /**初始化计算表的行号**/
    static {
        try{
            Connection conn= MysqlConnector.createConnection();
            String sql="select count(*) from food";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(food_id) from food");
            if(rs.next()){
                maxId= rs.getInt(1);
            }
            MysqlConnector.closeConnection(conn,ps,rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**新增菜品,需要指定菜品的类型,1:饮料,2:甜品,3:主食**/
    public int insert(Food food,int kind) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        PreparedStatement ps= null;

        //先插入food表
        String sql="insert into food values(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FoodDaoImpl.count+=1;
        FoodDaoImpl.maxId+=1;
        ps.setInt(1,FoodDaoImpl.maxId );
        ps.setInt(2, food.getStoreId());
        ps.setString(3,food.getFoodName());
        ps.setString(4, food.getFoodDescription());
        ps.setInt(5,food.getPrice());
        int res=ps.executeUpdate();

        //再插入food_foodkind_relation
        String sql1="insert into food_foodkind_relation values (?,?)";
        try {
            ps = conn.prepareStatement(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, kind);
        ps.setInt(2,FoodDaoImpl.maxId);
        ps.executeUpdate();

        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int insert(Food food,int id,int kind) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        PreparedStatement ps= null;
        //先插入food表
        String sql="insert into food values(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FoodDaoImpl.count+=1;
        ps.setInt(1,id );
        ps.setInt(2, food.getStoreId());
        ps.setString(3,food.getFoodName());
        ps.setString(4, food.getFoodDescription());
        ps.setInt(5,food.getPrice());
        int res=ps.executeUpdate();
        //再插入food_foodkind_relation
        String sql1="insert into food_foodkind_relation values (?,?)";
        try {
            ps = conn.prepareStatement(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, kind);
        ps.setInt(2,id);
        ps.executeUpdate();

        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(Food food) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        PreparedStatement ps= null;

        //先删除food_foodKind_relation表
        String sql1="delete from food_foodkind_relation where food_id=?";
        try {
            ps = conn.prepareStatement(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, food.getFoodId());
        ps.executeUpdate();

        //再删除food表
        String sql="delete from food where food_id = ?";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, food.getFoodId());
        FoodDaoImpl.count-=1;
        int res=ps.executeUpdate();

        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(Food food) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update food set food_name=?,food_description=?,price=? where food_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, food.getFoodName());
        ps.setString(2, food.getFoodDescription());
        ps.setInt(3, food.getPrice());
        ps.setInt(4,food.getFoodId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<Food> queryByPK(Food food) throws Exception {
        ArrayList<Food> resList=new ArrayList<Food>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from food where food_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, food.getFoodId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Food(rs.getInt(1),
                    rs.getInt(2),rs.getString(3),
                    rs.getString(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Food> queryByIndex(Food food) throws SQLException, ClassNotFoundException {
        ArrayList<Food> resList=new ArrayList<Food>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from food where =food_name?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, food.getFoodName());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Food(rs.getInt(1),
                    rs.getInt(2),rs.getString(3),
                    rs.getString(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Food> queryAll() throws Exception {
        ArrayList<Food> resList=new ArrayList<Food>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from food";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Food(rs.getInt(1),rs.getInt(2),
                    rs.getString(3),rs.getString(4),
                    rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
