import java.util.ArrayList;
import java.util.HashMap;

public class Flights {
    public HashMap<String, Flight> flightsDB = new HashMap<>();

    public void addFLight(Flight flight) {
        flightsDB.put(flight.getFlightID(), flight);
    }

    public void removeFLight(String flightID) {
        flightsDB.remove(flightID);
    }

    public boolean hasFlight(String flightID) {
        return flightsDB.containsKey(flightID);
    }

    public Flight getFlight(String flightID) {
        return flightsDB.get(flightID);
    }

    public void showAllFlights() {
        App.clearScreen();
        System.out.printf("%31c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n\n", ' ');
        flightsDB.values().forEach(System.out::print);
    }

    public void printSelectedFlights(ArrayList<Flight> flights) {
        App.clearScreen();
        System.out.printf("%31c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats \n\n", ' ');
        flights.forEach(System.out::print);
        if (flights.size() == 0) {
            System.out.printf("%50c%s!! No flights available !!%s", ' ', CColors.RED, CColors.RESET);
        }
    }
}
