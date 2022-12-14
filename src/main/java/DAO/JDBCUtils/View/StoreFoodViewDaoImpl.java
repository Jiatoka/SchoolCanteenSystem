package DAO.JDBCUtils.View;

import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.Order.OrderDaoImpl;
import ObjectInstance.Order.Order;
import ObjectInstance.User.StoreUser;
import ObjectInstance.View.StoreFoodView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreFoodViewDaoImpl implements StoreFoodViewDao{

    public List<StoreFoodView> queryById(StoreUser storeUser) throws Exception {
        ArrayList<StoreFoodView> resList=new ArrayList<StoreFoodView>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store_food_view where store_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, storeUser.getId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new StoreFoodView(rs.getInt(1),
                    rs.getString(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5),
                    rs.getInt(6)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<StoreFoodView> queryByName(StoreUser storeUser) throws SQLException, ClassNotFoundException {
        ArrayList<StoreFoodView> resList=new ArrayList<StoreFoodView>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store_food_view where store_name=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, storeUser.getName());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new StoreFoodView(rs.getInt(1),
                    rs.getString(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5),
                    rs.getInt(6)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<StoreFoodView> queryAll() throws Exception {
        ArrayList<StoreFoodView> resList=new ArrayList<StoreFoodView>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store_food_view";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new StoreFoodView(rs.getInt(1),rs.getString(2),
                    rs.getInt(3),rs.getString(4),
                    rs.getString(5),rs.getInt(6)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
