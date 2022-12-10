package ObjectInstance.User;

/**
 * @author Liang
 */
public class StoreUser extends UserTemplate{
    int canteenId;

    public int getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(int canteenId) {
        this.canteenId = canteenId;
    }

    public StoreUser(int id, String userAccount, int canteenId, String name, String phone){
        super();
        this.id=id;
        this.userAccount=userAccount;
        this.canteenId=canteenId;
        this.name=name;
        this.phone=phone;
    }
    public String toString() {
        return "id:"+this.id+"\tname:"+this.name+"\tuserAccount:"+this.userAccount;
    }
}
