import java.util.Scanner;

public class UserPanel {

    private final Scanner scanner = new Scanner(System.in);
    private final User user;

    public UserPanel(User user) {
        this.user = user;

        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printUserPanel();
            option = App.getIntInput();
            switch (option) {
                case 5 -> chargeAccount();
                case 6 -> passwordChanger(user);
                case 0 -> {/* LOL */}
                default -> App.printInvalidInput();
            }
        }
    }

    private void printUserPanel() {
        System.out.printf("""
                                  %50c-------------------------------------------------
                                  %50c|                                               |
                                  %50c|             Welcome :  \033[1;34m%-20s\033[0m   |
                                  %50c|                                               |
                                  %50c|     Your balance is :  \033[0;32m%,-15d$\033[0m       |
                                  %50c|                                               |
                                  %50c-------------------------------------------------
                                                  
                                  %50c         1 - Search flight ticket
                                                  
                                  %50c         2 - Ticket booking
                                                  
                                  %50c         3 - Ticket cancelation
                                                      
                                  %50c         4 - Tickets bought
                                                      
                                  %50c         5 - Add charge
                                                      
                                  %50c         6 - Change password
                                                      
                                  %50c         0 - Sign out
                                  %50c>>""" + ' ', ' ', ' ', ' ', user.getUsername(), ' ', ' ', user.getBalance(), ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    public void chargeAccount() {
        boolean notDone = true;
        while (notDone) {
            App.clearScreen();
            System.out.printf("""
                                      %54c----------------------------------------
                                      %54c|                                      |
                                      %54c|       Welcome    :  \033[1;34m%-10s\033[0m       |
                                      %54c|                                      |
                                      %54c|  Your balance is :  \033[0;32m%,-7d$\033[0m         |
                                      %54c|                                      |
                                      %54c----------------------------------------
                                                                            
                                      %51cWould you like to add balance to your account?
                                                                            
                                      %67c1 - Sure
                                                                            
                                      %67c0 - I will do it later
                                                                            
                                      %54c>>""" + ' ', ' ', ' ', ' ', user.getUsername(), ' ', ' ', user.getBalance(), ' ', ' ', ' ', ' ', ' ', ' ');
            int option = App.getIntInput();
            switch (option) {
                case 1 -> {
                    int charged;
                    System.out.printf("\n\n%55cHow much would you like to charge? \n%60c[ Current balance: \033[0;32m%,d\033[0m ]\n\n%54c>> ", ' ', ' ', user.getBalance(), ' ');
                    charged = App.getIntInput();
                    while (charged <= 0) {
                        System.out.printf("%68c!! Wrong input try again !!\n%54c>> ", ' ', ' ');
                        App.rest();
                        charged = App.getIntInput();
                    }
                    user.addBalance(charged);
                }
                case 0 -> notDone = false;
                default -> App.printInvalidInput();
            }
        }
    }

    private void passwordChanger(User user) {
        System.out.printf("\n%56cYou're about to change your password\n%56cEnter your current password: ", ' ', ' ');
        if (scanner.next().equals(user.getPassword())) {
            System.out.printf("\n%56cEnter your new password: ", ' ');
            user.setPassword(scanner.next());
        } else System.out.printf("%56c    \033[0;31m!! Access denied !!\033[0m\n\n", ' ');
    }
}
