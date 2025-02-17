public class User {
    private int id;
    private String username;
    private float balance;

    public User(int id, String username, float balance){
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public float getBalance(){
        return balance;
    }
    
    public void setBalance(float balance){
        this.balance = balance;
    }
}
