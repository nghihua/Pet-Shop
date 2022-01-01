package database;
import java.sql.*;

public class PostgreSQLJDBC {
    static Connection c = null;
    static Statement stmt = null;

    public static void ConnectDatabase()
    {
        // Try to get connection to database
        try {
            String url = "jdbc:postgresql://john.db.elephantsql.com:5432/ftwadznd";
            String user = "ftwadznd";
            String password = "bi2s-Mm5wQ6UDK-J9I4wKdzyBJetNDmF";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            System.exit(0);
            //System.out.println("Some error occurred..." + e);
        }
    }

    public static void DisconnectDatabase()
    {
        try{
            c.close();
            System.out.println("Database disconnected successfully!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
