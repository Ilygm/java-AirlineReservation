import java.util.Scanner;

public class AdminPanel {
    private final Scanner scanner = new Scanner(System.in);
    private final Flights flights;

    public AdminPanel(Flights flights) {
        this.flights = flights;

        App.clearScreen();
        int option = -1;
        while (option != 0) {
            printAdminPanel();
            option = App.getIntInput();
            switch (option) {
                case 1 -> addFlight();
                case 4 -> flights.showFlights();
                case 0 -> {/* he, he */}
                default -> App.invalidInput();
            }
            App.clearScreen();
        }
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

    public void addFlight() {
        App.clearScreen();
        Flight tempFlight = new Flight(null, null, null, null, 0, 0, 0);
        System.out.printf("""
                                  %50c--------------------------------
                                  %50c|                              |
                                  %50c|       Adding a Flight        |
                                  %50c|                              |
                                  %50c--------------------------------
                                                                    
                                  %47c- Exp: EX-22, WG-45, SS-55
                                                                    
                                  %47cEnter the flight ID:""" + ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
        String tempString = scanner.next();
        while (!tempString.matches("^[A-Z][A-Z]-\\d\\d$")) {
            System.out.printf("%50c  \033[0;31m! Provided ID is incorrect !\033[0m\n%47cEnter the flight ID: ", ' ', ' ');
            tempString = scanner.next();
        }
        tempFlight.setFlightID(tempString);
        System.out.printf("\n%47cEnter the origin city: ", ' ');
        tempString = scanner.next();
        tempFlight.setOrigin(tempString);
        System.out.printf("\n%47cEnter the destination city: ", ' ');
        tempString = scanner.next();
        tempFlight.setDestination(tempString);
        System.out.printf("\n%47cEnter the date: ", ' ');
        tempString = scanner.next();
        tempFlight.setDate(tempString);
        System.out.printf("\n%47cEnter the departure time in 24h format: ", ' ');
        int tempInt = App.getIntInput();
        while (tempInt > 2400 || tempInt < 0) {
            System.out.printf("%50c  \033[0;31m! Provided time is incorrect !\033[0m\n%47cEnter the departure time again: ", ' ', ' ');
            tempInt = App.getIntInput();
        }
        tempFlight.setTime(tempInt);
        System.out.printf("\n%47cEnter the airplane's seat count: ", ' ');
        tempInt = App.getIntInput();
        while (tempInt < 0) {
            System.out.printf("%50c  \033[0;31m! Provided seat is incorrect !\033[0m\n%47cEnter the seat count again: ", ' ', ' ');
            tempInt = App.getIntInput();
        }
        tempFlight.setAvailableSeats(tempInt);
        flights.addFLight(tempFlight);
        System.out.printf("%46c\033[0;32m!!    Your flight has been added      !!\033[0m", ' ');
        App.rest();
    }
}
