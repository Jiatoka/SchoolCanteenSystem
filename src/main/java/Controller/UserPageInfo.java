package Controller;

import ObjectInstance.Canteen.Canteen;
import ObjectInstance.Food.Food;
import ObjectInstance.User.StoreUser;

import java.util.HashMap;
import java.util.List;

/**记录了用户页面所需要的信息**/
public class UserPageInfo {
    private static Canteen canteen;
    /**菜品的总价**/
    private static int sum;

    public static int getSum() {
        return sum;
    }

    public static void setSum(int sum) {
        UserPageInfo.sum = sum;
    }

    public static List<HashMap<Food, Integer>> getFoodList() {
        return foodList;
    }

    public static void setFoodList(List<HashMap<Food, Integer>> foodList) {
        UserPageInfo.foodList = foodList;
    }

    private static List<HashMap<Food,Integer>> foodList;
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
        foodList=null;
        sum=0;
    }
}
