import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TransactionDaoImpl implements TransactionDao {
    private Connection conn;
    public TransactionDaoImpl(){
        this.conn = Conn.getInstance();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (\n"
                + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "user_id INTEGER NOT NULL,\n"
                + "amount REAL NOT NULL,\n"
                + "type TEXT NOT NULL,\n"
                + "date TEXT NOT NULL,\n"
                + "remarks TEXT,\n"
                + "FOREIGN KEY(user_id) REFERENCES users(id)\n"
                + ");";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransaction(int userId, float amount, String type, String date, String remarks) {
        String sql = "INSERT INTO transactions(user_id, amount, type, date, remarks) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setFloat(2, amount);
            pstmt.setString(3, type);
            pstmt.setString(4, date);
            pstmt.setString(5, remarks);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Transaction getTransaction(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transaction(transactionId, rs.getInt("user_id"), rs.getFloat("amount"),
                        rs.getString("type"), rs.getString("date"), rs.getString("remarks"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(rs.getInt("transaction_id"), userId, rs.getFloat("amount"),
                        rs.getString("type"), rs.getString("date"), rs.getString("remarks")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    public void deleteTransaction(int transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTransactions(int userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editTransaction(int transactionId, float amount, String type, String date, String remarks) {
        String sql = "UPDATE transactions SET amount = ?, type = ?, date = ?, remarks = ? WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, amount);
            pstmt.setString(2, type);
            pstmt.setString(3, date);
            pstmt.setString(4, remarks);
            pstmt.setInt(5, transactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM transactions";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
