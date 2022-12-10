package Model;

import DAO.JDBCUtils.UserAccountDao;
import DAO.JDBCUtils.UserAccountDaoImpl;
import DAO.JDBCUtils.UserDAO.*;
import ObjectInstance.User.Administrator;
import ObjectInstance.User.NormalUser;
import ObjectInstance.User.StoreUser;
import ObjectInstance.UserAccount;

import java.util.List;

/**用户注册(商家，普通用户，管理员)**/
public class RegisterUtil {
    private RegisterUtil(){

    }

    /**普通用户注册**/
    public static boolean register(UserAccount userAccount, NormalUser normalUser) throws Exception {
        //查找账号是否存在
        UserAccountDao userAccountDao=new UserAccountDaoImpl();
        List<UserAccount> userAccountList=userAccountDao.queryByPK(userAccount);
        if(userAccountList.size()>0){
            System.err.println("注册失败,账号`"+userAccount.getUserAccount()+"`已经存在");
            return  false;
        }

        //注册账号
        userAccountDao.insert(userAccount);

        //注册用户表
        NormalUserDao normalUserDao=new NormalUserDaoimpl();
        normalUserDao.insert(normalUser);
        return true;
    }

    /**商家注册**/
    public static boolean register(UserAccount userAccount, StoreUser storeUser) throws Exception {
        //查找账号是否存在
        UserAccountDao userAccountDao=new UserAccountDaoImpl();
        List<UserAccount> userAccountList=userAccountDao.queryByPK(userAccount);
        if(userAccountList.size()>0){
            System.err.println("注册失败,账号`"+userAccount.getUserAccount()+"`已经存在");
            return  false;
        }

        //注册账号
        userAccountDao.insert(userAccount);

        //注册用户表
        StoreUserDao storeUserDao=new StoreUserimpl();
        storeUserDao.insert(storeUser);
        return true;
    }

    /**管理员注册**/
    /**商家注册**/
    public static boolean register(UserAccount userAccount, Administrator administrator) throws Exception {
        //查找账号是否存在
        UserAccountDao userAccountDao=new UserAccountDaoImpl();
        List<UserAccount> userAccountList=userAccountDao.queryByPK(userAccount);
        if(userAccountList.size()>0){
            System.err.println("注册失败,账号`"+userAccount.getUserAccount()+"`已经存在");
            return  false;
        }

        //注册账号
        userAccountDao.insert(userAccount);

        //注册用户表
        AdministratorDao administratorDao =new AdministratorDaoimpl();
        administratorDao.insert(administrator);
        return true;
    }
}
