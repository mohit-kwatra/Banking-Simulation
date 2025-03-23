package view;

import controller.AdminController;

import java.util.Scanner;

public class MainMenu
{
    public void displayMenu()
    {
        int choice;

        Scanner scanner = new Scanner(System.in);
        AdminController adminController = new AdminController();
        AdminMenu adminMenu = new AdminMenu();
        UserMenu userMenu = new UserMenu();

        System.out.print("Available options:\n1. Admin\n2. User\nEnter option: ");
        choice = scanner.nextInt();

        if(choice == 1)
        {
            if(adminController.login() == 1)
                adminMenu.displayMenu();
            else
                System.out.println("Invalid username or password");
        }
        else if(choice == 2)
            userMenu.displayMenu();
        else
            System.out.println("Invalid option. Please try again.");
    }
}
