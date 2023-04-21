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
                case 1 -> filteringMenu();
                case 5 -> chargeAccount();
                case 6 -> passwordChanger(user);
                case 0 -> {/* Filling empty space :) */}
                default -> App.printInvalidInput();
            }
        }
    }

    private void printUserPanel() {
        System.out.printf("""
                                  %50c-------------------------------------------------
                                  %50c|                                               |
                                  %50c|             Welcome :  \033[1;34m%-20s\033[0m   |
                                  %50c|                                               |
                                  %50c|     Your balance is :  \033[0;32m%,-15d$\033[0m       |
                                  %50c|                                               |
                                  %50c-------------------------------------------------
                                                                    
                                  %50c           1 - Search flight ticket
                                                                    
                                  %50c           2 - Ticket booking
                                                                    
                                  %50c           3 - Ticket cancelation
                                                                    
                                  %50c           4 - Tickets bought
                                                                    
                                  %50c           5 - Add charge
                                                                    
                                  %50c           6 - Change password
                                                                    
                                  %50c           0 - Sign out
                                  %50c  >>""" + ' ', ' ', ' ', ' ', user.getUsername(), ' ', ' ', user.getBalance(), ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

    private void passwordChanger(User user) {
        System.out.printf("\n%56cYou're about to change your password\n%56cEnter your current password: ", ' ', ' ');
        if (scanner.next().equals(user.getPassword())) {
            System.out.printf("\n%56cEnter your new password: ", ' ');
            user.setPassword(scanner.next());
        } else System.out.printf("%56c    \033[0;31m!! Access denied !!\033[0m\n\n", ' ');
    }

    private void filteringMenu() {
        ArrayList<Flight> filteredFlights = new ArrayList<>();
        boolean isFiltered = false;
        while (true) {
            App.clearScreen();
            if (isFiltered) {
                flights.printSelectedFlights(filteredFlights);
            } else flights.showAllFlights();
            printFilteringMenu();
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

    private String getDateInput() {
        String tempString = getStringX("date (exp. yyyy-mm-dd)");
        while (!tempString.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d$")) {
            System.out.printf("%50c  \033[0;31m! Provided date is incorrect !\033[0m\n", ' ');
            tempString = getStringX("date (exp. yyyy-mm-dd)");
        }
        return tempString;
    }

    private void printFilteringMenu() {
        System.out.printf("""
                                
                                
                %50cWhat parameter would you like to search:
                                    
                %50c            1 - Origin
                %50c            2 - Destination
                %50c            3 - Date
                %50c            4 - Time Range
                %50c            5 - Price Range
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

    public void chargeAccount() {
        boolean notDone = true;
        while (notDone) {
            App.clearScreen();
            printChargeAccount();
            int option = App.getIntInput();
            switch (option) {
                case 1 -> {
                    int charged;
                    System.out.printf("\n\n%55cHow much would you like to charge? \n%60c[ Current balance: \033[0;32m%,d\033[0m ]\n\n%54c>> ", ' ', ' ', user.getBalance(), ' ');
                    charged = App.getIntInput();
                    while (charged <= 0) {
                        System.out.printf("%68c!! Wrong input try again !!\n%54c>> ", ' ', ' ');
                        App.rest();
                        charged = App.getIntInput();
                    }
                    user.addBalance(charged);
                }
                case 0 -> notDone = false;
                default -> App.printInvalidInput();
            }
        }
    }

    private void printChargeAccount() {
        System.out.printf("""
                                  %54c----------------------------------------
                                  %54c|                                      |
                                  %54c|       Welcome    :  \033[1;34m%-10s\033[0m       |
                                  %54c|                                      |
                                  %54c|  Your balance is :  \033[0;32m%,-7d$\033[0m         |
                                  %54c|                                      |
                                  %54c----------------------------------------
                                                                        
                                  %51cWould you like to add balance to your account?
                                                                        
                                  %67c1 - Sure
                                                                        
                                  %67c0 - I will do it later
                                                                        
                                  %54c>>""" + ' ', ' ', ' ', ' ', user.getUsername(), ' ', ' ', user.getBalance(), ' ', ' ', ' ', ' ', ' ', ' ');
    }

    private String getStringX(String text) {
        System.out.printf("\n%50c Enter the %s: %s", ' ', text, CColors.CYAN);
        text = scanner.next();
        System.out.print(CColors.RESET);
        return text;
    }
}
