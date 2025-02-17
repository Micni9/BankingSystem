import javax.swing.*;

public class App{
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                LoginState loginState = new LoginState();
                LoginFrame loginFrame = new LoginFrame(loginState);
                loginFrame.setVisible(true);

                loginFrame.addWindowListener(new java.awt.event.WindowAdapter(){
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent){
                        if(loginState.isLoggedIn()){
                            MainFrame mainFrame = new MainFrame(loginState.getLoginId());
                            mainFrame.setVisible(true);
                        }else{
                            System.exit(0);
                            Conn.closeConnection();
                        }
                    }
                });
            }
        });
    }
}
