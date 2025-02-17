import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private static final String URL = "jdbc:sqlite:banking.db";
    private static Connection instance = null;

    private Conn(){}

    public static Connection getInstance(){
        if (instance == null){
            try {
                instance = DriverManager.getConnection(URL);
                System.out.println("Connected to the database");
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }

    public static void closeConnection(){
        if (instance != null){
            try {
                instance.close();
                instance = null;
                System.out.println("Disconnected from the database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
