package DAO.JDBCUtils;

import ObjectInstance.UserAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class UserAccountDaoImpl implements UserAccountDao{

    private static int count=0;
    public static int getCount() {
        return count;
    }

    /**初始化计算表的行号**/
    static {
        try{
            Connection conn=MysqlConnector.createConnection();
            String sql="select count(*) from user_account";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            count=rs.getInt(1);
            MysqlConnector.closeConnection(conn,ps,rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(UserAccount userAccount) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into user_account values(?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, userAccount.getUserAccount());
        ps.setInt(2,userAccount.getState());
        ps.setString(3, userAccount.getUserPassword());
        UserAccountDaoImpl.count+=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(UserAccount userAccount) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from user_account where user_account.user_account = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, userAccount.getUserAccount());
        UserAccountDaoImpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(UserAccount userAccount) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update user_account set state=?,user_password=? where user_account=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,userAccount.getState());
        ps.setString(2, userAccount.getUserPassword());
        ps.setString(3, userAccount.getUserAccount());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<UserAccount> queryByPK(UserAccount userAccount) throws Exception {
        ArrayList<UserAccount> resList=new ArrayList<UserAccount>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from user_account where user_account=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setString(1, userAccount.getUserAccount());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new UserAccount(rs.getString(1),rs.getInt(2),rs.getString(3)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<UserAccount> queryAll() throws Exception {
        ArrayList<UserAccount> resList=new ArrayList<UserAccount>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from user_account";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new UserAccount(rs.getString(1),rs.getInt(2),rs.getString(3)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
