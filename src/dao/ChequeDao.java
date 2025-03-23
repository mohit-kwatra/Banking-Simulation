package dao;

import model.Account;
import model.Cheque;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class ChequeDao
{
    public int issueCheque(Cheque cheque)
    {
        Connection connection = null;
        Statement statement = null;

        int rowsAffected = 0;
        String insertSQL = "INSERT INTO cheques (account_id, payee_name, amount, issued_on) VALUES (" + cheque.getAccountId() + ",'" + cheque.getPayeeName() + "'," + cheque.getAmount() + ",'" + cheque.getIssuedOn() + "')";

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                rowsAffected = statement.executeUpdate(insertSQL);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return rowsAffected;
    }

    public ArrayList<Cheque> retrieveIssuedCheques(Account account)
    {
        ArrayList<Cheque> issuedCheques = new ArrayList<Cheque>();
        Cheque cheque;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String selectSQL = "SELECT * FROM cheques WHERE account_id = " + account.getAccountId();

        try
        {
            connection = DatabaseConnection.getConnection();

            if(connection != null)
            {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(selectSQL);

                while(resultSet.next())
                {
                    cheque = new Cheque();
                    cheque.setChequeId(resultSet.getInt("cheque_id"));
                    cheque.setAccountId(resultSet.getInt("account_id"));
                    cheque.setPayeeName(resultSet.getString("payee_name"));
                    cheque.setAmount(resultSet.getBigDecimal("amount"));
                    cheque.setIssuedOn(resultSet.getDate("issued_on"));
                    cheque.setStatus(resultSet.getString("status"));

                    issuedCheques.add(cheque);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return issuedCheques;
    }
}
