package ObjectInstance.Order;
/**顾客的联系方式**/
public class Contact {
    private int orderId;
    private int userId;
    private String userName;
    private String phone;
    private String address;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contact(int orderId, int userId, String userName, String phone, String address){
        this.orderId=orderId;
        this.userId=userId;
        this.userName=userName;
        this.phone=phone;
        this.address=address;
    }

}
