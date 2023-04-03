import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    public User makeNewUser(ArrayList<User> users) {
        Scanner scanner = new Scanner(System.in);
        App.clearScreen();
        System.out.print("""
                                 -----------------------------
                                 |                           |
                                 |      \033[1;37mNew user signup\033[0m      |
                                 |                           |
                                 -----------------------------
                                 
                                   [ \033[4;37mUsername Requirements\033[0m ]
                                                                  
                                 - At least 4 letters
                                                                  
                                 - It can't start with a number
                                                                  
                                 >> Enter your Username:""" + ' ');
        boolean isDuplicate = true;
        String tempUsername = scanner.next();
        while (isDuplicate) {
            isDuplicate = false;
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(tempUsername)) {
                    System.out.print("   !! Current username is used, try again !!\n\n>> Enter a new username: ");
                    tempUsername = scanner.next();
                    isDuplicate = true;
                    break;
                }
            }
        }
        while (!(tempUsername.matches("^[a-zA-Z]\\w*"))) {
            System.out.printf("    !! Username is not acceptable ( %s )!!\n>> Enter another username: ", ("\033[1;31m" + tempUsername + "\033[0m"));
            tempUsername = scanner.next();
        }
        System.out.print("""
                                 
                                   [ \033[4;37mPassword Requirements\033[0m ]
                                                                  
                                 - It should only contain ( a - z | A - Z | 0 - 9 | _ )
                                                                  
                                 >> Enter your password:""" + ' ');
        String tempPassword = scanner.next();
        while (!(tempUsername.matches("^\\w*"))) {
            System.out.printf("   !! Password is not acceptable ( %s ) !!\n>> Enter another password: ", ("\033[1;31m" + tempPassword + "\033[0m"));
            tempPassword = scanner.next();
        }
        System.out.println("\n    !! Your account has been successfully created !! ");
        App.rest();
        return (new User(tempUsername, tempPassword));
    }

    public void chargeAccount(User user) {
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
                                          
                                      >>""" + ' ', user.getUsername(), user.getBalance());
            int option = App.testInput();
            switch (option) {
                case 1 -> {
                    int charged;
                    System.out.printf("\n\n  How much would you like to charge? \n  [ Current balance: \033[0;32m%,d\033[0m ]\n\n>> ", user.getBalance());
                    charged = App.testInput();
                    while (charged <= 0) {
                        System.out.print("  !! Wrong input try again !!\n>> ");
                        App.rest();
                        charged = App.testInput();
                    }
                    user.addBalance(charged);
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
