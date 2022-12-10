package ObjectInstance.Food;
/**food表的记录**/
public class Food {
    private int foodId;
    private int storeId;
    private String foodName;
    private String foodDescription;
    private int price;

    public Food(int foodId, int storeId, String foodName, String foodDescription, int price) {
        this.foodId = foodId;
        this.storeId = storeId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.price = price;
    }

    public Food(int foodId, int storeId, String foodName, int price) {
        this.foodId = foodId;
        this.storeId = storeId;
        this.foodName = foodName;
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
