package DAO.JDBCUtils.Order;

import DAO.JDBCUtils.MysqlConnector;
import ObjectInstance.Order.Contact;
import ObjectInstance.Order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liang
 */
public class ContactDaoImpl implements ContactDao{
    public int insert(Contact contact) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into contact values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,contact.getOrderId() );
        ps.setInt(2, contact.getUserId());
        ps.setString(3, contact.getUserName());
        ps.setString(4, contact.getPhone());
        ps.setString(5, contact.getAddress());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return  OrderDaoImpl.maxId;
    }

    public int delete(Contact contact) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from contact where order_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,contact.getOrderId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    /**只会更新订单状态**/
    public int update(Contact contact) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update contact set address=? where order_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1,contact.getAddress());
        ps.setInt(2, contact.getOrderId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<Contact> queryByPK(Contact contact) throws Exception {
        ArrayList<Contact> resList=new ArrayList<Contact>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from contact where order_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, contact.getOrderId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Contact(rs.getInt(1),
                    rs.getInt(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Contact> queryAll() throws Exception {
        ArrayList<Contact> resList=new ArrayList<Contact>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from contact";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Contact(rs.getInt(1),rs.getInt(2),
                    rs.getString(3),rs.getString(4),
                    rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
