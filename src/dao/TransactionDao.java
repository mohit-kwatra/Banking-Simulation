package dao;

import model.Account;
import model.Transaction;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDao
{
    public ArrayList<Transaction> retrieveAllTransactions()
    {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Transaction transaction;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT * FROM transactions";

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                while(resultSet.next())
                {
                    transaction = new Transaction();
                    transaction.setTransactionId(resultSet.getInt("transaction_id"));
                    transaction.setAccountId(resultSet.getInt("account_id"));
                    transaction.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                    transaction.setTransactionType(resultSet.getString("transaction_type"));
                    transaction.setAmount(resultSet.getBigDecimal("amount"));
                    transaction.setStatus(resultSet.getString("status"));
                    transaction.setTransactionTimestamp(resultSet.getTimestamp("transaction_timestamp"));

                    transactions.add(transaction);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return transactions;
    }

    public int createTransaction(Transaction transaction)
    {
        Connection connection = null;
        Statement statement = null;
        int rowsAffected = 0;

        String insertSQL = "INSERT INTO transactions (account_id, payment_method_id, amount, transaction_type, status) VALUES(" + transaction.getAccountId() + "," + transaction.getPaymentMethodId() + "," + transaction.getAmount() + ",'" + transaction.getTransactionType() + "','" + transaction.getStatus() + "')";

        try
        {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(insertSQL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return rowsAffected;
    }

    public ArrayList<Transaction> retrieveAccountTransactions(Account account, int limit)
    {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Transaction transaction;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL;
        if(limit == -1)
            selectSQL = "SELECT * FROM transactions WHERE account_id = " + account.getAccountId();
        else
            selectSQL = "SELECT * FROM transactions WHERE account_id = " + account.getAccountId() + " ORDER BY transaction_id DESC LIMIT " + limit;

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                while(resultSet.next())
                {
                    transaction = new Transaction();
                    transaction.setTransactionId(resultSet.getInt("transaction_id"));
                    transaction.setAccountId(resultSet.getInt("account_id"));
                    transaction.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                    transaction.setTransactionType(resultSet.getString("transaction_type"));
                    transaction.setAmount(resultSet.getBigDecimal("amount"));
                    transaction.setStatus(resultSet.getString("status"));
                    transaction.setTransactionTimestamp(resultSet.getTimestamp("transaction_timestamp"));

                    transactions.add(transaction);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return transactions;
    }
}
