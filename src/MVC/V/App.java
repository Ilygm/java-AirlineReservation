package MVC.V;

import MVC.C.*;

enum MainMenu {
    SIGN_IN("Sign in"),
    SIGN_UP("Sign up"),
    EXIT("Exit");

    private final String line;

    MainMenu(String line) {
        this.line = line;
    }

    public static MainMenu valueOf(int idx) {
        try {
            if (idx == 0)
                return MainMenu.EXIT;
            return MainMenu.values()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static void printMainMenu() {
        System.out
                .print("""

                               d8888 d8b         888 d8b                       8888888b.                                                       888    d8b                  \s
                              d88888 Y8P         888 Y8P                       888   Y88b                                                      888    Y8P                  \s
                             d88P888             888                           888    888                                                      888                         \s
                            d88P 888 888 888d888 888 888 88888b.   .d88b.      888   d88P  .d88b.  .d8888b   .d88b.  888d888 888  888  8888b.  888888 888  .d88b.  88888b. \s
                           d88P  888 888 888P"   888 888 888 "88b d8P  Y8b     8888888P"  d8P  Y8b 88K      d8P  Y8b 888P"   888  888     "88b 888    888 d88""88b 888 "88b\s
                          d88P   888 888 888     888 888 888  888 88888888     888 T88b   88888888 "Y8888b. 88888888 888     Y88  88P .d888888 888    888 888  888 888  888\s
                         d8888888888 888 888     888 888 888  888 Y8b.         888  T88b  Y8b.          X88 Y8b.     888      Y8bd8P  888  888 Y88b.  888 Y88..88P 888  888\s
                        d88P     888 888 888     888 888 888  888  "Y8888      888   T88b  "Y8888   88888P'  "Y8888  888       Y88P   "Y888888  "Y888 888  "Y88P"  888  888\s

                                                                              ---------------------------------------
                                                                              |   _______  ______ __   __ _     _   |
                                                                              |   |  |  | |______ | \\\\  | |     |   |
                                                                              |   |  |  | |______ |  \\\\_| |_____|   |
                                                                              |                                     |
                                                                              ---------------------------------------

                        """);

        for (int i = 0; i < MainMenu.values().length - 1; i++)
            System.out.printf("%68c%d - %s\n\n", ' ', i + 1, MainMenu.values()[i].line);
        System.out.printf("%68c0 - Exit\n\n", ' ');
    }

    @Override
    public String toString() {
        return line;
    }
}

public class App {
    private final UserController userController = new UserController();
    private final AdminController adminController = new AdminController();
    private final FlightController flightController = new FlightController();
    private final TicketController ticketController = new TicketController();
    private final Root root = new Root(flightController, userController, ticketController);
    public void mainMenu() {
        while (true) {
            Utils.clearScreen();
            MainMenu.printMainMenu();
            System.out.printf("%65c>> ", ' ');
            switch (MainMenu.valueOf(Utils.getInt())) {
                case SIGN_IN -> signIn();
                case SIGN_UP -> signUp();
                case EXIT -> {
                    return;
                }
                case null, default -> Utils.printMissInputWarning();
            }
        }
    }

    private void signUp() {
        // TODO: 5/26/2023
    }

    public void signIn() {
        while (true) {
            Utils.clearScreen();
            printSignIn();
            System.out.printf("%61c      >> ", ' ');
            switch (Utils.getInt()) {
                case 1 -> getUserCredential(true); // TODO: 5/19/2023
                case 2 -> getUserCredential(false); // TODO: 5/19/2023
                case 0 -> { return; }
                default -> Utils.printMissInputWarning();
            }
        }
    }

    private void printSignIn() {
        System.out.printf("""
                %61c-------------------------
                %61c|                       |
                %61c|        %sSign-In%s        |
                %61c|                       |
                %61c-------------------------

                %61c      1 - Admin login

                %61c      2 - User login

                %61c      0 - Exit

                """, ' ', ' ', ' ', AnsiColor.FOREGROUND_BLUE94, AnsiColor.RESET_COLOR, ' ', ' ', ' ', ' ', ' ');
    }

    private void getUserCredential(boolean isAdmin) {
        System.out.println();
        String tempUsername = Utils.getString(63, "Input username: ", "", AnsiColor.FOREGROUND_BRIGHT_GREEN);
        String tempPassword = Utils.getString(63, "Input password: ", "", AnsiColor.FOREGROUND_BRIGHT_GREEN);
        if (isAdmin) {
            if (adminController.checkAdminPassword(tempUsername, tempPassword)) {
                new AdminPanel(tempUsername, flightController, root);
                return;
            }
        } else {
            if (userController.checkUserCredentials(tempUsername, tempPassword)) {
                new UserPanel(userController, flightController, ticketController, root, tempUsername, tempPassword);
                return;
            }
        }
        System.out.println();
        Utils.printMessage(57, "!!  Incorrect Credentials  !!", AnsiColor.FOREGROUND_BRIGHT_RED, 1500);
    }
}