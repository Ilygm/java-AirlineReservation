import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class User {
    private String username, password;
    private int balance = 0;
    private HashSet<String> flightIDs = new HashSet<>();

    public User(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public User(ArrayList<User> users) {
        makeNewUser(users);
        chargeAccount();
    }

    public void addFlight(String flightID) {
        flightIDs.add(flightID);
    }

    public void removeFlight(String flightID) {
        flightIDs.remove(flightID);
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
        System.out.printf("""
                                  %56c-----------------------------
                                  %56c|                           |
                                  %56c|      \033[1;36mNew user signup\033[0m      |
                                  %56c|                           |
                                  %56c-----------------------------
                                                                    
                                  %56c  [ \033[4;37mUsername Requirements\033[0m ]
                                                                    
                                  %56c- At least choose 4 letters.
                                                                    
                                  %56c- It can't start with a number.
                                                                    
                                  %56cEnter your Username >>""" + "\033[1;36m ", ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
        username = scanner.next();
        boolean isDuplicate = true;
        while (isDuplicate) {
            isDuplicate = false;
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    System.out.printf("%47c\033[0;31m    !! Current username is used, try again !!\033[0m\n\n%56cEnter a new username >> \033[1;36m ", ' ', ' ');
                    username = scanner.next();
                    isDuplicate = true;
                    break;
                }
            }
        }
        while (!(username.matches("^[a-zA-Z]\\w*$")) || username.length() < 4) {
            System.out.printf("%56c\033[0m    !! Username is not acceptable ( %s )!!\n%56cEnter another username >> \033[1;36m ", ' ', ("\033[0;31m" + username + "\033[0m"), ' ');
            username = scanner.next();
        }
        setPassword();
        System.out.printf("\n%45c\033[0m    \033[1;32m!! Your account has been successfully created !!\033[0m ", ' ');
        App.rest();
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
                                                                            
                                      %54c>>""" + ' ', ' ', ' ', ' ', username, ' ', ' ', balance, ' ', ' ', ' ', ' ', ' ', ' ');
            int option = App.getIntInput();
            switch (option) {
                case 1 -> {
                    int charged;
                    System.out.printf("\n\n%55cHow much would you like to charge? \n%60c[ Current balance: \033[0;32m%,d\033[0m ]\n\n%54c>> ", ' ', ' ', balance, ' ');
                    charged = App.getIntInput();
                    while (charged <= 0) {
                        System.out.printf("%68c!! Wrong input try again !!\n%54c>> ", ' ', ' ');
                        App.rest();
                        charged = App.getIntInput();
                    }
                    balance += charged;
                }
                case 0 -> notDone = false;
                default -> App.invalidInput();
            }
        }
    }

    public void setPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("""
                                  \033[0m
                                  %54c    [ \033[4;37mPassword Requirements\033[0m ]
                                                                   
                                  %54c  - It should only contain ( a - z | A - Z | 0 - 9 | _ ).
                                                                   
                                  %54c  - It should be longer than 4 charecters.
                                                                   
                                  %54c  Enter your password >>""" + "\033[0;36m ", ' ', ' ', ' ', ' ');
        password = scanner.next();
        while (!(username.matches("^\\w*$")) || password.length() < 5) {
            System.out.printf("%54c\033[0m   !! Password is not acceptable ( %s ) !!\n%54cEnter another password >> \033[0;36m ", ' ', ("\033[1;31m" + password + "\033[0m"), ' ');
            password = scanner.next();
        }
        System.out.print("\033[0m");
    }
}
