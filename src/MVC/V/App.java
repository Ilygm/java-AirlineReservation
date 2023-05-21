package MVC.V;

import MVC.C.UserController;

public class App {
    private final Utils utils = new Utils();
    private final UserController userController = new UserController();

    public void mainMenu() {
        while (true) {
            utils.clearScreen();
            MainMenu.printMainMenu();
            System.out.printf("%65c>> ", ' ');
            switch (MainMenu.valueOf(utils.getInt())) {
                case SIGN_IN -> signIn();
                case SIGN_UP -> signUp();
                case EXIT -> {
                    return;
                }
                case null -> utils.printMissInputWarning();
            }
        }
    }

    private void signUp() {
    }

    public void signIn() {
        while (true) {
            utils.clearScreen();
            printSignIn();
            System.out.printf("%61c      >> ", ' ');
            switch (utils.getInt()) {
                case 1 -> getUserCredential(true);  // TODO: 5/19/2023
                case 2 -> getUserCredential(false); // TODO: 5/19/2023
                case 3 -> {
                    return;
                }
                default -> utils.printMissInputWarning();
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
                                                  
                %61c      2 - User  login
                                                  
                %61c      3 - Exit
                                                  
                """, ' ', ' ', ' ', AnsiColor.FOREGROUND_BLUE94, AnsiColor.RESET_COLOR, ' ', ' ', ' ', ' ', ' ');
    }

    private void getUserCredential(boolean isAdmin) {
        System.out.println();
        String tempUsername = utils.getString(63, "Input username: ", "", AnsiColor.FOREGROUND_BRIGHT_GREEN);
        String tempPassword = utils.getString(63, "Input password: ", "", AnsiColor.FOREGROUND_BRIGHT_GREEN);
        if (isAdmin) if (true) {
        } // TODO: 5/19/2023 ADMIN_PANEL
        else if (userController.checkUserCredentials(tempUsername, tempPassword)) {
        }// TODO: 5/19/2023 USER_PANEL
        else utils.printMessage(61, "!! Incorrect Credentials !!", AnsiColor.FOREGROUND_BRIGHT_RED);
    }
}