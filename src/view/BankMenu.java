package view;

import controller.BankController;
import model.User;

import java.util.Scanner;

public class BankMenu
{
    public void displayMenu(User currentUser)
    {
        int choice;
        Scanner scanner = new Scanner(System.in);
        BankController bankController = new BankController();

        System.out.print("Available options:\n1. View transactions\n2. Issue a cheque\n3. Apply for credit card(experimental)\n4. View issued cheques\nEnter option: ");
        choice = scanner.nextInt();

        if(choice == 1)
            bankController.viewTransactions(currentUser);
        else if(choice == 2)
            bankController.issueCheque(currentUser);
        else if(choice == 3)
            bankController.applyForCreditCard(currentUser);
        else if(choice == 4)
            bankController.viewIssuedCheques(currentUser);
        else
            System.out.println("Invalid option.");
    }
}
