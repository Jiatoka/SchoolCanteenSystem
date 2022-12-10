package ObjectInstance.Canteen;

public class Canteen {
    private int id;
    private int addressId;
    private int administatorId;
    private String name;
    private int maxNumber;

    public Canteen(int id,int addressId,int administatorId,String name,int maxNumber){
        this.id=id;
        this.addressId=addressId;
        this.administatorId=administatorId;
        this.name=name;
        this.maxNumber=maxNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAdministatorId() {
        return administatorId;
    }

    public void setAdministatorId(int administatorId) {
        this.administatorId = administatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
