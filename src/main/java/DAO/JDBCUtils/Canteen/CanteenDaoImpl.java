package DAO.JDBCUtils.Canteen;

import DAO.JDBCUtils.MysqlConnector;
import DAO.JDBCUtils.UserDAO.StoreUserimpl;
import ObjectInstance.Canteen.Canteen;
import ObjectInstance.User.StoreUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CanteenDaoImpl implements CanteenDao{
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
            String sql="select count(*) from canteen";
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs=ps.executeQuery("select max(c_id) from canteen");
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

    public int insert(Canteen canteen) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into canteen values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CanteenDaoImpl.count+=1;
        CanteenDaoImpl.maxId+=1;
        ps.setInt(1,CanteenDaoImpl.maxId );
        ps.setInt(2, canteen.getAddressId());
        ps.setInt(3, canteen.getAdministatorId());
        ps.setString(4, canteen.getName());
        ps.setInt(5, canteen.getMaxNumber());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int insert(Canteen canteen,int id) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="insert into canteen values(?,?,?,?,?)";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CanteenDaoImpl.count+=1;
        ps.setInt(1,id );
        ps.setInt(2, canteen.getAddressId());
        ps.setInt(3, canteen.getAdministatorId());
        ps.setString(4, canteen.getName());
        ps.setInt(5, canteen.getMaxNumber());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int delete(Canteen canteen) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="delete from canteen where c_id = ?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, canteen.getId());
        CanteenDaoImpl.count-=1;
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public int update(Canteen canteen) throws SQLException, ClassNotFoundException {
        Connection conn=MysqlConnector.createConnection();
        String sql="update canteen set a_id=?,c_name=?,c_max_number=? where c_id=?";
        PreparedStatement ps= null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1,canteen.getAdministatorId());
        ps.setString(2, canteen.getName());
        ps.setInt(3,canteen.getMaxNumber());
        ps.setInt(4,canteen.getId());
        int res=ps.executeUpdate();
        MysqlConnector.closeConnection(conn,ps);
        return res;
    }

    public List<Canteen> queryByPK(Canteen canteen) throws Exception {
        ArrayList<Canteen> resList=new ArrayList<Canteen>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from canteen where c_id=?";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ps.setInt(1, canteen.getId());
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Canteen(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getString(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }

    public List<Canteen> queryAll() throws Exception {
        ArrayList<Canteen> resList=new ArrayList<Canteen>();
        Connection conn=MysqlConnector.createConnection();
        String sql="select * from canteen";
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs=ps.executeQuery();
        while(rs.next()){
            resList.add(new Canteen(rs.getInt(1),
                    rs.getInt(2),rs.getInt(3),
                    rs.getString(4),rs.getInt(5)));
        }
        MysqlConnector.closeConnection(conn,ps,rs);
        return  resList;
    }
}
