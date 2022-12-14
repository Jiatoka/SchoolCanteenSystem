package ObjectInstance.View;
/**商家菜单视图**/
public class StoreFoodView {
    private int storeId;
    private String storeName;
    private int foodId;
    private String foodName;
    private String foodDescription;
    private int price;

    public StoreFoodView(int storeId,String storeName,int foodId,String foodName,String foodDescription,
                         int price){
        this.storeId=storeId;
        this.storeName=storeName;
        this.foodId=foodId;
        this.foodName=foodName;
        this.foodDescription=foodDescription;
        this.price=price;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
