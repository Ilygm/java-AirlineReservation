import java.io.*;
import java.util.Scanner;

public class DataBase {
    // Initialization of database
    public final Users users = new Users();
    private final Tickets tickets = new Tickets();
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights = new Flights();

    private final File dataFile = new File("Data.dat");

    /**
     * Menu for the Sign-In page
     */
    private void printSignInMenu() {
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
                                  %56c >>""" + ' ', ' ', ' ', ' ', CColors.CYAN, CColors.RESET, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
                ' ', ' ');
    }

    /**
     * Sign in page
     */
    public void signInPage() {
        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printSignInMenu();
            option = App.getIntInput();
            switch (option) {
                case 1 -> {
                    System.out.printf("\n%56cInput the admin password: %s", ' ', CColors.CYAN);
                    if (scanner.next().equals("Admin")) {
                        System.out.print(CColors.RESET);
                        new AdminPanel(flights, tickets);
                    } else {
                        System.out.printf("%60c%s!! Your password is incorrect !!%s", ' ', CColors.RED, CColors.RESET);
                        App.rest();
                    }
                }
                case 2 -> searchListMenu();
                case 0 -> {
                    /* Nothing to see here */
                }
                default -> App.printInvalidInput();
            }
        }
    }

    /**
     * Checks for the correct username and password
     * Lastly it moves to the user's panel
     */
    public void searchListMenu() {
        System.out.printf("\n%56cEnter your username:%s ", ' ', CColors.CYAN);
        String username = scanner.next();
        System.out.printf("\n%56c%sEnter your password:%s ", ' ', CColors.RESET, CColors.CYAN);
        String password = scanner.next();
        System.out.println(CColors.RESET);
        username = username.toLowerCase();
        if (users.isUserPassCorrect(username, password)) {
            new UserPanel(users.usersDB.get(username), flights, tickets);
        } else {
            System.out.printf("%40c!! Either your username ( %s ) or password ( %s ) is incorrect !!", ' ',
                    CColors.RED + username + CColors.RESET, CColors.RED + password + CColors.RESET);
            App.rest();
        }
    }

    /**
     * Makes a new user entry
     */
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

                                  %56cEnter your Username >>%s""" + ' ', ' ', ' ', ' ', CColors.CYAN, CColors.RESET, ' ', ' ', ' ',
                CColors.WHITE, CColors.RESET, ' ', ' ', ' ', CColors.CYAN);
        users.addUser(usernameInput(), passwordInput());
        System.out.printf("\n%45c    %s!! Your account has been successfully created !!%s ", ' ', CColors.GREEN,
                CColors.RESET);
        App.rest();
    }

    /**
     * Makes sure the username is in correct format
     *
     * @return Corrected Username
     */
    private String usernameInput() {
        String tempUsername = scanner.next();
        while (users.usersDB.containsKey(tempUsername)) {
            System.out.printf(
                    "%47c%s    !! Current username is used, try again !!%s\n\n%56cEnter a new username >> %s ", ' ',
                    CColors.RED, CColors.RESET, ' ', CColors.CYAN);
            tempUsername = scanner.next();
        }
        while (!(tempUsername.matches("^[a-zA-Z]\\w*$")) || tempUsername.length() < 4) {
            System.out.printf("%56c%s    !! Username is not acceptable ( %s )!!\n%56cEnter another username >> %s ",
                    ' ', CColors.RESET, (CColors.RED + tempUsername + CColors.RESET), ' ', CColors.CYAN);
            tempUsername = scanner.next();
        }
        return tempUsername;
    }

    /**
     * Makes sure the password is in correct format
     *
     * @return Corrected password
     */
    private String passwordInput() {
        String tempPass;
        System.out.printf("""
                                  %s
                                  %54c    [ %sPassword Requirements%s ]

                                  %54c  - It should only contain ( a - z | A - Z | 0 - 9 | _ ).

                                  %54c  - It should be longer than 4 characters.

                                  %54c  Enter your password >>""" + CColors.CYAN + ' ', CColors.RESET, ' ', CColors.WHITE, CColors.RESET,
                ' ', ' ', ' ');
        tempPass = scanner.next();
        while (!(tempPass.matches("^\\w*$")) || tempPass.length() < 5) {
            System.out.printf("%54c%s   !! Password is not acceptable ( %s ) !!\n%54cEnter another password >> %s ",
                    ' ', CColors.RESET, (CColors.RED + tempPass + CColors.RESET), ' ', CColors.CYAN);
            tempPass = scanner.next();
        }
        System.out.print(CColors.RESET);
        return tempPass;
    }

    public void readSerializedData() throws IOException {
        if (!dataFile.exists()) {
            if (!dataFile.createNewFile()) {
                System.err.print(" FAILED TO MAKE DATA FILE ");
                System.exit(80);
            }
        } else {
            ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(dataFile));
            try {
                while (true) {
                    Object temp = iStream.readObject();
                    switch (temp.getClass().getSimpleName()) {
                        case "Flight" -> flights.addFlight((Flight) temp);
                        case "User" -> users.addUser((User) temp);
                        case "Ticket" -> tickets.ticketsDB.add((Ticket) temp);
                    }
                }
            } catch (ClassNotFoundException err) {
                System.out.print(err.getMessage());
                System.exit(80);
            } catch (EOFException err) {/* NOTHING */}
            iStream.close();
        }
    }

    public void writeSerializedData() throws IOException {
        ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(dataFile));
        try {
            for (User user : users.usersDB.values()) {
                oStream.writeObject(user);
            }
            for (Flight flight : flights.flightsDB.values()) {
                oStream.writeObject(flight);
            }
            for (Ticket ticket : tickets.ticketsDB) {
                oStream.writeObject(ticket);
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            System.exit(80);
        }
        oStream.close();
    }
}
