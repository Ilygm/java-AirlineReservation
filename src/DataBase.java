import java.util.Scanner;

public class DataBase {
    public final Users users = new Users();
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights = new Flights();

    {
        flights.flightsDB.put("FA-17", new Flight("FA-17", "Yazd", "Shiraz", "1401-12-10", "12:30", 500, 100_000));
        flights.flightsDB.put("TA-55", new Flight("TA-55", "Yazd", "Isfahan", "1401-10-10", "23:59", 600, 100_000));
        flights.flightsDB.put("GG-55", new Flight("GG-55", "Yazd", "Tehran", "1401-12-10", "23:59", 600, 200_000));
        flights.flightsDB.put("SS-55", new Flight("SS-55", "Yazd", "Mashhad", "1402-12-10", "20:00", 600, 500_000));
        flights.flightsDB.put("AA-55", new Flight("AA-55", "Yazd", "Kerman", "1400-05-20", "02:00", 600, 10_000));
    }

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
                case 1 -> new AdminPanel(flights);
                case 2 -> searchListMenu();
                case 0 -> {/* Nothing to see here */}
                default -> App.printInvalidInput();
            }
        }
    }

    public void searchListMenu() {
        System.out.printf("\n%56cEnter your username:\033[1;36m ", ' ');
        String username = scanner.next();
        System.out.printf("\n%56c\033[0mEnter your password:\033[0;36m ", ' ');
        String password = scanner.next();
        System.out.println("\033[0m");
        username = username.toLowerCase();
        if (users.isUserPassCorrect(username, password)) {
            new UserPanel(users.usersDB.get(username));
        } else {
            System.out.printf("%40cEither your username (\033[0;31m%s\033[0m) or password (\033[0;31m%s\033[0m) is wrong!", ' ', username, password);
            App.rest();
        }
    }

    public void makeNewUser() {
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
        users.addUser(usernameInput(), passwordInput());
        System.out.printf("\n%45c\033[0m    \033[1;32m!! Your account has been successfully created !!\033[0m ", ' ');
        App.rest();
    }

    private String usernameInput() {
        String tempUsername = scanner.next();
        while (users.usersDB.containsKey(tempUsername)) {
            System.out.printf("%47c\033[0;31m    !! Current username is used, try again !!\033[0m\n\n%56cEnter a new username >> \033[1;36m ", ' ', ' ');
            tempUsername = scanner.next();
        }
        while (!(tempUsername.matches("^[a-zA-Z]\\w*$")) || tempUsername.length() < 4) {
            System.out.printf("%56c\033[0m    !! Username is not acceptable ( %s )!!\n%56cEnter another username >> \033[1;36m ", ' ', ("\033[0;31m" + tempUsername + "\033[0m"), ' ');
            tempUsername = scanner.next();
        }
        return tempUsername;
    }

    private String passwordInput() {
        String tempPass;
        System.out.printf("""
                                  \033[0m
                                  %54c    [ \033[4;37mPassword Requirements\033[0m ]
                                                                   
                                  %54c  - It should only contain ( a - z | A - Z | 0 - 9 | _ ).
                                                                   
                                  %54c  - It should be longer than 4 charecters.
                                                                   
                                  %54c  Enter your password >>""" + "\033[0;36m ", ' ', ' ', ' ', ' ');
        tempPass = scanner.next();
        while (!(tempPass.matches("^\\w*$")) || tempPass.length() < 5) {
            System.out.printf("%54c\033[0m   !! Password is not acceptable ( %s ) !!\n%54cEnter another password >> \033[0;36m ", ' ', ("\033[1;31m" + tempPass + "\033[0m"), ' ');
            tempPass = scanner.next();
        }
        System.out.print("\033[0m");
        return tempPass;
    }
}
