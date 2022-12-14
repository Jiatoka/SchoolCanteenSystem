package Controller;

import ObjectInstance.Canteen.Canteen;
import ObjectInstance.User.StoreUser;

/**记录了用户页面所需要的信息**/
public class UserPageInfo {
    private static Canteen canteen;

    public static Canteen getCanteen() {
        return canteen;
    }

    public static void setCanteen(Canteen canteen) {
        UserPageInfo.canteen = canteen;
    }

    public static StoreUser getStoreUser() {
        return storeUser;
    }

    public static void setStoreUser(StoreUser storeUser) {
        UserPageInfo.storeUser = storeUser;
    }

    private static StoreUser storeUser;

    public  static void initVar(){
        canteen=null;
        storeUser=null;
    }
}
