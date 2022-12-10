package DAO.JDBCUtils.Order;

import DAO.JDBCUtils.MysqlConnector;
import ObjectInstance.Order.Order;
import ObjectInstance.Order.OrderInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**访问订单详情表(order_info)**/
public class OrderInfoDaoImpl implements OrderInfoDao{
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
            String sql="select count(*) from order_info";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(order_info_id) from order_info");
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

    public int insert(OrderInfo orderInfo) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into order_info values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        OrderInfoDaoImpl.count+=1;
        OrderInfoDaoImpl.maxId+=1;
        ps.setInt(1,OrderInfoDaoImpl.maxId);
        ps.setInt(2, orderInfo.getOrderId());
        ps.setInt(3, orderInfo.getFoodId());
        ps.setInt(4, orderInfo.getOrderInfoNumber());
        ps.setInt(5, orderInfo.getOrderInfoPrice());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(OrderInfo orderInfo) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from order_info where order_info_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,orderInfo.getOrderInfoId());
        OrderInfoDaoImpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    /**只会更新订单状态**/
    public int update(OrderInfo orderInfo) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update order_info set order_info_number=?,order_info_price=? where order_info_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,orderInfo.getOrderInfoNumber());
        ps.setInt(2, orderInfo.getOrderInfoPrice());
        ps.setInt(3,orderInfo.getOrderInfoId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<OrderInfo> queryByPK(OrderInfo orderInfo) throws Exception {
        ArrayList<OrderInfo> resList=new ArrayList<OrderInfo>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from order_info where order_info_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, orderInfo.getOrderInfoId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new OrderInfo(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getInt(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    /**根据订单号查找订单详情**/
    public List<OrderInfo> queryByIndex(OrderInfo orderInfo) throws SQLException, ClassNotFoundException {
        ArrayList<OrderInfo> resList=new ArrayList<OrderInfo>();
        Connection conn=MysqlConnector.createConnection();
        PreparedStatement ps= null;
        String sql="select * from order_info where order_id=?";

        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,orderInfo.getOrderId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new OrderInfo(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getInt(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<OrderInfo> queryAll() throws Exception {
        ArrayList<OrderInfo> resList=new ArrayList<OrderInfo>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from order_info";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new OrderInfo(rs.getInt(1),rs.getInt(2),
                    rs.getInt(3),rs.getInt(4),
                    rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
