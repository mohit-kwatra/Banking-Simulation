package dao;

import java.sql.*;

public class DatabaseConnection
{
    public static Connection getConnection()
    {
        Connection connection = null;

        String conURL = "jdbc:mysql://localhost:3306/assignment_banking_system";
        String user = "root";
        String password = "toor";

        try
        {
            connection = DriverManager.getConnection(conURL, user, password);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
