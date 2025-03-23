package view;

import controller.AdminController;

import java.util.Scanner;

public class AdminMenu
{
    public void displayMenu()
    {
        int choice;

        Scanner scanner = new Scanner(System.in);
        AdminController adminController = new AdminController();

        System.out.print("Available options:\n1. Create a Bank Account\n2. View Transactions\nEnter option: ");
        choice = scanner.nextInt();

        if(choice == 1)
            adminController.createBankAccount();
        else if(choice == 2)
            adminController.viewTransactions();
        else
            System.out.println("Invalid option. Please try again.");
    }
}
