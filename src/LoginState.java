public class LoginState {
    private boolean loggedIn;
    private int id;

    public LoginState(){
        this.loggedIn = false;
        this.id = -1;
    }

    public boolean isLoggedIn(){
        return loggedIn;
    }

    public int getLoginId(){
        return id;
    }

    public void setLoggedIn(int id){
        this.loggedIn = true;
        this.id = id;
    }
}
