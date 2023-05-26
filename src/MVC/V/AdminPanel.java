package MVC.V;

import MVC.C.FlightController;
import MVC.C.Root;

enum AdminPanelMenu {
    ADD_FLIGHT("Add flight"),
    CURRENT_SCHEDULES("Current schedules"),

    EXIT("Exit");

    private final String line;

    AdminPanelMenu(String line) {
        this.line = line;
    }

    public static AdminPanelMenu valueOf(int idx) {
        try {
            if (idx == 0)
                return AdminPanelMenu.EXIT;
            return AdminPanelMenu.values()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static void printMenu(String username) {
        System.out.printf("""
                %56c-------------------------------
                %56c|                             |
                %56c|       Welcome %s%-10s%s    |
                %56c|                             |
                %56c-------------------------------
                                
                """, ' ', ' ', ' ', AnsiColor.FOREGROUND_BRIGHT_BLUE, username, AnsiColor.RESET_COLOR, ' ', ' ');

        for (int i = 0; i < AdminPanelMenu.values().length - 1; i++) {
            System.out.printf("%60c%d - %s\n", ' ', i + 1, AdminPanelMenu.values()[i].line);
        }
        System.out.printf("%60c0 - %s\n%60c>> ", ' ', AdminPanelMenu.EXIT.line, ' ');
    }
}

public class AdminPanel {
    private final String username;

    private final FlightController flightController;
    private final Root root;

    public AdminPanel(String username, FlightController flightController, Root root) {
        this.username = username;
        this.flightController = flightController;
        this.root = root;
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            Utils.clearScreen();
            AdminPanelMenu.printMenu(username);
            switch (AdminPanelMenu.valueOf(Utils.getInt())) {
                case ADD_FLIGHT -> addFlight();
                case CURRENT_SCHEDULES -> currentFlights();
                case EXIT -> {
                    return;
                }
                case null, default -> {
                }
            }
        }
    }

    private void addFlight() {
        Utils.clearScreen();
        System.out.printf("""
                                  %56c--------------------------------
                                  %56c|                              |
                                  %56c|       Adding a Flight        |
                                  %56c|                              |
                                  %56c--------------------------------
                                                                                                   
                                  """ + ' ', ' ', ' ', ' ', ' ', ' ');
        String flightID = Utils.getString(55, "Enter flightID (exp. EX-22, WG-45): ", "", AnsiColor.FOREGROUND_GREEN92);
        while (!flightID.matches("^[A-Z][A-Z]-\\d\\d$") || flightController.doesFlightExist(flightID)) {
            Utils.printMessage(55, "!! Provided ID is not available !!\n", AnsiColor.FOREGROUND_RED91, 1500);
            flightID = Utils.getString(55, "Enter flightID (exp. EX-22, WG-45): ", "", AnsiColor.FOREGROUND_GREEN92);
        }
        String origin = Utils.getString(55, "Enter origin: ", "", AnsiColor.FOREGROUND_GREEN92);
        String destination = Utils.getString(55, "Enter destination: ", "", AnsiColor.FOREGROUND_GREEN92);
        String date = getDateInput();
        Utils.printMessage(55, "Enter departure time in 24h format (exp. 1200, 2400): ", "", 0);
        int tempTime = Utils.getInt();
        while (tempTime / 2400 > 24 || tempTime % 100 > 59) {
            Utils.printMessage(55, "!! Provided time is incorrect !!\n", AnsiColor.FOREGROUND_RED91, 1500);
            Utils.printMessage(55, "Enter departure time in 24h format (exp. 1200, 2400): ", "", 0);
            tempTime = Utils.getInt();
        }
        Utils.printMessage(55, "Enter the price: ", "", 0);
        int price = Utils.getInt();
        while (price < 0) {
            Utils.printMessage(55, "!! Provided price is incorrect !!\n", AnsiColor.FOREGROUND_RED91, 1500);
            Utils.printMessage(55, "Enter the price: ", "", 0);
            price = Utils.getInt();
        }
        Utils.printMessage(55, "Enter seat count: ", "", 0);
        int seats = Utils.getInt();
        while (seats < 1) {
            Utils.printMessage(55, "!! Provided price is incorrect !!\n", AnsiColor.FOREGROUND_RED91, 1500);
            Utils.printMessage(55, "Enter the price: ", "", 0);
            seats = Utils.getInt();
        }
        if (flightController.registerNewFlight(flightID, origin, destination, date, tempTime, seats, price)) {
            Utils.printMessage(60, "A new flight has been created", AnsiColor.FOREGROUND_GREEN92, 0);
        } else Utils.printMessage(65, " !! Action failed !!\n", AnsiColor.FOREGROUND_BRIGHT_RED, 1500);
    }

    private String getDateInput() {
        String tempString = Utils.getString(55, "Provide a date (exp. yyyy-mm-dd): ", "", "");
        while (!tempString.matches("^\\d\\d\\d\\d-[0-1]\\d-[0-3]\\d$")) {
            Utils.printMissInputWarning();
            tempString = Utils.getString(55, "Provide a date (exp. yyyy-mm-dd): ", "", "");
        }
        return tempString;
    }

    private void printFlights(String[] flightList) {
        System.out.printf("%36c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n", ' ');
        for (String s : flightList) {
            System.out.printf("%36c%s", ' ', s);
        }
    }

    private void currentFlights() {
        while (true) {
            Utils.clearScreen();
            printFlights(flightController.filterFlights(null, null, null, null, -1, -1, -1));
            flightsMenu();
            switch (Utils.getInt()) {
                case 1 -> {
                    String flightID = Utils.getString(55, "Enter flightID: ", "", AnsiColor.FOREGROUND_GREEN92);
                    if (flightController.doesFlightExist(flightID)) modifyMenu(flightID);
                    else Utils.printMessage(55, "!! FlightID not found !!", AnsiColor.FOREGROUND_RED91, 1500);
                }
                case 2 -> {
                    String flightID = Utils.getString(55, "Enter flightID: ", "", AnsiColor.FOREGROUND_GREEN92);
                    if (flightController.doesFlightExist(flightID)) root.removeFlight(flightID);
                    else Utils.printMessage(55, "!! FlightID not found !!", AnsiColor.FOREGROUND_RED91, 1500);
                }
                case 0 -> {
                    return;
                }
                default -> Utils.printMissInputWarning();
            }
        }
    }

    private void flightsMenu() {
        System.out.printf("""
                                                  
                                  %56c1 - Modify flight
                                                  
                                  %56c2 - Remove flight
                                                  
                                  %56c0 - Exit
                                                  
                                  %56c>>""" + " ", ' ', ' ', ' ', ' ');
    }

    private void modifyMenu(String flightID) {
        while (true) {
            Utils.clearScreen();
            printModifyMenu(flightController.getFlight(flightID));
            switch (Utils.getInt()) {
                case 1 ->
                        flightController.changeOrigin(flightID, Utils.getString(57, "Enter origin: ", "", AnsiColor.FOREGROUND_GREEN92));
                case 2 ->
                        flightController.changeDestination(flightID, Utils.getString(57, "Enter origin: ", "", AnsiColor.FOREGROUND_GREEN92));
                case 3 -> flightController.changeDate(flightID, getDateInput());
                case 4 -> {
                    Utils.printMessage(57, "Enter price: ", "", 0);
                    int price = Utils.getInt();
                    if (price > 0) flightController.changePrice(flightID, price);
                }
                case 5 -> changeFlightTime(flightID);
                case 6 -> {
                    Utils.printMessage(57, "Enter seat-count: ", "", 0);
                    int seats = Utils.getInt();
                    if (seats > 0) flightController.changeSeats(flightID, seats);
                }
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void changeFlightTime(String flightID) {
        Utils.printMessage(57, "Enter time in 24h format: ", "", 0);
        int time = Utils.getInt();
        while (time / 100 > 24 || time % 100 > 59) {
            Utils.printMissInputWarning();
            Utils.printMessage(57, "Enter time in 24h format: ", "", 0);
            time = Utils.getInt();
        }
        flightController.changeTime(flightID, time);
    }

    private void printModifyMenu(String flightDetails) {
        System.out.printf("""
                                  %40c%s
                                                  
                                  %60cWhat would you like to change?
                                                  
                                  %60c1 - Origin
                                  %60c2 - Destination
                                  %60c3 - Date
                                  %60c4 - Price
                                  %60c5 - Time
                                  %60c6 - Seat count
                                  %60c0 - Exit
                                  %60c>>""" + " ", ' ', flightDetails, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }
}