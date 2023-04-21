import java.util.Scanner;

public class DataBase {
    public final Users users = new Users();
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights = new Flights();

    {
        flights.flightsDB.put("FA-17", new Flight("FA-17", "Yazd", "Shiraz", "1401-12-10", 1230, 0, 100_000));
        flights.flightsDB.put("TA-55", new Flight("TA-55", "Tehran", "Isfahan", "1401-10-10", 2359, 600, 100_000));
        flights.flightsDB.put("GG-55", new Flight("GG-55", "Yazd", "Tehran", "1401-12-10", 2359, 600, 200_000));
        flights.flightsDB.put("SS-55", new Flight("SS-55", "Mashhad", "Kerman", "1402-12-10", 2000, 600, 500_000));
        flights.flightsDB.put("AA-55", new Flight("AA-55", "Yazd", "Kerman", "1400-05-20", 200, 600, 10_000));
        flights.flightsDB.put("AA-46", new Flight("AA-46", "Isfahan", "Tehran", "1400-06-20", 500, 600, 100_000));
        flights.flightsDB.put("AA-14", new Flight("AA-14", "Germany", "Iran", "1402-10-20", 900, 0, 100_000));
        flights.flightsDB.put("AA-41", new Flight("AA-41", "Iran", "Germany", "1402-10-20", 300, 1, 100_000));
        users.addUser("test", "test");
        users.usersDB.get("test").changeBalance(200_000_000);
    }

    public void signInPage() {
        int option = -1;
        while (option != 0) {
            App.clearScreen();
            System.out.printf("""
                                      %56c-------------------------
                                      %56c|                       |
                                      %56c|       %s Sign in%s        |
                                      %56c|                       |
                                      %56c-------------------------
                                      %56c
                                      %56c   1 - Login as Admin
                                      %56c
                                      %56c   2 - Login as User
                                      %56c
                                      %56c   0 - Main menu
                                      %56c
                                      %56c >>""" + ' ', ' ', ' ', ' ', CColors.CYAN, CColors.RESET, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
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
        System.out.printf("\n%56cEnter your username:%s ", ' ', CColors.CYAN);
        String username = scanner.next();
        System.out.printf("\n%56c%sEnter your password:%s ", ' ', CColors.RESET, CColors.CYAN);
        String password = scanner.next();
        System.out.println(CColors.RESET);
        username = username.toLowerCase();
        if (users.isUserPassCorrect(username, password)) {
            new UserPanel(users.usersDB.get(username), flights);
        } else {
            System.out.printf("%40c!! Either your username ( %s ) or password ( %s ) is wrong !!", ' ', CColors.RED + username + CColors.RESET, CColors.RED + password + CColors.RESET);
            App.rest();
        }
    }

    public void makeNewUser() {
        App.clearScreen();
        System.out.printf("""
                                  %56c-----------------------------
                                  %56c|                           |
                                  %56c|      %sNew user signup%s      |
                                  %56c|                           |
                                  %56c-----------------------------
                                                                    
                                  %56c  [ %sUsername Requirements%s ]
                                                                    
                                  %56c- At least choose 4 letters.
                                                                    
                                  %56c- It can't start with a number.
                                                                    
                                  %56cEnter your Username >>%s""" + ' ', ' ', ' ', ' ', CColors.CYAN, CColors.RESET, ' ', ' ', ' ', CColors.WHITE, CColors.RESET, ' ', ' ', ' ', CColors.CYAN);
        users.addUser(usernameInput(), passwordInput());
        System.out.printf("\n%45c    %s!! Your account has been successfully created !!%s ", ' ', CColors.GREEN, CColors.RESET);
        App.rest();
    }

    private String usernameInput() {
        String tempUsername = scanner.next();
        while (users.usersDB.containsKey(tempUsername)) {
            System.out.printf("%47c%s    !! Current username is used, try again !!%s\n\n%56cEnter a new username >> %s ", ' ', CColors.RED, CColors.RESET, ' ', CColors.CYAN);
            tempUsername = scanner.next();
        }
        while (!(tempUsername.matches("^[a-zA-Z]\\w*$")) || tempUsername.length() < 4) {
            System.out.printf("%56c%s    !! Username is not acceptable ( %s )!!\n%56cEnter another username >> %s ", ' ', CColors.RESET, (CColors.RED + tempUsername + CColors.RESET), ' ', CColors.CYAN);
            tempUsername = scanner.next();
        }
        return tempUsername;
    }

    private String passwordInput() {
        String tempPass;
        System.out.printf("""
                                  %s
                                  %54c    [ %sPassword Requirements%s ]
                                                                   
                                  %54c  - It should only contain ( a - z | A - Z | 0 - 9 | _ ).
                                                                   
                                  %54c  - It should be longer than 4 characters.
                                                                   
                                  %54c  Enter your password >>""" + CColors.CYAN + ' ', CColors.RESET, ' ', CColors.WHITE, CColors.RESET, ' ', ' ', ' ');
        tempPass = scanner.next();
        while (!(tempPass.matches("^\\w*$")) || tempPass.length() < 5) {
            System.out.printf("%54c%s   !! Password is not acceptable ( %s ) !!\n%54cEnter another password >> %s ", ' ', CColors.RESET, (CColors.RED + tempPass + CColors.RESET), ' ', CColors.CYAN);
            tempPass = scanner.next();
        }
        System.out.print(CColors.RESET);
        return tempPass;
    }
}
