package ObjectInstance.Order;
/**order_info表的记录**/
public class OrderInfo {
    /**订单详情**/
    private int orderInfoId;
    /**订单号**/
    private int orderId;
    /**菜品**/
    private int foodId;
    /**菜品的数量**/
    private int orderInfoNumber;
    /**菜品价格**/
    private int orderInfoPrice;

    public int getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(int orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getOrderInfoNumber() {
        return orderInfoNumber;
    }

    public void setOrderInfoNumber(int orderInfoNumber) {
        this.orderInfoNumber = orderInfoNumber;
    }

    public int getOrderInfoPrice() {
        return orderInfoPrice;
    }

    public void setOrderInfoPrice(int orderInfoPrice) {
        this.orderInfoPrice = orderInfoPrice;
    }

    public OrderInfo(int orderInfoId, int orderId, int foodId, int orderInfoNumber, int orderInfoPrice) {
        this.orderInfoId = orderInfoId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.orderInfoNumber = orderInfoNumber;
        this.orderInfoPrice = orderInfoPrice;
    }
}
