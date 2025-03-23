package controller;

import dao.UserDao;
import model.User;

import java.util.Scanner;

public class UserController
{
    private Scanner scanner = new Scanner(System.in);
    private UserDao userDao = new UserDao();

    public User bankLogin()
    {
        String email;
        String password;

        System.out.print(":: Bank Login ::\nEnter email: ");
        email = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();

        return userDao.retrieveUserByEmail(email, password);
    }

    public User atmLogin()
    {
        String cardNumber;
        String pin;

        System.out.print(":: ATM Login ::\nEnter card number: ");
        cardNumber = scanner.nextLine();

        System.out.print("Enter pin: ");
        pin = scanner.nextLine();

        return userDao.retrieveUserByCardNumber(cardNumber, pin);
    }
}
