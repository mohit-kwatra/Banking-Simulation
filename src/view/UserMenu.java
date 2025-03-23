package view;

import controller.UserController;
import model.User;

import java.util.Scanner;

public class UserMenu
{
    public void displayMenu()
    {
        int choice;
        User user;
        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();

        BankMenu bankMenu = new BankMenu();
        ATMMenu atmMenu = new ATMMenu();


        System.out.print("Available options:\n1. Bank\n2. ATM\nEnter option: ");
        choice = scanner.nextInt();

        if(choice == 1)
        {
            user = userController.bankLogin();
            if(user != null)
                bankMenu.displayMenu(user);
            else
                System.out.println("Invalid credentials.");
        }
        else if(choice == 2)
        {
            user = userController.atmLogin();
            if(user != null)
                atmMenu.displayMenu(user);
            else
                System.out.println("Invalid credentials.");
        }
        else
            System.out.println("Invalid option. Please try again.");
    }
}
