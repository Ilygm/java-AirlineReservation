import java.util.ArrayList;
import java.util.Scanner;

public class UserPanel {
    private final Scanner scanner = new Scanner(System.in);
    private final User user;
    private final Flights flights;
    private final Tickets tickets;

    /**
     * Main panel that the user sees
     *
     * @param user    contains the user's information
     * @param flights contains: flightsDB and related functions
     * @param tickets contains: ticketsDB and related functions
     */
    public UserPanel(User user, Flights flights, Tickets tickets) {
        this.user = user;
        this.flights = flights;
        this.tickets = tickets;

        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printUserPanel();
            option = App.getIntInput();
            switch (option) {
                case 1 -> availableFlights();
                case 2 -> {
                    App.clearScreen();
                    tickets.printOwnedTickets(user);
                    System.out.printf("\n%40cPress Enter to continue...", ' ');
                    scanner.nextLine();
                    if (scanner.hasNextLine()) scanner.nextLine();
                }
                case 3 -> chargeAccount();
                case 4 -> passwordChanger();
                case 0 -> {/* Filling empty space :) */}
                default -> App.printInvalidInput();
            }
        }
    }

    /**
     * Charges the user's wallet (Bank's gateway is needed)
     */
    private void chargeAccount() {
        int charged;
        System.out.printf("\n\n%55cHow much would you like to charge? \n%60c[ Current balance: %s%,d%s ]\n\n%54c>> ", ' ', ' ', CColors.GREEN, user.getBalance(), CColors.RESET, ' ');
        charged = App.getIntInput();
        while (charged <= 0) {
            System.out.printf("%68c!! Wrong input try again !!\n%54c>> ", ' ', ' ');
            App.rest();
            charged = App.getIntInput();
        }
        user.changeBalance(charged);
    }

    /**
     * Menu of UserPanel
     */
    private void printUserPanel() {
        System.out.printf("""
                                  %56c-------------------------------------------------
                                  %56c|                                               |
                                  %56c|             Welcome :  %s%-23s%s|
                                  %56c|                                               |
                                  %56c|     Your balance is :  %s%,-15d$%s       |
                                  %56c|                                               |
                                  %56c-------------------------------------------------
                                                                    
                                  %56c         1 - Ticket searching and booking
                                                                    
                                  %56c         2 - Owned tickets
                                                                    
                                  %56c         3 - Add charge
                                                                    
                                  %56c         4 - Change password
                                                                    
                                  %56c         0 - Sign out
                                  %56c  >>""" + ' ', ' ', ' ', ' ', CColors.BLUE, user.getUsername(), CColors.RESET, ' ', ' ', CColors.GREEN, user.getBalance(), CColors.RESET, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    /**
     * Changes the users password
     */
    private void passwordChanger() {
        System.out.printf("\n%56cYou're about to change your password\n%56cEnter your current password: ", ' ', ' ');
        if (scanner.next().equals(user.getPassword())) {
            System.out.printf("\n%56cEnter your new password: ", ' ');
            String tempPass = scanner.next();
            while (!(tempPass.matches("^\\w*$")) || tempPass.length() < 5) {
                System.out.printf("""
                                          %60c%s   !! Password is not acceptable ( %s ) !!
                                          %56cEnter another password >> %s""" + ' ', ' ', CColors.RESET, (CColors.RED + tempPass + CColors.RESET), ' ', CColors.CYAN);
                tempPass = scanner.next();
            }
            user.setPassword(tempPass);
            System.out.printf("\n%60c%s!! Password Changed !!%s", ' ', CColors.GREEN, CColors.RESET);
        } else {
            System.out.printf("%65c%s!! Access denied !!%s\n\n", ' ', CColors.RED, CColors.RESET);
        }
        App.rest();
    }

    /**
     * Shows the available flights and lets user filter them
     */
    private void availableFlights() {
        ArrayList<Flight> filteredFlights = new ArrayList<>();
        boolean isFiltered = false;
        while (true) {
            App.clearScreen();
            if (isFiltered) {
                flights.printSelectedFlights(filteredFlights);
            } else flights.showAllFlights();
            printAvailableFlights();
            int option = App.getIntInput();
            if (option > 0 && option < 6) isFiltered = true;
            switch (option) {
                case 1 -> filteredFlights = flights.filterFlights(2, getStringX("origin"), 0);
                case 2 -> filteredFlights = flights.filterFlights(1, getStringX("destination"), 0);
                case 3 -> filteredFlights = flights.filterFlights(3, getDateInput(), 0);
                case 4 ->
                        filteredFlights = flights.filterFlights(4, getStringX("time ( Morning / AfterNoon / Night)"), 0);
                case 5 -> {
                    System.out.printf("%50cEnter the price limit: ", ' ');
                    filteredFlights = flights.filterFlights(5, "", App.getIntInput());
                }
                case 6 -> isFiltered = false;
                case 7 -> ticketBuyer();
                case 0 -> { return; }
                default -> App.printInvalidInput();
            }
        }
    }

    /**
     * Buys and generates a ticket for user
     */
    private void ticketBuyer() {
        System.out.printf("%50cEnter Flight id: %s", ' ', CColors.CYAN);
        String tempFlightID = scanner.next();
        System.out.print(CColors.RESET);
        if (flights.hasFlight(tempFlightID)) {
            Flight tempFlight = flights.getFlight(tempFlightID);
            if (tempFlight.getAvailableSeats() != 0) {
                if (user.boughtFlights.contains(tempFlight)) {
                    System.out.printf("%s%50c!! You have already bought this flight !!%s", CColors.RED, ' ', CColors.RESET);
                } else if (tempFlight.getPrice() > user.getBalance()) {
                    System.out.printf("%s%50c!! You do not have enough balance !!%s", CColors.RED, ' ', CColors.RESET);
                } else {
                    tempFlight.changeAvailableSeats(-1);
                    user.changeBalance(-tempFlight.getPrice());
                    user.boughtFlights.add(tempFlight);
                    tickets.ticketsDB.add(new Ticket(tempFlight, user, tempFlight.getAvailableSeats() + "_" + user.getUsername()));
                    System.out.printf("%s%55c!! You have bought this flight !!%s", CColors.GREEN, ' ', CColors.RESET);
                }
            } else {
                System.out.printf("%s%50c!! Selected flight has no more seats !!%s", CColors.RED, ' ', CColors.RESET);
            }
        } else {
            System.out.printf("%s%60c!! FlightID not found !!%s", CColors.RED, ' ', CColors.RESET);

        }
        App.rest();
    }

    /**
     * Makes sure the date is in correct format
     * @return Formatted string used as date
     */
    private String getDateInput() {
        String tempString = getStringX("date (exp. yyyy-mm-dd)");
        while (!tempString.matches("^\\d\\d\\d\\d-[0-1]\\d-[0-3]\\d$")) {
            System.out.printf("%50c  %s!! Provided date is incorrect !!%s", ' ', CColors.RED, CColors.RESET);
            tempString = getStringX("date (exp. yyyy-mm-dd)");
        }
        return tempString;
    }

    /**
     * Menu for filtering and buying flights
     */
    private void printAvailableFlights() {
        System.out.printf("""
                                                  
                                                  
                                  %50cWhat parameter would you like to search:
                                                      
                                  %50c            1 - Origin
                                  %50c            2 - Destination
                                  %50c            3 - Date
                                  %50c            4 - Time Range
                                  %50c            5 - Price limit
                                  %50c            6 - Reset Filters
                                                  
                                  %50c            7 - Reserve a flight
                                  %50c            0 - Go back
                                  %50c   >>""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
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
}
