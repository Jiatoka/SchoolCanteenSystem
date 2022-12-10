package Model;

import DAO.JDBCUtils.UserAccountDao;
import DAO.JDBCUtils.UserAccountDaoImpl;
import DAO.JDBCUtils.UserDAO.*;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**用户登入(商家,普通用户,管理员)**/
public class LoginUtil {
    /**身份：0普通用户，1商家，2管理员**/
    private static int identity=0;

    private static int id=0;
    public static int getId(){
        return id;
    }
    public static int getIdentity() {
        return identity;
    }

    public static void setIdentity(int identity) {
        LoginUtil.identity = identity;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoginUtil.name = name;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        LoginUtil.account = account;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        LoginUtil.phone = phone;
    }

    private static String name;
    private static String account;
    private static String gender;
    private static String phone;

    public static String getGender() {
        return gender;
    }

    //查看登入状态
    public static int getState() {
        return state;
    }
    private LoginUtil(){

    }
    private static int state=0;
    /**登入**/
    public static boolean login(String account,String password) throws Exception {
        UserAccountDao userAccountDao=new UserAccountDaoImpl();
        List<UserAccount> userAccountList=userAccountDao.queryByPK(new UserAccount(account.trim(),0,password.trim()));
        if(userAccountList.size()==0){
            System.err.println("账号不存在");
            return false;
        }
        UserAccount userAccount=userAccountList.get(0);
        if((password.trim()).equals(userAccount.getUserPassword().trim())){
            if(isUser(account.trim())){
                state=1;
                return true;
            }else if(isStore(account.trim())){
                state=1;
                return true;
            }
            else if(isAdministrator(account.trim())){
                state=1;
                return true;
            }
            System.err.println("账号信息丢，请重新注册账号");
            return false;
        }
        System.err.println("密码错误,请检查密码");
        return false;
    }

    /**是否为普通用户**/
    private static boolean isUser(String account) throws SQLException, ClassNotFoundException {
       NormalUserDao normalUserDao=new NormalUserDaoimpl();
       List<NormalUser> normalUserList= normalUserDao.queryByIndex(new NormalUser(1,account
       ,"","",""));
       if(normalUserList.size()==0){
           return false;
       }else {
           NormalUser normalUser=normalUserList.get(0);
           LoginUtil.identity=0;
           LoginUtil.name=normalUser.getName();
           LoginUtil.account=normalUser.getUserAccount();
           LoginUtil.gender=normalUser.getGender();
           LoginUtil.phone=normalUser.getPhone();
           LoginUtil.id=normalUser.getId();
           return true;
       }
    }

    /**是否为商家**/
    private static boolean isStore(String account) throws SQLException, ClassNotFoundException {
        StoreUserDao storeUserDao=new StoreUserimpl();
        List<StoreUser> storeUsers= storeUserDao.queryByIndex(new StoreUser(1,account
                ,1,"",""));
        if(storeUsers.size()==0){
            return false;
        }else {
            StoreUser storeUser=storeUsers.get(0);
            LoginUtil.identity=1;
            LoginUtil.name=storeUser.getName();
            LoginUtil.account=storeUser.getUserAccount();
            LoginUtil.gender="";
            LoginUtil.phone=storeUser.getPhone();
            LoginUtil.id=storeUser.getId();
            return true;
        }
    }

    /**是否为管理员**/
    private static boolean isAdministrator(String account) throws SQLException, ClassNotFoundException {
        AdministratorDao administratorDao=new AdministratorDaoimpl();
        List<Administrator> administratorList= administratorDao.queryByIndex(new Administrator(1,account
                ,"","",""));
        if(administratorList.size()==0){
            return false;
        }else {
            Administrator administrator=administratorList.get(0);
            LoginUtil.identity=2;
            LoginUtil.name=administrator.getName();
            LoginUtil.account=administrator.getUserAccount();
            LoginUtil.gender=administrator.getGender();
            LoginUtil.phone=administrator.getPhone();
            LoginUtil.id=administrator.getId();
            return true;
        }
    }

    /**初始化静态变量**/
    public static void initVar(){
        LoginUtil.state=0;
        LoginUtil.account="";
        LoginUtil.phone="";
        LoginUtil.gender="";
        LoginUtil.identity=0;
        LoginUtil.name="";
        LoginUtil.id=0;
    }
}
