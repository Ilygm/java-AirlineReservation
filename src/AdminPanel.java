import java.util.Scanner;

public class AdminPanel {
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights;

    public AdminPanel(Flights flights) {
        this.flights = flights;

        int option = -1;
        while (option != 0) {
            App.clearScreen();
            printAdminPanel();
            option = App.getIntInput();
            switch (option) {
                case 1 -> addFlight();
                case 2 -> modifyFlight();
                case 3 -> removeFlight();
                case 4 -> {
                    flights.showAllFlights();
                    System.out.printf("\n%40cPress Enter to continue...", ' ');
                    scanner.nextLine();
                }
                case 0 -> {/* he, he */}
                default -> App.printInvalidInput();
            }
        }
    }

    private void addFlight() {
        App.clearScreen();
        Flight tempFlight = new Flight(null, null, null, null, 0, 0, 0);
        System.out.printf("""
                                  %55c--------------------------------
                                  %55c|                              |
                                  %55c|       Adding a Flight        |
                                  %55c|                              |
                                  %55c--------------------------------
                                                                                                   
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
        flights.addFLight(tempFlight);
        System.out.printf("\n%56c%s!!    Your flight has been added      !!%s", ' ', CColors.GREEN, CColors.RESET);
        if (scanner.hasNextLine()) scanner.nextLine();
        App.rest();
    }

    private void modifyFlight() {
        App.clearScreen();
        flights.showAllFlights();
        System.out.printf("\n\n%40cEnter a flightID to modify: %s", ' ', CColors.CYAN);
        String tempFlightID = scanner.next();
        System.out.println(CColors.RESET);
        if (flights.hasFlight(tempFlightID)) {
            if (flights.getFlight(tempFlightID).isModified) {
                System.out.printf("%50c%s!! Flight is already bought !!%s", ' ', CColors.RED, CColors.RESET);
                App.rest();
                return;
            }
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
                default -> App.printInvalidInput();
            }
            if (scanner.hasNextLine()) scanner.nextLine();
        } else {
            System.out.printf("%s%60c!! FlightID not found !!%s", CColors.RED, ' ', CColors.RESET);
            App.rest();
        }
    }

    private void removeFlight() {
        App.clearScreen();
        flights.showAllFlights();
        System.out.printf("\n\n%40cEnter a flightID to remove: %s", ' ', CColors.RED);
        String tempFlightID = scanner.next();
        if (flights.hasFlight(tempFlightID)) {
            if (flights.getFlight(tempFlightID).isModified) {
                System.out.printf("%50c%s!! Flight is already bought !!%s", ' ', CColors.RED, CColors.RESET);
                App.rest();
                return;
            }
            System.out.printf("%s\n%35cSelected flight:\n\n%s", CColors.RESET, ' ', flights.getFlight(tempFlightID));
            System.out.printf("\n%50cAre you sure (y / n)? ", ' ');
            if (scanner.next().equals("y")) {
                flights.removeFLight(tempFlightID);
                System.out.printf("%50c%s!! Selected flight has been removed !!%s", ' ', CColors.CYAN, CColors.RESET);
                App.rest();
            }
        } else {
            System.out.printf("%60c!! FlightID not found !!%s", ' ', CColors.RESET);
            App.rest();
        }
        scanner.nextLine();
    }

    private void printModifyMenu() {
        System.out.printf("""
                                  %55c  What would you like to change?
                                  %55c1 - Origin
                                  %55c2 - Destination
                                  %55c3 - Price
                                  %55c4 - Date
                                  %55c5 - Time
                                  %55c>>""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    }

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

    private String getStringX(String text) {
        System.out.printf("\n%50c Enter the %s: %s", ' ', text, CColors.CYAN);
        text = scanner.next();
        System.out.print(CColors.RESET);
        return text;
    }

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
