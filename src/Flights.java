import java.util.ArrayList;
import java.util.HashMap;

public class Flights {
    public final HashMap<String, Flight> flightsDB = new HashMap<>();

    public void addFlight(Flight flight) {
        flightsDB.put(flight.getFlightID(), flight);
    }

    public void removeFlight(String flightID) {
        flightsDB.remove(flightID);
    }

    /**
     * Checks the existence of a flight
     *
     * @param flightID Used to identify the flight
     * @return true if that flight exists
     */
    public boolean hasFlight(String flightID) {
        return flightsDB.containsKey(flightID);
    }

    /**
     * Passes the available flight
     *
     * @param flightID Used to find the flight
     * @return the flight object
     */
    public Flight getFlight(String flightID) {
        return flightsDB.get(flightID);
    }

    /**
     * Prints all the flights in a formatted form
     */
    public void showAllFlights() {
        System.out.printf("%31c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n\n", ' ');
        flightsDB.values().forEach(System.out::print);
    }

    /**
     * Prints the given flights in a formatted form
     *
     * @param flights List of the flights chosen to be printed
     */
    public void printSelectedFlights(ArrayList<Flight> flights) {
        App.clearScreen();
        System.out.printf("%31c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n\n", ' ');
        flights.forEach(System.out::print);
        if (flights.size() == 0) {
            System.out.printf("%50c%s!! No flights available !!%s", ' ', CColors.RED, CColors.RESET);
        }
    }

    /**
     * Filters all the flights
     * @param field      What is it being filtered by (1 - Destination, 2 - Origin, 3 - Date, 4 - Time, 5 - Price)
     * @param searchText Searched by this text
     * @param priceLimit Max price
     * @return List of the filtered flights
     */
    public ArrayList<Flight> filterFlights(int field, String searchText, int priceLimit) {
        ArrayList<Flight> filteredFlights = new ArrayList<>();
        if (priceLimit == -25) return filteredFlights;
        for (Flight flight : flightsDB.values()) {
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
}
