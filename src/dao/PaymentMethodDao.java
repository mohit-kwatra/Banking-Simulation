package dao;

import model.Account;
import model.PaymentMethod;

import java.sql.*;

public class PaymentMethodDao
{
    public int hasCreditCard(Account account)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        int recordFound = 0;
        String selectSQL = "SELECT * FROM payment_methods WHERE card_type = 'credit' AND account_id = " + account.getAccountId();

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                if(resultSet.next())
                    recordFound = 1;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return recordFound;
    }

    public int addCreditCard(PaymentMethod paymentMethod, Account account)
    {
        Connection connection = null;
        Statement statement = null;

        int rowsAffected = 0;
        String insertSQL = "INSERT INTO payment_methods (account_id, card_type, card_number, pin, expiry_date) VALUES (" + account.getAccountId() + ",'" + paymentMethod.getCardType() + "','" + paymentMethod.getCardNumber() + "','" + paymentMethod.getPin() + "', DATE_ADD(CURDATE(), INTERVAL 4 YEAR))";

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                rowsAffected = statement.executeUpdate(insertSQL);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return rowsAffected;
    }
}
