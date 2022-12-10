package DAO.JDBCUtils.UserDAO;

import DAO.JDBCUtils.MysqlConnector;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreUserimpl implements StoreUserDao{
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
            String sql="select count(*) from store";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(store_id) from store");
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

    public int insert(StoreUser storeUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into store values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StoreUserimpl.count+=1;
        StoreUserimpl.maxId+=1;
        ps.setInt(1,StoreUserimpl.maxId );
        ps.setString(2, storeUser.getUserAccount());
        ps.setInt(3, storeUser.getCanteenId());
        ps.setString(4, storeUser.getName());
        ps.setString(5, storeUser.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int insert(StoreUser storeUser,int id) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into store values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StoreUserimpl.count+=1;
        ps.setInt(1,id );
        ps.setString(2, storeUser.getUserAccount());
        ps.setInt(3, storeUser.getCanteenId());
        ps.setString(4, storeUser.getName());
        ps.setString(5, storeUser.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(StoreUser storeUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from store where store_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, storeUser.getId());
        StoreUserimpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(StoreUser storeUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update store set store_name=?,store_phone=? where store_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1,storeUser.getName());
        ps.setString(2, storeUser.getPhone());
        ps.setInt(3,storeUser.getId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<StoreUser> queryByPK(StoreUser storeUser) throws Exception {
        ArrayList<StoreUser> resList=new ArrayList<StoreUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store where store_id=?";
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
            resList.add(new StoreUser(rs.getInt(1),
                    rs.getString(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<StoreUser> queryByIndex(StoreUser storeUser) throws SQLException, ClassNotFoundException {
        ArrayList<StoreUser> resList=new ArrayList<StoreUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store where user_account=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, storeUser.getUserAccount());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new StoreUser(rs.getInt(1),
                    rs.getString(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<StoreUser> queryAll() throws Exception {
        ArrayList<StoreUser> resList=new ArrayList<StoreUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from store";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new StoreUser(rs.getInt(1),
                    rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

}
