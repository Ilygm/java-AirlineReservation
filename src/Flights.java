import java.util.HashMap;

public class Flights {
    public HashMap<String, Flight> flightsDB = new HashMap<>();

    public void showFlights() {
        System.out.print(flightsDB.values());
    }

    public void addFLight(Flight flight){flightsDB.put(flight.getFlightID(), flight);}
}
