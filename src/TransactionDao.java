import java.util.List;

public interface TransactionDao {
    public void addTransaction(int userId, float amount, String type, String date, String remarks);
    public Transaction getTransaction(int transactionId);
    public List<Transaction> getTransactions(int userId);
    public void deleteTransaction(int transactionId);
    public void deleteTransactions(int userId);
    public void editTransaction(int transactionId, float amount, String type, String date, String remarks);
    public void deleteAll();
}
