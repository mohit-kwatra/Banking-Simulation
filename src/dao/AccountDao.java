package dao;

import model.Account;
import model.User;

import java.math.BigDecimal;
import java.sql.*;

public class AccountDao
{
    public int insertNewAccount(User user)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        int isAccountCreated = 0;

        String insertUserSQL = "INSERT INTO users (full_name, email, password, creation_date) values('" + user.getName() + "','" + user.getEmail() + "','" + user.getPassword() + "',CURRENT_DATE)";
        String insertAccountSQL;
        String insertPaymentMethodSQL;

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                connection.setAutoCommit(false);

                statement = connection.createStatement();
                statement.executeUpdate(insertUserSQL, Statement.RETURN_GENERATED_KEYS);

                resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    user.setUserId(resultSet.getInt(1));

                insertAccountSQL = "INSERT INTO accounts (user_id, account_number, account_type, balance) values(" + user.getUserId() + ",'" + user.getAccount().getAccountNumber() + "','" + user.getAccount().getAccountType() + "'," + user.getAccount().getBalance() + ")";
                statement.executeUpdate(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);

                resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    user.getAccount().setAccountId(resultSet.getInt(1));

                insertPaymentMethodSQL = "INSERT INTO payment_methods (account_id, card_type, card_number, pin, expiry_date) values(" + user.getAccount().getAccountId() + ",'" + user.getAccount().getPaymentMethod().getCardType() + "','" + user.getAccount().getPaymentMethod().getCardNumber() + "','" + user.getAccount().getPaymentMethod().getPin() + "',DATE_ADD(CURDATE(), INTERVAL 4 YEAR))";
                statement.executeUpdate(insertPaymentMethodSQL);

                connection.commit();
                isAccountCreated = 1;
            }
        }
        catch(SQLException e)
        {
            try
            {
                connection.rollback();
            }
            catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

            System.out.println(e.getMessage());
        }

        return isAccountCreated;
    }

    public BigDecimal retrieveAccountBalance(Account account)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT balance FROM accounts WHERE account_id = " + account.getAccountId();
        BigDecimal balance = null;

        try
        {
           connection = DatabaseConnection.getConnection();

           if(connection != null)
           {
               statement = connection.createStatement();
               resultSet = statement.executeQuery(selectSQL);

               if(resultSet.next())
               {
                   balance = resultSet.getBigDecimal("balance");
               }
           }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    public int addAccountBalance(Account account, BigDecimal amount)
    {
        Connection connection = null;
        Statement statement = null;
        int rowsAffected = 0;

        String updateSQL = "UPDATE accounts SET balance = balance + " + amount + " WHERE account_id = " + account.getAccountId();

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                rowsAffected = statement.executeUpdate(updateSQL);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    public int updateAccountBalance(Account account, BigDecimal newBalance)
    {
        Connection connection = null;
        Statement statement = null;
        int rowsAffected = 0;

        String updateSQL = "UPDATE accounts SET balance = " + newBalance + " WHERE account_id = " + account.getAccountId();

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                rowsAffected = statement.executeUpdate(updateSQL);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }
}
