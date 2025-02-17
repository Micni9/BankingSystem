public interface UserDao {
    public void register(String username, String hashedpassword);
    public boolean login(String username, String hashedpassword);
    public User getByUsername(String username);
    public User getById(int id);
    public void updateBalance(int id, float balance);
    public boolean exists(String username);
    public void deposit(int id, float amount);
    public void withdraw(int id, float amount);
    void delete(int id);
}
