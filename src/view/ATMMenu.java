package view;

import controller.ATMController;
import model.User;

import java.util.Scanner;

public class ATMMenu
{
    public void displayMenu(User currentUser)
    {
        int choice;
        Scanner scanner = new Scanner(System.in);
        ATMController atmController = new ATMController();

        System.out.print("Available options:\n1. Cash Deposit\n2. Cash Withdrawal\n3. Balance Inquiry\n4. Mini Statement\nEnter option: ");
        choice = scanner.nextInt();

        if(choice == 1)
            atmController.depositCash(currentUser);
        else if(choice == 2)
            atmController.withdrawCash(currentUser);
        else if(choice == 3)
            atmController.balanceInquiry(currentUser);
        else if(choice == 4)
            atmController.displayMiniStatement(currentUser);
        else
            System.out.println("Invalid option.");
    }
}
