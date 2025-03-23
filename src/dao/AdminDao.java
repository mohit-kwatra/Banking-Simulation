package dao;

import model.Admin;

import java.sql.*;

public class AdminDao
{
    public int checkAdmin(Admin admin)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT * From admin WHERE email = '" + admin.getEmail() + "' and password = '" + admin.getPassword() + "'";
        int isAdmin = 0;

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                if(resultSet.next())
                    isAdmin = 1;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return isAdmin;
    }
}
