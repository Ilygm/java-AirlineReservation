import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String username, password;
    private int balance = 0;

    public User(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public User(ArrayList<User> users) {
        makeNewUser(users);
        chargeAccount();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    private void makeNewUser(ArrayList<User> users) {
        Scanner scanner = new Scanner(System.in);
        App.clearScreen();
        System.out.print("""
                                 -----------------------------
                                 |                           |
                                 |      \033[1;36mNew user signup\033[0m      |
                                 |                           |
                                 -----------------------------
                                                                  
                                   [ \033[4;37mUsername Requirements\033[0m ]
                                                                  
                                 - At least choose 4 letters.
                                                                  
                                 - It can't start with a number.
                                                                  
                                 >> Enter your Username:""" + "\033[1;36m ");
        username = scanner.next();
        boolean isDuplicate = true;
        while (isDuplicate) {
            isDuplicate = false;
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    System.out.print("\033[0m   !! Current username is used, try again !!\n\n>> Enter a new username:\033[1;36m ");
                    username = scanner.next();
                    isDuplicate = true;
                    break;
                }
            }
        }
        while (!(username.matches("^[a-zA-Z]\\w*$")) || username.length() < 5) {
            System.out.printf("\033[0m    !! Username is not acceptable ( %s )!!\n>> Enter another username:\033[1;36m ", ("\033[0;31m" + username + "\033[0m"));
            username = scanner.next();
        }
        System.out.print("""
                                 \033[0m
                                   [ \033[4;37mPassword Requirements\033[0m ]
                                                                  
                                 - It should only contain ( a - z | A - Z | 0 - 9 | _ ).
                                                                  
                                 - It should be longer than 4 charecters.
                                                                  
                                 >> Enter your password:""" + "\033[0;36m ");
        password = scanner.next();
        while (!(username.matches("^\\w*$")) || password.length() < 5) {
            System.out.printf("\033[0m   !! Password is not acceptable ( %s ) !!\n>> Enter another password:\033[0;36m ", ("\033[1;31m" + password + "\033[0m"));
            password = scanner.next();
        }
        System.out.println("\n\033[0m    !! Your account has been successfully created !! ");
        App.rest();
    }

    private void chargeAccount() {
        boolean notDone = true;
        while (notDone) {
            App.clearScreen();
            System.out.printf("""
                                      -------------------------------------------------
                                      |                                               |
                                      |       Welcome    :  \033[1;34m%-20s\033[0m      |
                                      |                                               |
                                      |  Your balance is :  \033[0;32m%,-7d$\033[0m                  |
                                      |                                               |
                                      -------------------------------------------------
                                                       
                                        Would you like to add balance to your account?
                                        
                                        1 - Sure
                                        
                                        2 - I will do it later
                                          
                                      >>""" + ' ', username, balance);
            int option = App.testInput();
            switch (option) {
                case 1 -> {
                    int charged;
                    System.out.printf("\n\n  How much would you like to charge? \n  [ Current balance: \033[0;32m%,d\033[0m ]\n\n>> ", balance);
                    charged = App.testInput();
                    while (charged <= 0) {
                        System.out.print("  !! Wrong input try again !!\n>> ");
                        App.rest();
                        charged = App.testInput();
                    }
                    balance += charged;
                }
                case 2 -> notDone = false;
                default -> {
                    System.out.print("  !! Wrong input try again !! ");
                    App.rest();
                }
            }
        }
    }
}
