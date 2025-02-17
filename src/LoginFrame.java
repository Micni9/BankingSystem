import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class LoginFrame extends JFrame{
    public LoginFrame(LoginState loginState){
        UserDao userDao = new UserDaoImpl();
        setTitle("LogIn to Banking System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,150);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());
        JLabel usernameLabel = new JLabel("username: ");
        usernamePanel.add(usernameLabel);
        JTextField usernameField = new JTextField(10);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        JLabel passwordLabel = new JLabel("password: ");
        passwordPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(10);
        passwordPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        JButton registerButton = new JButton("Register");
        buttonPanel.add(registerButton);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = hash(new String(passwordField.getPassword()));
            if(userDao.login(username, password)){
                User user = userDao.getByUsername(username);
                loginState.setLoggedIn(user.getId());
                System.out.println("Welcome " + username);
                dispose();
            }else{
                System.out.println("Invalid username or password");
            }
        });
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = hash(new String(passwordField.getPassword()));
            if(userDao.exists(username)){
                System.out.println("User already exists");
            }else{
                userDao.register(username, password);
                System.out.println("User registered");
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    private static String hash(String password){
        return password;
    }
}
