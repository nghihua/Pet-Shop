package database;

import java.sql.*;

public class PostgreSQLJDBC {
    static Connection c = null;
    static Statement stmt = null;

    public static void connectDatabase()
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

    public static void disconnectDatabase()
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

    public static void insertToDatabase(String sql)
    {
        try
        {
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static ResultSet readFromDatabase(String sql)
    {
        ResultSet rs = null;
        try
        {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public static void closeStatement()
    {
        try {
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static int countResult(String sql)
    {
        int count = 0;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                count ++;
            }
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
}
