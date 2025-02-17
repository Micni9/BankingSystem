import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{
    private Connection conn;
    public UserDaoImpl(){
        this.conn = Conn.getInstance();
    }

    @Override
    public void register(String username, String hashedpassword){
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, hashedpassword);
            pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean login(String username, String hashedpassword){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, hashedpassword);
            return pstmt.executeQuery().next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    @Override
    public User getByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            var rs = pstmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getFloat("balance"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User getById(int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getFloat("balance"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateBalance(int id, float balance){
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setFloat(1, balance);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            getById(id).setBalance(balance);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM users WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean exists(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            return pstmt.executeQuery().next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void deposit(int id, float amount){
        User user = getById(id);
        updateBalance(id, user.getBalance() + amount);
    }

    @Override
    public void withdraw(int id, float amount){
        User user = getById(id);
        if(user.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient balance");
        }
        updateBalance(id, user.getBalance() - amount);
    }
}