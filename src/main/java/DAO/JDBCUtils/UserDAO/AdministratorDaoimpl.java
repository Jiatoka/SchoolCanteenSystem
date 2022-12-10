package DAO.JDBCUtils.UserDAO;

import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.UserAccountDaoImpl;
import ObjectInstance.User.Administrator;
import ObjectInstance.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDaoimpl implements AdministratorDao{
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
            String sql="select count(*) from administrator";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(a_id) from administrator");
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

    public int insert(Administrator administrator) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into administrator values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AdministratorDaoimpl.count+=1;
        AdministratorDaoimpl.maxId+=1;
        ps.setInt(1,AdministratorDaoimpl.maxId );
        ps.setString(2, administrator.getUserAccount());
        ps.setString(3, administrator.getName());
        ps.setString(4, administrator.getGender());
        ps.setString(5, administrator.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int insert(Administrator administrator,int id) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into administrator values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AdministratorDaoimpl.count+=1;
        ps.setInt(1,id );
        ps.setString(2, administrator.getUserAccount());
        ps.setString(3, administrator.getName());
        ps.setString(4, administrator.getGender());
        ps.setString(5, administrator.getPhone());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(Administrator administrator) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from administrator where a_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, administrator.getId());
        AdministratorDaoimpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(Administrator administrator) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update administrator set a_name=?,a_sex=?,a_phone=? where a_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1,administrator.getName());
        ps.setString(2, administrator.getGender());
        ps.setString(3, administrator.getPhone());
        ps.setInt(4,administrator.getId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<Administrator> queryByPK(Administrator administrator) throws Exception {
        ArrayList<Administrator> resList=new ArrayList<Administrator>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from administrator where a_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, administrator.getId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Administrator(rs.getInt(1),
                    rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Administrator> queryByIndex(Administrator administrator) throws SQLException, ClassNotFoundException {
        ArrayList<Administrator> resList=new ArrayList<Administrator>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from administrator where user_account=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, administrator.getUserAccount());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Administrator(rs.getInt(1),
                    rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Administrator> queryAll() throws Exception {
        ArrayList<Administrator> resList=new ArrayList<Administrator>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from administrator";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Administrator(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
