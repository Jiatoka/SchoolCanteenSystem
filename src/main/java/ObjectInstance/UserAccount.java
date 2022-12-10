package ObjectInstance;
/**账号表**/
public class UserAccount {
    private String userAccount;
    private int state=0;
    private String userPassword;
    public UserAccount(String userAccount, int state, String userPassword) throws Exception {
        if(userAccount.length()>50){
            System.err.println("账号长度不能超过25位");
            throw new Exception();
        }
        if(userPassword.length()>20){
            System.err.println("密码长度不能超过20位");
            throw new Exception();
        }

        if(state!=0){
            this.state=1;
        }
        else {
            this.state=0;
        }
        this.userAccount=userAccount;
        this.userPassword=userPassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "userAccount:"+this.userAccount+"\tuserPassword:"+this.userPassword
                +"\tstate:"+this.state;
    }
}
