package DAO.JDBCUtils.UserDAO;

import DAO.JDBCUtils.MysqlConnector;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NormalUserDaoimpl implements NormalUserDao{
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
            String sql="select count(*) from normal_user";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(n_id) from normal_user");
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

    public int insert(NormalUser normalUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into normal_user values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NormalUserDaoimpl.count+=1;
        NormalUserDaoimpl.maxId+=1;
        ps.setInt(1,NormalUserDaoimpl.maxId );
        ps.setString(2, normalUser.getUserAccount());
        ps.setString(3, normalUser.getName());
        ps.setString(4, normalUser.getGender());
        ps.setString(5, normalUser.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int insert(NormalUser normalUser,int id) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into normal_user values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NormalUserDaoimpl.count+=1;
        ps.setInt(1,id );
        ps.setString(2, normalUser.getUserAccount());
        ps.setString(3, normalUser.getName());
        ps.setString(4, normalUser.getGender());
        ps.setString(5, normalUser.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(NormalUser normalUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from normal_user where n_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, normalUser.getId());
        NormalUserDaoimpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(NormalUser normalUser) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update normal_user set n_name=?,n_sex=?,n_phone=? where n_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1,normalUser.getName());
        ps.setString(2, normalUser.getGender());
        ps.setString(3, normalUser.getPhone());
        ps.setInt(4,normalUser.getId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<NormalUser> queryByPK(NormalUser normalUser) throws Exception {
        ArrayList<NormalUser> resList=new ArrayList<NormalUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from normal_user where n_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, normalUser.getId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new NormalUser(rs.getInt(1),
                    rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<NormalUser> queryByIndex(NormalUser normalUser) throws SQLException, ClassNotFoundException {
        ArrayList<NormalUser> resList=new ArrayList<NormalUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from normal_user where user_account=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, normalUser.getUserAccount());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new NormalUser(rs.getInt(1),
                    rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<NormalUser> queryAll() throws Exception {
        ArrayList<NormalUser> resList=new ArrayList<NormalUser>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from normal_user";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new NormalUser(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
