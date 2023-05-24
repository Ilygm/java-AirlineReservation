package MVC.V;

import MVC.C.FlightController;
import MVC.C.UserController;

enum UserPanelMenu {
    TICKET_LIST("Ticket searching and booking"),
    OWNED_TICKETS("Owned tickets"),
    ADD_CHARGE("Add charge"),
    CHANGE_PASS("Change password"),
    SIGN_OUT("Sign out");

    private final String line;

    UserPanelMenu(String line) {
        this.line = line;
    }

    static void printMenu(String username, double balance) {
        System.out.printf("""
                %56c-------------------------------------------------
                %56c|                                               |
                %56c|             Welcome : %s%-15s%s         |
                %56c|                                               |
                %56c|     Your balance is : %s$%-15.2f%s        |
                %56c|                                               |
                %56c-------------------------------------------------
                """, ' ', ' ', ' ', AnsiColor.FOREGROUND_BLUE94, username, AnsiColor.RESET_COLOR, ' ', ' ', AnsiColor.FOREGROUND_GREEN92, balance, AnsiColor.RESET_COLOR, ' ', ' ');
        System.out.println();
        for (int i = 0; i < UserPanelMenu.values().length - 1; i++)
            System.out.printf("%65c%d - %s\n\n", ' ', i + 1, UserPanelMenu.values()[i].line);
        System.out.printf("%65c0 - Sign out\n\n", ' ').printf("%65c>> ", ' ');
    }

    static UserPanelMenu valueOf(int idx) {
        try {
            if (idx == 0)
                return UserPanelMenu.SIGN_OUT;
            return UserPanelMenu.values()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}

public class UserPanel {

    private final UserController userController = new UserController();
    private final FlightController flightController = new FlightController();
    private String username;
    private String password;
    private double userBalance;

    public UserPanel(String username, String password) {
        this.username = username;
        this.password = password;
        showMenu();
    }

    private void showMenu() {
        while (true) {
            Utils.clearScreen();
            updateUserValues();
            UserPanelMenu.printMenu(username, userBalance);
            switch (UserPanelMenu.valueOf(Utils.getInt())) {
                case TICKET_LIST -> ticketMenu();
                case OWNED_TICKETS -> {
                } // TODO: 5/24/2023
                case ADD_CHARGE -> addCharge();
                case CHANGE_PASS -> changePassword();
                case SIGN_OUT -> { return; }
                case null, default -> Utils.printMissInputWarning();
            }
        }
    }

    private void addCharge() {
        System.out.printf("%60cHow much would you like to charge?%s ", ' ', AnsiColor.FOREGROUND_GREEN92);
        double temp = Utils.getDouble();
        System.out.print(AnsiColor.RESET_COLOR);
        if (temp > 0) {
            userController.addBalanceToUser(username, temp);
            updateUserValues();
        } else Utils.printMissInputWarning();
    }

    private void updateUserValues() {
        String allInfo = userController.getUserInfo(username, password);
        String[] infoList = allInfo.split(",");
        username = infoList[0];
        try {
            userBalance = Double.parseDouble(infoList[1]);
        } catch (NumberFormatException e) {
            // NOTHING FOR NOW
        }
    }

    private void changePassword() {
        String pass = Utils.getString(60, "What is your current password? ", "", AnsiColor.FOREGROUND_RED91);
        if (pass.equals(password)) {
            password = Utils.getString(60, "Input the new password: ", "", AnsiColor.FOREGROUND_RED91);
            userController.setNewPass(username, password);
        } else
            Utils.printMessage(65, "!! Access Denied !!", AnsiColor.FOREGROUND_BRIGHT_RED, 1500);
    }

    private void ticketMenu() {
        String origin = null;
        String destination = null;
        String date = null;
        int price = -1;

        while (true) {
            Utils.clearScreen();
            printFlights(flightController.filterFlights(null, origin, destination, date, -1, -1, price));
            printAvailableOptions();
            switch (Utils.getInt()) {
                case 1 -> origin = Utils.getString(50, "What's the origin? ", "", "");
                case 2 -> destination = Utils.getString(50, "What's the destination? ", "", "");
                case 3 -> date = getDateInput();
                case 4 -> {
                    System.out.printf("%50cWhat's the max price:%s ", ' ', AnsiColor.FOREGROUND_GREEN92);
                    System.out.print(AnsiColor.RESET_COLOR);
                    price = Utils.getInt();
                }
                case 5 -> {
                    origin = null;
                    destination = null;
                    date = null;
                    price = -1;
                }
                case 6 -> {    }
                case 0 -> { return; }
                default -> Utils.printMissInputWarning();
            }
        }
    }

    private void printAvailableOptions() {
        System.out.printf("""
                                                  
                                                  
                                  %50cWhat parameter would you like to search:
                                                      
                                  %50c            1 - Origin
                                  %50c            2 - Destination
                                  %50c            3 - Date
                                  %50c            4 - Price limit
                                  %50c            5 - Reset Filters
                                                  
                                  %50c            6 - Reserve a flight
                                  %50c            0 - Go back
                                  %50c   >>""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    private String getDateInput() {
        String tempString = Utils.getString(40,"Provide a date (exp. yyyy-mm-dd): ", "", "");
        while (!tempString.matches("^\\d\\d\\d\\d-[0-1]\\d-[0-3]\\d$")) {
            Utils.printMissInputWarning();
            tempString = Utils.getString(40,"Provide a date (exp. yyyy-mm-dd): ", "", "");
        }
        return tempString;
    }

    private void printFlights(String[] flightList) {
        System.out.printf("%36c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n", ' ');
        for (String s : flightList) {
            System.out.printf("%36c%s", ' ', s);
        }
    }
}