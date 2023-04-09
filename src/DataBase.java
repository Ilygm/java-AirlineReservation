import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    public ArrayList<Flight> flights = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<User> admins = new ArrayList<>();

    public void signInPage() {
        int option = -1;
        while (option != 0) {
            App.clearScreen();
            System.out.printf("""
                                      %56c-------------------------
                                      %56c|                       |
                                      %56c|        \033[1;36mSign in\033[0m        |
                                      %56c|                       |
                                      %56c-------------------------
                                      %56c
                                      %56c   1 - Login as Admin
                                      %56c
                                      %56c   2 - Login as User
                                      %56c
                                      %56c   0 - Main menu
                                      %56c
                                      %56c >>""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
            option = App.getIntInput();
            switch (option) {
                case 1 -> searchList(admins);
                case 2 -> searchList(users);
                case 0 -> {
                }
                default -> App.invalidInput();
            }
        }
    }

    private void searchList(ArrayList<User> list) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n%56cEnter your username:\033[1;36m ", ' ');
        String username = scanner.next();
        System.out.printf("\n%56c\033[0mEnter your password:\033[0;36m ", ' ');
        String password = scanner.next();
        System.out.println("\033[0m");

        if (username.matches("^[a-zA-Z]\\w*$") && username.length() > 4) {
            if (password.length() >= 4) {
                for (User user : list) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        System.out.printf("%60c Welcome %s !!", ' ', username);
                        App.rest();
                        userPanel(user);
                        return;
                    }
                }
            }
        }
        System.out.printf("%40cEither your username (\033[0;31m%s\033[0m) or password (\033[0;31m%s\033[0m) is wrong!", ' ', username, password);
        App.rest();
    }

    private void userPanel(User user) {
        Scanner scanner = new Scanner(System.in);
        App.clearScreen();
        int option = -1;
        while (option != 0) {
            printUserPanel(user.getUsername(), user.getBalance());
            option = App.getIntInput();
            switch (option) {
                case 5 -> user.chargeAccount();
                case 6 -> {
                    System.out.printf("\n%56cYou're about to change your password\n%56cEnter your current password: ", ' ', ' ');
                    String tempPass = scanner.next();
                    if (tempPass.equals(user.getPassword())) user.setPassword();
                    else System.out.printf("%56c    \033[0;31m!! Access denied !!\033[0m\n\n", ' ');
                }
                case 0 -> {
                }
                default -> App.invalidInput();
            }
            App.clearScreen();
        }
    }

    private void printUserPanel(String user, int balance) {
        System.out.printf("""
                                  %50c-------------------------------------------------
                                  %50c|                                               |
                                  %50c|         Welcome    :  \033[1;34m%-20s\033[0m    |
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
                                  %50c>>""" + ' ', ' ', ' ', ' ', user, ' ', ' ', balance, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }
}
