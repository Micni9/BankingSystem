import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class MainFrame extends JFrame {
    private JPanel balancePanel;
    private JPanel depositPanel;
    private JPanel withdrawPanel;
    private JPanel transactionPanel;
    public MainFrame(int id){
        System.out.println("Welcome to the MainFrame");
        setTitle("Banking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout());
        UserDao userDao = new UserDaoImpl();
        TransactionDao transactionDao = new TransactionDaoImpl();
        User user = userDao.getById(id);
        JLabel balanceLabel = new JLabel("Balance: " + user.getBalance());
        balancePanel.add(balanceLabel);

        depositPanel = new JPanel();
        depositPanel.setLayout(new FlowLayout());
        JLabel depositLabel = new JLabel("Deposit: ");
        depositPanel.add(depositLabel);
        JTextField depositField = new JTextField(10);
        depositPanel.add(depositField);
        JButton depositButton = new JButton("Deposit");
        depositPanel.add(depositButton);
        depositButton.addActionListener(e -> {
            if(depositField.getText().length() > 0){
                float deposit = Float.parseFloat(depositField.getText());
                userDao.deposit(id, deposit);
                User updatedUser = userDao.getById(id);
                transactionDao.addTransaction(id, deposit, "deposit", "2021-09-01", "Deposit");
                balanceLabel.setText("Balance: " + updatedUser.getBalance());
                depositField.setText("");
            }
        });

        withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new FlowLayout());
        JLabel withdrawLabel = new JLabel("Withdraw: ");
        withdrawPanel.add(withdrawLabel);
        JTextField withdrawField = new JTextField(10);
        withdrawPanel.add(withdrawField);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawPanel.add(withdrawButton);
        withdrawButton.addActionListener(e -> {
            if(withdrawField.getText().length() > 0){
                try{
                    float withdraw = Float.parseFloat(withdrawField.getText());
                    userDao.withdraw(id, withdraw);
                    User updatedUser = userDao.getById(id);
                    transactionDao.addTransaction(id, withdraw, "withdraw", "2021-09-01", "Withdrawal");
                    balanceLabel.setText("Balance: " + updatedUser.getBalance());
                }catch(IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
                withdrawField.setText("");
            }
        });

        transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        JLabel transactionLabel = new JLabel("Transactions");
        transactionPanel.add(transactionLabel);
        for(Transaction transaction : transactionDao.getTransactions(id)){
            JLabel transactionEntry = new JLabel(transaction.toString());
            transactionPanel.add(transactionEntry);
        }

        mainPanel.add(balancePanel);
        mainPanel.add(depositPanel);
        mainPanel.add(withdrawPanel);
        mainPanel.add(transactionPanel);
        add(mainPanel);
    }

    protected void processWindowEvent(java.awt.event.WindowEvent e){
        if(e.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING){
            Conn.closeConnection();
            System.exit(0);
        }
        super.processWindowEvent(e);
    }
}
