package ObjectInstance.User;

/**
 * @author Liang
 */
public class Administrator extends UserTemplate{
    String gender;
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Administrator(int id,String userAccount,String name,String gender,String phone){
        super();
        this.id=id;
        this.userAccount=userAccount;
        this.gender=gender;
        this.name=name;
        this.phone=phone;
    }

    public Administrator(int id,String userAccount,String name,String phone){
        super();
        this.id=id;
        this.userAccount=userAccount;
        this.gender="";
        this.name=name;
        this.phone=phone;
    }

    public String toString() {
        return "id:"+this.id+"\tname:"+this.name+"\tuserAccount:"+this.userAccount;
    }
}
