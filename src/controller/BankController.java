package controller;

import dao.AccountDao;
import dao.ChequeDao;
import dao.PaymentMethodDao;
import dao.TransactionDao;
import model.Cheque;
import model.PaymentMethod;
import model.Transaction;
import model.User;
import util.Utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class BankController
{
    private TransactionDao transactionDao = new TransactionDao();
    private PaymentMethodDao paymentMethodDao = new PaymentMethodDao();
    private AccountDao accountDao = new AccountDao();
    private ChequeDao chequeDao = new ChequeDao();
    private Scanner scanner = new Scanner(System.in);

    public void viewTransactions(User currentUser)
    {
        ArrayList<Transaction> transactions = transactionDao.retrieveAccountTransactions(currentUser.getAccount(), -1);

        for(Transaction t : transactions)
        {
            System.out.println(t.toString());
        }
    }

    public void applyForCreditCard(User currentUser)
    {
        int hasCreditCard;
        int isCardAdded;
        String cardNumber;
        String pin;
        PaymentMethod cardInfo;

        hasCreditCard = paymentMethodDao.hasCreditCard(currentUser.getAccount());

        if(hasCreditCard == 0)
        {
            System.out.print("Enter details:\nPin(4-Digit): ");
            pin = scanner.nextLine();

            cardNumber = Utils.generateNumber(16);

            cardInfo = new PaymentMethod();
            cardInfo.setCardType("credit");
            cardInfo.setCardNumber(cardNumber);
            cardInfo.setPin(pin);

            isCardAdded = paymentMethodDao.addCreditCard(cardInfo, currentUser.getAccount());

            if(isCardAdded == 1)
                System.out.println("Success.");
            else
                System.out.println("Failed.");
        }
        else
            System.out.println("Credit card already exists.");
    }

    public void issueCheque(User currentUser)
    {
        String payeeName;
        BigDecimal amount;
        String date;
        Date sqlDate;

        BigDecimal accountBalance;
        int isBalanceUpdated;
        int isChequeIssued;

        Transaction transaction;
        Cheque cheque = new Cheque();


        System.out.print("Enter details:\nPayee Name: ");
        payeeName = scanner.nextLine();

        System.out.print("Amount: ");
        amount = scanner.nextBigDecimal();

        System.out.print("Date: ");
        scanner.nextLine();
        date = scanner.nextLine();
        sqlDate = Utils.getSqlDate(date);

        cheque.setAccountId(currentUser.getAccount().getAccountId());
        cheque.setPayeeName(payeeName);
        cheque.setAmount(amount);
        cheque.setIssuedOn(sqlDate);

        accountBalance = accountDao.retrieveAccountBalance(currentUser.getAccount());

        if(amount.compareTo(accountBalance) > 0)
        {
            System.out.println("Insufficient funds.");
        }
        else
        {
            isBalanceUpdated = accountDao.updateAccountBalance(currentUser.getAccount(), accountBalance.subtract(amount));

            if(isBalanceUpdated == 1)
            {
                isChequeIssued = chequeDao.issueCheque(cheque);

                if(isChequeIssued == 1)
                {
                    transaction = new Transaction();
                    transaction.setAccountId(currentUser.getAccount().getAccountId());
                    transaction.setTransactionType("debit");
                    transaction.setPaymentMethodId(currentUser.getAccount().getPaymentMethod().getMethodId());
                    transaction.setAmount(amount);
                    transaction.setStatus("finished");

                    transactionDao.createTransaction(transaction);
                    System.out.println("Success.");
                }
                else
                    System.out.println("Failed.");
            }
            else
                System.out.println("Failed.");
        }
    }

    public void viewIssuedCheques(User currentUser)
    {
        ArrayList<Cheque> issuedCheques = chequeDao.retrieveIssuedCheques(currentUser.getAccount());

        for(Cheque c : issuedCheques)
        {
            System.out.println(c.toString());
        }
    }
}
