package controller;

import dao.AccountDao;
import dao.AdminDao;
import dao.TransactionDao;
import model.*;
import util.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminController
{
    private Scanner scanner = new Scanner(System.in);
    private AccountDao accountDao = new AccountDao();
    private TransactionDao transactionDao = new TransactionDao();

    public int login()
    {
        String email;
        String password;
        int isAdmin;

        AdminDao adminDao = new AdminDao();
        Admin admin = new Admin();

        System.out.print(":: Admin Login ::\nEnter email: ");
        email = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();

        admin.setEmail(email);
        admin.setPassword(password);

        isAdmin = adminDao.checkAdmin(admin);

        return isAdmin;
    }

    public void createBankAccount()
    {
        String username;
        String email;
        String password;
        String accountNumber;
        BigDecimal balance;
        String cardNumber;
        String pin;

        User newUser = new User();
        Account newAccount = new Account();
        PaymentMethod newPaymentMethod = new PaymentMethod();

        System.out.print("Enter details:\nUsername: ");
        username = scanner.nextLine();

        System.out.print("Email: ");
        email = scanner.nextLine();

        System.out.print("Password: ");
        password = scanner.nextLine();

        accountNumber = Utils.generateNumber(10);

        System.out.print("Initial Balance: ");
        balance = scanner.nextBigDecimal();

        cardNumber = Utils.generateNumber(16);

        System.out.print("Pin: ");
        scanner.nextLine();
        pin = scanner.nextLine();

        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAccount(newAccount);

        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType("savings");
        newAccount.setBalance(balance);
        newAccount.setPaymentMethod(newPaymentMethod);

        newPaymentMethod.setCardNumber(cardNumber);
        newPaymentMethod.setCardType("debit");
        newPaymentMethod.setPin(pin);

        if(accountDao.insertNewAccount(newUser) == 1)
            System.out.println("Success.");
        else
            System.out.println("Failed.");
    }

    public void viewTransactions()
    {
        ArrayList<Transaction> transactions = transactionDao.retrieveAllTransactions();

        for(Transaction t : transactions)
            System.out.println(t.toString());
    }

}
