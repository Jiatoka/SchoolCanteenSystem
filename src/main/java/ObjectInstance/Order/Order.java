package ObjectInstance.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**order_table表的记录**/
public class Order {
    private int orderId;
    private int storeId;
    private int userId;
    private Date date;
    private String orderTime;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**总金额**/
    private int price;
    /**备注**/
    private String tips;
    /**0:未接单,1:接单**/
    private int state;
    /**格式化日期**/
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    public Order(int orderId, int storeId, int userId, Date date,int price, String tips, int state) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.userId = userId;
        this.date = date;
        this.price=price;
        this.tips = tips;
        this.state = state;
        this.orderTime=ft.format(this.date);
    }

    public Order(int orderId, int storeId, int userId, Date date, int price,int state) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.userId = userId;
        this.date = date;
        this.price=price;
        this.state = state;
        this.orderTime=ft.format(this.date);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
