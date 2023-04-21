import java.util.ArrayList;
import java.util.Scanner;

public class UserPanel {
    private final Scanner scanner = new Scanner(System.in);
    private final User user;
    private final Flights flights;

    public UserPanel(User user, Flights flights) {
        this.user = user;
        this.flights = flights;

        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printUserPanel();
            option = App.getIntInput();
            switch (option) {
                case 1 -> availableFlights();
                case 2 -> {
                    flights.printSelectedFlights(user.boughtFlights);
                    App.rest(2000);
                }
                case 3 -> chargeAccount(user);
                case 4 -> passwordChanger(user);
                case 0 -> {/* Filling empty space :) */}
                default -> App.printInvalidInput();
            }
        }
    }

    private void chargeAccount(User user) {
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

    private void printUserPanel() {
        System.out.printf("""
                                  %55c-------------------------------------------------
                                  %55c|                                               |
                                  %55c|             Welcome :  %-20s   |
                                  %55c|                                               |
                                  %55c|     Your balance is :  %s%,-15d$%s       |
                                  %55c|                                               |
                                  %55c-------------------------------------------------
                                                                    
                                  %50c           1 - Ticket searching and booking
                                                                    
                                  %50c           2 - Owned tickets
                                                                    
                                  %50c           3 - Add charge
                                                                    
                                  %50c           4 - Change password
                                                                    
                                  %50c           0 - Sign out
                                  %50c  >>""" + ' ', ' ', ' ', ' ', CColors.BLUE + user.getUsername() + CColors.RESET, ' ', ' ', CColors.GREEN, user.getBalance(), CColors.RESET, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    private void passwordChanger(User user) {
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
            switch (option) {
                case 1 -> filteredFlights = filterFlights(2, getStringX("origin"), 0);
                case 2 -> filteredFlights = filterFlights(1, getStringX("destination"), 0);
                case 3 -> filteredFlights = filterFlights(3, getDateInput(), 0);
                case 4 -> filteredFlights = filterFlights(4, getStringX("time ( Morning / AfterNoon / Night)"), 0);
                case 5 -> {
                    System.out.printf("%50cEnter the price limit: ", ' ');
                    filteredFlights = filterFlights(5, "", App.getIntInput());
                }
                case 6 -> {
                    isFiltered = false;
                    continue;
                }
                case 7 -> {
                    ticketBuyer();
                    continue;
                }
                case 0 -> {
                    return;
                }
                default -> {
                    App.printInvalidInput();
                    continue;
                }
            }
            isFiltered = true;
        }
    }

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
                    tempFlight.reduceAvailableSeats(1);
                    user.changeBalance(-tempFlight.getPrice());
                    user.boughtFlights.add(tempFlight);
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

    private String getDateInput() {
        String tempString = getStringX("date (exp. yyyy-mm-dd)");
        while (!tempString.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d$")) {
            System.out.printf("%50c  %s!! Provided date is incorrect !!%s", ' ', CColors.RED, CColors.RESET);
            tempString = getStringX("date (exp. yyyy-mm-dd)");
        }
        return tempString;
    }

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

    private ArrayList<Flight> filterFlights(int field, String searchText, int priceLimit) {
        ArrayList<Flight> filteredFlights = new ArrayList<>();
        if (priceLimit == -25) return filteredFlights;
        for (Flight flight : flights.flightsDB.values()) {
            switch (field) {
                case 1 -> {
                    if (flight.getDestination().equals(searchText)) filteredFlights.add(flight);
                }
                case 2 -> {
                    if (flight.getOrigin().equals(searchText)) filteredFlights.add(flight);
                }
                case 3 -> {
                    if (flight.getDate().equals(searchText)) filteredFlights.add(flight);
                }
                case 4 -> {
                    switch (searchText) {
                        case "Morning" -> {
                            if (flight.getTime() >= 5_00 && flight.getTime() <= 11_59) filteredFlights.add(flight);
                        }
                        case "Afternoon" -> {
                            if (flight.getTime() >= 12_00 && flight.getTime() <= 18_59) filteredFlights.add(flight);
                        }
                        case "Night" -> {
                            if (flight.getTime() >= 19_00 && flight.getTime() <= 4_59) filteredFlights.add(flight);
                        }
                        default -> {
                            System.out.printf("%50c%s!! Wrong time was given !!%s", ' ', CColors.RED, CColors.RESET);
                            App.rest();
                        }
                    }
                }
                case 5 -> {
                    if (flight.getPrice() <= priceLimit) filteredFlights.add(flight);
                }
            }
        }
        return filteredFlights;
    }

    private String getStringX(String text) {
        System.out.printf("\n%50c Enter the %s: %s", ' ', text, CColors.CYAN);
        text = scanner.next();
        System.out.print(CColors.RESET);
        return text;
    }
}
