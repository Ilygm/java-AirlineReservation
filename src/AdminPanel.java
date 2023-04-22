import java.util.ArrayList;
import java.util.Scanner;

public class AdminPanel {
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights;

    private final Tickets tickets;

    /**
     * Main menu for admins
     *
     * @param flights contains: flightsDB and related functions
     * @param tickets contains: ticketsDB and related functions
     */
    public AdminPanel(Flights flights, Tickets tickets) {
        this.flights = flights;
        this.tickets = tickets;

        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printAdminPanel();
            option = App.getIntInput();
            switch (option) {
                case 1 -> addFlight();
                case 2 -> modifyMenu();
                case 3 -> removeFlight();
                case 4 -> viewFlights(flights);
                case 0 -> {/* he, he */}
                default -> App.printInvalidInput();
            }
        }
    }

    /**
     * Displays all the available flights and their passengers
     *
     * @param flights contains: flightsDB and related functions
     */
    private void viewFlights(Flights flights) {
        App.clearScreen();
        flights.showAllFlights();
        System.out.printf("""
                                                  
                                  %40cChoose an option:
                                                  
                                  %40c1 - Passengers in a flight
                                  %40c0 - Go back
                                  %40c>>""" + ' ', ' ', ' ', ' ', ' ');
        int option = App.getIntInput();
        switch (option) {
            case 1 -> {
                System.out.printf("\n\n%40cEnter a flightID to checkout: %s", ' ', CColors.CYAN);
                String tempFlightID = scanner.next();
                System.out.print(CColors.RESET);
                if (flights.hasFlight(tempFlightID)) {
                    tickets.printPassengerList(flights.getFlight(tempFlightID));
                    System.out.printf("\n\n%50cPress Enter to continue...", ' ');
                    scanner.nextLine();
                    scanner.nextLine();
                } else {
                    System.out.printf("%50c%s!! FlightID not found !!%s", ' ', CColors.RED, CColors.RESET);
                    App.rest();
                }
            }
            case 0 -> {/* Another empty space filled */}
            default -> App.printInvalidInput();
        }
    }

    /**
     * Starts the process to add a new flight
     */
    private void addFlight() {
        App.clearScreen();
        Flight tempFlight = new Flight(null, null, null, null, 0, 0, 0);
        System.out.printf("""
                                  %56c--------------------------------
                                  %56c|                              |
                                  %56c|       Adding a Flight        |
                                  %56c|                              |
                                  %56c--------------------------------
                                                                                                   
                                  """ + ' ', ' ', ' ', ' ', ' ', ' ');
        String tempString = getStringX("flightID (exp. EX-22, WG-45)");
        while (!tempString.matches("^[A-Z][A-Z]-\\d\\d$")) {
            System.out.printf("%50c  %s! Provided ID is incorrect !%s\n", ' ', CColors.RED, CColors.RESET);
            tempString = getStringX("flightID (exp. EX-22, WG-45)");
        }
        tempFlight.setFlightID(tempString);
        tempFlight.setOrigin((getStringX("origin")));
        tempFlight.setDestination((getStringX("destination")));
        tempString = getStringX("date (exp. yyyy-mm-dd)");
        while (!tempString.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d$")) {
            System.out.printf("%50c  %s! Provided date is incorrect !%s\n", ' ', CColors.RED, CColors.RESET);
            tempString = getStringX("date (exp. yyyy-mm-dd)");
        }
        tempFlight.setDate(tempString);
        int tempTime = getIntX("departure time (exp. 1200, 2400)");
        while (tempTime > 2400) {
            System.out.printf("%50c  %s! Provided time is incorrect !%s\n", ' ', CColors.RED, CColors.RESET);
            tempTime = getIntX("departure time (exp. 1200, 2400)");
        }
        tempFlight.setTime(tempTime);
        tempFlight.setPrice(getIntX("price"));
        tempFlight.setAvailableSeats(getIntX("seat"));
        flights.addFlight(tempFlight);
        System.out.printf("\n%56c%s!!    Your flight has been added      !!%s", ' ', CColors.GREEN, CColors.RESET);
        if (scanner.hasNextLine()) scanner.nextLine();
        App.rest();
    }

    /**
     * Modifies an existing flight
     */
    private void modifyMenu() {
        App.clearScreen();
        flights.showAllFlights();
        System.out.printf("\n\n%40cEnter a flightID to modify: %s", ' ', CColors.CYAN);
        String tempFlightID = scanner.next();
        System.out.println(CColors.RESET);
        if (flights.hasFlight(tempFlightID)) {
            printModifyMenu();
            int option = App.getIntInput();
            switch (option) {
                case 1 -> flights.getFlight(tempFlightID).setOrigin(getStringX("new origin"));
                case 2 -> flights.getFlight(tempFlightID).setDestination(getStringX("new destination"));
                case 3 -> flights.getFlight(tempFlightID).setPrice(getIntX("new price"));
                case 4 -> flights.getFlight(tempFlightID).setDate(getStringX("new date"));
                case 5 -> {
                    int tempTime;
                    while ((tempTime = getIntX("new time")) > 2400) {
                        System.out.printf("%60c%s!! Given value is incorrect !!%s", ' ', CColors.RED, CColors.RESET);
                    }
                    flights.getFlight(tempFlightID).setTime(tempTime);
                }
                case 0 -> {return;}
                default -> App.printInvalidInput();
            }
            if (scanner.hasNextLine()) scanner.nextLine();
        } else {
            System.out.printf("%s%60c!! FlightID not found !!%s", CColors.RED, ' ', CColors.RESET);
            App.rest();
        }
    }

    /**
     * Removes an existing flight nad refunds for the users
     */
    private void removeFlight() {
        App.clearScreen();
        flights.showAllFlights();
        System.out.printf("\n\n%40cEnter a flightID to remove: %s", ' ', CColors.RED);
        String tempFlightID = scanner.next();
        if (flights.hasFlight(tempFlightID)) {
            ArrayList<User> userList = tickets.usersForFlight(flights.getFlight(tempFlightID));
            for (User user : userList) {
                user.changeBalance(flights.getFlight(tempFlightID).getPrice());
            }
            System.out.printf("%60c%s!! Flight has been removed !!%s", ' ', CColors.PURPLE, CColors.RESET);
            tickets.removeTicket(flights.getFlight(tempFlightID));
            flights.removeFlight(tempFlightID);
        } else {
            System.out.printf("%60c!! FlightID not found !!%s", ' ', CColors.RESET);
        }
        App.rest();
        scanner.nextLine();
    }

    /**
     * prints the menu of modifyMenu
     */
    private void printModifyMenu() {
        System.out.printf("""
                                  %55c  What would you like to change?
                                  %55c1 - Origin
                                  %55c2 - Destination
                                  %55c3 - Price
                                  %55c4 - Date
                                  %55c5 - Time
                                  %55c5 - Return
                                  %55c>>""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    /**
     * prints the menu of AdminPanel
     */
    private void printAdminPanel() {
        System.out.printf("""
                                  %50c-------------------------------------------------
                                  %50c|                                               |
                                  %50c|                Welcome Boss                   |
                                  %50c|        You are logged in as: %-17s|
                                  %50c|                                               |
                                  %50c-------------------------------------------------
                                                  
                                  %50c         1 - Add flight
                                                  
                                  %50c         2 - Update flight
                                                  
                                  %50c         3 - Remove flight
                                                      
                                  %50c         4 - Current schedules
                                                      
                                  %50c         0 - Sign out
                                  %50c>>""" + ' ', ' ', ' ', ' ', ' ', "Admin", ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    /**
     * Gets a string input from the user
     * @param text displays the required input
     * @return String input that the user provided
     */
    private String getStringX(String text) {
        System.out.printf("\n%50c Enter the %s: %s", ' ', text, CColors.CYAN);
        text = scanner.next();
        System.out.print(CColors.RESET);
        return text;
    }

    /**
     * Makes sure user provides a correct integer
     * @param text displays the required input
     * @return Int input that the user provided
     */
    private int getIntX(String text) {
        do {
            System.out.printf("\n%50c Enter the %s: %s", ' ', text, CColors.CYAN);
            int newValue = App.getIntInput();
            System.out.printf(CColors.RESET);
            if (newValue > 0) return newValue;
            else {
                System.out.printf("%60c%s!! Given value cannot be negative !!%s", ' ', CColors.RED, CColors.RESET);
                App.rest();
            }
        } while (true);
    }
}
