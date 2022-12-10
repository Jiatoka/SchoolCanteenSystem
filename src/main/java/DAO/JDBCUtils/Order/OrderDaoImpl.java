package DAO.JDBCUtils.Order;

import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.UserDAO.AdministratorDaoimpl;
import ObjectInstance.Order.Order;
import ObjectInstance.User.Administrator;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**访问订单表(order_table)**/
public class OrderDaoImpl implements OrderDao{
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
            String sql="select count(*) from order_table";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(order_id) from order_table");
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

    public int insert(Order order) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into order_table values(?,?,?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        OrderDaoImpl.count+=1;
        OrderDaoImpl.maxId+=1;
        ps.setInt(1,OrderDaoImpl.maxId );
        ps.setInt(2, order.getStoreId());
        ps.setInt(3, order.getUserId());
        ps.setString(4, order.getOrderTime());
        ps.setInt(5, order.getPrice());
        ps.setString(6,order.getTips());
        ps.setInt(7,order.getState());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return  OrderDaoImpl.maxId;
    }

    public int delete(Order order) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from order_table where order_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,order.getOrderId());
        OrderDaoImpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    /**只会更新订单状态**/
    public int update(Order order) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update order_table set state=? where order_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,order.getState());
        ps.setInt(2, order.getOrderId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<Order> queryByPK(Order order) throws Exception {
        ArrayList<Order> resList=new ArrayList<Order>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from order_table where order_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, order.getOrderId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Order(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getDate(4),rs.getInt(5),
                    rs.getString(6),rs.getInt(7)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Order> queryByIndex(Order order,int identity) throws SQLException, ClassNotFoundException {
        ArrayList<Order> resList=new ArrayList<Order>();
        Connection conn=MysqlConnector.createConnection();
        PreparedStatement ps= null;
        String sql;
        if(identity==0){
            sql="select * from order_table where n_id=?";
        }else{
            sql="select * from order_table where store_id=?";
        }

        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(identity==0){
            ps.setInt(1, order.getUserId());
        }else{
            ps.setInt(1, order.getStoreId());
        }

        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Order(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getDate(4),rs.getInt(5),
                    rs.getString(6),rs.getInt(7)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Order> queryAll() throws Exception {
        ArrayList<Order> resList=new ArrayList<Order>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from order_table";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Order(rs.getInt(1),rs.getInt(2),
                    rs.getInt(3),rs.getDate(4),
                    rs.getInt(5),rs.getString(6),
                    rs.getInt(7)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
