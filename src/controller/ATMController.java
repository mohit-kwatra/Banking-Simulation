package controller;

import dao.AccountDao;
import dao.DatabaseConnection;
import dao.TransactionDao;
import model.Transaction;
import model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ATMController
{
    private AccountDao accountDao = new AccountDao();
    private TransactionDao transactionDao = new TransactionDao();

    private Transaction transaction = new Transaction();
    private Scanner scanner = new Scanner(System.in);


    public void depositCash(User currentUser)
    {
        BigDecimal amount;
        int rowsAffected;

        System.out.print("Enter amount: ");
        amount = scanner.nextBigDecimal();

        if(amount.compareTo(new BigDecimal(0)) > 0)
        {
            rowsAffected = accountDao.addAccountBalance(currentUser.getAccount(), amount);

            if(rowsAffected == 1)
            {
                setTransactionProperties(currentUser.getAccount().getAccountId(), currentUser.getAccount().getPaymentMethod().getMethodId(), amount, "credit", "finished");
                rowsAffected = transactionDao.createTransaction(transaction);

                if(rowsAffected == 1)
                    System.out.println("Success.");
                else
                    System.out.println("Failed.");
            }
            else
                System.out.println("Failed.");
        }
        else
            System.out.println("Invalid amount.");
    }

    public void withdrawCash(User currentUser)
    {
        BigDecimal amount, balance;
        int rowsAffected;

        System.out.print("Enter amount: ");
        amount = scanner.nextBigDecimal();

        balance = accountDao.retrieveAccountBalance(currentUser.getAccount());

        if(amount.compareTo(balance) > 0)
            System.out.println("Insufficient balance.");
        else
        {
            rowsAffected = accountDao.updateAccountBalance(currentUser.getAccount(), balance.subtract(amount));

            if(rowsAffected == 1)
            {
                setTransactionProperties(currentUser.getAccount().getAccountId(), currentUser.getAccount().getPaymentMethod().getMethodId(), amount, "debit", "finished");
                rowsAffected = transactionDao.createTransaction(transaction);

                if(rowsAffected == 1)
                    System.out.println("Success.");
                else
                    System.out.println("Failed.");
            }
            else
                System.out.println("Failed.");
        }
    }

    private void setTransactionProperties(int accountId, int methodId, BigDecimal amount, String type, String status)
    {
        transaction.setAccountId(accountId);
        transaction.setPaymentMethodId(methodId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setStatus(status);
    }

    public void balanceInquiry(User currentUser)
    {
        BigDecimal balance = accountDao.retrieveAccountBalance(currentUser.getAccount());

        System.out.println("Your current balance is " + balance);
    }

    public void displayMiniStatement(User currentUser)
    {
        ArrayList<Transaction> transactions = transactionDao.retrieveAccountTransactions(currentUser.getAccount(), 5);

        for(Transaction t : transactions)
        {
            System.out.println(t.toString());
        }
    }
}
