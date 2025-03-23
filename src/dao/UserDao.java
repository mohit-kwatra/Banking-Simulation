package dao;

import model.Account;
import model.PaymentMethod;
import model.User;

import java.sql.*;

public class UserDao
{
    public User retrieveUserByEmail(String email, String password)
    {
        User user = null;
        Account account = null;
        PaymentMethod paymentMethod = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT users.user_id AS user_id, accounts.account_id AS account_id, payment_methods.method_id AS method_id FROM users INNER JOIN accounts ON users.user_id = accounts.user_id INNER JOIN payment_methods ON accounts.account_id = payment_methods.account_id WHERE users.email = '" + email + "' AND users.password = '" + password + "'";

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                if(resultSet.next())
                {
                    user = new User();
                    account = new Account();
                    paymentMethod = new PaymentMethod();

                    user.setUserId(resultSet.getInt("user_id"));
                    account.setAccountId(resultSet.getInt("account_id"));
                    paymentMethod.setMethodId(resultSet.getInt("method_id"));

                    user.setAccount(account);
                    account.setPaymentMethod(paymentMethod);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User retrieveUserByCardNumber(String cardNumber, String pin)
    {
        User user = null;
        Account account = null;
        PaymentMethod paymentMethod = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT users.user_id AS user_id, accounts.account_id AS account_id, payment_methods.method_id AS method_id FROM users INNER JOIN accounts ON users.user_id = accounts.user_id INNER JOIN payment_methods ON accounts.account_id = payment_methods.account_id WHERE payment_methods.card_number = '" + cardNumber + "' AND payment_methods.pin = '" + pin + "'";

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                if(resultSet.next())
                {
                    user = new User();
                    account = new Account();
                    paymentMethod = new PaymentMethod();

                    user.setUserId(resultSet.getInt("user_id"));
                    account.setAccountId(resultSet.getInt("account_id"));
                    paymentMethod.setMethodId(resultSet.getInt("method_id"));

                    user.setAccount(account);
                    account.setPaymentMethod(paymentMethod);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return user;
    }
}
